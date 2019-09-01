package com.intuit.controllers;

import com.intuit.dao.entities.Rep;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.models.responses.DefaultResponse;
import com.intuit.services.RepService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.intuit.utils.Constants.JSON_CONTENT_TYPE;

/**
 * Created by Sunil on 9/1/19.
 */
@RestController
@RequestMapping("/v1/reps")
@Log4j2
public class RepController {

    @Autowired
    RepService repService;

    @PostMapping(produces = JSON_CONTENT_TYPE)
    public ResponseEntity<DefaultResponse<Map<String, Object>>> create(@RequestBody Rep rep) {
        DefaultResponse<Map<String, Object>> defaultResponse = new DefaultResponse<>();

        try {
            Map<String, Object> data = new HashMap<>();
            String repId = repService.createEntry(rep);
            data.put("id", repId);
            defaultResponse.setData(data);

            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
