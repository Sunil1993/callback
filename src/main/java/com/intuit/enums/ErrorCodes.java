package com.intuit.enums;

import lombok.Getter;

/**
 * Created by Sunil on 9/2/19.
 */
@Getter
public enum ErrorCodes {
    DEFAULT_MESSAGE(100004,"Some Internal Error Occurred!!");

    int errorCode;
    String errMsg;

    ErrorCodes(int i, String errMsg) {
        errorCode=i;
        this.errMsg=errMsg;
    }
}
