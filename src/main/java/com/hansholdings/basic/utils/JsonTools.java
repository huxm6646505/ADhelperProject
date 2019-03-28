package com.hansholdings.basic.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.commons.Constants;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by maozz11347 on 2017/8/10.
 */
public class JsonTools {
    /**
     * code: tools.Containt <parameters>
     * desp: execute stauts
     * type: 'object' or 'list'
     * data: String or HashMap
     * */
    public JSONObject toRespJSONObject(int code, String desp, String type, Object data){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("code:'"+code+"'");
        if(!"".equals(desp)){
            sb.append(",");
            sb.append("desp:'"+desp+"'");
        }
        if(!"".equals(type)){
            sb.append(",");
            sb.append("type:'"+type+"',");
            if(!"".equals(data)){
                sb.append("data:'"+((String)data).replaceAll("'", "")+"'");
            }
        }

        sb.append(",");
        long tms = System.currentTimeMillis();
        sb.append("svctms:'"+tms+"'");

        sb.append("}");
        return JSON.parseObject(sb.toString());
    }

    public static JSONObject getSucessResult(){
        JSONObject result;
        result = JSON.parseObject("{'code':'"+ Constants.SUCCESS_PROCESSE_REQUEST+"','desp':'成功处理请求.'}");
        return result;
    }

    public static JSONObject getFailtResult(){
        JSONObject result;
        result = JSON.parseObject("{'code':'"+Constants.SERVER_INNEL_ERROE+"','desp':'请求处理失败.'}");
        return result;
    }

    public static JSONObject getFailtResult(String msg){
        JSONObject result;
        result = JSON.parseObject("{'code':'"+Constants.SERVER_INNEL_ERROE+"','desp':'"+msg+"'}");
        return result;
    }

    public static JSONObject getFailtResult(String code,String msg){
        JSONObject result;
        result = JSON.parseObject("{'code':'"+code+"',desp:'"+msg+"'}");
        return result;
    }
    //字符串日期------》 Timestamp日期
    public static Timestamp stringToTimestamp(String str) throws Exception {
        Timestamp result = null;
        if (str != null && str.trim().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            result = new Timestamp(sdf.parse(str).getTime());
        }
        return result;
    }

    //字符串日期------》 Timestamp日期"yyyy-MM-dd HH:mm:ss"
    public static  Timestamp stringToTimestamp2(String str) throws Exception {
        Timestamp result = null;
        if (str != null && str.trim().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = new Timestamp(sdf.parse(str).getTime());
        }
        return result;
    }


    public static String ouToString(String str){
        String[] sa=str.split("_");
        for(int i=0;i < sa.length / 2;i++){
            String temp = sa[i];
            sa[i] = sa[sa.length - 1 -i];
            sa[sa.length - 1 -i] = temp;
        }
        String ns= StringUtils.join(sa,",").replace(",",",OU=");
        return ns;
    }

    /**说明：ldap的时间戳是从1601年1月1日0时起经过的1E-7秒（即100纳秒）的个数（时间是GMT的，中国的北京的时间需要加上8个小时）
     例如：以lastLogon、pwdLastSet、accountExpires等属性为代表(输出没有'Z'结尾)
     *   nt时间戳转java的时间戳：
     */

    public static long getJavaDateByLdapNTTime(long ldapTime) {
        long javaTime = ldapTime - 116445312000000000L;
        javaTime = Long.parseLong(String.valueOf(javaTime).substring(0, 13)) + 57599875L;
        return javaTime;
    }

    /**
     *java的时间戳转nt时间戳：
     */
    public static long getLdapNTTimeByJavaDate(long date) {
        date = date - 57599875L;
        date = date * 10000;
        return date + 116445312000000000L;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
