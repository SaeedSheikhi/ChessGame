package com.chess.engine.player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 2017-07-26.
 */
public class Member {

    private static Map<String, Member> MEMBER_MAP = new HashMap<>();
    private String name;
    private String email;
    private String id;
    private String imagePath;



    public Member(){}
    private Member (String id){
        this.id = id;
    }

    public static Member of(String id) {
        Member user = MEMBER_MAP.get(id);
        if (user == null) {
            user = new Member(id);
            MEMBER_MAP.put(id, user);
        }
        return user;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
