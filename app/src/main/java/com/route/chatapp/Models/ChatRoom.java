package com.route.chatapp.Models;

public class ChatRoom {
    String id;
    String name;
    String desc;
    String CreatedAt;

    public ChatRoom() {
    }

    public ChatRoom(String name, String desc, String createdAt) {
        this.name = name;
        this.desc = desc;
        CreatedAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }
}
