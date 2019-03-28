function showUserView(setting) {
    $("#manageUserForm")[0].reset();
    if (setting == undefined) {
        $("#addUser").modal('show');
        $("#manageUserForm").find("input[name='department']").val($("#dep").val()?$("#dep").val():$("#ou").val());
        return;
    }
   // console.log(setting)
    $("#manageUserForm").find("input[name='distinguishedName']").val(setting.distinguishedName);
    $("#manageUserForm").find("input[name='name']").val(setting.name);
    // $("#manageUserForm").find("input[name='initials']").val(setting.initials);
    $(":radio[name='initials'][value='" + setting.initials + "']").prop("checked", "checked");
    $(":radio[name='userAccountControl'][value='" + setting.userAccountControl + "']").prop("checked", "checked");
    $("#manageUserForm").find("input[name='sAMAccountName']").val(setting.sAMAccountName);
    $("#manageUserForm").find("input[name='old_sAMAccountName']").val(setting.sAMAccountName);
    $("#manageUserForm").find("input[name='userPrincipalName']").val(setting.userPrincipalName);
    $("#manageUserForm").find("input[name='mail']").val(setting.mail);
    $("#manageUserForm").find("input[name='telephoneNumber']").val(setting.telephoneNumber);
    $("#manageUserForm").find("input[name='old_telephoneNumber']").val(setting.telephoneNumber);
    $("#manageUserForm").find("input[name='physicalDeliveryOfficeName']").val(setting.physicalDeliveryOfficeName);
    $("#manageUserForm").find("input[name='department']").val(setting.department);
    $("#addUser").modal('show');
}

function showUserPwdView(setting) {
    $("#managePwdForm")[0].reset();

   // console.log(setting)
    $("#idx2").val(setting.distinguishedName);

    $("#upPwdUser").modal('show');
}

function valid(){
    $("#manageUserForm").validate({
        rules : {
            name :{
                required:true
            },
            sAMAccountName :{
                required:true,
                remote: {
                    type: "post",
                    url: "/aduser/validUserByQuery",
                    data: {
                        sAMAccountName: function() {
                            return  $("#manageUserForm").find("input[name='sAMAccountName']").val();
                        }
                    },
                    // dataType: "html",
                    dataFilter: function(data, type) {
                        var old_sAMAccountName=$("#manageUserForm").find("input[name='old_sAMAccountName']").val();
                        var sAMAccountName=$("#manageUserForm").find("input[name='sAMAccountName']").val();
                        if(old_sAMAccountName!=null&&old_sAMAccountName==sAMAccountName){
                            return true;
                        }else if(old_sAMAccountName==null||(old_sAMAccountName!=null&&old_sAMAccountName!=sAMAccountName)){
                            if (data == "false")
                                return true;
                            else
                                return false;
                        }
                    }
                }
            },
            mail :{
                required:true,
                email:true
            },
            department :{
                required:true
            },
            telephoneNumber :{
                required:true,
                remote: {
                    type: "post",
                    url: "/aduser/validUserByQuery",
                    data: {
                        telephoneNumber: function() {
                            return  $("#manageUserForm").find("input[name='telephoneNumber']").val();
                        }
                    },
                    dataFilter: function(data, type) {
                        var old_telephoneNumber = $("#manageUserForm").find("input[name='old_telephoneNumber']").val();
                        var telephoneNumber = $("#manageUserForm").find("input[name='telephoneNumber']").val();
                        if (old_telephoneNumber != null && old_telephoneNumber == telephoneNumber) {
                            return true;
                        } else if (old_telephoneNumber == null || (old_telephoneNumber != null && old_telephoneNumber != telephoneNumber)) {
                            if (data == "false")
                                return true;
                            else
                                return false;
                        }
                    }
                }
            },
            unicodePwd : {
                required : ($('.password').is(":visible"))?true:false,
                minlength : 8,
                regexPassword :true
            },
            repassword : {
                required : ($('.repassword').is(":visible"))?true:false,
                minlength : 8,
                regexPassword :true,
                equalTo : "#password"
            }
        },
        messages : {
            name: {
                required: "姓名不能为空"
            },
            sAMAccountName: {
                required: "工号不能为空",
                remote: "工号不可用"
            },
            email:{
                required:"邮箱不能为空",
                email:"邮箱地址不正确"
            },
            department: {
                required: "请输入部门",
            },
            telephoneNumber: {
                required: "手机号码不能为空",
                remote: "手机号码不可用"
            },
            unicodePwd : {
                required : "请输入密码",
                minlength : "密码长度不能小于 8 个字母",
                regexPassword: '密码至少包一个字母长度至少8位',
            },
            repassword : {
                required : "请输入密码",
                minlength : "密码长度不能小于 8 个字母",
                regexPassword: '密码至少包一个字母长度至少8位',
                equalTo : "两次密码输入不一致"
            }
        }
    });
}
function loadUserTable(data) {

    $('.user-table').dataTable({
        "processing": true,
        "serverSide": true,//启用服务器端分页
        "destroy": true,
        "pageLength": 10,
        "ordering": false,
        // "renderer": "bootstrap",//渲染样式：Bootstrap和jquery-ui
        "pagingType": "simple_numbers",//分页样式：simple,simple_numbers,full,full_numbers
        "autoWidth": true,
        "stateSave": false,//保持翻页状态，和comTable.fnDraw(false);结合使用
        "searching": true,//datatables默认搜索
        "ajax": {
            "url": "/aduser/getUsers",
            "type": 'POST',
            "data": function (d) {
                var param = {};
                param.draw = d.draw;
                param.start = d.start;
                param.length = d.length;
                param.search=d.search.value;
                param.ou=data;
                param.userAccountControl=$("#userState").val()
                return param;//自定义需要传递的参数。
            },
            "dataSrc": function (str) {
                var data;
                if (typeof str === 'object') {
                    data = str;
                }
                else {
                    data = eval("(" + str + ")");
                }
                var datas=data.data;
                for(var i=0,l=datas.length;i<l;i++){
                    if(!datas[i].hasOwnProperty("initials")){
                        datas[i].initials="";
                    }
                    if(!datas[i].hasOwnProperty("mail")){
                        datas[i].mail="";
                    }
                    if(!datas[i].hasOwnProperty("telephoneNumber")){
                        datas[i].telephoneNumber="";
                    }
                    if(!datas[i].hasOwnProperty("physicalDeliveryOfficeName")){
                        datas[i].physicalDeliveryOfficeName="";
                    }
                   // datas[i].departName=datas[i].department.substring(3, datas[i].department.indexOf(","));
                    datas[i].departName=datas[i].department.replace(",DC=hansholdings,DC=com","").replace(/OU=/g,"").replace(/,/g,"_");
                }
                return str.data;
            }
        },
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "initials"},
            {"data": "sAMAccountName"},
            {"data": "userPrincipalName","visible": false},
            {"data": "departName","width":"200px"},
            {"data": "mail","width":"150px"},
            {"data": "telephoneNumber"},
            {"data": "physicalDeliveryOfficeName"},
            {"data": "department","visible": false},
            {"data": "userAccountControl"},
            {"data": "pwdLastSet","visible": false},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
            },
            {
                targets: -1,
                defaultContent: "<div class='btn-group'>"+
                "<button id='user-update' class='btn btn-primary btn-sm "+($("#updateUserInfo").val().indexOf("false")>=0?'hidden':'')+"' type='button' title='修改操作'><i class='glyphicon glyphicon-edit'></i></button>"+
                "<button id='user-upPwd' class='btn btn-primary btn-sm "+($("#passwUpdate").val().indexOf("false")>=0?'hidden':'')+"' type='button' title='密码修改'><i class='glyphicon glyphicon-lock'></i></button>"+
                "<button id='user-forbid' class='btn btn-primary btn-sm "+($("#accountState").val().indexOf("false")>=0?'hidden':'')+"' type='button' title='关闭用户'><i class='glyphicon glyphicon-remove-circle'></i></button>"+
                "</div>"
            }
        ],
        //在每次table被draw完后调用
        fnDrawCallback: function(){
            var api = this.api();
            //获取到本页开始的条数
            var startIndex= api.context[0]._iDisplayStart;
            api.column(0).nodes().each(function(cell, i) {
                cell.innerHTML = startIndex + i + 1;
            })
        },
        "language": {
            "lengthMenu": "每页 _MENU_ 条记录",
            "processing": "数据加载中...",
            "paginate": {
                "first": "首页",
                "last": "末页",
                "previous": "上一页",
                "next": "下一页"
            },
            "zeroRecords": "没有找到符合条件的数据",
            //  "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
            "info": "显示 _START_ 到 _END_ 项, 共 _TOTAL_ 项",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_ 条记录过滤)",
            "search": "搜索："
        }

    });
    $('.user-table tbody').delegate('#user-update', 'click', function () {
        $('.password').hide();
        $('.repassword').hide();
        var datas = $(".user-table").DataTable().row( $(this).parents("tr") ).data();
        showUserView(datas);
        valid();
    });
    $('.user-table tbody').delegate('#user-upPwd', 'click', function () {
        var datas = $(".user-table").DataTable().row( $(this).parents("tr") ).data();
        showUserPwdView(datas);
    });
    $('.user-table tbody').delegate('#user-forbid', 'click', function () {
        var datas = $(".user-table").DataTable().row($(this).parents("tr")).data();
        var userAccountControl=(datas.userAccountControl==='禁用')?'启用':'禁用';
        swal({
            title: "您确定要"+userAccountControl+"此账号吗",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: userAccountControl,
            cancelButtonText: "取消",
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            if (isConfirm === true) {
                var json = {
                    distinguishedName: datas.distinguishedName,
                    name: datas.name,
                    sAMAccountName: datas.sAMAccountName,
                    userPrincipalName: datas.userPrincipalName,
                    mail: datas.mail,
                    telephoneNumber: datas.telephoneNumber,
                    initials: datas.initials,
                    userAccountControl: (datas.userAccountControl==='禁用')?'正常':'禁用',
                    department: datas.department
                };
                $.post('/aduser/saveOrUpdate', json, function () {
                    swal(userAccountControl+"成功", "已成功"+userAccountControl+"!", "success");
                })
            } else {
                swal(userAccountControl+"取消", "您取消了"+userAccountControl+"操作！", "error");
            }
        });
    });
}

function loadOuSelect(ouid,rootOU,description){
    $.ajax({
        type:"post",
        url:"/ou/getOUBydes",
        data: {'rootOU': rootOU,'description':description},
        success:function(msg){
            var objMsg=msg;
            var txt="<option></option>";
            for(var i=0,l=objMsg.length;i<l;i++){
                if(objMsg[i].distinguishedName!=""){
                    txt += "<option value='"+objMsg[i].distinguishedName+"'>"+objMsg[i].postalCode+" "+objMsg[i].name+"</option>";
                }
            }
            $("#" +ouid).html(txt);
            //  $('#ou').selectpicker('refresh');
        },
        error:function(msg){
            alert("获取数据异常，请联系管理员！");
        }
    });
}

function submitData() {
    if ($("#manageUserForm").valid()) {
        $.ajax({
            type: "POST",
            url: "/aduser/saveOrUpdate.html",
            data: $('#manageUserForm').serialize(),
            success: function (msg) {
                if (msg === 'true') {
                    $("#addUser").modal('hide');
                    loadUserTable($("#dep").val() != "" ? $("#dep").val() : $("#ou").val());
                }
            },
            error: function () {
            }
        });
        return false;
    }
}

function refreshUploadfile(service) {
    $("#response").empty();
    $("#excelFile").fileinput("clear");
    $("#excelFile").fileinput("reset");
    $('#excelFile').fileinput('refresh');
    $('#excelFile').fileinput('enable');
    $('#excelFile').fileinput( "refresh",{
        uploadExtraData: {
            service: service
        }
    });
}

$(document).ready(function() {
   InitExcelFile('adUserUpload');
    loadUserTable();
    loadOuSelect('ou','OU=大族集团,DC=hansholdings,DC=com','公司');
    //公司下拉框监听
    $("#ou").change(function(){
        loadUserTable($("#ou").val());
        loadOuSelect('dep',  $("#ou").val(),'部门');
    });
    //部门下拉监听
    $("#dep").change(function(){
        loadUserTable($("#dep").val());
    });
    //状态
    $("#userState").change(function(){
        loadUserTable($("#dep").val()!=""?$("#dep").val():$("#ou").val());
    });
    // 添加用户
    $("#addUserButton").click(function() {
        $('.password').show();
        $('.repassword').show();

        //  $("#manageUserForm").data('validator').resetForm();
        showUserView();
        //$("#manageUserForm").validate().resetForm();
        valid();
    });

    //导入用户
    $("#importUserButton").click(function() {
        refreshUploadfile('adUserUpload');
        $("#import").modal('show');
        $("#excelModul").attr('href','/exportFile/TestUser.xls');
    });

    //用户信息更新
    $("#updateUserButton").click(function() {
        refreshUploadfile('updateUserUpload');
        $("#import").modal('show');
        $("#excelModul").attr('href','/exportFile/updateUser.xls');
    });

    //用户密码修改
    $("#pwdUserButton").click(function() {
        refreshUploadfile('pwdUserUpload');
        $("#import").modal('show');
        $("#excelModul").attr('href','/exportFile/updateUserPwd.xls');
    });

    //导出用户
    $("#exportUserButton").click(function() {
        $("#exportUser").modal('show');
        $("#downUserExcel").empty();
    });
    $("#exportButton").click(function() {
        $.ajax({
            type: "post",
            url: "/FileUpload/exportFile",
            data: {"ou":($("#dep").val()!=null&&$("#dep").val()!='')?$("#dep").val():$("#ou").val(),"userAccountControl":$("#userState").val(),"search":$("[type='search']").val()},
            async : false,
            dataType: "json",
            success: function (data ,textStatus, jqXHR)
            {
                if("200"==data.code){
                    var html;
                    html = "<div style='float:left;'><p style='text-align:left'>内容：  " + data.desp + "<a  href='/exportFile/"+ data.exportFnm + "'>详情下载。</a></p></div>"
                    $(html).appendTo($('#downUserExcel'));
                    return true;
                }else{
                    alert("不合法！错误信息如下："+data.errorMsg);
                    return false;
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败！");
            }
        });
    });

    //选择部门
    $('#selectDept').click(function() {
        $("#ouTreeModal").modal('show');
        loadTreeNode("/ou/loadOuTree", "ouSelectTree");
    });

    $('#treeSubmit').click(function() {
        var treeObj=$.fn.zTree.getZTreeObj("ouSelectTree");
        var nodes=treeObj.getSelectedNodes(true);
        var ou="";
        if (nodes.length > 0) {
            ou=nodes[0].id;
        }
        $('#manageUserForm').find("input[name='department']").val(ou);
    });

    if($("#userMagTool").val().indexOf("false")>=0){
        $('#tool').hide();
    }
    jQuery.validator.addMethod("regexPassword", function(value, element) {
        // var pPattern = /^.*(?=.{8,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/;
        //  var pPattern = /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;
        var pPattern = /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[a-zA-Z]).*$/;
        return this.optional(element) || pPattern.test(value);
    }, "至少一个字母");

    $.validator.setDefaults({
        highlight : function(e) {
            $(e).closest(".form-group").removeClass("has-success")
                .addClass("has-error")
        },
        success : function(e) {
            e.closest(".form-group").removeClass("has-error").addClass(
                "has-success")
        },
        errorElement : "span",
        errorPlacement : function(e, r) {
            e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent()
                    .parent().parent() : r.parent())
        },
        errorClass : "help-block m-b-none",
        validClass : "help-block m-b-none"
    });

    $("#managePwdForm").validate({
        rules : {
            "unicodePwd" : {
                required : true,
                minlength : 8,
                regexPassword :true
            },
            "repassword" : {
                required : true,
                minlength : 8,
                equalTo : "#password1",
                regexPassword :true
            }
        },
        messages : {
            unicodePwd : {
                required : "请输入密码",
                minlength : "密码长度不能小于 8 个字母",
                regexPassword: '密码至少包含一个字母，长度至少8位'
            },
            repassword : {
                required : "请输入密码",
                minlength : "密码长度不能小于 8 个字母",
                equalTo : "两次密码输入不一致",
                regexPassword: '密码至少包含一个字母，长度至少8位'
            }
        }
    });

    /* $("#manageUserForm").find("input[name='sAMAccountName']").blur(function(){
     var userPrincipalName=$("#manageUserForm").find("input[name='sAMAccountName']").val()+"@hansholdings.com";
     $("#manageUserForm").find("input[name='userPrincipalName']").val(userPrincipalName);
     })*/

    $("#manageUserForm").find("input[name='sAMAccountName']").on('input',function(){
        var userPrincipalName=$("#manageUserForm").find("input[name='sAMAccountName']").val()+"@hansholdings.com";
        $("#manageUserForm").find("input[name='userPrincipalName']").val(userPrincipalName);
    })
});