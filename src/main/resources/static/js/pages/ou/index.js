function onUserCheck() {
    var treeObj = $.fn.zTree.getZTreeObj("ouTree");
    var nodes = treeObj.getSelectedNodes(true);
    var ou = "";
    if (nodes.length > 0) {
        ou = nodes[0].id;
    }
    // $.fn.dataTable.ext.errMode =  'throw' //不显示任何错误信息
    $('#user-table').dataTable({
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
            "url": "/ou/getUsers",
            "type": 'POST',
            "data": function (d) {
                var param = {};
                param.draw = d.draw;
                param.start = d.start;
                param.length = d.length;
                param.search = d.search.value;
                param.ou = ou;
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
                var datas = data.data;
                for (var i = 0, l = datas.length; i < l; i++) {
                    if (!datas[i].hasOwnProperty("initials")) {
                        datas[i].initials = "";
                    }
                    if (!datas[i].hasOwnProperty("mail")) {
                        datas[i].mail = "";
                    }
                    if (!datas[i].hasOwnProperty("telephoneNumber")) {
                        datas[i].telephoneNumber = "";
                    }
                    if (!datas[i].hasOwnProperty("physicalDeliveryOfficeName")) {
                        datas[i].physicalDeliveryOfficeName = "";
                    }
                }
                return str.data;
            }
        },
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "initials"},
            {"data": "sAMAccountName"},
            {"data": "userPrincipalName"},
            {"data": "mail"},
            {"data": "telephoneNumber"},
            {"data": "physicalDeliveryOfficeName", "visible": false},
            {"data": "department", "visible": false},
            {"data": "userAccountControl"},
            {"data": null}
        ],
        "columnDefs": [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
            },
            {
                targets: -1,
                defaultContent: "<div class='btn-group'>" +
                "<button id='editUserRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-edit'></i></button>" +
                /*   "<button id='delUserRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-edit'></i></button>"+*/
                "</div>"
            }
        ],
        //在每次table被draw完后调用
        "fnDrawCallback": function () {
            var api = this.api();
            //获取到本页开始的条数
            var startIndex = api.context[0]._iDisplayStart;
            api.column(0).nodes().each(function (cell, i) {
                cell.innerHTML = startIndex + i + 1;
            })
        },
        "language": {
            "lengthMenu": "每页 _MENU_ 条记录",
            "processing": " 数据加载中...",
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
        },
        //    "dom": '<"top"lif>t<"bottom"rp><"clear">'

    });
    $('#user-div').show();
}

function onOuCheck() {
    var treeObj = $.fn.zTree.getZTreeObj("ouTree");
    var nodes = treeObj.getSelectedNodes(true);
    var ou = "";
    if (nodes.length > 0) {
        ou = nodes[0].id;
    }
    // $.fn.dataTable.ext.errMode =  'throw' //不显示任何错误信息
    $('#ou-table').dataTable({
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
            "url": "/ou/getOus",
            "type": 'POST',
            "data": function (d) {
                var param = {};
                param.draw = d.draw;
                param.start = d.start;
                param.length = d.length;
                param.search = d.search.value;
                param.ou = ou;
                param.rootOU = 'OU=大族集团';
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
                var datas = data.data;
                for (var i = 0, l = datas.length; i < l; i++) {
                    if (!datas[i].hasOwnProperty("managedBy")) {
                        datas[i].managedBy = "";
                    }
                    if (!datas[i].hasOwnProperty("managedByName")) {
                        datas[i].managedByName = "";
                    }
                    if (!datas[i].hasOwnProperty("postalCode")) {
                        datas[i].postalCode = "";
                    }
                    if (!datas[i].hasOwnProperty("street")) {
                        datas[i].street = "";
                    }
                    if (!datas[i].hasOwnProperty("description")) {
                        datas[i].description = "";
                    }
                }
                return str.data;
            }
        },
        "columns": [
            {"data": null},
            {"data": "name"},
            {"data": "managedByName"},
            {"data": "uid", "visible": false},
            {"data": "pid"},
            {"data": "postalCode"},
            {"data": "street", "visible": false},
            {"data": "description", "visible": false},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
            },
            {
                targets: -1,
                defaultContent: "<div class='btn-group'>" +
                "<button id='oueditRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-edit'></i></button>" +
                "<button id='oudisRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-remove-circle'></i></button>" +
                "</div>"
            }
        ],
        //在每次table被draw完后调用
        fnDrawCallback: function () {
            var api = this.api();
            //获取到本页开始的条数
            var startIndex = api.context[0]._iDisplayStart;
            api.column(0).nodes().each(function (cell, i) {
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
    $('#ou-div').show();
}

function addOrUpOu(setting) {
    $("#manageOuForm")[0].reset();
    if (setting == undefined) {
        var treeObj = $.fn.zTree.getZTreeObj("ouTree");
        var nodes = treeObj.getSelectedNodes(true);
        var ou = "";
        if (nodes.length > 0) {
            ou = nodes[0].id;
        }
        $("#pid").val(ou);
        $("#addOrUpOu").modal('show');
        return;
    }
    //  console.log(setting)
    //  $("#uid").val(setting.uid);
    $("#pid").val(setting.pid);
    $("#manageOuForm").find("input[name='uid']").val(setting.uid);
    $("#manageOuForm").find("input[name='name']").val(setting.name);
    $("#manageOuForm").find("input[name='postalCode']").val(setting.postalCode);
    $("#manageOuForm").find("input[name='old_postalCode']").val(setting.postalCode);
    $("#manageOuForm").find("input[name='street']").val(setting.street);
    $("#manageOuForm").find("input[name='old_street']").val(setting.street);
    $("#description").find("option[value='" + setting.description + "']").prop("selected", true);
    $("#manageOuForm").find("input[name='managedByName']").val(setting.managedByName);
    $("#manageOuForm").find("input[name='managedBy']").val(setting.managedBy);
    $("#addOrUpOu").modal('show');
}

function addOrUpUser(setting) {
    $("#manageOuUserForm")[0].reset();
    if (setting == undefined) {
        var treeObj = $.fn.zTree.getZTreeObj("ouTree");
        var nodes = treeObj.getSelectedNodes(true);
        var ou = "";
        if (nodes.length > 0) {
            ou = nodes[0].id;
        }
        $("#manageOuUserForm").find("input[name='department']").val(ou);
        $("#addOrUpUser").modal('show');
        return;
    }
    //console.log(setting)
    $("#manageOuUserForm").find("input[name='distinguishedName']").val(setting.distinguishedName);
    $("#manageOuUserForm").find("input[name='name']").val(setting.name);
    // $("#manageUserForm").find("input[name='initials']").val(setting.initials);
    $(":radio[name='initials'][value='" + setting.initials + "']").prop("checked", "checked");
    $("#manageOuUserForm").find("input[name='sAMAccountName']").val(setting.sAMAccountName);
    $("#manageOuUserForm").find("input[name='old_sAMAccountName']").val(setting.sAMAccountName);
    $("#manageOuUserForm").find("input[name='userPrincipalName']").val(setting.userPrincipalName);
    $("#manageOuUserForm").find("input[name='mail']").val(setting.mail);
    $("#manageOuUserForm").find("input[name='telephoneNumber']").val(setting.telephoneNumber);
    $("#manageOuUserForm").find("input[name='old_telephoneNumber']").val(setting.telephoneNumber);
    $("#manageOuUserForm").find("input[name='physicalDeliveryOfficeName']").val(setting.physicalDeliveryOfficeName);
    $("#manageOuUserForm").find("input[name='department']").val(setting.department);
    $(":radio[name='userAccountControl'][value='" + setting.userAccountControl + "']").prop("checked", "checked");
    $("#addOrUpUser").modal('show');
}

function valid() {
    $("#manageOuUserForm").validate({
        rules: {
            name: {
                required: true
            },
            sAMAccountName: {
                required: true,
                remote: {
                    type: "post",
                    url: "/aduser/validUserByQuery",
                    data: {
                        sAMAccountName: function () {
                            return $("#manageOuUserForm").find("input[name='sAMAccountName']").val();
                        }
                    },
                    // dataType: "html",
                    dataFilter: function (data, type) {
                        var old_sAMAccountName = $("#manageOuUserForm").find("input[name='old_sAMAccountName']").val();
                        var sAMAccountName = $("#manageOuUserForm").find("input[name='sAMAccountName']").val();
                        if (old_sAMAccountName != null && old_sAMAccountName == sAMAccountName) {
                            return true;
                        } else if (old_sAMAccountName == null || (old_sAMAccountName != null && old_sAMAccountName != sAMAccountName)) {
                            if (data == "false")
                                return true;
                            else
                                return false;
                        }
                    }
                }
            },
            mail: {
                required: true,
                email: true
            },
            department: {
                required: true
            },
            telephoneNumber: {
                required: true,
                remote: {
                    type: "post",
                    url: "/aduser/validUserByQuery",
                    data: {
                        telephoneNumber: function () {
                            return $("#manageOuUserForm").find("input[name='telephoneNumber']").val();
                        }
                    },
                    dataFilter: function (data, type) {
                        var old_telephoneNumber = $("#manageOuUserForm").find("input[name='old_telephoneNumber']").val();
                        var telephoneNumber = $("#manageOuUserForm").find("input[name='telephoneNumber']").val();
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
            unicodePwd: {
                required: ($('.password').is(":visible")) ? true : false,
                minlength: 8
            },
            repassword: {
                required: ($('.repassword').is(":visible")) ? true : false,
                minlength: 8,
                equalTo: "#password"
            }
        },
        messages: {
            name: {
                required: "姓名不能为空"
            },
            sAMAccountName: {
                required: "工号不能为空",
                remote: "工号不可用"
            },
            email: {
                required: "邮箱不能为空",
                email: "邮箱地址不正确"
            },
            department: {
                required: "请输入部门",
            },
            telephoneNumber: {
                required: "手机号码不能为空",
                remote: "手机号码不可用"
            },
            unicodePwd: {
                required: "请输入密码",
                minlength: "密码长度不能小于 8 个字母"
            },
            repassword: {
                required: "请输入密码",
                minlength: "密码长度不能小于 8 个字母",
                equalTo: "两次密码输入不一致"
            }
        }
    });
}

function validOu() {
    $("#manageOuForm").validate({
        rules: {
            name: {
                required: true
            },
            postalCode: {
                required: true,
                remote: {
                    type: "post",
                    url: "/ou/validOuByQuery",
                    data: {
                        postalCode: function () {
                            return $("#manageOuForm").find("input[name='postalCode']").val();
                        }
                    },
                    // dataType: "html",
                    dataFilter: function (data, type) {
                        var old_postalCode = $("#manageOuForm").find("input[name='old_postalCode']").val();
                        var postalCode = $("#manageOuForm").find("input[name='postalCode']").val();
                        if (old_postalCode != null && old_postalCode == postalCode) {
                            return true;
                        } else if (old_postalCode == null || (old_postalCode != null && old_postalCode != postalCode)) {
                            if (data == "false")
                                return true;
                            else
                                return false;
                        }
                    }
                }
            },
            street: {
                required: true//,
                /* remote: {
                 type: "post",
                 url: "/ou/validOuByQuery",
                 data: {
                 street: function() {
                 return  $("#manageOuForm").find("input[name='street']").val();
                 }
                 },
                 dataFilter: function(data, type) {
                 var old_street = $("#manageOuForm").find("input[name='old_street']").val();
                 var street = $("#manageOuForm").find("input[name='street']").val();
                 if (old_street != null && old_street == street) {
                 return true;
                 } else if (old_street == null || (old_street != null && old_street != street)) {
                 if (data == "false")
                 return true;
                 else
                 return false;
                 }
                 }
                 }*/
            },
            description: {
                required: true
            }
        },
        messages: {
            name: {
                required: "姓名不能为空"
            },
            postalCode: {
                required: "编码不能为空",
                remote: "编码不可用"
            },

            street: {
                required: "排序号不能为空"/*,
                 remote: "排序号不可用"*/
            },
            description: {
                required: "描述不能为空"
            },
        }
    });
}


$(document).ready(function () {

    $.validator.setDefaults({
        highlight: function (e) {
            $(e).closest(".form-group").removeClass("has-success")
                .addClass("has-error")
        },
        success: function (e) {
            e.closest(".form-group").removeClass("has-error").addClass(
                "has-success")
        },
        errorElement: "span",
        errorPlacement: function (e, r) {
            e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent()
                    .parent().parent() : r.parent())
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    })
    $('#user-div').hide();
    $('#ou-div').hide();
    loadTreeNode("/ou/loadOuTree", "ouTree");
    /*var treeObj = $.fn.zTree.getZTreeObj("ouTree");
     var nodes = treeObj.getNodes();
     for (var i = 0; i < nodes.length; i++) { //设置节点展开
     treeObj.expandNode(nodes[i], true, false, true);
     }*/

    $('#btn_user').click(function () {
        $('#ou-div').hide();
        onUserCheck();
        $('#user-table tbody').delegate('#editUserRow', 'click', function () {
            $('.password').hide();
            $('.repassword').hide();
            var datas = $("#user-table").DataTable().row($(this).parents("tr")).data();
            addOrUpUser(datas);
            valid();
        });
    });
    $('#btn_ou').click(function () {
        $('#user-div').hide();
        onOuCheck();
        $('#ou-table tbody').delegate('#oueditRow', 'click', function () {
            var datas = $("#ou-table").DataTable().row($(this).parents("tr")).data();
            addOrUpOu(datas);
            validOu();
        });

        $('#ou-table tbody').delegate('#oudisRow', 'click', function () {
            var datas = $("#ou-table").DataTable().row($(this).parents("tr")).data();
            swal({
                title: "您确定要禁用此组织吗",
                text: "禁用后可以到禁用组织管理模块操作恢复！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "禁用",
                cancelButtonText: "取消",
                confirmButtonClass: 'btn btn-success',
                cancelButtonClass: 'btn btn-danger',
                closeOnConfirm: false,
                closeOnCancel: false
            }, function (isConfirm) {
                if (isConfirm === true) {
                    var json = {
                        pid: 'OU=禁用,DC=hansholdings,DC=com',
                        uid: datas.uid,
                        name: datas.name
                    };
                    $.post('/ou/saveOrUpdateOu', json, function () {
                        swal("禁用成功", "已成功禁用！", "success");
                    })
                } else {
                    swal("删除取消", "您取消了删除操作！", "error");
                }
            });
        });
    });
    $('#addOuBtn').click(function () {
        addOrUpOu();
        validOu();
    });

    /*    $('#btn_tree').click(function () {
     $('#menu_tree').removeClass('col-md-3').addClass("col-md-2");
     $('#data_table').removeClass('col-md-9').addClass("col-md-10");
     });

     $('#btn_tree2').click(function () {
     $('#menu_tree').removeClass('col-md-2').addClass("col-md-3");
     $('#data_table').removeClass('col-md-10').addClass("col-md-9");
     });*/

    $('#addUserBtn').click(function () {
        $('.password').show();
        $('.repassword').show();
        addOrUpUser();
        valid();
    });

    $('#selectOu').click(function () {
        $("#ouTreeModal").modal('show');
        loadTreeNode("/ou/loadOuTree", "ouSelectTree");
    });

    //部门领导选择
    $('#selectManager').click(function () {
        $("#userList").modal('show');
        loadUserList();
        $('#user-list-table tbody').on('dblclick', 'tr', function () {
            var rawdata=$("#user-list-table").DataTable().row($(this)).data();
            $("#manageOuForm").find("input[name='managedByName']").val(rawdata.name);
            $("#manageOuForm").find("input[name='managedBy']").val(rawdata.distinguishedName);
            $("#userList").modal('hide');
        });
    });

    $('#selectDept').click(function () {
        $("#ouTreeModal").modal('show');
        loadTreeNode("/ou/loadOuTree", "ouSelectTree");
    });
    $('#treeSubmit').click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("ouSelectTree");
        var nodes = treeObj.getSelectedNodes(true);
        var ou = "";
        if (nodes.length > 0) {
            ou = nodes[0].id;
        }
        $('#pid').val(ou);
        $('#manageOuUserForm').find("input[name='department']").val(ou);
    });

    $("#manageOuUserForm").find("input[name='sAMAccountName']").on('input', function () {
        var userPrincipalName = $("#manageOuUserForm").find("input[name='sAMAccountName']").val() + "@hansholdings.com";
        $("#manageOuUserForm").find("input[name='userPrincipalName']").val(userPrincipalName);
    })


});
