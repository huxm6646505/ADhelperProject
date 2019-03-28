package com.hansholdings.basic.json;

public class Result {

    private Boolean success;
    private String  error;
    private String  message;

    private Object  item;

    public Result() {
    }

    public Result(Boolean success, String message, Object item) {
        setSuccess(success);
        setMessage(message);
        setItem(item);
    }

    public static Result error(String message) {
        Result r = new Result(false, message, null);
        r.setError(message);
        return r;
    }

    public static Result success(String message) {
        Result r = new Result(true, message, null);
        return r;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
