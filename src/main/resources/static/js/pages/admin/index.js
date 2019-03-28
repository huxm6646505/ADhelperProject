function showAdminView(setting) {
	$("#manageAdminForm")[0].reset();
	if (setting == undefined) {
		$("#addAdmin").modal('show');
		return;
	}

	$("#idx").val(setting.id);
	$("#manageAdminForm").find("input[name='user.username']").val(
			setting.username);
    $("#manageAdminForm").find("input[name='user.manager']").val(
        setting.manager);

	$("#manageAdminForm").find("input[name='member.name']").val(setting.name);
	$("#manageAdminForm").find("#sex"+setting.sex).prop("checked", true);
	$("#manageAdminForm").find("input[name='member.birthday']").val(
			setting.birthday);
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
	});
	$("#addAdmin").modal('show');
}

function validAdmin(){
    $("#manageAdminForm").validate({
        rules : {
            "user.username" : {
                required : true,
                minlength : 2
            },
            "user.password" : {
                required : ($("#idx").val()=="")?true:false,
                minlength : 5
            },
            "repassword" : {
                required : ($("#idx").val()=="")?true:false,
                minlength : 5,
                equalTo : "#password"
            },
            "member.name" : {
                required : true,
                minlength : 2
            }
        },
        messages : {
            "user.username" : {
                required : "请输入用户名",
                minlength : "用户名必需由两个字母组成"
            },
            "user.password" : {
                required : "请输入密码",
                minlength : "密码长度不能小于 5 个字母"
            },
            "repassword" : {
                required : "请输入密码",
                minlength : "密码长度不能小于 5 个字母",
                equalTo : "两次密码输入不一致"
            },
            "member.name" : {
                required : "请输入用户名",
                minlength : "用户名必需由两个字母组成"
            }
        }
    });
}

$(document).ready(function() {
	$('.admin-table').dataTable();
	// 更新

    $('.admin-table').delegate('.admin-update','click',function(){
        var jsonStr = $(this).attr("jsonStr");
        showAdminView(eval("(" + jsonStr + ")"));
        validAdmin()
	});
	// 添加用户
	$("#addAdminButton").click(function() {
       // $('.password').show();
       // $('.repassword').show();
		showAdminView();
        validAdmin()
	});
	// 删除

    $('.admin-table tbody').delegate('.admin-remove', 'click', function () {
        var data=eval("(" + $(this).attr("jsonStr") + ")");
         var locked=(data.locked==0)?'启用':'禁用';
        var url = $(this).attr('xhref');
        swal({
            title: "您确定要"+locked+data.userName+"吗",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: locked,
            cancelButtonText: "取消",
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            if (isConfirm === true) {
                var json = {
                    id : data.id,
                    locked : data.locked
                };
                $.post('/admin/remove', json, function () {
                    swal(locked+"成功", "已成功"+locked+"!", "success");
                    window.location.href =url;
                })
            } else {
                swal(locked+"取消", "您取消了"+locked+"操作！", "error");

            }
        });
    });
/*	$('.admin-table').delegate('.admin-remove','click',function(){
        var url = $(this).attr('xhref');
        swal({
            title : "您确定要删除这条信息吗",
            text : "删除后将无法恢复，请谨慎操作！",
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "删除",
            cancelButtonText : "取消",
            closeOnConfirm : false,
            closeOnCancel : false
        }, function(isConfirm) {
            if (isConfirm) {
                window.location.href = url;
            } else {
                swal("删除取消", "您取消了删除操作！", "error");
            }
        });
	});*/
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
	});
	$(".input-group.date").datepicker({
		todayBtn : "linked",
		keyboardNavigation : !1,
		forceParse : !1,
		calendarWeeks : !0,
		autoclose : !0,
		language : "zh-CN",
		format : "yyyy-mm-dd"
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
	})
});