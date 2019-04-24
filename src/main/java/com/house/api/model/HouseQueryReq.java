package com.house.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HouseQueryReq {
    private House query;
    private Integer limit;
    private Integer offset;
}
