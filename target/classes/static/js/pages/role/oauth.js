/**
 * 请求地址
 * 
 * @param addressUrl
 * @returns
 */
function loadTreeNode(addressUrl, treeComponentId) {
	// 组织树参数
	var setting = {
		async : {
			enable : true,
			url : addressUrl,
			autoParam : [ "id" ]
		},
		check : {
			enable : true
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",

			},
			view : {
				selectedMulti : true
			}
		}
	};

	$.get(addressUrl, function(result) {
		if (result) {
			$.fn.zTree.init($("#" + treeComponentId), setting, result.item);
		}
	});
}

$(document).ready(function() {
	loadTreeNode("/role/loadFunctionTree?id=" + _roleId, "functionTree");
	// form提交操作
	$("#roleOauthForm").submit(function() {
		var treeObj = $.fn.zTree.getZTreeObj("functionTree");
		var nodes = treeObj.getCheckedNodes(true);
		var functions = new Array();
		for (var i = 0; i < nodes.length; i++) {
			functions.push({
				id : nodes[i].id
			});
		}
		$.ajax({
			url : "/role/oauthsubmit",
			type : "post",
			dataType : "json",
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify({
				id : _roleId,
				functions : functions
			}),
			success : function(result) {
				if (result.success) {
					window.location.href = "/role/index.html";
				} else {
					swal("角色权限保存失败", "您取消了这个操作！", "error");
				}
			},
			error : function(r,s,m) {
				swal("角色权限保存失败", "您取消了这个操作！", "error");
			}
		});
		return false;
	});
});
