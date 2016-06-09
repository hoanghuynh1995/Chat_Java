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
public class Conversation implements Serializable{
    int id;
    String name;
    boolean groupChat;
    int totalSentence;
    public Conversation() {
    }

   

    public Conversation(int id, String name, boolean groupChat, int totalSentence) {
        this.id = id;
        this.name = name;
        this.groupChat = groupChat;
        this.totalSentence = totalSentence;
    }

    public int getTotalSentence() {
        return totalSentence;
    }

    public void setTotalSentence(int totalSentence) {
        this.totalSentence = totalSentence;
    }

    public boolean isGroupChat() {
        return groupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.groupChat = groupChat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
