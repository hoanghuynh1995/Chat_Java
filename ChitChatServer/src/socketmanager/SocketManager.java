/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmanager;

import chitchatserver.FriendDAO;
import java.net.*;

/**
 *
 * @author ASUS
 */
public class SocketManager {
    
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(3334);
            System.out.println("Start waiting for client...");
            
            //waiting for clients to connect. Server spends 2 threads for sending and receiving data from a client
            while(true){
                Socket ss = s.accept();
                System.out.println("before starting: client port:" + ss.getPort());
                System.out.println("before starting: connection dead:" + ss.isClosed());
                //Receiver includes Sender
                Thread receiver = new Thread(new Receiver(ss));
                receiver.start();
                System.out.println("after starting: connection dead:" + ss.isClosed());
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
