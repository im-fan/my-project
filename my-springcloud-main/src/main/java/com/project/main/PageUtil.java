package com.project.main;

import com.project.web.entity.PageInfo;

public class PageUtil {


    /**
     * 其实 PageHelper 的实现靠的就是这个变量，
     * 它的里面可以存储的东西，在真正需要计算分页的时候，可以随时取出来
     */
    private static final ThreadLocal<PageInfo> LOCAL_PAGE = new ThreadLocal<>();

    private static Integer StartPage = 0;
    private static Integer PageSize = 10;

    public static void startPage(Object object) {

        PageInfo page  = new PageInfo();
        page.setStartPage(StartPage);
        page.setPageSize(PageSize);
        LOCAL_PAGE.set(page);

    }

}
