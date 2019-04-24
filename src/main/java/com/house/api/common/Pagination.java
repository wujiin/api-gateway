package com.house.api.common;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

/**
 * @Description 分页查询条件
 * @Author wujin
 **/
@Data
@ToString
public class Pagination {
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int queryCount;
    private int pageNum;
    private int pageSize;
    private long totalCount;
    private List<Integer> pages = Lists.newArrayList();

    public Pagination(Integer pageSize, Integer pageNum, Long totalCount, Integer queryCount) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.queryCount = queryCount;
        for (int i = 1; i <= pageNum; i++) {
            pages.add(i);
        }
        long pageCount = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
        if (pageCount > pageNum) {
            for (int i = pageNum + 1; i <= pageCount; i++) {
                pages.add(i);
            }
        }
    }
}
