package com.intuit.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.dao.TimeSlotDao;
import com.intuit.dao.entities.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Component
public class TimeSlotComponent implements ApplicationRunner {
    @Autowired
    TimeSlotDao timeSlotDao;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files" + File.separator + "timeslots.json");
        List<TimeSlot> timeSlots = objectMapper.readValue(inputStream, new TypeReference<List<TimeSlot>>(){});

        for(TimeSlot timeSlot: timeSlots) {
            timeSlotDao.upsert(timeSlot);
        }
    }
}
