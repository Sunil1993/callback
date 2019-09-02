package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intuit.utils.ObjectMapperUtil;
import lombok.Data;
import org.bson.Document;
import java.io.Serializable;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
public abstract class AuditModel implements Serializable {
    Long createdAt;
    Long updatedAt;

    @JsonIgnore
    public Document getDocument(){
        return ObjectMapperUtil.parseObjectToDocumentRemoveNull(this);
    }
}
