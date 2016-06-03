/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmanager;

/**
 *
 * @author ASUS
 */
import ChatPackage.*;
import chitchatserver.ConversationDAO;
import chitchatserver.FriendDAO;
import chitchatserver.UserConversationDAO;
import chitchatserver.UserDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Receiver implements Runnable{
    Socket s;
    ObjectInputStream br;
    Sender sender;
    ChatPackage pack;
    public String userId;
    
    ReceiverManager receiverManager;
    
    public Receiver(Socket s,ReceiverManager receiverManager){
        this.s = s;
        this.receiverManager = receiverManager;
        sender = new Sender(s,null);
        Thread t = new Thread(sender);
        t.start();
    }     
    
    @Override
    public void run() {
        try{
                br = new ObjectInputStream(s.getInputStream());
               }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        boolean loop = true;
        while(loop){
            try {
                if(s.isClosed()){
                    System.out.println("Connection has closed");
                    break;
                }
                pack = (ChatPackage)br.readObject();
                
                
                //-------------------------------------HANDLE CODE CASES------------------------------------
                switch(pack.getCode()){
                    case -2:{
                        userId = pack.getUsername();
                        System.out.println("Username: " + userId);
                        receiverManager.addReceiver(this);
                        System.out.println("Receiver list count: " + receiverManager.receiverCount());
                        break;
                    }
                    case 0:{
//                        ChatPackage pack = new ChatPackage();
//                        sender.setChatPackage(pack);
                        break;
                    }
                    case 1:{//sign out
                        User user = (User)pack.getContent();
                        user.setStatus("Offline");
                        UserDAO.updateUser(user);
                        loop = false;
                        break;
                    }
                    case 2:{//sign up
                        //get user
                        User user = (User)pack.getContent();
                        //check if user exist
                        if(UserDAO.addUser(user)){//if doesn't exist -> send signal code (-1) to client
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(-1);
                            sender.setChatPackage(pack);
                        }else{ // if existed -> send signal code(0) to client
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(0);
                            sender.setChatPackage(pack);
                        }
                        loop = false;
                        break;
                    }
                    case 3:{//sign in
                        User user = (User)pack.getContent();
                        if(!UserDAO.signIn(user)){//user doesn't exist
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(2);
                            sender.setChatPackage(pack);
                        }else{//sign in successful
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(1);
                            sender.setChatPackage(pack);
                            //update status
                            user.setStatus("Online");
                            UserDAO.updateUser(user);
                        }
                        break;
                    }
                    case 4:{//add friend
                        String friendId = (String)pack.getContent();
                        if(UserDAO.getUser(friendId) == null){
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(3);
                            sender.setChatPackage(pack);
                        }
                        else{ //add friend success -> add conversation
                            Friend friend = new Friend();
                            friend.setFriendId((String)pack.getContent());
                            friend.setUserId(pack.getUsername());
                            FriendDAO.addFriend(friend);
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(4);
                            sender.setChatPackage(pack);
                        }
                        System.out.println("Handle adding friend");
                        break;
                    }
                    case 5:{//get friend list
                        System.out.println("get friend list request");
                        String userId = pack.getUsername();
                        ChatPackage pack = new ChatPackage();
                        pack.setCode(5);
                        pack.setContent(FriendDAO.getFriend(userId));
                        sender.setChatPackage(pack);
                        break;
                    }
                    case 6:{//get conversation list
                        System.out.println("get conversation list request");
                        String userId = pack.getUsername();
                        //get UserConversations
                        List<UserConversation> ucList = UserConversationDAO.getUserConversation(userId);
                        List<Conversation> rs = new ArrayList<Conversation>();
                        for(int i=0;i<ucList.size();i++){
                            rs.add(ConversationDAO.getConversation(ucList.get(i).getConversationId()));
                        }
                        ChatPackage pack = new ChatPackage();
                        pack.setCode(6);
                        pack.setContent(rs);
                        sender.setChatPackage(pack);
                        break;
                    }
                }
               
                //-------------------------------------------------------------------------
                
              
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                loop = false;
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        
        try {
            s.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        receiverManager.removeReceiver(s.getPort()); 
        System.out.println("end running receiver: connection dead:" + s.isClosed());
        
    }
    public void send(ChatPackage pack){
        sender.setChatPackage(pack);
    }

    public int getPort() {
        return s.getPort();
    }
}

