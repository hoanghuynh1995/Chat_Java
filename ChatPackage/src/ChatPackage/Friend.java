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
public class Friend implements Serializable{
    int id;
    String userId;
    String friendId;
    
    public Friend(){
        
    }

    public Friend(int id, String userId, String friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
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

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
    
    
}
