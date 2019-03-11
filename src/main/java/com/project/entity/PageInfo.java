package com.project.entity;

public class PageInfo<T> {

    private int startPage;
    private int pageSize;

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    private int tot;
    private T object;

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
    public PageInfo(T object,int tot,int startPage, int pageSize) {
        super();
        this.startPage = startPage;
        this.pageSize = pageSize;
        this.tot = tot;
        this.object = object;
    }
    public PageInfo() {
        super();
    }
}
