function showFunctionView(setting) {
	console.log(setting)
	$("#manageFunctionForm")[0].reset();
	if (setting.type != '1') {
		$("#urlFormGroup").removeClass('hide');
		$("#parentNameFormGroup").removeClass('hide');
	} else {
		$("#urlFormGroup").addClass('hide');
		$("#parentNameFormGroup").addClass('hide');
	}
	
	if (setting.type == 5) {
		swal("添加子类取消", "子类类型为按钮类型不能添加子类！", "error");
		return;
	}
	
	$("#idx").val(setting.id);
	$("#manageFunctionForm").find("input[name='name']").val(setting.name);
	$("#manageFunctionForm").find("input[name='url']").val(setting.url);
	$("#manageFunctionForm").find("input[name='shiroPermission']").val(setting.shiroPermission);
	
	$("#manageFunctionForm").find("input[name='type']").val(setting.type);
	$("#manageFunctionForm").find("input[name='parentName']").val(setting.pname);
	$("#manageFunctionForm").find("input[name='parent.id']").val(setting.pid);
	$("#addFunction").modal('show');
}

$(document).ready(function() {
	$('.function-table').dataTable();
	// 更新
    $('.function-table tbody').delegate(".function-update", 'click', function () {
		var jsonStr = $(this).attr("jsonStr");
		showFunctionView(eval("(" + jsonStr + ")" ));
	});
	// 添加分类
	$("#addFunctionButton").click(function() {
		showFunctionView({type:'1'});
	});

	// 添加子类

    $('.function-table tbody').delegate(".function-addChild", 'click', function () {
        var jsonStr = $(this).attr("jsonStr");
        showFunctionView(eval("(" + jsonStr + ")" ));
    });
	// 删除
    $('.function-table tbody').delegate(".function-remove", 'click', function () {
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
});