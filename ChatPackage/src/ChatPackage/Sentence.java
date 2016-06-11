/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatPackage;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Sentence implements Serializable{
    int id;
    String userId;
    int conversationId;
    String content;
    byte[] store;

    public Sentence(int id, String userId, int conversationId, String content, byte[] store) {
        this.id = id;
        this.userId = userId;
        this.conversationId = conversationId;
        this.content = content;
        this.store = store;
    }

    public Sentence() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getStore() {
        return store;
    }

    public void setStore(byte[] store) {
        this.store = store;
    }

    
}
