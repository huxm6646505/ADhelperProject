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
                    <h5>登录管理</h5>
                </div>
                <div class="ibox-content">
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example logininfo-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>用户名</th>
                            <th>访问IP</th>
                            <th>浏览器版本</th>
                            <th>登录时间</th>
                            <th>退出时间</th>
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
<script src="/js/pages/logging/loginfo.js"></script>
</body>
</html>
