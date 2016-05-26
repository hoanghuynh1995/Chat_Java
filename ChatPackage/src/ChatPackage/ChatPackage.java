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
import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class ChatPackage implements Serializable{
    int code;
    String username;
    int conversationId; //==-1 if code !=0
    Object content;
    
    public ChatPackage(){
        
    }

    public ChatPackage(int code, String username, int conversationId, Object content) {
        this.code = code;
        this.username = username;
        this.conversationId = conversationId;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
    
}
