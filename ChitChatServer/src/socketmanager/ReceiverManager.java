/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ReceiverManager implements Runnable{
    public List<Receiver> receiverList;
    
    private String username;
    private ChatPackage.ChatPackage pack;
    
    private final Lock queueLock = new ReentrantLock();
   
    public ReceiverManager(){
        receiverList = new ArrayList();
        username = null;
        pack = null;
    }
    public int receiverCount(){
        return receiverList.size();
    }
    public void addReceiver(Receiver receiver){
        receiverList.add(receiver);
    }
    public synchronized void send(String username, ChatPackage.ChatPackage pack){
        
        
        this.username = username;
        this.pack = pack;
        synchronized(this){
            
            this.notify();
            try {
                this.wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ReceiverManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    @Override
    public void run() {
        while(true){
            
            queueLock.lock();
            for(int i=0;i<receiverList.size();i++){
                if(receiverList.get(i).userId.equals(username)){
                    receiverList.get(i).send(pack);
                    break;
                }
            }
            synchronized(this){
                    this.notify();
            } 
            synchronized(this){
                try {
                    
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReceiverManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            queueLock.unlock();
        
        }
    }

    public void removeReceiver(int port) {
        for(int i=0;i<receiverList.size();i++){
            if(receiverList.get(i).getPort() == port){
                receiverList.remove(i);
            }
        }
    }
    
    
}
