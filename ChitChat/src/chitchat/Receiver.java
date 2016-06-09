/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Receiver implements Runnable{
    Socket s;
    ChatPackage pack;
    MainUI MainUIRef;
    public Receiver(Socket s, MainUI ref){
        this.s = s;
        MainUIRef = ref;
    }
    public Receiver(Socket s){
        this.s = s;
    }
    @Override
    public void run() {
        InputStream is;
        ObjectInputStream ois;
        try{
            is = s.getInputStream();
            ois = new ObjectInputStream(is);
            boolean loop = true;
            while(loop){
                pack = (ChatPackage)ois.readObject();
                
                //---------------------------------HANDLE CODE CASES------------------------------------------
                switch(pack.getCode()){
                    case -3:{//test
                        JOptionPane.showMessageDialog(null, "Test!!");
                        break;
                    }
                    case -1:{//signup successful
                        SignUp.responseFlag = 1;
                        loop = false;
                        break;
                    }
                    case 0:{//signup failed
                        //SignUp.responseFlag = 0;
                        
                        //loop = false;
                        System.out.println("Received!");
                        break;
                    }
                    case 1:{//sign in successful
                        SignIn.responseFlag = 1;
                        loop = false;
                        break;
                    }
                    case 2:{//Sign in failed
                        SignIn.responseFlag = 0;
                        loop = false;
                        break;
                    }
                    case 3:{//FriendId doesn't exist
                        MainUI.responseFlag = 0;
                        System.out.println("Friend doesn't exist");
                        synchronized(MainUI.responseFlag){
                            MainUI.responseFlag.notifyAll();
                        }
                        break;
                    }
                    case 4:{//add friend successful
                        MainUI.responseFlag = 1;
                        break;
                    }
                    case 5:{//friend list
//                        System.out.println("Received friend list");
//                        List<Friend> friendList = (List<Friend>)pack.getContent();
//                        MainUIRef.addFriendList(friendList);
                        break;
                    }
                    case 6:{//conversation list
                        System.out.println("Received group conversation list");
                        List<Conversation> conversationList = (List<Conversation>)pack.getContent();
                        MainUIRef.addGroupConversationList(conversationList);
                        break;
                    }
                    case 7:{
                        System.out.println("Received friend conversation list");
                        List<Conversation> conversationList = (List<Conversation>)pack.getContent();
                        MainUIRef.addFriendConversationList(conversationList);
                        break;
                    }
                    case 8:{//conversation's sentence
                        System.out.println("Received conversation's sentence");
                        MainUIRef.setCurrentConversation((List<Sentence>)pack.getContent(),pack.getConversationId());
                        break;
                    }
                    case 9:{//chat
                        MainUIRef.addSentence((Sentence)pack.getContent());
                        break;
                    }
                    case 10:{//add group conversation
                        if(pack.getConversationId() == -1){//one of members's ids doesn't exist
                            JOptionPane.showMessageDialog(MainUIRef, "Please choose a conversation!!");
                            break;
                        }else{
                            MainUIRef.addGroupToList((Conversation)pack.getContent());
                            break;
                        }
                    }
                }
                //--------------------------------------------------------------------------------------------
                
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
