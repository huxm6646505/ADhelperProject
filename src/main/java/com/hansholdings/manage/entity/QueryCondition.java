package com.hansholdings.manage.entity;

/**
 * Created by maozz11347 on 2017/8/16.
 */
public class QueryCondition {
    private Integer draw;

    private Integer start;

    private Integer length;

    private String search;

    private String  ou;

    private String rootOU;

    private String userAccountControl;

    private String maxOperationTime;

    private String minOperationTime;

    private String operateStype;

    private String serviceStype;

    private String userName;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getRootOU() {
        return rootOU;
    }

    public void setRootOU(String rootOU) {
        this.rootOU = rootOU;
    }

    public String getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(String userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getMaxOperationTime() {
        return maxOperationTime;
    }

    public void setMaxOperationTime(String maxOperationTime) {
        this.maxOperationTime = maxOperationTime;
    }

    public String getMinOperationTime() {
        return minOperationTime;
    }

    public void setMinOperationTime(String minOperationTime) {
        this.minOperationTime = minOperationTime;
    }

    public String getOperateStype() {
        return operateStype;
    }

    public void setOperateStype(String operateStype) {
        this.operateStype = operateStype;
    }

    public String getServiceStype() {
        return serviceStype;
    }

    public void setServiceStype(String serviceStype) {
        this.serviceStype = serviceStype;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "QueryCondition{" +
                "draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                ", search='" + search + '\'' +
                ", ou='" + ou + '\'' +
                ", rootOU='" + rootOU + '\'' +
                ", userAccountControl='" + userAccountControl + '\'' +
                ", maxOperationTime='" + maxOperationTime + '\'' +
                ", minOperationTime='" + minOperationTime + '\'' +
                ", operateStype='" + operateStype + '\'' +
                ", serviceStype='" + serviceStype + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }


}
