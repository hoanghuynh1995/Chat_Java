/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

import ChatPackage.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */
public class UserDAO {
    public static ChatPackage.User getUser(String userId){
        ChatPackage.User rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            rs = (ChatPackage.User)session.get(ChatPackage.User.class,userId);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return rs;
    }
    public static boolean addUser(User user){
        if(UserDAO.getUser(user.getId()) != null){
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return true;
    }
    public static boolean updateUser(User user){
        if(getUser(user.getId()) == null){
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return true;
    }
}
