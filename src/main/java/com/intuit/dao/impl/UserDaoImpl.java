package com.intuit.dao.impl;

import com.intuit.dao.MongoConnection;
import com.intuit.dao.UserDao;
import com.intuit.dao.entities.User;
import com.intuit.utils.Constants;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.USER_COLLECTION;

/**
 * Created by Sunil on 9/1/19.
 */
@Service
public class UserDaoImpl implements UserDao {
    private MongoCollection<Document> getUserColl() {
        return MongoConnection.getClient().getDatabase(Constants.MONGO_DATABASE).getCollection(USER_COLLECTION);
    }


    @Override
    public String save(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        user.setCreatedAt(currentTimeMillis);
        user.setUpdatedAt(currentTimeMillis);

        Document doc = user.getDocument();
        getUserColl().insertOne(doc);
        return String.valueOf(doc.get(Constants.MONGO_OBJECT_ID));
    }
}
