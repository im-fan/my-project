package com.project.web.entity;

public class PageInfo<T> {

    private int startPage;
    private int pageSize;
    private int tot;
    private T data;

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public PageInfo(T data,int tot,int startPage, int pageSize) {
        super();
        this.startPage = startPage;
        this.pageSize = pageSize;
        this.tot = tot;
        this.data = data;
    }
    public PageInfo() {
        super();
    }
}
