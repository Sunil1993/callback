package com.intuit.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Sunil on 9/1/19.
 */
@Component
public class MongoConnection {

    private static String mongoUri;
    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        mongoUri = env.getProperty("mongo.uri");
    }

    public static MongoClient mongoClient;

    public static MongoClient getClient() {
        if (mongoClient==null){
            mongoClient = new MongoClient(new MongoClientURI(mongoUri));
        }
        return mongoClient;
    }
}
