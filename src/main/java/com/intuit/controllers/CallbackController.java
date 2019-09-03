package com.intuit.controllers;

import com.intuit.dao.entities.Callback;
import com.intuit.enums.ErrorCodes;
import com.intuit.exceptions.PersistentException;
import com.intuit.exceptions.ValidationException;
import com.intuit.models.requests.CallbackEntryCreateReq;
import com.intuit.models.requests.CallbackRescheduleReq;
import com.intuit.models.requests.UserCreateReq;
import com.intuit.models.responses.DefaultResponse;
import com.intuit.services.CallbackEntryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.intuit.utils.Constants.JSON_CONTENT_TYPE;

/**
 * Created by Sunil on 9/1/19.
 */
@RestController
@RequestMapping("/v1/callbackEntries")
@Log4j2
public class CallbackController {

    @Autowired
    CallbackEntryService callbackEntryService;

    @PostMapping(produces = JSON_CONTENT_TYPE)
    public ResponseEntity<DefaultResponse<Map<String, Object>>> create(@RequestBody Callback callback) {
        DefaultResponse<Map<String, Object>> defaultResponse = new DefaultResponse<>();

        try {
            Map<String, Object> data = new HashMap<>();
            String id = callbackEntryService.add(callback);
            data.put("id", id);
            data.put("message", "Success");
            defaultResponse.setData(data);

            return new ResponseEntity<>(defaultResponse, HttpStatus.CREATED);
        } catch (ValidationException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
        } catch (PersistentException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{callbackId}/reschedule", produces = JSON_CONTENT_TYPE)
    public ResponseEntity<DefaultResponse<Map<String, Object>>> reschedule(
            @PathVariable String callbackId,
            @RequestBody CallbackRescheduleReq callbackRescheduleReq) {
        DefaultResponse<Map<String, Object>> defaultResponse = new DefaultResponse<>();

        try {
            Map<String, Object> data = new HashMap<>();
            Callback callback = Callback.getInstance(callbackRescheduleReq);
            callbackEntryService.reschedule(callbackId, callback);
            data.put("message", "Successfully re-scheduled");
            defaultResponse.setData(data);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ValidationException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
        } catch (PersistentException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{callbackId}/cancel", produces = JSON_CONTENT_TYPE)
    public ResponseEntity<DefaultResponse<Map<String, Object>>> cancelCall(
            @PathVariable String callbackId) {
        DefaultResponse<Map<String, Object>> defaultResponse = new DefaultResponse<>();

        try {
            Map<String, Object> data = new HashMap<>();
            callbackEntryService.cancel(callbackId);
            data.put("message", "cancelled");
            defaultResponse.setData(data);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ValidationException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(e.getMessage());
            return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
        } catch (PersistentException e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            defaultResponse.addError(ErrorCodes.DEFAULT_MESSAGE.getErrMsg());
            return new ResponseEntity<>(defaultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
