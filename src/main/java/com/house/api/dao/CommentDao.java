package com.house.api.dao;

import java.util.List;

import com.house.api.model.BlogQueryReq;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.house.api.common.RestResponse;
import com.house.api.config.GenericRest;
import com.house.api.model.Blog;
import com.house.api.model.Comment;
import com.house.api.model.CommentReq;
import com.house.api.model.ListResponse;
import com.house.api.utils.Rests;

@Repository
public class CommentDao {
  
  @Autowired
  private GenericRest rest;
  
  @Value("${comment.service.name}")
  private String commentServiceName;

  public void addComment(CommentReq commentReq) {
    Rests.exc(() ->{

        String url = Rests.toUrl(commentServiceName, "/comment/add");
        ResponseEntity<RestResponse<Object>> responseEntity = rest.post(url,commentReq,new ParameterizedTypeReference<RestResponse<Object>>() {});
        return responseEntity.getBody();
     });
  }


  public List<Comment> listComment(CommentReq commentReq) {
    return Rests.exc(() ->{
        String url = Rests.toUrl(commentServiceName, "/comment/list");
        ResponseEntity<RestResponse<List<Comment>>> entity = rest.post(url, commentReq, new ParameterizedTypeReference<RestResponse<List<Comment>>>() {});
        return entity.getBody();
    }).getResult();
  }


  public Pair<List<Blog>, Long> getBlogs(Blog query, Integer limit, Integer offset) {
    ListResponse<Blog>  listResponse  = Rests.exc(() ->{
        String url = Rests.toUrl(commentServiceName, "/blog/list");
        BlogQueryReq blogQueryReq = new BlogQueryReq();
        blogQueryReq.setBlog(query);
        blogQueryReq.setLimit(limit);
        blogQueryReq.setOffset(offset);
        ResponseEntity<RestResponse<ListResponse<Blog>>> entity = rest.post(url, blogQueryReq, new ParameterizedTypeReference<RestResponse<ListResponse<Blog>>>() {});
        return entity.getBody();
    }).getResult();
    return ImmutablePair.of(listResponse.getList(), listResponse.getCount());
  }


  public Blog getBlog(int id) {
    return Rests.exc(() ->{
        String url = Rests.toUrl(commentServiceName, "/blog/one?id=" + id);
        ResponseEntity<RestResponse<Blog>> entity = rest.get(url, new ParameterizedTypeReference<RestResponse<Blog>>() {});
        return entity.getBody();
    }).getResult();
  }
  
  
  
  

}
