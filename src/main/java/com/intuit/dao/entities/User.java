package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.enums.UserType;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.utils.ObjectMapperUtil;
import lombok.Data;
import org.bson.Document;

import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends UserAbstract {
    private UserType type = UserType.CUSTOMER;

    public static User getInstance(UserCreateReq userCreateReq) {
        User user = new User();
        user.setEmail(userCreateReq.getEmail());
        user.setName(userCreateReq.getName());
        user.setPhoneNo(userCreateReq.getPhoneNo());
        return user;
    }

    public static User getInstance(Document doc) {
        User user = ObjectMapperUtil.parseDocumentModel(doc, User.class);
        if ( doc.containsKey(MONGO_OBJECT_ID) ){
            user.setId(String.valueOf(doc.get(MONGO_OBJECT_ID)));
        }
        return user;
    }
}
