<div class="modal " id="addUser" tabindex="-1" role="dialog"
	aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content animated bounceIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">编辑用户</h4>
			</div>
			<form class="form-horizontal" id="manageUserForm"  method="post">
				<small class="font-bold">
					<div class="modal-body">
                        <input name="distinguishedName" id="distinguishedName" class="hide" value="">
                        <div class="form-group ">
							<label class="col-lg-3 control-label">姓名：</label>
							<div class="col-lg-8">
								<input type="text" name="name" placeholder="请输入用户姓名"  class="form-control">
							</div>
						</div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">性别：</label>
                            <div class="col-lg-8 radio">
                                <label>
                                    <input type="radio" name="initials" id="optionsRadios1" value="男" checked> 男
                                </label>
                                <label>
                                    <input type="radio" name="initials" id="optionsRadios2" value="女">女
                                </label>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">工号：</label>
                            <input name="old_sAMAccountName"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" name="sAMAccountName" placeholder="请输入工号"  class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">账号：</label>
                            <div class="col-lg-8">
                                <input type="text" name="userPrincipalName" placeholder=""  class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">邮箱：</label>
                            <div class="col-lg-8">
                                <input type="text" name="mail" placeholder="请输入邮箱"  class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">所属部门：</label>
                            <div class="col-lg-8">
                                <input type="text" name="department" class="form-control" readonly>
                            </div>
                            <button type="button" class="btn btn-info btn-sm pull-right" id="selectDept" >选择</button>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">手机号码：</label>
                            <input name="old_telephoneNumber"  class="hide">
                            <div class="col-lg-8">
                                <input type="text" name="telephoneNumber" placeholder="请输入手机号码"  class="form-control">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-lg-3 control-label">办公电话：</label>
                            <div class="col-lg-8">
                                <input type="text" name="physicalDeliveryOfficeName" placeholder="请输入办公电话"  class="form-control">
                            </div>
                        </div>
                       <div class="form-group ">
                            <label class="col-lg-3 control-label">状态：</label>
                            <div class="col-lg-8 radio">
                                <label>
                                    <input type="radio" name="userAccountControl" id="optionsRadios1" value="正常" checked> 正常
                                </label>
                                <label>
                                    <input type="radio" name="userAccountControl" id="optionsRadios2" value="禁用">禁用
                                </label>
                            </div>
                        </div>
                        <div class="form-group password">
                            <label class="col-lg-3 control-label">密码：</label>
                            <div class="col-lg-8">
                                <input type="password" id="password" name="unicodePwd" class="form-control" placeholder="密码至少包含一个字母，长度至少8位">
                            </div>
                        </div>
                        <div class="form-group repassword">
                            <label class="col-lg-3 control-label">重新输入密码：</label>
                            <div class="col-lg-8">
                                <input type="password" name="repassword" id="repassword" class="form-control">
                            </div>
                        </div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="btn_save" onclick="submitData()">保存</button>
					</div>
				</small>
			</form>
		</div>
		<small class="font-bold"> </small>
	</div>
	<small class="font-bold"> </small>
</div>