package com.boomshair.mainentrance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Response {

    private Integer errorNo;
    private ErrorStatus errorStatus;
    private String message;
    private JSONObject resultJSON;
    public enum ErrorStatus {
        SUCCESS(1),
        ERROR(-1);
        int errorNo;
        public Integer getErrorNo() {
            return errorNo;
        }
        ErrorStatus(int errorNo) {
            this.errorNo = errorNo;
        }
    }

}
