package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.enums.CallbackStatus;
import com.intuit.utils.ObjectMapperUtil;
import lombok.Data;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Date;

import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Callback extends AuditModel {
    String id;
    CallbackStatus status;
    String userId;
    String timeSlotId;
    Date slotDate;
    Long startTime;
    Long endTime;

    public static Callback getInstance(Object object){
        return ObjectMapperUtil.getObjectMapper().convertValue(object, Callback.class);
    }

    public static Callback getInstance(Document doc) {
        Callback callback = ObjectMapperUtil.parseDocumentModel(doc, Callback.class);
        if ( doc.containsKey(MONGO_OBJECT_ID) ){
            callback.setId(String.valueOf(doc.get(MONGO_OBJECT_ID)));
        }
        return callback;
    }
}
