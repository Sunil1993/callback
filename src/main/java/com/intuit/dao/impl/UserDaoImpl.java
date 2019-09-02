package com.intuit.dao.impl;

import com.intuit.dao.MongoConnection;
import com.intuit.dao.UserDao;
import com.intuit.dao.entities.User;
import com.intuit.exceptions.ValidationException;
import com.intuit.utils.Constants;
import com.intuit.utils.MongoQueryHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.intuit.utils.Constants.CALLBACK_COLLECTION;
import static com.intuit.utils.Constants.MONGO_OBJECT_ID;
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

    @Override
    public User findOne(String id) throws ValidationException {
        ObjectId objectId = MongoQueryHelper.getObjectId(id);

        Document document = new Document(MONGO_OBJECT_ID, objectId);

        FindIterable<Document> documents = getUserColl().find(document);

        if(documents != null && documents.first() != null) {
            return User.getInstance(documents.first());
        }

        return null;
    }

    @Override
    public List<String> findEmailIdsForUsers(List<String> userIds) {
        Document filterByMultipleIdsCondition = MongoQueryHelper.getFilterByMultipleIdsCondition(userIds);
        Bson projections = Projections.include("email");

        FindIterable<Document> documents = getUserColl().find(filterByMultipleIdsCondition).projection(projections);

        List<String> emails = new ArrayList<>();
        for(Document document : documents) {
            emails.add(String.valueOf(document.get("email")));
        }

        return emails;
    }
}
