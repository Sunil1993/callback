package com.intuit.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.dao.entities.Callback;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public class CallbackEntryCreateReq extends  Callback {
    @JsonFormat(pattern="yyyy-MM-dd", timezone="Etc/UTC")
    private Date date;
}
