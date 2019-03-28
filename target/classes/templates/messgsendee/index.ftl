<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>日志管理</title>
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
    <link href="/js/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/css/plugins/datapicker/bootstrap-datepicker.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>通知管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>通知管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>通知管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addmessgsendeeButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加通知人
                        </button>
                    </div>
                    <hr>
                    <table class="table table-striped table-bordered table-hover dataTables-example messgsendee-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>UID</th>
                            <th>OU组织</th>
                            <th>接收人</th>
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

<div class="modal " id="messgsendee_detail" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">详情</h4>
            </div>
            <form class="form-horizontal" id="manageMessgsendeeForm" method="post" action="/mesgsendee/saveOrUpdateMessgSendee.html">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="id" id="id" class="hide" value="">
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">OU组织：</label>
                            <div class="col-lg-8">
                                <input type="text" name="deptou" class="form-control" readonly>
                                <button type="button" class="btn btn-success " data-toggle="modal" id="selectOu">
                                    选择
                                </button>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">接收人：</label>
                            <div class="col-lg-8">
                                <input type="text" name="sendee" class="form-control" >
                                <button type="button" class="btn btn-success" data-toggle="modal" id="selectSendee">选择</button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">保存</button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    </div>
                </small>
            </form>
        </div>
        <small class="font-bold"></small>
    </div>
    <small class="font-bold"></small>
</div>

<#include "../common/ouTree.ftl">
<#include "../common/userList.ftl">

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
<script src="/js/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<!-- Page-Level Scripts -->
<script src="/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/js/pages/messgsendee/index.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<script src="/js/pages/common/userList.js"></script>
</body>
</html>
