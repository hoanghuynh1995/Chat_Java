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

/**
 *
 * @author ASUS
 */
public class Sender implements Runnable{
    Socket s;
    ChatPackage.ChatPackage pack;
    //BufferedWriter bw;
    //public static boolean breaking = false;
    public Sender(Socket s, ChatPackage.ChatPackage pack){
        this.s = s;
        this.pack = pack;
    }
    public void setChatPackage(ChatPackage.ChatPackage pack){
        this.pack = pack;
        synchronized(this){
            this.notify();
        }
    }
    @Override
    public void run() {
        ObjectOutputStream oos;
        
            try{
                oos = new ObjectOutputStream(s.getOutputStream());//intialized ObjectOutputStream once for every socket
                //creating another ObjectOutputStream to write on the same Stream will throw exception
                while(true){
                    if(pack != null){
                        oos.writeObject(pack);
                        oos.flush();
                        pack=null;
                    }
                    
                    //use synchronized for blocking thread, prevent this thread to use CPU
                    synchronized(this){
                        wait();
                    }
                }
                
                
                
                
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        
    }
    
    
    
}
