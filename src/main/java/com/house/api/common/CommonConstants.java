package com.house.api.common;

/**
 * @Description 公共常量
 **/
public interface CommonConstants {
    /**
     * 评论类型
     */
    Integer COMMENT_HOUST_TYPE = 1;  //1-房产评论
    Integer COMMENT_BLOG_TYPE = 2;   //2-博客百科评论

    /**
     * 默认显示记录数
     */
    Integer RECOM_SIZE = 3;  //热门房产
    Integer COMMENT_COUNT = 8; //房产评论数
    Integer AGENT_COUNT = 6; //经纪人列表显示个数
    Integer AGENT_AGENCY_COUNT = 3;  //显示经纪人代理的房产数
    Integer AGENT_AGENCY_PAGECOUNT = 1; //显示经纪人代理的房产页数
    /**
     * 机构创建用户名称
     **/
    String AGENCY_COUNT = "979762929@qq.com";

    String LOGIN_USER_ATTRIBUTE = "loginUser";

}
