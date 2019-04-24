package com.house.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@Data
@ToString
public class RestResponse<T> {

    private int code;
    private String msg;
    private T result;

    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }

    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> error(RestCode code) {
        return new RestResponse<T>(code.code, code.msg);
    }

    public RestResponse() {
        this(RestCode.OK.code, RestCode.OK.msg);
    }

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
