(function () {

    window.getRole = function (userId) {
        const url = getCtxPath() + '/user/showRoleList';
        $.getJSON(url, {id: userId}, function (result) {
            console.log(result);
            const dataList = result.object;
            let roleArray = [];
            honglu.forEach(dataList, function (item) {
                roleArray.push(item.roleName || item.remark || '');
            })
            $('#role' + userId).text(roleArray.join(","))
        })
        return ""
    }

    const pageConfig = {
        table: {
            tools: {
                edit: {
                    width: "900",   // 默认：1100px
                    height: "650",  // 默认450px
                    title: "修改",
                    url: getCtxPath() + '/user/toUpdateUser?id={{id}}&detail=false'
                },
                del: {
                    invoke: function (obj) {
                        debugger;
                        const msg = '确定删除[<label style="color: #00AA91;">' + obj.realName + '</label>]?';
                        const url = getCtxPath() + '/user/del';
                        honglu.dialog.confirm(msg, function () {
                            honglu.ajax.post(url, {id: obj.id}, function (result) {
                                if (result.code == '000000') {
                                    honglu.dialog.alert("删除成功！", function () {
                                        layui.table.reload('dataList');
                                    });
                                } else {
                                    honglu.dialog.msg(result.msg);
                                }
                            });
                        });
                    }
                },
                changePwd:{
                    title: "修改密码" ,
                    url: getCtxPath() + '/user/goRePass?id={{id}}'
                },
            },


            render: {
                url: getCtxPath() + '/user/showUserList',
                cols: [[
                    {type: 'checkbox', fixed: true, width: '80'},
                    {field: true, fixed: true, title: '序号', width: '60', templet: '<div>{{d.LAY_TABLE_INDEX+1}}</div>', align: 'center'},
                    {field: 'username', title: '用户名', width: '10%', align: 'center'},
                    {field: 'realName', title: '真实姓名', width: '10%', align: 'center'},
                    {field: 'mobile', title: '手机号', width: '10%', align: 'center'},
                    {field: 'roleName', title: '角色', width: '10%', align: 'center'},
                    {field: 'age', title: '年龄', width: '10%', align: 'center'},
                    {field: 'email', title: '邮箱', width: '10%', align: 'center'},
                    {field: 'createBy', title: '创建人', width: '10%', align: 'center'},
                    {field: 'createDate', title: '创建时间', width: '10%', align: 'center', templet: '<div>{{ honglu.util.dateFormat(d.createDate) }}</div>'},
                    {field: 'updateDate', title: '更新时间', width: '10%', align: 'center', templet: '<div>{{ honglu.util.dateFormat(d.updateDate) }}</div>'},
                    {field: 'right', fixed: 'right', title: '操作', width: '20%', align: 'center', toolbar: "#barDemo"}
                ]]
            }

        },
        form: {
            on: []
        },
        active: {
            add: function () {
                honglu.dialog.open({
                    width: "900",   // 默认：1100px
                    height: "650",  // 默认450px
                    title: "新增",
                    url: getCtxPath() + '/user/showAddUser'
                });
            },
            delete: function () {
                const checkStatus = layui.table.checkStatus('dataList'),
                    data = checkStatus.data;
                if (!data.length) {
                    honglu.dialog.msg("请勾选用户");
                    return false;
                }
                let ids = [];
                honglu.forEach(data, function (item) {
                    ids.push(item.id);
                })
                honglu.dialog.confirm("确定删除勾选的" + ids.length + "个用户？", function () {
                    const url = getCtxPath() + '/user/del'
                    honglu.ajax.post(url, {id: ids.join(",")}, function (result) {
                        if (result.code == '000000') {
                            honglu.dialog.alert("删除成功！", function () {
                                layui.table.reload('dataList');
                            });
                        } else {
                            honglu.dialog.msg(result.msg);
                        }
                    })
                })
            }
        }
    }
    if (honglu.adapter.layui.init) {
        honglu.adapter.layui.init(pageConfig);
    }

})();