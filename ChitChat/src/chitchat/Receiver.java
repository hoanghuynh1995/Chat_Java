/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.ChatPackage;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class Receiver implements Runnable{
    Socket s;
    ChatPackage pack;
    
    public Receiver(Socket s){
        this.s = s;
    }
    
    @Override
    public void run() {
        InputStream is;
        ObjectInputStream ois;
        try{
            is = s.getInputStream();
            ois = new ObjectInputStream(is);
            boolean loop = true;
            while(loop){
                pack = (ChatPackage)ois.readObject();
                
                //---------------------------------HANDLE CODE CASES------------------------------------------
                switch(pack.getCode()){
                    case -1:{//signup successful
                        SignUp.responseFlag = 1;
                        loop = false;
                        break;
                    }
                    case 0:{//signup failed
                        SignUp.responseFlag = 0;
                        loop = false;
                        break;
                    }
                    case 1:{//sign in successful
                        SignIn.responseFlag = 1;
                        loop = false;
                        break;
                    }
                    case 2:{//Sign in failed
                        SignIn.responseFlag = 0;
                        loop = false;
                        break;
                    }
                }
                //--------------------------------------------------------------------------------------------
                s.close();
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
