package com.example.justin.simpletwitter.model.entity;

import static java.sql.Types.INTEGER;

/**
 * Model class for a UserMention entity
 */

public class UserMention extends Entity{

    private String name;
    private String id_str;
    private int id;

    public UserMention(String text, int startIndex, int endIndex, String name, String id_str, int id) {
        super(text, startIndex, endIndex);
        this.name = name;
        this.id_str = id_str;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return "UserMention";
    }

    @Override
    public String toString() {
        return "UserMention{" +
                "name='" + name + '\'' +
                ", id_str='" + id_str + '\'' +
                ", id=" + id +
                '}';
    }
}
