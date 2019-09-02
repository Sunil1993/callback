package com.intuit.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.dao.entities.Callback;
import com.intuit.dao.entities.TimeSlot;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class CallbackEntryCreateReq extends Callback {
}
