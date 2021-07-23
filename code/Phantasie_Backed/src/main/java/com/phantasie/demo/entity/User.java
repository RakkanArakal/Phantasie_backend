package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cartList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class User {
    private Integer user_id;

<<<<<<< HEAD:code/Phantasie_Backed/src/main/java/com/phantasie/demo/entity/User.java
=======
    private String nick_name;

    private String unlock_card;

    private String achieve;

    private String unlock_skill;

    private String unlock_equipment;

>>>>>>> efaab6e (14:15):code/Phantasie_Backend/src/main/java/com/phantasie/demo/entity/User.java
    private UserVerify userVerify;

    public User() {

    }

    @Id
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_id")
    public Integer getUser_id() { return user_id; }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Column(name = "nick_name")
    public String getNick_name() { return nick_name; }

    public void setNick_name(String name) {
        this.nick_name = name;
    }

    @OneToOne(cascade = CascadeType.ALL) // 相互关联，共同增删改查
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserVerify getUserVerify() { return userVerify; }

    public void setUserVerify(UserVerify userVerify) { this.userVerify = userVerify; }
}
