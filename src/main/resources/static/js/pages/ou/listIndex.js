
function onOuList(data) {
    $('#ouList-table').dataTable({
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
                param.ou=data;
               // param.rootOU = 'OU=大族集团';
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
            {"data": "uid", "visible": false},
            {"data": "street"},
            {"data": "postalCode"},
            {"data": "name"},
            {"data": "managedByName"},
            {"data": "pid"},
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
    $('#ouList-table tbody').delegate('#oueditRow', 'click', function () {
        var datas = $("#ouList-table").DataTable().row($(this).parents("tr")).data();
        addOrUpOuList(datas);
        validOu();
    });
    $('#ouList-table tbody').delegate('#oudisRow', 'click', function () {
        var datas = $("#ouList-table").DataTable().row($(this).parents("tr")).data();
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
}

function validOu() {
    $("#manageOuListForm").validate({
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
                            return $("#manageOuListForm").find("input[name='postalCode']").val();
                        }
                    },
                    // dataType: "html",
                    dataFilter: function (data, type) {
                        var old_postalCode = $("#manageOuListForm").find("input[name='old_postalCode']").val();
                        var postalCode = $("#manageOuListForm").find("input[name='postalCode']").val();
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
                required: true
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

function addOrUpOuList(setting) {
    $("#manageOuListForm")[0].reset();
    if (setting == undefined) {
        $("#addOrUpOuList").modal('show');
        $("#manageOuListForm").find("input[name='pid']").val($("#dep").val()?$("#dep").val():($("#ou").val()?$("#ou").val():$("#group").val()));
        return;
    }

    $("#pid").val(setting.pid);
    $("#manageOuListForm").find("input[name='uid']").val(setting.uid);
    $("#manageOuListForm").find("input[name='name']").val(setting.name);
    $("#manageOuListForm").find("input[name='postalCode']").val(setting.postalCode);
    $("#manageOuListForm").find("input[name='old_postalCode']").val(setting.postalCode);
    $("#manageOuListForm").find("input[name='street']").val(setting.street);
    $("#manageOuListForm").find("input[name='old_street']").val(setting.street);
    $("#description").find("option[value='" + setting.description + "']").prop("selected", true);
    $("#manageOuListForm").find("input[name='managedByName']").val(setting.managedByName);
    $("#manageOuListForm").find("input[name='managedBy']").val(setting.managedBy);
    $("#addOrUpOuList").modal('show');
}

function PostData() {
    if($("#manageOuListForm").valid()) {
        $.ajax({
            type: "POST",
            url: "/ou/saveOrUpdateOuList.html",
            data: $('#manageOuListForm').serialize(),
            success: function (msg) {
                if (msg === 'true') {
                    $("#addOrUpOuList").modal('hide');
                    onOuList($("#dep").val() != "" ? $("#dep").val() : ($("#ou").val() != "" ? $("#ou").val() : $("#group").val()));
                }
            },
            error: function () {
            }
        });
        return false;
    }
}


$(document).ready(function() {
    onOuList();
    loadOuSelect('group','OU=大族集团,DC=hansholdings,DC=com','集团');
    //集团下拉框监听
    $("#group").change(function(){
        onOuList($("#group").val());
        loadOuSelect('ou',  $("#group").val(),'公司');
    });
    //公司下拉框监听
    $("#ou").change(function(){
        onOuList($("#ou").val());
        loadOuSelect('dep',  $("#ou").val(),'部门');
    });
    //部门下拉监听
    $("#dep").change(function(){
        onOuList($("#dep").val());
    });

    $('#addOuButton').click(function () {
        addOrUpOuList();
        validOu();
    });

    $('#selectPOu').click(function () {
        $("#ouTreeModal").modal('show');
        loadTreeNode("/ou/loadOuTree", "ouSelectTree");
    });

    //部门领导选择
    $('#selectManager').click(function () {
        $("#userList").modal('show');
        loadUserList();
        $('#user-list-table tbody').on('dblclick', 'tr', function () {
            var rawdata=$("#user-list-table").DataTable().row($(this)).data();
            $("#manageOuListForm").find("input[name='managedByName']").val(rawdata.name);
            $("#manageOuListForm").find("input[name='managedBy']").val(rawdata.distinguishedName);
            $("#userList").modal('hide');
        });
    });
    $('#clear_select').click(function () {
        $("#manageOuListForm").find("input[name='managedByName']").val("");
        $("#manageOuListForm").find("input[name='managedBy']").val("");
    });
    $('#treeSubmit').click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("ouSelectTree");
        var nodes = treeObj.getSelectedNodes(true);
        var ou = "";
        if (nodes.length > 0) {
            ou = nodes[0].id;
        }
        $('#pid').val(ou);
        $('#manageOuListForm').find("input[name='department']").val(ou);
    });
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

});