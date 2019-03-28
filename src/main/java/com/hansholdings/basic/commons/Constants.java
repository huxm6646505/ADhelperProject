package com.hansholdings.basic.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常量类
 *
 * @author
 */
public class Constants {

    /**
     * 时间戳
     */
    public final static String RESET_TIME = new SimpleDateFormat("yyyyMMddhh").format(new Date());

    /**
     * session key
     */
    public final static String USER_SESSION_ID = "user_session_id";

    /**
     * admin session key
     */
    public final static String ADMIN_SESSION_ID = "admin_session_id";

    /**
     * ouroles session key
     */
    public final static String OUROLES_SESSION_ID = "ouroles_session_id";

    /**
     * 服务器内部错误
     */
    public final static String SERVER_INNEL_ERROE = "500";

    /**
     * 方法错误请求
     */
    public final static String METHOD_REQUEST_ERROR = "501";

    /**
     * 成功处理请求
     */
    public final static String SUCCESS_PROCESSE_REQUEST = "200";

    /**
     * 身份验证错误
     */
    public final static String AUTHEN_ERROR = "401";

    /**
     * 错误请求
     */
    public final static String REQUEST_ERROR = "400";

    /**
     * 数据错误
     */
    public final static String DATA_ERROR = "402";

    /**
     * add 操作
     */
    public final static String OPERATE_STYPE_ADD="新增信息";

    /**
     * update 操作
     */
    public final static String OPERATE_STYPE_UPDATE="修改信息";

    /**
     * update密码
     */
    public final static String OPERATE_STYPE_UPDTEASSWORD="密码修改";

    public final static String OPERATE_STYPE_LANDRAYINTERFACE="蓝凌接口";

    public final static String OPERATE_STYPE_LEAVE="员工离职";

    public final static String OPERATE_STYPE_ENTRY="员工入职";

    public final static String OPERATE_STYPE_TRANSFER="员工调职";

    public final static String OPERATE_STYPE_UPDATES="员工更新";


}
