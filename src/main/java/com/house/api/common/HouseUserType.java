package com.house.api.common;

/**
 * @Description 收藏房产的类型
 * @Author wujin
 **/
public enum HouseUserType {
    SALE(1), BOOKMARK(2);

    public final Integer value;

    HouseUserType(Integer value) {
        this.value = value;
    }
}
