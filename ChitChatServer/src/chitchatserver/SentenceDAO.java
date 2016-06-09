/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

import ChatPackage.Sentence;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */
public class SentenceDAO {
    public static ChatPackage.Sentence getSentence(int id){
        Sentence rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            rs = (Sentence)session.get(Sentence.class, id);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return rs;
    }
    public static List<ChatPackage.Sentence> getSentences(int conversationId){
        List<ChatPackage.Sentence> rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            String hql = "from Sentence s where s.conversationId=:conversationId";
            Query query = session.createQuery(hql);
            query.setInteger("conversationId", conversationId);
            rs = query.list();
        }catch(Exception ex){
            System.out.println("Error getting sentences: " + ex.getMessage());
        }finally{
            session.close();
        }
        return rs;
    }
    public static boolean addSentence(Sentence sentence){
        if(SentenceDAO.getSentence(sentence.getId())!=null){
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = session.beginTransaction();
            session.save(sentence);
            transaction.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return true;
    }
}
