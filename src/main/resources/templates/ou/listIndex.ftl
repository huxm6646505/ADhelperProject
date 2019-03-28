<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>OU列表管理</title>
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
<div class="wrapper  animated fadeInRight">
    <div class="row">
        <div class="">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>OU管理列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addOuButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加OU
                        </button>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label class="control-label">所属集团:</label>
                        <select id="group" class="combobox">
                        </select>
                        <label class="control-label">所属公司:</label>
                         <select id="ou" class="combobox">
                         </select>
                        <label class="control-label">所属部门:</label>
                        <select id="dep" class="combobox">
                        </select>
                    </div>
                    <table
                            class="table table-striped table-bordered table-hover table-condensed" id="ouList-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>标识</th>
                            <th>排序号</th>
                            <th>编码</th>
                            <th>名称</th>
                            <th>部门领导</th>
                            <th class="col-md-4">上级组织</th>
                            <th>描述</th>
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

<div class="modal" id="addOrUpOuList" tabindex="-1" role="dialog"
     aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加组织</h4>
            </div>
          <#--  <form class="form-horizontal" id="manageOuListForm"  action="/ou/saveOrUpdateOuList.html" method="post">-->
         <form class="form-horizontal" id="manageOuListForm" method="post">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="uid" id="uid" class="hide" value="">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">组织名：</label>
                            <div class="col-lg-8">
                                <input type="text" id="name" name="name" placeholder="组织名" class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">上级组织：</label>
                            <div class="col-lg-8">
                                <input type="text" id="pid" name="pid" class="form-control" readonly>
                                <button type="button" class="btn btn-success " data-toggle="modal" id="selectPOu">
                                    选择
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">部门领导：</label>
                            <div class="col-lg-8">
                                <input type="text" id="managedBy" name="managedBy" class="hide" >
                                <input type="text" id="managedByName" name="managedByName" class="form-control" readonly>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success" data-toggle="modal" id="selectManager">选择</button>
                                    <button type="button" class="btn btn-success" data-toggle="modal" id="clear_select">清空</button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">编码：</label>
                            <input name="old_postalCode"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" id="postalCode" name="postalCode" placeholder="编码" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">排序号：</label>
                            <input name="old_street"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" id="street" name="street" placeholder="排序号" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">描述：</label>
                            <div class="col-lg-8">
                                <select id="description" name="description" class= "form-control" >
                                    <option value=""> 请选择</option >
                                    <option value="公司"> 公司</option >
                                    <option value="部门"> 部门</option >
                                    <option value="集团"> 集团</option >
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="PostData()">保存</button>
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
</body>
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
<#--<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="//cdn.bootcss.com/iCheck/1.0.2/icheck.min.js"></script>-->
<script src="/js/pages/ou/listIndex.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<script src="/js/pages/common/userList.js"></script>

</html>
