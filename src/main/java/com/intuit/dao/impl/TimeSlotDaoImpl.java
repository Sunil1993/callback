package com.intuit.dao.impl;

import com.intuit.dao.MongoConnection;
import com.intuit.dao.TimeSlotDao;
import com.intuit.dao.entities.TimeSlot;
import com.intuit.exceptions.ValidationException;
import com.intuit.utils.Constants;
import com.intuit.utils.MongoQueryHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.MONGO_OBJECT_ID;
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

        Document query = new Document("startTime", String.valueOf(timeSlot.getStartTime())).append("endTime", String.valueOf(timeSlot.getEndTime()));
        Document doc = timeSlot.getDocument();
        getTimeSlotColl().updateOne(query, new Document(SET_OPRTN, doc), new UpdateOptions().upsert(true));
    }

    @Override
    public TimeSlot getTime(String id) throws ValidationException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);

        Document document = new Document(MONGO_OBJECT_ID, objectId);

        FindIterable<Document> documents = getTimeSlotColl().find(document);

        if(documents != null && documents.first() != null) {
            return TimeSlot.getInstance(documents.first());
        }

        return null;
    }
}
