/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.ChatPackage;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Sender implements Runnable{
    Socket s;
    ChatPackage pack;
    Boolean free = false;
    private final Lock queueLock = new ReentrantLock();
    public Sender(Socket s, ChatPackage pack){
        this.s = s;
        this.pack = pack;
    }
    public synchronized void setChatPackage(ChatPackage pack){
        
        System.out.println("Begin");
        this.pack = pack;
        synchronized(this){
            this.notify();
            try {
                this.wait();
                System.out.println("Waked up");
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Sent");
    }
    @Override
    public void run() {
        ObjectOutputStream oos;
        try{
            oos = new ObjectOutputStream(s.getOutputStream());
            
            while(true){
                    queueLock.lock();
                    if(pack != null){
                    oos.writeObject(pack);
                    
                    oos.flush();
                    pack=null;
                    queueLock.unlock();
                }
                synchronized(this){
                    this.notify();
                    System.out.println("Notified");
                }
                synchronized(this){
                    this.wait();
                }
                System.out.println("Awake");
                
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
}
