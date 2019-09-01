package com.intuit.models.requests;

import com.intuit.dao.entities.CallbackEntry;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class CallbackEntryCreateReq {
    @NonNull
    Integer userId;

    CallbackEntry callbackEntry;
}
