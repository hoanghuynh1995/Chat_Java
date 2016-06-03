/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchatserver;

import ChatPackage.Conversation;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */
public class ConversationDAO {
    public static ChatPackage.Conversation getConversation(int conversationId){
        ChatPackage.Conversation rs = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            rs = (ChatPackage.Conversation)session.get(ChatPackage.Conversation.class, conversationId);
        }catch(Exception ex){
            System.out.println("error getting conversation");
        }finally{
            session.close();
        }
        return rs;
    }
    
    public static int addConversation(Conversation conversation){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = session.beginTransaction();
            session.save(conversation);
            transaction.commit();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            session.close();
        }
        return conversation.getId();
    }
}
