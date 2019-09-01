package com.intuit.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultResponse<K> {
    private K data;
    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }
}
