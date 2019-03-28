<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LDAP系统配置</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link ref="//cdn.bootcss.com/zTree.v3/3.5.24/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>
<body class="gray-bg">
<#--<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>LDAP系统配置</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a>LDAP系统配置</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>-->
<div class="wrapper animated fadeInRight">
    <div class="row">
        <div class="">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>LDAP系统配置</h5>
                </div>
                <div class="ibox-content">
                    <hr>
                    <form class="form-horizontal" id="manageLdapForm" action="/ldap/saveOrUpdateLdap.html" method="post">
                        <small class="font-bold">
                            <div class="modal-body">
                                <div class="form-group ">
                                    <label class="col-lg-3 control-label">服务器URL：</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="ldapURL" placeholder="请输入服务器地址"  class="form-control" value=${ldap.ldapURL}>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label class="col-lg-3 control-label">账号：</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="username" placeholder="请输入账号"  class="form-control" value=${ldap.username}>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">密码：</label>
                                    <div class="col-lg-8">
                                        <input type="password"  name="password" class="form-control" value=${ldap.password}>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">保存</button>
                            </div>
                        </small>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/js/plugins/jeditable/jquery.jeditable.js"></script>
<!-- 自定义js -->
<script src="/js/content.js?v=1.0.0"></script>
<!-- 第三方插件 -->
<script src="/js/plugins/pace/pace.min.js"></script>
<script
        src="//cdn.bootcss.com/zTree.v3/3.5.24/js/jquery.ztree.all.min.js"></script>
<!-- Page-Level Scripts -->
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="//cdn.bootcss.com/iCheck/1.0.2/icheck.min.js"></script>

</html>
