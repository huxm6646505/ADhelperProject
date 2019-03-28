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

<div class="wrapper wrapper-content animated fadeInRight">

    <div class="wrapper animated fadeInRight">
        <div class="row">
            <div class="">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>详情</h5>
                    </div>
                    <div class="ibox-content">
                        <hr>
                        <form class="form-horizontal" id="adupinfoForm" action="" method="get">
                                    <div class="form-group ">
                                        <label class="col-lg-3 control-label">操作用户</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="userName"  class="form-control" readonly value=${adupinfo.userName} >
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-lg-3 control-label">操作类型</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="operateStype"  class="form-control" readonly value=${adupinfo.operateStype} >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label">服务类型：</label>
                                        <div class="col-lg-8">
                                            <input type="text"  name="serviceStype" class="form-control" readonly value=${adupinfo.serviceStype} >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label">修改前内容：</label>
                                        <div class="col-lg-8">
                                            <textarea class="form-control" rows="15" name="originalValue"  readonly>${adupinfo.originalValue}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-3 control-label">修改后内容：</label>
                                        <div class="col-lg-8">
                                            <textarea class="form-control" rows="20" name="currentValue" readonly >${adupinfo.currentValue}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label class="col-lg-3 control-label">操作时间：</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="operationTime" class="form-control" value=${adupinfo.operationTime} readonly >
                                        </div>
                                    </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
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
<script>
        $(document).ready(function() {
            var currentValue= $("#adupinfoForm").find("textarea[name='currentValue']").val();
            var originalValue= $("#adupinfoForm").find("textarea[name='originalValue']").val();
            $("#adupinfoForm").find("input[name='operationTime']").val('${adupinfo.operationTime}');
            $("#adupinfoForm").find("textarea[name='currentValue']").val(currentValue?currentValue.replace(/","/g,"\"\n\n\"").replace(/(^\{*)|(\}*$)/g,""):"");
            $("#adupinfoForm").find("textarea[name='originalValue']").val(originalValue?originalValue.replace(/","/g,"\"\n\n\"").replace(/(^\{*)|(\}*$)/g,""):"");
        })
</script>
</body>
</html>
