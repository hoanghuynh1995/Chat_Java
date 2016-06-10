/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

import ChatPackage.User;
import java.util.List;
import org.hibernate.Query;
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
    public static boolean signIn(User user){
        if(getUser(user.getId()) == null){
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            List<User> temp = null;
            String hql = "from User u where u.id=:userId and u.pass=:userPass";
            Query query = session.createQuery(hql);
            query.setString("userId", user.getId());
            query.setString("userPass", user.getPass());
            temp = query.list();
            if(temp.size() == 0){
                return false;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return true;
    }
    public static String getStatus(String userId){
        String rs = "";
        User user = UserDAO.getUser(userId);
        if(user != null){
            rs = user.getStatus();
        }
        return rs;
    }
}
