package com.hansholdings.basic.template;

import com.hansholdings.basic.commons.Constants;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MultiUrlTemplateModelEx implements TemplateMethodModelEx {

    /** 原始域名：含新域名编号所在位置的占位符 */
    private String host = null;

    private boolean isNowRefush = false;

    /** 后缀 /min.js /js */
    private String suffix = null;

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (null == arguments || arguments.size() == 0) {
            return "";
        }
        // 获取资源文件名
        String url = arguments.get(0) == null ? "" : arguments.get(0).toString();

        if (StringUtils.isBlank(url)) {
            return "";
        }

        if (!host.endsWith("/")) {
            host = host + "/";
        }

        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        if (!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }

        StringBuffer result = new StringBuffer();
        result.append(host).append(url).append(suffix);

        if (isNowRefush) {
            result.append("?t=").append(Constants.RESET_TIME);
        }

        return result.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return String 返回值
     */
    public String getHost() {
        return host;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param host 参考说明
     */
    public void setHost(String host) {
        this.host = host;
    }

    public boolean isNowRefush() {
        return isNowRefush;
    }

    public void setNowRefush(boolean isNowRefush) {
        this.isNowRefush = isNowRefush;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
