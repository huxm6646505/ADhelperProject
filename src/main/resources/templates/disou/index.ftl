<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>禁用管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
<#--   <link rel="shortcut icon" href="favicon.ico">-->
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/js/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


</head>
<body class="gray-bg">
<div class="wrapper animated fadeInRight">
    <div class="row">
        <div class="">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>禁用管理</h5>
                </div>
                <div class="ibox-content">
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example disou-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>名称</th>
                            <th>部门领导</th>
                            <th>标识</th>
                            <th>上级组织</th>
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
<div class="modal" id="revertOu" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <form class="form-horizontal" id="manageDisOuForm" action="/ou/saveOrUpdateOu.html" method="post">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="uid" id="uid" class="hide" value="">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">组织名：</label>
                            <div class="col-lg-8">
                                <input type="text"  name="name" placeholder="组织名" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">上级组织：</label>
                            <div class="col-lg-8">
                                <input type="text"  name="pid" class="form-control" readonly>
                                <button type="button" class="btn btn-success " data-toggle="modal" id="selectOu">
                                    选择
                                </button>
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
        <small class="font-bold"></small>
    </div>
    <small class="font-bold"></small>
</div>

<#include "../common/ouTree.ftl">
</body>
<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/js/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script src="/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Data Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>

<script src="/js/pages/disou/index.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<!-- 第三方插件 -->
<script src="/js/plugins/pace/pace.min.js"></script>

</html>
