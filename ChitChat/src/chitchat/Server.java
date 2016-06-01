/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.ChatPackage;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Server {
    public static void main(String[] args){
        try {
            
            Socket s = new Socket("localhost",3334);
            Sender send = new Sender(s,null);
            Thread receiver = new Thread(new Receiver(s));
            Thread sender = new Thread(send);
            receiver.start();
            sender.start();
            while(true){
                DataInputStream din=new DataInputStream(System.in);
		din.readLine();
                
                ChatPackage a = new ChatPackage();
                send.setChatPackage(a);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
