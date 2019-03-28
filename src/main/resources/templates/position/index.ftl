<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>岗位管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link hrel="shortcut icon" href="favicon.ico">
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
<#--<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>岗位管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>岗位管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>-->
<div class="wrapper  animated fadeInRight">
    <div class="row">
        <div class="">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>岗位管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addPositionButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加岗位
                        </button>
                    </div>
                    <hr>
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example " id="position-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>名称</th>
                            <th>标识</th>
                           <th>操作</th>
                        </tr>
                        </thead>

                     <tbody>

                      </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="addOrUpPosition" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加岗位</h4>
            </div>
            <form class="form-horizontal" id="managePositionForm" action="/position/saveOrUpdate.html" method="post">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="uid" id="uid" class="hide" value="">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">岗位名：</label>
                            <div class="col-lg-8">
                                <input type="text" id="name" name="name" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                        <button type="submit" class="btn btn-primary">保存</button>
                    </div>
                </small>
            </form>

        </div>
        <small class="font-bold"> </small>
    </div>
    <small class="font-bold"> </small>
</div>
<#include "showUser.ftl">
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
<script src="/js/pages/position/index.js"></script>
</body>
</html>
