/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chitchat;

import ChatPackage.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class MainUI extends javax.swing.JFrame {

    /**
     * Creates new form MainUI
     */
    public static Integer responseFlag = -1;
    public static String userId;
    
    DefaultListModel friendModel,conversationModel;
    Socket s;
    Sender sender;
    
    
    
    
    List<Sentence> currentConversation = null;
    int currentConversationId = -1;
    
    public MainUI(String userId) {
        this.userId = userId;
        initComponents();
        this.setTitle("ChitChat - " + userId);
        friendModel = new DefaultListModel();
        conversationModel = new DefaultListModel();
        
        init();
        
    }
    void init(){
        connectionInit();
        friendListInit();
        conversationListInit();
        
    }
    void connectionInit(){
        try{
            s = new Socket("localhost",3334);
            sender = new Sender(s,null);
            //second parameter is used for MainUI to receive data from receiver
            Thread receiver = new Thread(new Receiver(s,this));
            receiver.setPriority(1);
            receiver.start();
            Thread senderThread = new Thread(sender);
            senderThread.setPriority(Thread.MAX_PRIORITY);
            senderThread.start();
            Thread.sleep(100);
            ChatPackage pack = new ChatPackage();
            pack.setCode(-2);
            pack.setUsername(userId);
            sender.setChatPackage(pack);
        }catch(Exception ex){
            System.out.println("Connection error: " + ex.getMessage());
        }
    }
    void friendListInit(){
        ChatPackage pack = new ChatPackage();
        pack.setCode(5);
        pack.setUsername(userId);
        sender.setChatPackage(pack);
        System.out.println("Send friend list request");
        listFriends.setModel(friendModel);
        listFriends.setCellRenderer(new CellRenderer(userId));
        listFriends.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    tfConversation.setText("");
                    //lbConversationName.setText(CellRenderer.friendName(((Conversation)(Object)listFriends.getSelectedValue()).getName(), userId));
                    lbConversationName.setText(((FriendConversation)(Object)listFriends.getSelectedValue()).getName());
                    Conversation a = ((FriendConversation)(Object)listFriends.getSelectedValue()).getConversation();
                    int index = listFriends.getSelectedIndex();
                    ChatPackage pack = new ChatPackage();
                    pack.setCode(7);
                    pack.setConversationId(a.getId());
                    sender.setChatPackage(pack);
                }
            }
        });
    }
    void conversationListInit(){
        ChatPackage pack = new ChatPackage();
        pack.setCode(6);
        pack.setUsername(userId);
        System.out.println("Send conversation list request");
        sender.setChatPackage(pack);
        listConversations.setModel(conversationModel);
        listConversations.setCellRenderer(new CellRenderer(userId));
        listConversations.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    tfConversation.setText("");
                    lbConversationName.setText(((Conversation)(Object)listConversations.getSelectedValue()).getName());
                    Conversation a = (Conversation)(Object)listConversations.getSelectedValue();
                    
                    ChatPackage pack = new ChatPackage();
                    pack.setCode(7);
                    pack.setConversationId(a.getId());
                    sender.setChatPackage(pack);
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFriends = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listConversations = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        btnAddGroup = new javax.swing.JButton();
        btnAddFriend = new javax.swing.JButton();
        tfFriendId = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lbConversationName = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfConversation = new javax.swing.JTextArea();
        tfChatBox = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        rbOnline = new javax.swing.JRadioButton();
        rbInvisible = new javax.swing.JRadioButton();
        rbAway = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Friends");

        jScrollPane1.setViewportView(listFriends);

        jLabel2.setText("Group");

        jScrollPane2.setViewportView(listConversations);

        btnAddGroup.setText("Add Group");
        btnAddGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddGroupActionPerformed(evt);
            }
        });

        btnAddFriend.setText("Add friend");
        btnAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFriendActionPerformed(evt);
            }
        });

        tfFriendId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFriendIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(tfFriendId, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddGroup)
                    .addComponent(btnAddFriend))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(tfFriendId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddFriend)
                .addGap(57, 57, 57)
                .addComponent(btnAddGroup)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbConversationName.setText("Conversation name");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tfConversation.setEditable(false);
        tfConversation.setColumns(20);
        tfConversation.setLineWrap(true);
        tfConversation.setRows(5);
        tfConversation.setWrapStyleWord(true);
        jScrollPane3.setViewportView(tfConversation);

        tfChatBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfChatBoxActionPerformed(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tfChatBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbConversationName)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lbConversationName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfChatBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend)))
        );

        jLabel3.setText("Status:");

        rbOnline.setSelected(true);
        rbOnline.setText("Online");
        rbOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbOnlineActionPerformed(evt);
            }
        });

        rbInvisible.setText("Invisible");
        rbInvisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInvisibleActionPerformed(evt);
            }
        });

        rbAway.setText("Away");
        rbAway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAwayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbOnline)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                .addComponent(rbAway)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbInvisible)
                .addGap(62, 62, 62))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rbOnline)
                    .addComponent(rbInvisible)
                    .addComponent(rbAway))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFriendActionPerformed
        if(tfFriendId.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please type friend's username!!");
            return;
        }
        for(int i=0;i<friendModel.getSize();i++){
            if(tfFriendId.getText().equals(getFriendIdFromConversation((Conversation)friendModel.get(i))))
            {
                JOptionPane.showMessageDialog(this, "This user was already your friend!!");
                return;
            }
        }
        ChatPackage pack = new ChatPackage();
        pack.setCode(4);
        pack.setUsername(userId);
        pack.setContent(tfFriendId.getText());
        sender.setChatPackage(pack);
        //while(MainUI.responseFlag==-1);
        //System.out.println("MainUI: Friend doesn't exist");
        while(true){
            System.out.print(1);
            if(MainUI.responseFlag == 0){
                JOptionPane.showMessageDialog(this, "Friend's username doesn't exist!!");
                MainUI.responseFlag = -1;
                return;
            }
            if(MainUI.responseFlag == 1){
                JOptionPane.showMessageDialog(this, "Add friend successful!!");
                MainUI.responseFlag = -1;
                //friendModel.addElement(tfFriendId.getText());
                ChatPackage temp = new ChatPackage();
                temp.setCode(6);
                temp.setUsername(userId);
                sender.setChatPackage(temp);
                tfFriendId.setText("");
                return;
            }
        }
    }//GEN-LAST:event_btnAddFriendActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if(tfChatBox.getText().equals("")){
            return;
        }
        if(currentConversationId == -1){
            JOptionPane.showMessageDialog(this, "Please choose a conversation!!");
            return;
        }
        Sentence sentence = new Sentence();
        sentence.setUserId(userId);
        sentence.setConversationId(currentConversationId);
        sentence.setContent(tfChatBox.getText());
        
        ChatPackage pack = new ChatPackage();
        pack.setCode(0);
        pack.setConversationId(currentConversationId);
        pack.setContent(sentence);
        pack.setUsername(userId);
        
        sender.setChatPackage(pack);
        tfChatBox.setText("");
    }//GEN-LAST:event_btnSendActionPerformed

    private void tfChatBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChatBoxActionPerformed
        btnSend.doClick();
    }//GEN-LAST:event_tfChatBoxActionPerformed

    private void tfFriendIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFriendIdActionPerformed
        btnAddFriend.doClick();
    }//GEN-LAST:event_tfFriendIdActionPerformed

    private void btnAddGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddGroupActionPerformed
        AddGroupDialog dialog = new AddGroupDialog(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnAddGroupActionPerformed

    private void rbOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbOnlineActionPerformed
        rbAway.setSelected(false);
        rbInvisible.setSelected(false);
        ChatPackage pack = new ChatPackage();
        pack.setCode(9);
        pack.setUsername(userId);
        pack.setContent("Online");
        sender.setChatPackage(pack);
    }//GEN-LAST:event_rbOnlineActionPerformed

    private void rbAwayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAwayActionPerformed
        rbOnline.setSelected(false);
        rbInvisible.setSelected(false);
        ChatPackage pack = new ChatPackage();
        pack.setCode(9);
        pack.setUsername(userId);
        pack.setContent("Away");
        sender.setChatPackage(pack);
    }//GEN-LAST:event_rbAwayActionPerformed

    private void rbInvisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInvisibleActionPerformed
        rbOnline.setSelected(false);
        rbAway.setSelected(false);
        ChatPackage pack = new ChatPackage();
        pack.setCode(9);
        pack.setUsername(userId);
        pack.setContent("Offline");
        sender.setChatPackage(pack);
    }//GEN-LAST:event_rbInvisibleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI(userId).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFriend;
    private javax.swing.JButton btnAddGroup;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbConversationName;
    private javax.swing.JList<String> listConversations;
    private javax.swing.JList<String> listFriends;
    private javax.swing.JRadioButton rbAway;
    private javax.swing.JRadioButton rbInvisible;
    private javax.swing.JRadioButton rbOnline;
    private javax.swing.JTextField tfChatBox;
    private javax.swing.JTextArea tfConversation;
    private javax.swing.JTextField tfFriendId;
    // End of variables declaration//GEN-END:variables

//    public void addFriendList(List<Friend> friendList){
//        for(int i=0;i<friendList.size();i++){
//            friendModel.addElement(friendList.get(i).getFriendId());
//        }
//    }
    public void addGroupConversationList(List<Conversation> conversationList){
        
        conversationModel.clear();
        for(int i=0;i<conversationList.size();i++){
            conversationModel.addElement(conversationList.get(i));//add conversation only
        }
    }
    public void addFriendConversationList(List<Conversation> conversationList){
        friendModel.clear();
        for(int i=0;i<conversationList.size();i++){
            //must add FriendConversation
            FriendConversation fc = new FriendConversation();
            fc.setConversation(conversationList.get(i));
            fc.setName(getFriendIdFromConversation(conversationList.get(i)));
            fc.setStatus("Offline");
            friendModel.addElement(fc);
        }
        ChatPackage pack = new ChatPackage();
        pack.setCode(10);
        sender.setChatPackage(pack);
    }
    
    String getFriendIdFromConversation(Conversation c){
        String rs = null;
        String cname = ((Conversation)c).getName();
        String name1 = cname.substring(0,cname.indexOf(" - "));
        String name2 = cname.substring(cname.indexOf(" - ") + 3, cname.length());
        return name1.equals(userId)?name2:name1;
    }
    void setCurrentConversation(List<Sentence> sentenceList,int ConversationId){
        //Collections.sort(sentenceList,new SentenceListComparator());
        currentConversation = sentenceList;
        
        renderConversation();
        this.currentConversationId = ConversationId;
    }
    void renderConversation(){
        for(int i=0;i<currentConversation.size();i++){
            tfConversation.append(currentConversation.get(i).getUserId() + " : " +
                    currentConversation.get(i).getContent() + "\n");
        }
        tfConversation.setCaretPosition(tfConversation.getDocument().getLength());
        
    }
    void addSentence(Sentence s){
        if(s.getConversationId() != currentConversationId){
            return;
        }
        currentConversation.add(s);
        tfConversation.append(s.getUserId() + " : " + s.getContent() + "\n");
        tfConversation.setCaretPosition(tfConversation.getDocument().getLength());
    }
    
    void addGroupToList(Conversation groupConversation){
        conversationModel.addElement(groupConversation);
    }

    void addGroupRequest(List<String> UserIds, String groupName) {
        if(UserIds.indexOf(userId) == -1){
            UserIds.add(0, userId);
        }
        groupName += " : ";
        for(int i=0;i<UserIds.size();i++){
            groupName += UserIds.get(i) + " - ";
        }
        groupName = groupName.substring(0,groupName.length()-3);
        ChatPackage pack = new ChatPackage();
        pack.setCode(8);
        pack.setUsername(groupName);//userId temperarily to be groupName for this case
        pack.setContent(UserIds);
        sender.setChatPackage(pack);
    }
    
    void setFriendStatus(String userId, String status){
        for(int i=0;i<friendModel.size();i++){
            if(((FriendConversation)friendModel.get(i)).getName().equals(userId)){
                FriendConversation fc = (FriendConversation)friendModel.get(i);
                fc.setStatus(status);
                friendModel.set(i, fc);
                //((FriendConversation)friendModel.get(i)).setStatus(status);
                return;
            }
        }
    }
    void setFriendsStatus(HashMap map){
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            String userId = (String)entry.getKey();
            String status = (String)entry.getValue();
            for(int i=0;i<friendModel.size();i++){
                if(((FriendConversation)friendModel.get(i)).getName().equals(userId)){
                    FriendConversation fc = (FriendConversation)friendModel.get(i);
                    fc.setStatus(status);
                    friendModel.set(i, fc);
                    //((FriendConversation)friendModel.get(i)).setStatus(status);
                    continue;
                }
            }
            
        }
        
    }
}

class SentenceListComparator implements Comparator<Sentence>{

    @Override
    public int compare(Sentence o1, Sentence o2) {
        return ((Integer)o1.getSequence()).compareTo((Integer)o2.getSequence());
    }
    
}

class CellRenderer implements ListCellRenderer{
    String userId;
    public CellRenderer(String userId){
        this.userId = userId;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //setText(((student)value).name);
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        //if(((FriendConversation)(value)).getConversation().isGroupChat()){
        if(value.getClass().equals(Conversation.class)){
            String cname = ((Conversation)value).getName();
            String name = cname.substring(0,cname.indexOf(" :"));
            label.setText(name);
        }else{
//            String cname = ((Conversation)value).getName();
//            String name1 = cname.substring(0,cname.indexOf(" - "));
//            String name2 = cname.substring(cname.indexOf(" - ") + 3, cname.length());
//            label.setText(name1.equals(userId)?name2:name1);
            label.setText(((FriendConversation)value).getName());
            if(((FriendConversation)value).getStatus().equals("Online")){
                label.setBackground(Color.GREEN);
            }
            if(((FriendConversation)value).getStatus().equals("Away")){
                label.setBackground(Color.RED);
            }
            if(((FriendConversation)value).getStatus().equals("Offline")){
                label.setBackground(Color.WHITE);
                
            }
        }
        if(isSelected){
            label.setBackground(Color.LIGHT_GRAY);
        }
        return label;
    }
    
    public static String friendName(String conversationName, String userName){
        if(conversationName == ""){
            return "";
        }
        String name1 = conversationName.substring(0,conversationName.indexOf(" - "));
        String name2 = conversationName.substring(conversationName.indexOf(" - ") + 3, conversationName.length());
        return name1.equals(userName)?name2:name1;
    }
}
class FriendConversation{
    private Conversation conversation;
    private String status;
    private String name;
    public FriendConversation(Conversation conversation, String status) {
        this.conversation = conversation;
        this.status = status;
        this.name = "Not available";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public FriendConversation(){
        status = "Offline";
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
}