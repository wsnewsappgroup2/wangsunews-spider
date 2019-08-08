package com.crow.result;

import java.util.List;

public class ColumnsInfoResult<T> {
    private boolean success;
    private String msg;
    private T columns;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getColumns() {
        return columns;
    }

    public void setColumns(T columns) {
        this.columns = columns;
    }
}
