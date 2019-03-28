<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>用户管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="/css/plugins/iCheck/skins/all.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link href="/css/plugins/datapicker/bootstrap-datepicker.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>用户管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>用户管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addAdminButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加用户
                        </button>
                    </div>
                    <hr>
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example admin-table">
                        <thead>
                        <tr>
                            <th>唯一标识</th>
                            <th>用户名</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>生日</th>
                            <th>最后登录时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list admins as admin>
                        <tr>
                            <td>${admin.id}</td>
                            <td>${(admin.user.username)!''}</td>
                            <td>${(admin.member.name)!''}</td>
                            <td>
                            	<#if admin.member.sex??>
                            		<#if admin.member.sex == 1>
                            			男
                            		<#elseif admin.member.sex == 2>
                            			女
                            		</#if>
                            	</#if>
                            </td>
                            <td>${(admin.member.birthday?date)!''}</td>
                            <td>${admin.loginTime!''}</td>
                            <td>
                                    <#if admin.locked == 0>
                                        <span class="label label-danger">无效</span>
                                    <#elseif admin.locked == 1>
                                        <span class="label label-success">有效</span>
                                    </#if>
                            </td>
                            <td>
                                <a jsonStr="{'id':'${admin.id}','locked':'${(admin.locked)!''}','userName':'${(admin.user.username)!''}'}"
                                        xhref="/admin/index" class="admin-remove" title="修改状态"><i
                                        class="glyphicon glyphicon-remove-circle "></i>
                                </a>
                                <a jsonStr="{'id':'${admin.id}','name':'${(admin.member.name)!''}','sex':'${(admin.member.sex)!''}','birthday':'${(admin.member.birthday?date)!''}','username':'${(admin.user.username)!''}','manager':'${(admin.user.manager)!''}'}"
                                   class="admin-update"
                                   title="修改操作">
                                    <i class="glyphicon glyphicon-edit"></i>
                                </a>
                               	<a href="/admin/oauth?id=${admin.id}" class="role-oauth" title="用户授权">
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

<#include "addAdmin.ftl">
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
<script src="/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/js/plugins/validate/messages_zh.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/js/plugins/datapicker/bootstrap-datepicker.zh-CN.js"></script>
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/pages/admin/index.js"></script>
</body>
</html>
