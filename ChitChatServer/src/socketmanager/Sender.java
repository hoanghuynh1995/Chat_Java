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
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    ChatPackage.ChatPackage pack;
    Boolean free = true;
    private final Lock queueLock = new ReentrantLock();
    //BufferedWriter bw;
    //public static boolean breaking = false;
    public Sender(Socket s, ChatPackage.ChatPackage pack){
        this.s = s;
        this.pack = pack;
    }
    public synchronized void setChatPackage(ChatPackage.ChatPackage pack){
//        synchronized(free){
//            while(!free);
//        }
        //queueLock.lock();
        //System.out.println("Begin locking");
        free = false;
        this.pack = pack;
        synchronized(this){
            this.notify();
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //queueLock.unlock();
        //System.out.println("After locking");
    }
    @Override
    public void run() {
        ObjectOutputStream oos;
        
            try{
                oos = new ObjectOutputStream(s.getOutputStream());//intialized ObjectOutputStream once for every socket
                //creating another ObjectOutputStream to write on the same Stream will throw exception
                while(true){
                    if(pack != null){
                        queueLock.lock();
                        oos.writeObject(pack);
                        System.out.println("Sent");
                        oos.flush();
                        pack=null;
                        free = true;
                        queueLock.unlock();
                    }
                    synchronized(this){
                        this.notify();
                    }
                    //use synchronized for blocking thread, prevent this thread to use CPU
                    synchronized(this){
                        this.wait();
                    }
                }
                
                
                
                
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        
    }
    
    
    
}
