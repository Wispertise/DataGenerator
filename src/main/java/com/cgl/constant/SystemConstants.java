package com.cgl.constant;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  文章最多查询条数
     */
    public static final int MAX_SELECT_NUMBER = 10;

    /**
     * 表示正常状态的分类
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 是否为管理员
     */
    public static final Long IS_ADMIN = 1L;

    /**
     * redis缓存更新浏览次数用
     */
    public static final String ARTICLE_VIEW_COUNT = "article:viewCount";

    /**
     *  正常状态
     */
    public static final String NORMAL = "0";
    /**
     * 管理员用户
     */
    public static final String ADMIN = "1";


}