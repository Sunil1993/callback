package com.intuit.dao.entities;

import com.intuit.enums.CallbackStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@Entity
@Table(name = "entries")
public class CallbackEntry extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private CallbackStatus status;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
}
