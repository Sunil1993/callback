package com.intuit.dao.impl;

import com.intuit.dao.MongoConnection;
import com.intuit.dao.TimeSlotDao;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.utils.Constants;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.springframework.stereotype.Service;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.MONGO_OPERATIONS.SET_OPRTN;
import static com.intuit.utils.Constants.TIME_SLOT_COLLECTION;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class TimeSlotDaoImpl implements TimeSlotDao {
    private MongoCollection<Document> getTimeSlotColl() {
        return MongoConnection.getClient().getDatabase(Constants.MONGO_DATABASE).getCollection(TIME_SLOT_COLLECTION);
    }


    @Override
    public void upsert(TimeSlot timeSlot) {
        long currentTimeMillis = System.currentTimeMillis();
        timeSlot.setCreatedAt(currentTimeMillis);
        timeSlot.setUpdatedAt(currentTimeMillis);

        Document query = new Document("startTime", timeSlot.getStartTime()).append("endTime", timeSlot.getEndTime());
        Document doc = timeSlot.getDocument();
        getTimeSlotColl().updateOne(query, new Document(SET_OPRTN, doc), new UpdateOptions().upsert(true));
    }
}
