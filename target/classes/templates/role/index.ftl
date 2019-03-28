<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>角色管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="//cdn.bootcss.com/iCheck/1.0.2/skins/all.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>角色管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>角色管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>角色管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addRoleButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加角色
                        </button>
                    </div>
                    <hr>
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example role-table">
                        <thead>
                        <tr>
                            <th>唯一标识</th>
                            <th>角色名</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list roles as role>
                        <tr>
                            <td>${role.id}</td>
                            <td>${role.name}</td>
                            <td>${(role.createdDate)!''}</td>
                            <td>
                                <a xhref="/role/remove?id=${role.id}" class="role-remove" title="删除操作">
                                	<i class="glyphicon glyphicon-remove"></i>
                                </a>
                                <a jsonStr="{'id':'${role.id}','name':'${role.name}'}" class="role-update" title="修改操作">
                                    <i class="glyphicon glyphicon-edit"></i>
                                </a>
                                <a href="/role/oauth?id=${role.id}" class="role-oauth" title="角色授权">
                                	<i class="glyphicon glyphicon-cog"></i>
                                </a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "addRole.ftl">
<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/js/plugins/jeditable/jquery.jeditable.js"></script>
<!-- Data Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<!-- 自定义js -->
<script src="/js/content.js?v=1.0.0"></script>
<!-- 第三方插件 -->
<script src="/js/plugins/pace/pace.min.js"></script>
<!-- Page-Level Scripts -->
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="//cdn.bootcss.com/iCheck/1.0.2/icheck.min.js"></script>
<script src="/js/pages/role/index.js"></script>
</body>
</html>
