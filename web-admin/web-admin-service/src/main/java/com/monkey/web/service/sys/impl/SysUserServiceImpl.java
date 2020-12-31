package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.BeanCopyUtils;
import com.monkey.common.utils.Detect;
import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.entity.SysRoleUser;
import com.monkey.web.entity.SysUser;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import com.monkey.web.request.UserRequest;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.service.sys.SysUserService;
import com.monkey.web.utils.CmsSysUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/23 18:05
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Value("${monkey.web.default-password:123456}")
    private String defaultPassword;

    private final SysUserRepository sysUserRepository;

    private final SysRoleUserMenuService sysRoleUserMenuService;

    public SysUserServiceImpl(SysUserRepository sysUserRepository, SysRoleUserMenuService sysRoleUserMenuService) {
        this.sysUserRepository = sysUserRepository;
        this.sysRoleUserMenuService = sysRoleUserMenuService;
    }

    @Override
    public SysUser selectByUserId(Integer userId) {
        return this.sysUserRepository.findById(userId).orElse(null);
    }

    @Override
    public SysUser findEnableUserByUsername(String username) {
        return this.sysUserRepository.findByUsernameAndDelFlag(username, Byte.parseByte(IsValid.valid.getValue()));
    }

    @Override
    public void updateById(SysUser sysUser) {
        this.sysUserRepository.save(sysUser);
    }

    @Override
    public Page<SysUser> getUserListWithPage(UserRequest request) {
        List<Integer> userIds = null;
        if (Objects.nonNull(request.getRoleId())) {
            //查询有该角色的用户
            userIds = this.sysRoleUserMenuService.getUserIdsByRoleId(request.getRoleId());
        }
        final var ids = userIds;
        var specification = new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                var predicates = new ArrayList<Predicate>();
                if (Detect.isNotNullOrEmpty(request.getUsername())) {
                    predicates.add(builder.equal(root.get("username").as(String.class), request.getUsername()));
                }
                if (Detect.isNotNullOrEmpty(request.getRealName())) {
                    predicates.add(builder.like(root.get("realName").as(String.class), request.getRealName()));
                }
                if (Detect.isNotNullOrEmpty(ids)) {
                    predicates.add(builder.in(root.get("id")).value(ids));
                }
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        var sysUser = new SysUser();
        BeanCopyUtils.beanCopy(request, sysUser);
        var page = request.getPage() <= 0 ? 0 : request.getPage() - 1;
        var pageable = PageRequest.of(page, request.getLimit(), Sort.by(Sort.Direction.DESC, "updateTime"));
        return this.sysUserRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateUser(SysUser user, Integer[] roleIds) {
        var username = CmsSysUserUtil.getCurrentUser().getUsername();
        var date = new Date();
        if (Objects.isNull(user.getId())) {
            //新增
            var password = CmsSysUserUtil.getMD5(Detect.isNullOrEmpty(user.getPassword()) ? defaultPassword : user.getPassword(), user.getUsername());
            user.setPassword(password);
            user.setCreateTime(date);
            user.setCreateUser(username);
            user.setUpdateTime(date);
            user.setUpdateUser(username);
            this.sysUserRepository.save(user);
        } else {
            //更新
            var sysUser = this.selectByUserId(user.getId());
            var password = Detect.isNullOrEmpty(user.getPassword()) ? sysUser.getPassword() : CmsSysUserUtil.getMD5(user.getPassword(), user.getUsername());
            BeanCopyUtils.beanCopyWithIgnore(user, sysUser);
            sysUser.setPassword(password);
            user.setUpdateTime(date);
            user.setUpdateUser(username);
            this.sysUserRepository.saveAndFlush(sysUser);
        }
        var roleIdList = Arrays.asList(roleIds);
        //查询用户已有角色ids
        var existsRoleIds = this.sysRoleUserMenuService.getRolesByUserId(user.getId());
        //要删除的用户角色
        var delRoleIds = existsRoleIds.stream().filter(roleId -> !roleIdList.contains(roleId)).collect(Collectors.toList());
        //要添加的用户角色
        var addRoleUserList = roleIdList.stream().filter(roleId -> !existsRoleIds.contains(roleId)).map(roleId -> {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(user.getId());
            return roleUser;
        }).collect(Collectors.toList());

        if (Detect.isNotNullOrEmpty(delRoleIds)) {
            this.sysRoleUserMenuService.deleteRoleUserByRoleIds(delRoleIds);
        }

        if (Detect.isNotNullOrEmpty(addRoleUserList)) {
            this.sysRoleUserMenuService.batchInsertRoleUser(addRoleUserList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delUser(String ids) {
        if (Detect.isNullOrEmpty(ids)) {
            return;
        }
        var userIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        this.sysUserRepository.deleteByIds(userIds);
        //删除中间表数据
        this.sysRoleUserMenuService.deleteRoleUserByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPass(Integer id, String newPwd) {
        var sysUser = this.selectByUserId(id);
        newPwd = CmsSysUserUtil.getMD5(newPwd, sysUser.getUsername());
        if (sysUser.getPassword().equals(newPwd)) {
            log.error("new password:{} equals old password:{}", newPwd, sysUser.getPassword());
            throw WebException.throwException(WebErrorCode.NEW_PWD_EQUALS_OLD);
        }
        sysUser.setPassword(newPwd);
        sysUser.setUpdateUser(CmsSysUserUtil.getCurrentUser().getUsername());
        sysUser.setUpdateTime(new Date());
        this.sysUserRepository.saveAndFlush(sysUser);
    }

    @Override
    public Boolean checkUserName(String uname) {
        return Objects.nonNull(this.sysUserRepository.findByUsername(uname));
    }
}
