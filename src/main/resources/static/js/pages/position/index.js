
function loadPosition() {

    $('#position-table').dataTable({
        "processing": true,
        "destroy": true,
        "searching": true,
        "ajax": {
            "url": "/position/getPositions",
            "type": 'POST',
            "dataSrc": function (str) {
                var data;
                if (typeof str === 'object') {
                    data = str;
                }
                else {
                    data = eval("(" + str + ")");
                }
                var datas=data.data;
                return datas;
            }
        },
        "columns": [
            {"data": "null"},
            {"data": "name"},
            {"data": "uid", "visible": false},
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
                "<button id='showUserRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-user'></i></button>"+
                "<button id='editPosRow' class='btn btn-primary btn-sm' type='button'><i class='glyphicon glyphicon-edit'></i></button>"+
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
        }

    });
}

function posUserView(uid) {
    $('#pos-user-table').dataTable({
        "processing": true,
        "destroy": true,
        "ajax": {
            "url": "/position/getMembers/"+uid,
            "type": 'POST',
            "dataSrc": function (str) {
                var data;
                if (typeof str === 'object') {
                    data = str;
                }
                else {
                    data = eval("(" + str + ")");
                }
                var datas=data.data;
                for(var i=0,l=datas.length;i<l;i++) {
                    if (!datas[i].hasOwnProperty("name")) {
                        datas[i].name = "无";
                    }
                }

                return datas;
            }
        },
        "columns": [
           {"data": "id"},
            {"data": "name"}
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
        }

    });
    $("#showPosUser").modal('show');
}

function addOrUpPos(setting) {
    $("#managePositionForm")[0].reset();
    if (setting == undefined) {
        $("#addOrUpPosition").modal('show');
        return;
    }
    $("#uid").val(setting.uid);
    $("#managePositionForm").find("input[name='name']").val(setting.name);
    $("#addOrUpPosition").modal('show');
}

$(document).ready(function () {
    loadPosition();
    $('#position-table tbody').delegate('#showUserRow', 'click', function () {
        var datas = $("#position-table").DataTable().row( $(this).parents("tr") ).data();
        posUserView(datas.uid);
    });
    $('#position-table tbody').delegate('#editPosRow', 'click', function () {
        var datas = $("#position-table").DataTable().row( $(this).parents("tr") ).data();
        addOrUpPos(datas);
    });

    // 添加岗位
    $("#addPositionButton").click(function() {
        addOrUpPos();
    });
});
