function showRoleView(setting) {
	$("#manageRoleForm")[0].reset();
	if (setting == undefined) {
		$("#addRole").modal('show');
		return;
	}
	console.log(setting)
	$("#manageRoleForm").find("input[name='id']").val(setting.id);
	$("#manageRoleForm").find("input[name='name']").val(setting.name);
	$("#addRole").modal('show');
}

$(document).ready(
		function() {
			$('.role-table').dataTable();
			// 更新
			$('.role-update').click(function() {
				var jsonStr = $(this).attr("jsonStr");
				showRoleView(eval("(" + jsonStr + ")"));
			});
			// 添加角色
			$("#addRoleButton").click(function() {
				showRoleView();
			});
			// 删除
			$('.role-remove').click(function() {
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
			
			$("#manageRoleForm").validate({
				rules : {
					"name" : {
						required : true,
						minlength : 2
					},
				},
				messages : {
					"name" : {
						required : "请输入角色名",
						minlength : "角色名必需由两个字母组成"
					}
				}
			});
		});