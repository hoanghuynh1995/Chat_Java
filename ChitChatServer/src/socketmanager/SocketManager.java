/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmanager;

import chitchatserver.ConversationDAO;
import chitchatserver.FriendDAO;
import chitchatserver.SentenceDAO;
import chitchatserver.UserConversationDAO;
import java.net.*;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class SocketManager {
    
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(3334);
            System.out.println("Start waiting for client...");
            
            ReceiverManager receiverManager = new ReceiverManager();
            Thread t = new Thread(receiverManager);
            t.setPriority(3);
            t.start();
            //waiting for clients to connect. Server spends 2 threads for sending and receiving data from a client
            while(true){
                Socket ss = s.accept();
                System.out.println("before starting: client port:" + ss.getPort());
                System.out.println("before starting: connection dead:" + ss.isClosed());
                //Receiver includes Sender
                Receiver r = new Receiver(ss,receiverManager);
                System.out.println("Receiver list count: " + receiverManager.receiverCount());
                Thread receiver = new Thread(r);
                receiver.setPriority(1);
                receiver.start();
                System.out.println("after starting: connection dead:" + ss.isClosed());
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
