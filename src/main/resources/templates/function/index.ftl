<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>目录菜单管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>菜单管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>菜单管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>目录菜单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addFunctionButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加分类
                        </button>
                    </div>
                    <hr>
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example function-table">
                        <thead>
                        <tr>
                            <th>唯一标识</th>
                            <th>名称</th>
                            <th>上级名称</th>
                            <th>授权</th>
                            <th>类型</th>
                            <th>url地址</th>
                            <th>是否有效</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list functions as function>
                        <tr>
                            <td>${function.id}</td>
                            <td>${function.name}</td>
                            <td>${(function.parent.name)!''}</td>
                            <td>${function.shiroPermission}</td>
                            <td><#if function.type==1>分类
                            <#elseif function.type==2>菜单
                            <#elseif function.type == 3>链接
                            <#elseif function.type == 4>按钮
                            <#else>其他
                            </#if></td>
                            <td>${function.url!''}</td>
                            <td>${function.active?string('有效','无效')}</td>
                            <td>
                                <a xhref="/function/remove?id=${function.id}" class="function-remove" title="删除操作"><i
                                        class="glyphicon glyphicon-remove"></i>
                                </a>
                                <a jsonStr="{'id':'${function.id}','name':'${function.name}','type':'${function.type}','url':'${function.url!''}','pid':'${(function.parent.id)!''}','pname':'${(function.parent.name)!''}','shiroPermission':'${function.shiroPermission}'}"
                                   class="function-update"
                                   title="修改操作">
                                    <i class="glyphicon glyphicon-edit"></i>
                                </a>
                                <a jsonStr="{'type':'${function.type + 1}','pid':'${function.id}','pname':'${function.name}'}"
                                   class="function-addChild"
                                   title="添加子类"><i class="fa fa-plus"></i>
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

<#include "addFunction.ftl">
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
<script src="/js/pages/function/index.js"></script>
</body>
</html>
