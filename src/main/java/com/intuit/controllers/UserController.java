package com.intuit.controllers;

import com.intuit.enums.ErrorCodes;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;
import com.intuit.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.intuit.models.responses.DefaultResponse;
import com.intuit.models.requests.UserCreateReq;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.intuit.utils.Constants.JSON_CONTENT_TYPE;

/**
 * Created by Sunil on 9/1/19.
 */
@RestController
@RequestMapping("/v1/users")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(produces = JSON_CONTENT_TYPE)
    public ResponseEntity<DefaultResponse<Map<String, Object>>> create(@RequestBody UserCreateReq userCreateReq) {
        DefaultResponse<Map<String, Object>> defaultResponse = new DefaultResponse<>();

        try {
            Map<String, Object> data = new HashMap<>();
            String userId = userService.createUser(userCreateReq);
            data.put("id", userId);
            defaultResponse.setData(data);

            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (PersistentException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
