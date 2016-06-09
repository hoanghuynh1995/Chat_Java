/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

import ChatPackage.UserConversation;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */
public class UserConversationDAO {
    //get userconversation by user id
    public static List<UserConversation> getUserConversation(String userId){
        List<UserConversation> rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            String hql = "from UserConversation u where u.userId=:userId";
            Query query = session.createQuery(hql);
            query.setString("userId", userId);
            rs = query.list();
        }catch(Exception ex){
            System.out.println("Error getting UserConversation" + ex.getMessage());
        }finally{
            session.close();
        }
        return rs;
    }
    //get userconversation by conversation id
    public static List<UserConversation> getUserConversation(int conversationId){
        List<UserConversation> rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            String hql = "from UserConversation u where u.conversationId=:convId";
            Query query = session.createQuery(hql);
            query.setInteger("convId", conversationId);
            rs = query.list();
        }catch(Exception ex){
            System.out.println("Error getting UserConversation" + ex.getMessage());
        }finally{
            session.close();
        }
        return rs;
    }
    public static boolean addUserConversation(UserConversation uc){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = session.beginTransaction();
            session.save(uc);
            transaction.commit();
        }catch(Exception ex){
            System.out.println("Error adding UserConversation: " + ex.getMessage());
        }finally{
            session.close();
        }
        return true;
    }
}
