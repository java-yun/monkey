<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>monkey</title>
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/layui/css/layui.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/font-awesome/css/font-awesome.min.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/app.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/themes/default.css" media="all" id="skin" kit-skin />
  <style>
    <#--前端无聊美化ing-->
    .layui-footer{background-color: #2F4056;}
    .layui-side-scroll{border-right: 3px solid #009688;}
  </style>
</head>

<body class="kit-theme">
<div class="layui-layout layui-layout-admin kit-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">monkey数据后台</div>
    <div class="layui-logo kit-logo-mobile"></div>
    <div class="layui-hide-xs">
    <ul class="layui-nav layui-layout-left kit-nav">
      <#list topMenus as child>
      	<#if child_index == 0>
            <li class="layui-nav-item layui-this" >
                <a class="change-mode" data-module-id="${child.code}" href="#"><i aria-hidden="true" class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
            </li>
        <#else>
            <li class="layui-nav-item" >
                <a class="change-mode" data-module-id="${child.code}" href="#"><i aria-hidden="true" class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
            </li>
        </#if>
      </#list>
    </div>
    <ul class="layui-nav layui-layout-right kit-nav">
      <li class="layui-nav-item">
        <a href="#">
          <i class="layui-icon">&#xe63f;</i> 皮肤
        </a>
        <dl class="layui-nav-child skin">
          <dd><a href="#" data-skin="default" style="color:#393D49;"><i class="layui-icon">&#xe658;</i> 默认</a></dd>
          <dd><a href="#" data-skin="orange" style="color:#ff6700;"><i class="layui-icon">&#xe658;</i> 橘子橙</a></dd>
          <dd><a href="#" data-skin="green" style="color:#00a65a;"><i class="layui-icon">&#xe658;</i> 春天绿</a></dd>
          <dd><a href="#" data-skin="pink" style="color:#FA6086;"><i class="layui-icon">&#xe658;</i> 少女粉</a></dd>
          <dd><a href="#" data-skin="blue.1" style="color:#00c0ef;"><i class="layui-icon">&#xe658;</i> 天空蓝</a></dd>
          <dd><a href="#" data-skin="red" style="color:#dd4b39;"><i class="layui-icon">&#xe658;</i> 枫叶红</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item">
        <a href="#">
          <#assign currentUser = Session["currentUser"]>
          ${currentUser.username}
        </a>
        <dl class="layui-nav-child">
          <dd><a class="user-detail" data-module-id="${currentUser.id}" href="javascript:;">基本资料</a></dd>
          <dd><a class="user-uppw" data-module-id="${currentUser.id}" href="javascript:;">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${re.contextPath}/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
    </ul>
  </div>

<#macro tree data start end>
  <#if (start=="start")>
  <div class="layui-side layui-nav-tree layui-bg-black kit-side">
  <div class="layui-side-scroll">
    <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
  <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
  </#if>
  <#list data as child>
	    <#if child.children?size gt 0 >
		      <li class="layui-nav-item">
		        <a class="" href="javascript:;"><i aria-hidden="true" class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
		        <dl class="layui-nav-child">
		          <@tree data=child.children start="" end=""/>
		        </dl>
		      </li>
	    <#else>
		       <dd class="layui-nav-item layui-nav-itemed">
		          <a href="javascript:;" kit-target data-options="{url:'${re.contextPath}/${child.url}',icon:'${child.icon}',title:'${child.name}',id:'${child.id}'}">
		          <i class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
		       </dd>
	    </#if>
  </#list>
  <#if (end=="end")>
  </ul>
  </div>
  </div>
  </#if>
</#macro>
<@tree data=leftMenus start="start" end="end"/>
  <div class="layui-body" id="container">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i> 请稍等...</div>
  </div>

  <div class="layui-footer">
  <!-- 底部固定区域 -->
  2020 &copy; CMS管理系统
</div>
</div>
<script src="${re.contextPath}/plugin/plugins/layui/layui.js"></script>
<script src="${re.contextPath}/plugin/js/system/main/base.js"></script>
<script src="${re.contextPath}/plugin/js/system/main/main.js"></script>
</body>

</html>
