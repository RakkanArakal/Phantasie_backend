package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_verify")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "user"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "user_id")
public class UserVerify {
    private Integer user_id;
    private String username;
    private String password;

    private User user;

    public UserVerify() {

    }


    @Id
    @Column(name = "user_id")
    public Integer getUser_id() { return user_id; }

    public void setUser_id(Integer user_id) { this.user_id = user_id; }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(mappedBy = "userVerify", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
