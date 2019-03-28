(function() {
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
	});

	$('#external-events div.external-event').each(function() {
		var eventObject = {
			title : $.trim($(this).text())
		};

		$(this).data('eventObject', eventObject);
		$(this).draggable({
			zIndex : 999,
			revert : true, // will cause the event to go back to its
			revertDuration : 0
		});
	});

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

	$('#calendar').fullCalendar(
			{
				locale : 'zh-cn',
				header : {
					left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek,agendaDay,listMonth'
				},
				editable : true,
				droppable : true,
				drop : function(date, allDay) {
					var originalEventObject = $(this).data('eventObject');
					var copiedEventObject = $.extend({}, originalEventObject);
					copiedEventObject.start = date;
					copiedEventObject.allDay = allDay;

					$('#calendar').fullCalendar('renderEvent',
							copiedEventObject, true);

					if ($('#drop-remove').is(':checked')) {
						$(this).remove();
					}

				},
				dayClick : function(date, allDay, jsEvent, view) {
					$("#addEvent").modal('show');
				},
				events : "/calendar/loadEvents.htm",
			});

	$("#manageEventForm").validate({
		rules : {
			"eventDesc" : {
				required : true,
				maxlength : 255
			}
		},
		messages : {
			"eventDesc" : {
				required : "请输入日程内容",
				maxlength : "日程内容不能多于255个字符"
			}
		}
	});

	function showResponse(responseText, statusText, xhr, $form) {
		if (statusText == "success") {
			if (responseText == 1) {
				$.fancybox.close();// 关闭弹出层
				$('#calendar').fullCalendar('refetchEvents'); // 重新获取所有事件数据
			} else {
				alert(responseText);
			}
		} else {
			alert(statusText);
		}
	}

	$('#manageEventForm').ajaxForm({
		success : showResponse
	// 成功返回
	});
});