package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ListResponse<T> {
    private List<T> list;
    private Long count;

    public static <T> ListResponse<T> build(List<T> list, Long count) {
        ListResponse<T> response = new ListResponse<>();
        response.setCount(count);
        response.setList(list);
        return response;
    }
}
