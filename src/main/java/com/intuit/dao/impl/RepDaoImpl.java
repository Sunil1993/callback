package com.intuit.dao.impl;

import com.intuit.dao.MongoConnection;
import com.intuit.dao.RepDao;
import com.intuit.dao.entities.Rep;
import com.intuit.exceptions.PersistentException;
import com.intuit.utils.Constants;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import static com.intuit.utils.Constants.REP_COLLECTION;

/**
 * Created by Sunil on 9/2/19.
 */
@Service
public class RepDaoImpl implements RepDao {

    private MongoCollection<Document> getUserColl() {
        return MongoConnection.getClient().getDatabase(Constants.MONGO_DATABASE).getCollection(REP_COLLECTION);
    }

    @Override
    public String save(Rep rep) throws PersistentException {
        long currentTimeMillis = System.currentTimeMillis();
        rep.setCreatedAt(currentTimeMillis);
        rep.setUpdatedAt(currentTimeMillis);

        Document doc = rep.getDocument();
        try {
            getUserColl().insertOne(doc);
            return String.valueOf(doc.get(Constants.MONGO_OBJECT_ID));
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }

    @Override
    public long count() throws PersistentException {
        try {
            return getUserColl().countDocuments();
        } catch (MongoException e) {
            throw new PersistentException(e.getMessage());
        }
    }
}
