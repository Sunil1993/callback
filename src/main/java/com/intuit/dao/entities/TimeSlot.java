package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.utils.ObjectMapperUtil;
import lombok.Data;
import org.bson.Document;

import java.sql.Time;

import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSlot extends AuditModel {
    private String id;
    private Time startTime;
    private Time endTime;

    public static TimeSlot getInstance(Document doc) {
        TimeSlot timeSlot = ObjectMapperUtil.parseDocumentModel(doc, TimeSlot.class);
        if ( doc.containsKey(MONGO_OBJECT_ID) ){
            timeSlot.setId(String.valueOf(doc.get(MONGO_OBJECT_ID)));
        }
        return timeSlot;
    }
}
