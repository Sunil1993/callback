package com.intuit.dao.impl;

import com.intuit.dao.CallbackDao;
import com.intuit.dao.entities.Callback;
import com.intuit.exceptions.ValidationException;
import com.intuit.utils.Constants;
import com.intuit.dao.MongoConnection;
import com.intuit.utils.MongoQueryHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class CallbackDaoImpl implements CallbackDao {

    private MongoCollection<Document> getCallbackColl() {
        return MongoConnection.getClient().getDatabase(Constants.MONGO_DATABASE).getCollection(CALLBACK_COLLECTION);
    }

    @Override
    public String save(Callback callback) {
        long currentTimeMillis = System.currentTimeMillis();
        callback.setCreatedAt(currentTimeMillis);
        callback.setUpdatedAt(currentTimeMillis);

        Document doc = callback.getDocument();
        getCallbackColl().insertOne(doc);
        return String.valueOf(doc.get(MONGO_OBJECT_ID));
    }

    @Override
    public long countDocumentsInTimeSlot(Long startTime, Long endTime) {
        Document query = new Document("startTime", new Document(Constants.MONGO_COMPARATORS.GREATER_THAN_OR_EQUAL, startTime))
                .append("endTime", new Document(Constants.MONGO_COMPARATORS.LESS_THAN_OR_EQUAL, endTime));

        return getCallbackColl().countDocuments(query);
    }

    @Override
    public Callback findOne(String id) throws ValidationException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);

        Document document = new Document(MONGO_OBJECT_ID, objectId);

        FindIterable<Document> documents = getCallbackColl().find(document);

        if(documents != null && documents.first() != null) {
            return Callback.getInstance(documents.first());
        }

        return null;
    }

    @Override
    public void updateOne(String id, Callback callback) throws ValidationException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);
        Document query = new Document(MONGO_OBJECT_ID, objectId);

        callback.setUpdatedAt(System.currentTimeMillis());
        Document doc = callback.getDocument();

        getCallbackColl().updateOne(query, new Document(Constants.MONGO_OPERATIONS.SET_OPRTN, doc));
    }
}
