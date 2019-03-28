/**
 * Created by maozz11347 on 2017/12/19.
 */

function showView(setting) {
    $("#manageOperationForm")[0].reset();
    if (setting == undefined) {
        $("#operation_detail").modal('show');
        return;
    }
    $("#manageOperationForm").find("input[name='userName']").val(setting.userName);
    $("#manageOperationForm").find("input[name='operateStype']").val(setting.operateStype);
    $("#manageOperationForm").find("input[name='serviceStype']").val(setting.serviceStype);
    $("#manageOperationForm").find("textarea[name='currentValue']").val(setting.currentValue?setting.currentValue.replace(/","/g,"\"\n\n\"").replace(/(^\{*)|(\}*$)/g,""):"");
    $("#manageOperationForm").find("textarea[name='originalValue']").val(setting.originalValue?setting.originalValue.replace(/","/g,"\"\n\n\"").replace(/(^\{*)|(\}*$)/g,""):"");
    $("#manageOperationForm").find("input[name='operationTime']").val(setting.operationTime);
    $("#operation_detail").modal('show');
}

function loadDatables(){
    $('.opeartioninfo-table').dataTable({
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
            "url": "/logging/getOperationinfos",
            "type": 'POST',
            "data": function (d) {
                var param = {};
                param.draw = d.draw;
                param.start = d.start;
                param.length = d.length;
                param.minOperationTime= $("#minOperationTime").val();
                param.maxOperationTime= $("#maxOperationTime").val();
                param.serviceStype=$("#serviceStype").val();
                param.operateStype=$("#operateStype").val();
                param.userName=$("#userName").val();
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
                    if(!datas[i].hasOwnProperty("currentValue")){
                        datas[i].currentValue="";
                    }
                }
                return str.data;
            }
        },
        "columns": [
            {"data": "id"},
            {"data": "userName"},
            {"data": "operateStype"},
            {"data": "serviceStype"},
            {"data": "currentValue","visible": false},
            {"data": "operationTime"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
            },
            {
                targets: -1,
                defaultContent: "<div class='btn-group'><button id='operate-detail' class='btn btn-primary btn-sm' type='button'>查看详情</button></div>"
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

    $('.opeartioninfo-table tbody').delegate('#operate-detail', 'click', function () {
        var datas = $(".opeartioninfo-table").DataTable().row( $(this).parents("tr") ).data();
        showView(datas);
    });
}
$(document).ready(function() {

    $(".input-group.date").datepicker({
        todayBtn : "linked",
        keyboardNavigation : !1,
        forceParse : !1,
        calendarWeeks : !0,
        autoclose : !0,
        language : "zh-CN",
        format : "yyyy-mm-dd"
    });

    loadDatables();

    $("#btnSearch").click(function() {
        loadDatables();
    });

    $("#btnReset").click(function() {
        $("#minOperationTime").val(null);
        $("#maxOperationTime").val(null);
    });

    $("#btnExport").click(function(){

        $("#exportOpLog").modal('show');
        $("#downOpLogExcel").empty();
        $.ajax({
            type: "post",
            url: "/FileUpload/exportOpLog",
            data:{
                "minOperationTime" : $("#minOperationTime").val(),
                "maxOperationTime" : $("#maxOperationTime").val(),
                "serviceStype" : $("#serviceStype").val(),
                "operateStype" : $("#operateStype").val(),
                "userName" :$("#userName").val()
            },
            async : false,
            dataType: "json",
            success: function (data)
            {
                if("200"==data.code){
                    var html;
                    html = "<div style='float:left;'><p style='text-align:left'>内容：  " + data.desp + "<a  href='/exportFile/"+ data.exportFnm + "'>详情下载。</a></p></div>"
                    $(html).appendTo($('#downOpLogExcel'));
                    return true;
                }else{
                    alert("不合法！错误信息如下："+data.errorMsg);
                    return false;
                }
            },
            error:function (XMLHttpRequest, textStatus) {
                alert("请求失败！");
            }
        });
    });


});