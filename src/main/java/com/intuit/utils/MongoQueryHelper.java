package com.intuit.utils;

import com.intuit.exceptions.ValidationException;
import org.bson.types.ObjectId;

/**
 * Created by Sunil on 9/2/19.
 */
public class MongoQueryHelper {

    public static ObjectId getObjectId(String idValue) throws ValidationException {
        ObjectId objectId;
        try {
            return new ObjectId(idValue);
        } catch (Exception e) {
            throw new ValidationException("INVALID value for id : " + idValue);
        }
    }
}
