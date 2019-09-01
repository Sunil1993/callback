package com.intuit.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateReq {
    String email;
    String phoneNo;
    String name;
}
