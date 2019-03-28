<div class="modal" id="addAdmin" tabindex="-1" role="dialog"
	aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content animated bounceIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">编辑用户</h4>
			</div>
			<form class="form-horizontal" id="manageAdminForm" action="/admin/saveOrUpdate.html" method="post">
				<small class="font-bold">
					<div class="modal-body">
                        <input name="id" id="idx" class="hide" value="">
                        <div class="form-group ">
							<label class="col-lg-3 control-label">用户名称(必填)：</label>
							<div class="col-lg-8">
								<input type="text" name="user.username" placeholder="请输入用户名称"  class="form-control">
							</div>
						</div>
						<div class="form-group password">
							<label class="col-lg-3 control-label">密码：</label>
							<div class="col-lg-8">
								<input type="password" id="password" name="user.password" class="form-control" placeholder="密码长度不能小于 5 个字母">
							</div>
						</div>
						<div class="form-group repassword">
							<label class="col-lg-3 control-label">重新输入密码：</label>
							<div class="col-lg-8">
								<input type="password" name="repassword" id="repassword" class="form-control">
							</div>
						</div>
						<div class="form-group" id="urlFormGroup">
							<label class="col-lg-3 control-label">姓名(必填)：</label>
							<div class="col-lg-8">
								<input type="text" name="member.name" placeholder="请输入姓名" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">性别(必填)：</label>
							<div class="col-lg-8">
								<label class="radio-inline i-checks"> <input type="radio" id="sex1" value="1" name="member.sex">
									<i></i> 男
								</label>
								<label class="radio-inline i-checks"> <input type="radio" id="sex2" value="2" name="member.sex">
									<i></i> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">生日(必填)：</label>
							<div class="col-lg-8">
								<div class="input-group date">
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span> <input type="text"
										class="form-control" name="member.birthday" value="2014-11-11">
								</div>
							</div>
						</div>
                        <div class="form-group hide ">
                            <label class="col-lg-3 control-label">管理者</label>
                            <div class="col-lg-8">
                                <input type="text" name="user.manager" placeholder=""  class="form-control ">
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