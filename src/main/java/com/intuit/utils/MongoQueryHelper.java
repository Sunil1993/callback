package com.intuit.utils;

import com.intuit.exceptions.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.intuit.utils.Constants.MONGO_OBJECT_ID;

/**
 * Created by Sunil on 9/2/19.
 */
@Log4j2
public class MongoQueryHelper {

    public static ObjectId getObjectId(String idValue) throws ValidationException {
        ObjectId objectId;
        try {
            return new ObjectId(idValue);
        } catch (Exception e) {
            throw new ValidationException("INVALID value for id : " + idValue);
        }
    }

    public static List<ObjectId> multipleObjectIds(List<String> ids) {
        List<ObjectId> objectIds = new ArrayList<>();
        for (String id : ids) {
            try {
                objectIds.add(new ObjectId(id));
            } catch (Exception e) {
                log.error("Invalid id");
            }
        }
        return objectIds;
    }

    public static Document getFilterByMultipleIdsCondition(List<String> ids) {
        List<ObjectId> objectIds = multipleObjectIds(ids);

        return new Document().append(MONGO_OBJECT_ID,
                new Document().append(Constants.MONGO_OPERATIONS.IN_OPRN, objectIds));
    }
}
