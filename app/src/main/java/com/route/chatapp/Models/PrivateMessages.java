package com.route.chatapp.Models;

public class PrivateMessages {
  private String id;
    private String content;
    private String senderid;
   private String sendername;
   private String recevierid;
   private String timestamp;

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }



    public PrivateMessages() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getRecevierid() {
        return recevierid;
    }

    public void setRecevierid(String recevierid) {
        this.recevierid = recevierid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

