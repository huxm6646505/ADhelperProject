<div class="modal" id="upPwdUser" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">修改密码</h4>
			</div>
			<form class="form-horizontal" id="managePwdForm" action="/aduser/upPwdUser.html" method="post">
				<small class="font-bold">
					<div class="modal-body">
                        <input name="distinguishedName" id="idx2" class="hide" value="">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">密码：</label>
                            <div class="col-lg-8">
                                <input type="password" id="password1" name="unicodePwd" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">重新输入密码：</label>
                            <div class="col-lg-8">
                                <input type="password" name="repassword" id="repassword" class="form-control">
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