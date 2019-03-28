<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>用户管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/js/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/js/plugins/bootstrap-fileinput/css/fileinput.css" rel="stylesheet"/>


</head>
<body class="gray-bg">
<div class="wrapper  animated fadeInRight">
    <div class="row">
        <div class="">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户管理</h5>
                </div>
                <div class="ibox-content">
                    <div id="tool">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="addUserButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 添加用户
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="exportUserButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 用户导出
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="importUserButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 导入用户
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="updateUserButton">
                            <i class="fa fa-plus" aria-hidden="true"></i> 用户更新
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal" id="pwdUserButton">
                            <i class="fa fa-plus" aria-hidden="true"></i>密码修改
                        </button>
                    </div>
                    </div>
                    <input id="updateUserInfo" type="hidden" value=" ${updateUserInfo?string('true','false')}">
                    <input id="passwUpdate" type="hidden" value=" ${passwUpdate?string('true','false')}">
                    <input id="accountState" type="hidden" value=" ${accountState?string('true','false')}">
                    <input id="userMagTool" type="hidden" value=" ${userMagTool?string('true','false')}">
                    <hr>
                    <div class="form-group ">
                        <label class="control-label">所属公司:</label>
                         <select id="ou" class="combobox">
                     </select>
                        <label class="control-label">所属部门:</label>
                        <select id="dep" class="combobox">
                        </select>
                        <label class="control-label">状态:</label>
                        <select id="userState" class="combobox">
                            <option></option>
                            <option value='66048'>正常</option>
                            <option value='66050'>禁用</option>
                        </select>
                    </div>
                    <table
                            class="table table-striped table-bordered table-hover dataTables-example user-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>姓名</th>
                            <th>姓別</th>
                            <th>工号</th>
                            <th>登录账号</th>
                            <th>所在部门</th>
                            <th>邮箱</th>
                            <th>个人电话</th>
                            <th>办公电话</th>
                            <th>部门</th>
                            <th>状态</th>
                            <th>密码修改时间</th>
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
<div class="modal" id="exportUser" tabindex="-1" role="dialog"
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
                        <button type="button" class="btn btn-success" data-toggle="modal" id="exportButton">
                            <i class="fa fa-plus" aria-hidden="true"></i>导出
                        </button>
                        <div id="downUserExcel">
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

<#include "addUser.ftl">
<#include "updatePwd.ftl">
<#include "../common/ouTree.ftl">
<#include "../common/uploadFile.ftl">
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
<script src="/js/plugins/validate/messages_zh.min.js"></script>
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/js/pages/aduser/index.js"></script>
<script src="/js/pages/common/ouTree.js"></script>
<script src="/js/pages/common/uploadFile.js"></script>
<!--bootstrap-fileinput-->
<script src="/js/plugins/bootstrap-fileinput/js/fileinput.js"></script>
<script src="/js/plugins/bootstrap-fileinput/js/locales/zh.js"></script>

</html>
