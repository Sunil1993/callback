package com.intuit.models.responses;

import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Sunil on 9/2/19.
 */
@Data
@AllArgsConstructor
public class RepAssignCallResponse {
    User user;
    Callback callback;
}
