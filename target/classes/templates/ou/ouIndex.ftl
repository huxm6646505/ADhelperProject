<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>角色授权</title>
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/js/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

</head>

<body class="gray-bg">
<#--<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>OU组织管理</h2>
        <ol class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a href="/ou/index.html">OU管理</a></li>
            <li><a>OU组织管理</a></li>
        </ol>
    </div>
    <div class="col-lg-2"></div>
</div>-->

<div class="wrapper animated fadeInRight">
    <div class="container">
        <div class="row">
            <div class="col-md-4" style="margin-left:-30px;" id="menu_tree">
                <div class="ibox float-e-margins">
                   <#-- <div class="ibox-title">
                        <h5>组织目录</h5>
                    </div>-->
           <#--         <div class="ibox-tools">
                      &lt;#&ndash;  <button type="button" class="btn btn-success"  id="btn_tree">按钮</button>&ndash;&gt;
                          <i class='glyphicon glyphicon-arrow-left' id="btn_tree"></i>
                          <i class='glyphicon glyphicon-arrow-right' id="btn_tree2"></i>
                    </div>-->
                    <div class="ibox-content">
                        <ul id="ouTree" class="ztree" ></ul>
                    </div>
                </div>
            </div>
            <div class="col-md-8" style="margin-left:-30px;" id="data_table">
                <div class="ibox-content">
                    <#--<div class="btn-group" role="group">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-success" data-toggle="modal" id="btn_user">用户</button>
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-success" data-toggle="modal" id="btn_group">岗位</button>
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-success" data-toggle="modal" id="btn_ou">组织OU</button>
                        </div>
                    </div>-->
                        <div class="collapse navbar-collapse navbar-responsive-collapse">
                            <ul class="nav navbar-nav">
                                <li><a id="btn_user">用户</a></li>
                                <li><a id="btn_group">岗位</a></li>
                                <li><a id="btn_ou">组织OU</a></li>
                            </ul>
                        </div>
                    <hr>
                    <div id="user-div">
                        <div class="btn-group">
                            <button type="button" class="btn btn-success " data-toggle="modal" id="addUserBtn">
                                <i class="fa fa-plus" aria-hidden="true"></i> 添加用户
                            </button>
                        </div>
                        <hr>
                        <table
                                class="table table-striped table-bordered table-hover table-condensed" id="user-table">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>姓名</th>
                                <th>姓別</th>
                                <th>工号</th>
                                <th>登录账号</th>
                                <th>邮箱</th>
                                <th>个人电话</th>
                                <th>办公电话</th>
                                <th>部门</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div id="ou-div">
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-success" data-toggle="modal" id="addOuBtn">
                                <i class="fa fa-plus" aria-hidden="true"></i> 添加OU
                            </button>
                        </div>
                        <hr>
                        <table
                                class="table table-striped table-bordered table-hover table-condensed" id="ou-table">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>名称</th>
                                <th>部门领导</th>
                                <th>标识</th>
                                <th class="col-md-3">上级组织</th>
                                <th>编码</th>
                                <th>排序号</th>
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
</div>

<div class="modal" id="addOrUpOu" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加组织</h4>
            </div>
            <form class="form-horizontal" id="manageOuForm" action="/ou/saveOrUpdateOu.html" method="post">
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
                                <button type="button" class="btn btn-success " data-toggle="modal" id="selectOu">
                                    选择
                                </button>
                            </div>
                        </div>
                            <div class="form-group">
                            <label class="col-lg-3 control-label">部门领导：</label>
                            <div class="col-lg-8">
                                <input type="text" id="managedBy" name="managedBy" class="hide" >
                                <input type="text" id="managedByName" name="managedByName" class="form-control" >
                                <button type="button" class="btn btn-success " data-toggle="modal" id="selectManager">
                                    选择
                                </button>
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
                        <button type="submit" class="btn btn-primary">保存</button>
                    </div>
                </small>
            </form>

        </div>
        <small class="font-bold"></small>
    </div>x
    <small class="font-bold"></small>
</div>

<div class="modal" id="addOrUpUser" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <form class="form-horizontal" id="manageOuUserForm" action="/ou/saveOrUpdateUser.html" method="post">
                <small class="font-bold">
                    <div class="modal-body">
                        <input name="distinguishedName" id="uid" class="hide" value="">
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">姓名：</label>
                            <div class="col-lg-8">
                                <input type="text" name="name" placeholder="请输入用户姓名" class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">性别：</label>
                            <div class="col-lg-8 radio">
                                <label>
                                    <input type="radio" name="initials" id="optionsRadios1" value="男" checked> 男
                                </label>
                                <label>
                                    <input type="radio" name="initials" id="optionsRadios2" value="女">女
                                </label>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">工号：</label>
                            <input name="old_sAMAccountName"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" name="sAMAccountName" placeholder="请输入" class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">账号：</label>
                            <div class="col-lg-8">
                                <input type="text" name="userPrincipalName" placeholder="请输入用户姓名" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">邮箱：</label>
                            <div class="col-lg-8">
                                <input type="text" name="mail" placeholder="请输入邮箱" class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">所属部门：</label>
                            <div class="col-lg-8">
                                <input type="text" name="department" class="form-control" readonly>
                            </div>
                            <button type="button" class="btn btn-info btn-sm pull-right" id="selectDept">选择</button>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">手机号码：</label>
                            <input name="old_telephoneNumber"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" name="telephoneNumber" placeholder="请输入手机号码" class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">办公电话：</label>
                            <div class="col-lg-8">
                                <input type="text" name="physicalDeliveryOfficeName" placeholder="请输入办公电话"
                                       class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">状态：</label>
                            <div class="col-lg-8 radio">
                                <label>
                                    <input type="radio" name="userAccountControl" id="optionsRadios1" value="正常" checked> 正常
                                </label>
                                <label>
                                    <input type="radio" name="userAccountControl" id="optionsRadios2" value="禁用">禁用
                                </label>
                            </div>
                        </div>
                        <div class="form-group password">
                            <label class="col-lg-3 control-label">密码：</label>
                            <div class="col-lg-8">
                                <input type="password" id="password" name="unicodePwd" class="form-control" placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group repassword">
                            <label class="col-lg-3 control-label">重新输入密码：</label>
                            <div class="col-lg-8">
                                <input type="password" name="repassword" id="repassword" class="form-control" placeholder="请重新输入密码">
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
<#include "../common/userList.ftl">
</body>

<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/plugins/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script src="/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Data Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>

<script src="/js/pages/ou/index.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<script src="/js/pages/common/userList.js"></script>
<!-- 第三方插件 -->
<script src="/js/plugins/pace/pace.min.js"></script>

</html>