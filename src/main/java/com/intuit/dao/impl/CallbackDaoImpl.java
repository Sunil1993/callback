package com.intuit.dao.impl;

import com.intuit.dao.CallbackDao;
import com.intuit.dao.entities.Callback;
import com.intuit.enums.CallbackStatus;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.TimeSlotInTimestamp;
import com.intuit.utils.Constants;
import com.intuit.dao.MongoConnection;
import com.intuit.utils.MongoQueryHelper;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.CREATED_AT;
import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
@Log4j2
public class CallbackDaoImpl implements CallbackDao {

    private MongoCollection<Document> getCallbackColl() {
        return MongoConnection.getClient().getDatabase(Constants.MONGO_DATABASE).getCollection(CALLBACK_COLLECTION);
    }

    @Override
    public String save(Callback callback) throws PersistentException {
        long currentTimeMillis = System.currentTimeMillis();
        callback.setCreatedAt(currentTimeMillis);
        callback.setUpdatedAt(currentTimeMillis);

        Document doc = callback.getDocument();
        try {
            getCallbackColl().insertOne(doc);
            return String.valueOf(doc.get(MONGO_OBJECT_ID));
        } catch (MongoException e) {
            log.error("Failed persist data for callback due to" + e.getMessage());
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public long countDocumentsInTimeSlot(Long startTime, Long endTime) throws PersistentException {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, startTime))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, endTime));

        try {
            return getCallbackColl().countDocuments(query);
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public Callback findOne(String id) throws ValidationException, PersistentException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);

        Document document = new Document(MONGO_OBJECT_ID, objectId);

        try {
            FindIterable<Document> documents = getCallbackColl().find(document);

            if (documents != null && documents.first() != null) {
                return Callback.getInstance(documents.first());
            }
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }

        return null;
    }

    @Override
    public void updateOne(String id, Callback callback) throws ValidationException, PersistentException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);
        Document query = new Document(MONGO_OBJECT_ID, objectId);

        callback.setUpdatedAt(System.currentTimeMillis());
        Document doc = callback.getDocument();

        try {
            getCallbackColl().updateOne(query, new Document(Constants.MONGO_OPERATIONS.SET_OPRTN, doc));
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public List<Callback> userIdsForNotification(TimeSlotInTimestamp scheduleTimeSlot) throws PersistentException {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, scheduleTimeSlot.getStartTime()))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, scheduleTimeSlot.getEndTime()))
                .append("status", CallbackStatus.CONFIRMATION_MAIL.name());

        Bson projection = Projections.include("userId", "status");
        List<Callback> callbackList = new ArrayList<>();
        try {
            FindIterable<Document> documents = getCallbackColl().find(query).projection(projection);
            for (Document document : documents) {
                callbackList.add(Callback.getInstance(document));
            }
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }

        return callbackList;
    }

    @Override
    public long countDocumentsInTimeSlotWithStatus(Long startTime, Long endTime, CallbackStatus status) {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, startTime))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, endTime))
                .append("status", status.name());

        return getCallbackColl().countDocuments(query);
    }

    @Override
    public void updateMultipleCallbackStatus(List<String> callbackIds, CallbackStatus status) throws PersistentException {
        Document query = MongoQueryHelper.getFilterByMultipleIdsCondition(callbackIds);
        Document doc = new Document(Constants.MONGO_OPERATIONS.SET_OPRTN, new Document("status", status.name()));

        try {
            getCallbackColl().updateMany(query, doc);
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public Callback getOneWaitingCustomer(Long startTime, Long endTime, CallbackStatus status) throws PersistentException {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, startTime))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, endTime))
                .append("status", status.name());

        try {
            FindIterable<Document> documents = getCallbackColl().find(query).sort(new Document(CREATED_AT, 1)).limit(1);
            if (documents != null && documents.first() != null) {
                return Callback.getInstance(documents.first());
            }
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteOne(String callbackId) throws ValidationException, PersistentException {
        ObjectId objectId = MongoQueryHelper.getObjectId(callbackId);
        Document deleteQuery = new Document(MONGO_OBJECT_ID, objectId);

        try {
            getCallbackColl().deleteOne(deleteQuery);
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public Callback assignRep(TimeSlotInTimestamp timeSlot, String repId) throws PersistentException {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, timeSlot.getStartTime()))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, timeSlot.getEndTime()))
                .append("status", CallbackStatus.CONFIRMATION_MAIL.name());

        Callback callback = new Callback();
        callback.setRepId(repId);
        Document updateDoc = callback.getDocument();

        try {
            Document callbackDoc = getCallbackColl().findOneAndUpdate(query, new Document(Constants.MONGO_OPERATIONS.SET_OPRTN, updateDoc),
                    new FindOneAndUpdateOptions().sort(new Document(CREATED_AT, 1))
                            .returnDocument(ReturnDocument.AFTER));

            if (callbackDoc != null) {
                return Callback.getInstance(callbackDoc);
            }
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }

        return null;
    }
}
