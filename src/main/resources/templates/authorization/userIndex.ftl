<!doctype html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<title>用户授权</title>
<link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="/css/animate.css" rel="stylesheet">
<link href="/css/style.css?v=4.1.0" rel="stylesheet">
	<link href="/js/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/iCheck/1.0.2/skins/all.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" href="/css/plugins/bootstrap-table/bootstrap-table.min.css">

<script>
	var _adminId = "${admin.id}";
</script>
</head>

<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<h2>用户权限管理</h2>
			<ol class="breadcrumb">
				<li><a href="index.html">主页</a></li>
				<li><a href="/admin/index.html">用户管理</a></li>
				<li><a>用户权限管理</a></li>
			</ol>
		</div>
		<div class="col-lg-2"></div>
	</div>

	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="ibox">
				<div class="ibox-title">
					<h5>用户权限信息</h5>
				</div>
				<div class="ibox-content">
					<div class="row">
						<form class="form-horizontal" action="/admin/oauthsubmit"
							method="post" id="adminOauthForm">
							<input type="hidden" value="${admin.id}" name="id" />
							<div class="form-group">
								<div class="form-group ">
									<label class="col-lg-3 control-label">用户名称：</label>
									<div class="col-lg-8">
										<span type="text" class="form-control">${admin.user.username}</span>
									</div>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="form-group ">
									<label class="col-lg-3 control-label">角色选择：</label>
									<div class="col-lg-8">
										<#list roles as role> 
											<label class="radio-inline i-checks"> 
												<input type="checkbox" value="${role.id}" 
												<#list admin.roles as r>
													<#if r.id == role.id>
														checked
													</#if>
												</#list>
												 name="roleIds"> 
												<i></i> ${role.name}
											</label> 
										</#list>
									</div>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
                            <div>
								<div id="toolbar" class="btn-group">
									<button id="btn_add" type="button" class="btn btn-default">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
									</button>
									<button id="btn_edit" type="button" class="btn btn-default">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
									</button>
									<button id="btn_delete" type="button" class="btn btn-default">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
									</button>
                           	 	</div>
								<table id="tb_ouroles"></table>
							</div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button type="submit" class="btn btn-primary">保存</button>
									<button type="button" class="btn btn-white"
										onclick="history.go(-1);">返回</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
    <div class="modal" id="addOuDn" tabindex="-1" role="dialog"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content animated bounceIn">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">选择组织</h4>
                </div>
                <form class="form-horizontal" id="oudnForm" onsubmit="return PostOuDnData()"  method="post">
                    <small class="font-bold">
                        <div class="modal-body">
                            <input name="id" id="ouid" class="hide" value="">
                            <input name="admin_id" id="admin_id" class="hide" value="">
							<div class="form-group">
                           		 <label class="col-lg-3 control-label">OU组织：</label>
                           		 <div class="col-lg-8">
                                	<input type="text" name="oudn" class="form-control" readonly>
                           		 </div>
                          		  <button type="button" class="btn btn-info btn-sm pull-right" id="selectOuDn" >选择</button>
                       		 </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">部门领导：</label>
                                <div class="col-lg-8">
                                    <input type="text" name="managedByName" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">编码：</label>
                                <div class="col-lg-8">
                                    <input type="text" name="postalCode" class="form-control" readonly>
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
	<#include "../common/ouTree.ftl">
	<#include "../common/ouList.ftl">
</body>

<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/pages/admin/oauth.js"></script>
<!-- 第三方插件 -->
<script src="/js/plugins/pace/pace.min.js"></script>
<script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<script src="/js/pages/common/ouList.js"></script>
</html>