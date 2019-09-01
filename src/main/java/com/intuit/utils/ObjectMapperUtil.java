package com.intuit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by Sunil on 9/1/19.
 */
public class ObjectMapperUtil {
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method will remove all the fields which are null.
     * @param obj
     * @return
     */
    public static Document parseObjectToDocumentRemoveNull(Object obj) {
        Document doc = objectMapper.convertValue(obj, Document.class);
        for (Map.Entry<String, Object> entry : new HashSet<>(doc.entrySet())){
            if (entry.getValue() == null){
                doc.remove(entry.getKey());
            }
        }
        return doc;
    }
}
