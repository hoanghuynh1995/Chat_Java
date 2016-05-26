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
    @Override
    public void run() {
        OutputStream os;
        ObjectOutputStream oos;
        try{
            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.flush();
            oos.writeObject(pack);
            oos.flush();
            oos.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
    
}
