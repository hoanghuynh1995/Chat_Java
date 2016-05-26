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
import ChatPackage.ChatPackage;
import ChatPackage.User;
import chitchatserver.UserDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Receiver implements Runnable{
    Socket s;
    ObjectInputStream br;
    
    ChatPackage pack;
    
    public Receiver(Socket s){
        this.s = s;
    }                  
    @Override
    public void run() {
        try{
                br = new ObjectInputStream(s.getInputStream());
               }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        boolean loop = true;
        while(loop){
            try {
                pack = (ChatPackage)br.readObject();
                
                
                //-------------------------------------HANDLE CODE CASES------------------------------------
                switch(pack.getCode()){
                    case 1:{//sign out
                        User user = (User)pack.getContent();
                        user.setStatus("Offline");
                        UserDAO.updateUser(user);
                        loop = false;
                        break;
                    }
                    case 2:{//sign up
                        //get user
                        User user = (User)pack.getContent();
                        //check if user exist
                        if(UserDAO.addUser(user)){//if doesn't exist -> send signal code (-1) to client
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(-1);
                            Thread t = new Thread(new Sender(s,pack));
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{ // if existed -> send signal code(0) to client
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(0);
                            Thread t = new Thread(new Sender(s,pack));
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        loop = false;
                        break;
                    }
                    case 3:{//sign in
                        User user = (User)pack.getContent();
                        if(UserDAO.getUser(user.getId()) == null){//user doesn't exist
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(2);
                            Thread t = new Thread(new Sender(s,pack));
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            loop = false;//disconnect if signing in fails
                        }else{//sign in successful
                            ChatPackage pack = new ChatPackage();
                            pack.setCode(1);
                            Thread t = new Thread(new Sender(s,pack));
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //update status
                            user.setStatus("Online");
                            UserDAO.updateUser(user);
                        }
                        break;
                    }
                }
               
                //-------------------------------------------------------------------------
                
              s.close();
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }
        
           
        System.out.println("end running receiver: connection dead:" + s.isClosed());
        
    }
    
}

