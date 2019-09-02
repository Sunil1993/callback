package com.intuit.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by Sunil on 9/1/19.
 */
public class Constants {
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String MONGO_DATABASE = "callback_service";

    public static final String CALLBACK_COLLECTION = "callback_coll";
    public static final String USER_COLLECTION = "user_coll";
    public static final String REP_COLLECTION = "rep_coll";
    public static final String TIME_SLOT_COLLECTION = "time_slot_coll";

    public static final String MONGO_OBJECT_ID = "_id";

    public static final Long REP_MAX_CALL_PER_HOUR = 10L;
    public static final Integer TIME_SLOT_DURATION = 3;

    public static final Long HOUR_DURATION = 1L * 60 * 60 * 1000;

    public static final String CREATED_AT = "createdAt";
    public static final String UPDDATED_AT = "updatedAt";

    public interface MONGO_OPERATIONS{

        String UNSET_OPRTN = "$unset";

        String SET_OPRTN   = "$set"  ;

        String OR_OPRTN   = "$or"  ;

        String ADD_TO_SET_OPRN ="$addToSet";

        String PULL_OPRN = "$pull";

        String PULL_ALL_OPRN = "$pullAll";

        String IN_OPRN = "$in";

        String NIN_OPRN = "$nin";

        String ALL_OPRN = "$all";

        String EACH_OPRN = "$each";

        String MATCH_OPRN = "$match";

        String EACH = "$each";
    }

    public interface MONGO_COMPARATORS {

        String LESS_THAN = "$lt";

        String LESS_THAN_OR_EQUAL = "$lte";

        String GREATER_THAN = " $gt";

        String GREATER_THAN_OR_EQUAL = "$gte";

        String NOT_EQUALS = "$ne";

        String EQUALS = "$eq";
    }

}
