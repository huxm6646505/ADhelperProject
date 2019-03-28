package com.hansholdings.manage.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by maozz11347 on 2018-7-26.
 */
@ApiModel(description = "返回接口类")
public class RestMessage {
    @ApiModelProperty(value = "返回数据")
    private Object data;
    @ApiModelProperty(value = "消息代码")
    private Integer code;
    @ApiModelProperty(value = "消息内容")
    private String desp;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
