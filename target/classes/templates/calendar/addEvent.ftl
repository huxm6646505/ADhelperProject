<div class="modal" id="addEvent" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加事件</h4>
			</div>
			<form class="form-horizontal" id="manageEventForm" action="/event/saveOrUpdate.html" method="post">
				<small class="font-bold">
					<div class="modal-body">
                        <input name="id" id="idx" class="hide" value="">
                        <div class="form-group ">
							<label class="col-lg-3 control-label">日程内容(必填)：</label>
							<div class="col-lg-8">
								<textarea type="text" name="eventDesc" placeholder="请输入日程内容"  class="form-control"></textarea>
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