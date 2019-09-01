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

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String email;

    @Column(name="phone_no")
    private String phoneNo;

    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    private UserType type = UserType.CUSTOMER;

    public static User getInstance(UserCreateReq userCreateReq) {
        User user = new User();
        user.setEmail(userCreateReq.getEmail());
        user.setName(userCreateReq.getName());
        user.setPhoneNo(userCreateReq.getPhoneNo());
        return user;
    }
}
