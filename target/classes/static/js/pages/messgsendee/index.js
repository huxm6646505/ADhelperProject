/**
 * Created by maozz11347 on 2018-9-12.
 */


function showView(setting) {
    $("#manageMessgsendeeForm")[0].reset();
    if (setting == undefined) {
        $("#messgsendee_detail").modal('show');
        return;
    }
    $("#manageMessgsendeeForm").find("input[name='id']").val(setting.id);
    $("#manageMessgsendeeForm").find("input[name='deptou']").val(setting.deptou);
   $("#manageMessgsendeeForm").find("input[name='sendee']").val(setting.sendee);
    $("#messgsendee_detail").modal('show');
}

function loadDatables(){
    $('.messgsendee-table').dataTable({
        "processing": true,
        "serverSide": true,//启用服务器端分页
        "destroy": true,
        "pageLength": 10,
        "ordering": false,
        // "renderer": "bootstrap",//渲染样式：Bootstrap和jquery-ui
        "pagingType": "simple_numbers",//分页样式：simple,simple_numbers,full,full_numbers
        "autoWidth": true,
        "stateSave": false,//保持翻页状态，和comTable.fnDraw(false);结合使用
        "searching": false,//datatables默认搜索
        "ajax": {
            "url": "/mesgsendee/getMessgSendee",
            "type": 'POST',
            "data": function (d) {
                var param = {};
                param.draw = d.draw;
                param.start = d.start;
                param.length = d.length;
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
                return str.data;
            }
        },
        "columns": [
            {"data": "uid"},
            {"data": "id" , "visible": false},
            {"data": "deptou"},
            {"data": "sendee"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
            },
            {
                targets: -1,
                defaultContent: "<div class='btn-group'><button id='messgsendee-update' class='btn btn-primary btn-sm' type='button'>修改</button></div>"
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

    $('.messgsendee-table tbody').delegate('#messgsendee-update', 'click', function () {
        var datas = $(".messgsendee-table").DataTable().row( $(this).parents("tr") ).data();
        showView(datas);
    });
}

function PostData() {
    if($("#manageMessgsendeeForm").valid()) {
        $.ajax({
            type: "POST",
            url: "/ou/saveOrUpdateOuList.html",
            data: $('#manageMessgsendeeForm').serialize(),
            success: function (msg) {
                if (msg === 'true') {
                    $("#messgsendee_detail").modal('hide');
                    loadDatables();
                }
            },
            error: function () {
            }
        });
        return false;
    }
}
$(document).ready(function() {

    loadDatables();

    $('#addmessgsendeeButton').click(function () {
        showView();
    });
    $('#selectOu').click(function () {
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
        $("input[ name='deptou'] ").val(ou)
    });


    $('#selectSendee').click(function () {
        $("#userList").modal('show');
        loadUserList();
        $('#user-list-table tbody').on('dblclick', 'tr', function () {
            var rawdata=$("#user-list-table").DataTable().row($(this)).data();
            var oldsendee=$("input[ name='sendee'] ").val();
            var sendee= (oldsendee!=null && oldsendee!='')?oldsendee+";"+rawdata.sAMAccountName:rawdata.sAMAccountName;
            $("input[ name='sendee'] ").val(sendee);
            $("#userList").modal('hide');
        });
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