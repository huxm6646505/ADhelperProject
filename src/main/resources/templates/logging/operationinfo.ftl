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
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/css/plugins/datapicker/bootstrap-datepicker.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>日志管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>日志管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>操作管理</h5>
                </div>
                <div class="ibox-content">
                    <table class="table table-striped table-bordered table-hover">
                        <tr>
                            <td><label class="control-label">操作用户：</label></td>
                            <td>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="userName">
                                </div>
                            </td>
                            <td><label class="control-label">业务类型：</label></td>
                            <td>
                                <select id="serviceStype" class="combobox">
                                    <option ></option>
                                    <option value="OU">OU</option>
                                    <option value="ADUSER">ADUSER</option>
                                    <option value="LANDRAYINTERFACE">LANDRAYINTERFACE</option>
                                </select>
                            </td>
                            <td><label class="control-label">操作类型：</label></td>
                            <td>
                                <select id="operateStype" class="combobox">
                                    <option ></option>
                                    <option value="新增信息">新增信息</option>
                                    <option value="修改信息">修改信息</option>
                                    <option value="密码修改">密码修改</option>
                                    <option value="员工离职">员工离职</option>
                                    <option value="员工入职">员工入职</option>
                                    <option value="员工调职">员工调职</option>
                                    <option value="员工更新">员工更新</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label class="control-label">开始日期：</label></td>
                            <td>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" class="form-control" id="minOperationTime">
                                </div>
                            </td>
                            <td><label class="control-label">结束日期：</label></td>
                            <td>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" class="form-control" id="maxOperationTime">
                                </div>
                            </td>

                            <td colspan="2">
                                <div class="btn-group" role="group">
                                    <button type="button" class="btn btn-success" data-toggle="modal" id="btnSearch">
                                        <i class="fa fa-search" aria-hidden="true"></i> 搜索
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" class="btn btn-success" data-toggle="modal" id="btnReset">
                                        <i class="fa fa-undo" aria-hidden="true"></i> 重置
                                    </button>
                                </div>
                                <div class="btn-group" role="group">
                                    <button type="button" class="btn btn-success" data-toggle="modal" id="btnExport">
                                        <i class="fa fa-file-excel-o" aria-hidden="true"></i> 导出
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="table table-striped table-bordered table-hover dataTables-example opeartioninfo-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>操作用户</th>
                            <th>操作类型</th>
                            <th>业务类型</th>
                            <th>修改信息</th>
                            <th>操作时间</th>
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

<div class="modal " id="operation_detail" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">详情</h4>
            </div>
            <form class="form-horizontal" id="manageOperationForm" method="">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="distinguishedName" id="distinguishedName" class="hide" value="">
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">用户：</label>
                            <div class="col-lg-8">
                                <input type="text" name="userName" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">操作类型：</label>
                            <div class="col-lg-8">
                                <input type="text" name="operateStype" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">服务类型：</label>
                            <div class="col-lg-8">
                                <input type="text" name="serviceStype" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">修改前内容：</label>
                            <div class="col-lg-8">
                                <textarea class="form-control" rows="12" name="originalValue" readonly></textarea>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">修改后内容：</label>
                            <div class="col-lg-8">
                                <textarea class="form-control" rows="12" name="currentValue" readonly></textarea>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">操作时间：</label>
                            <div class="col-lg-8">
                                <input type="text" name="operationTime" class="form-control" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    </div>
                </small>
            </form>
        </div>
        <small class="font-bold"></small>
    </div>
    <small class="font-bold"></small>
</div>

<div class="modal" id="exportOpLog" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">数据导出</h4>
            </div>
            <small class="font-bold">
                <div class="modal-body">
                    <div id="downOpLogExcel">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                </div>
            </small>
        </div>
        <small class="font-bold"> </small>
    </div>
    <small class="font-bold"> </small>
</div>

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
<script src="/js/pages/logging/operationinfo.js"></script>
</body>
</html>
