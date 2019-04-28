package com.house.api.feignclient;

import com.house.api.common.RestResponse;
import com.house.api.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 用户评论、百科服务
 * @Author wujin
 **/
@FeignClient(name = "comment")
public interface CommentClient {

    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    RestResponse<List<Comment>> getListComment(@RequestBody CommentReq commentReq);

    @RequestMapping(value = "/comment/add", method = RequestMethod.GET)
    RestResponse addComment(@RequestBody CommentReq commentReq);

    @RequestMapping(value = "/blog/list", method = RequestMethod.GET)
    RestResponse<ListResponse<Blog>> getBlogs(@RequestBody BlogQueryReq req);

    @RequestMapping(value = "/blog/one", method = RequestMethod.GET)
    RestResponse<Blog> getBlog(@RequestParam("id") int id);
}
