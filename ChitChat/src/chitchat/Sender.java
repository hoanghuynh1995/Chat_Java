/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.ChatPackage;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class Sender implements Runnable{
    Socket s;
    ChatPackage pack;
    public Sender(Socket s, ChatPackage pack){
        this.s = s;
        this.pack = pack;
    }
    public void setChatPackage(ChatPackage pack){
        this.pack = pack;
        synchronized(this){
            this.notify();
        }
    }
    @Override
    public void run() {
        ObjectOutputStream oos;
        try{
            oos = new ObjectOutputStream(s.getOutputStream());
            
            while(true){
                    if(pack != null){
                    oos.writeObject(pack);
                    oos.flush();
                    pack=null;
                }
                synchronized(this){
                    this.wait();
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
}
