<div class="modal" id="addFunction" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">编辑菜单</h4>
			</div>
			<form class="form-horizontal" id="manageFunctionForm" action="/function/add.html" method="post">
				<small class="font-bold">
					<div class="modal-body">
                        <input name="id" id="idx" class="hide" value="">
						<input name="type" class="hide" value="1">
						<input name="active" class="hide" value="1">
						<input name="parent.id" class="hide" value="">
						<div class="form-group hide" id="parentNameFormGroup">
							<label class="col-lg-3 control-label">父菜单名称：</label>
							<div class="col-lg-8">
								<input type="text" name="parentName" disabled class="form-control">
							</div>
						</div>
						<div class="form-group ">
							<label class="col-lg-3 control-label">菜单名称(必填)：</label>
							<div class="col-lg-8">
								<input type="text" name="name" placeholder="请填写分类名称" required class="form-control">
							</div>
						</div>
						<div class="form-group hide" id="urlFormGroup">
							<label class="col-lg-3 control-label">访问地址(必填)：</label>
							<div class="col-lg-8">
								<input type="text" name="url" placeholder="请填写访问地址" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">授权(必填)：</label>
							<div class="col-lg-8">
								<input type="text" name="shiroPermission" placeholder="多个用逗号分隔，如：user:list,user:create" required class="form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary">保存</button>
					</div>
				</small>
			</form>
		</div>
		<small class="font-bold"> </small>
	</div>
	<small class="font-bold"> </small>
</div>