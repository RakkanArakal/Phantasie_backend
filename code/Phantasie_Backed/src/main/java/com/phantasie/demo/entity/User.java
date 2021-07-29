package com.phantasie.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "userVerify", "cartList", "orderList"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class User {

<<<<<<< HEAD:code/Phantasie_Backed/src/main/java/com/phantasie/demo/entity/User.java
<<<<<<< HEAD:code/Phantasie_Backed/src/main/java/com/phantasie/demo/entity/User.java
=======
    private String nick_name;
=======
    private Integer userId;
>>>>>>> 18b44a7 (11:29):code/Phantasie_Backend/src/main/java/com/phantasie/demo/entity/User.java

    private String nickName;

    private String unlockCard;

    private String achieve;

    private String unlockSkill;

    private String unlockBuff;

>>>>>>> efaab6e (14:15):code/Phantasie_Backend/src/main/java/com/phantasie/demo/entity/User.java
    private UserVerify userVerify;

    private String job;

    private Integer skillId;

    private Integer buffId;

    private String cardLibrary;

    public User() {

        job = "Warrior";
    }

    @Id
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_id")
    public Integer getUserId() { return userId; }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }


    @OneToOne(cascade = CascadeType.ALL) // 相互关联，共同增删改查
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserVerify getUserVerify() { return userVerify; }

    public void setUserVerify(UserVerify userVerify) { this.userVerify = userVerify; }
}
