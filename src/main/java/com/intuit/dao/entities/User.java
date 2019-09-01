package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.enums.UserType;
import com.intuit.models.requests.UserCreateReq;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@Entity
@Table(name = "users")
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String email;

    @Column(name="phone_no")
    private String phoneNo;

    private String name;

    private UserType type = UserType.CUSTOMER;

    @OneToMany(mappedBy = "user")
    private List<CallbackEntry> callbackEntries;

    public static User getInstance(UserCreateReq userCreateReq) {
        User user = new User();
        user.setEmail(userCreateReq.getEmail());
        user.setName(userCreateReq.getName());
        user.setPhoneNo(userCreateReq.getPhoneNo());
        return user;
    }
}
