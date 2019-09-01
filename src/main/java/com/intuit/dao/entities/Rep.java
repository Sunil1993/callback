package com.intuit.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.enums.UserType;
import com.intuit.models.requests.UserCreateReq;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sunil on 9/1/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class Rep{
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

    private UserType type = UserType.REP;

    public static Rep getInstance(UserCreateReq userCreateReq) {
        Rep rep = new Rep();
        rep.setEmail(userCreateReq.getEmail());
        rep.setName(userCreateReq.getName());
        rep.setPhoneNo(userCreateReq.getPhoneNo());
        return rep;
    }
}
