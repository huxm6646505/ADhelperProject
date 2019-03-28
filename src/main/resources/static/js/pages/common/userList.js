/**
 * Created by maozz11347 on 2017/10/12.
 */
function loadUserList() {
    $('#user-list-table').dataTable({
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
                param.ou="OU=大族集团,DC=hansholdings,DC=com";
                param.serviceStype="userList";
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
            {"data": "physicalDeliveryOfficeName"},
            {"data": "department","visible": false},
            {"data": "distinguishedName","visible": false},
            {"data": "userAccountControl"}

        ],
        columnDefs: [
            {
                targets: 0,
                defaultContent: "<input type='checkbox' name='checkList'>"
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
}
