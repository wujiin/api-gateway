package com.house.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class HouseQueryReq {
    private House query;
    private Integer limit;
    private Integer offset;
}
