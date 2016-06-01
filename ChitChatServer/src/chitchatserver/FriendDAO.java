/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;
import ChatPackage.Friend;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author ASUS
 */
public class FriendDAO {
    public static List<Friend> getFriend(String userid){
        List<Friend> rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            String hql = "from Friend f where f.userId=:userId";
            Query query = session.createQuery(hql);
            query.setString("userId", userid);
            rs = query.list();
        }catch(Exception ex){
            System.out.println("Error getting friend list");
        }finally{
            session.close();
        }
        return rs;
    }
    public static boolean addFriend(Friend friend){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = session.beginTransaction();
            session.save(friend);
            transaction.commit();
        }catch(Exception ex){
            System.out.println("Error adding friend");
            return false;
        }finally{
            session.close();
        }
        return true;
    }
}
