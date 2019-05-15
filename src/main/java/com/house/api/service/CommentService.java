package com.house.api.service;

import java.util.List;

import com.house.api.common.CommonConstants;
import com.house.api.common.PageParams;
import com.house.api.feignclient.CommentClient;
import com.house.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.api.common.PageData;

/**
 * @Description 用户评论服务
 * @Author wujin
 **/
@Service
public class CommentService {

    @Autowired
    private CommentClient commentClient;

    /**
     * @Description 房产评论
     **/
    public void addHouseComment(Long houseId, String content, Long userId) {
        CommentReq commentReq = new CommentReq(userId, houseId, null, content, CommonConstants.COMMENT_HOUST_TYPE, null);
        commentClient.addComment(commentReq);
    }

    /**
     * @Description 百科评论
     **/
    public void addBlogComment(Integer blogId, String content, Long userId) {
        CommentReq commentReq = new CommentReq(userId, null, blogId, content, CommonConstants.COMMENT_BLOG_TYPE, null);
        commentClient.addComment(commentReq);
    }

    /**
     * @Description 获取房产评论信息
     **/
    public List<Comment> getHouseComments(long id) {
        CommentReq commentReq = new CommentReq(null, id, null, null, CommonConstants.COMMENT_HOUST_TYPE, CommonConstants.COMMENT_COUNT);
        return commentClient.getListComment(commentReq).getResult();
    }

    /**
     * @Description 获取百科评论信息
     **/
    public List<Comment> getBlogComments(int id) {
        CommentReq commentReq = new CommentReq(null, null, id, null, CommonConstants.COMMENT_BLOG_TYPE, CommonConstants.COMMENT_COUNT);
        return commentClient.getListComment(commentReq).getResult();
    }


    /**
     * @Description 查询博客详情
     **/
    public Blog queryOneBlog(int id) {
        return commentClient.getBlog(id).getResult();
    }

    /**
     * @Description 百科列表信息
     **/
    public PageData<Blog> queryBlogs(Blog query, PageParams build) {
        BlogQueryReq blogQueryReq = new BlogQueryReq(query, build.getLimit(), build.getOffset());
        ListResponse<Blog> result = commentClient.getBlogs(blogQueryReq).getResult();
        return PageData.buildPage(result.getList(), result.getCount(), build.getPageSize(), build.getPageNum());
    }
}
