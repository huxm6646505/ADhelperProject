<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> - 日历</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/css/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="/css/plugins/fullcalendar/fullcalendar.print.css" rel="stylesheet"  media='print'>
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>课程</h5>
                    </div>
                    <div class="ibox-content">
                        <div id='external-events'>
                            <p>可拖动的活动</p>
                            <div class='external-event navy-bg'>大班课</div>
                            <div class='external-event navy-bg'>小班课</div>
                            <div class='external-event navy-bg'>中班课</div>
                            
                            <p class="m-t">
                                <input type='checkbox' id='drop-remove' class="i-checks" checked />
                                <label for='drop-remove'>移动后删除</label>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>课程日历</h5>
                    </div>
                    <div class="ibox-content">
                        <div id="calendar"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <#include "addEvent.ftl">
    <!-- 全局js -->
    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>
    <!-- 自定义js -->
    <script src="/js/content.js?v=1.0.0"></script>
    <script src="/js/jquery-ui.custom.min.js"></script>
    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>
    <!-- Full Calendar -->
    <script src='/js/plugins/fullcalendar/moment.min.js'></script>
    <script src="/js/plugins/fullcalendar/fullcalendar.js"></script>
    <script src='/js/plugins/fullcalendar/locale-all.js'></script>
    <script src="/js/pages/calendar/index.js"></script>
</body>

</html>
