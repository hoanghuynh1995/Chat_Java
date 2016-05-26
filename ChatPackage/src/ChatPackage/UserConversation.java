/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatPackage;

/**
 *
 * @author ASUS
 */
public class UserConversation {
    int id;
    String userId;
    int conversationId;

    public UserConversation() {
    }
    
    public UserConversation(int id, String userId, int conversationId) {
        this.id = id;
        this.userId = userId;
        this.conversationId = conversationId;
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
    
}
