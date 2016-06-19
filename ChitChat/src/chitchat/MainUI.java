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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
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
    
    DefaultListModel friendModel,conversationModel,sentenceModel;
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
        sentenceModel = new DefaultListModel();
        sentenceModel.addListDataListener(new ListDataListener(){
            @Override
            public void intervalAdded(ListDataEvent e) {
                listSentences.ensureIndexIsVisible(sentenceModel.size()-1);
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                listSentences.ensureIndexIsVisible(sentenceModel.size()-1);
            }
        });
        listSentences.setModel(sentenceModel);
        listSentences.setCellRenderer(new ConversationRenderer(userId));
        
        listSentences.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                Sentence sentence = (Sentence)(Object)listSentences.getSelectedValue();
                if(sentence.getStore()!=null){
                    byte[] bytesArray = sentence.getStore();
                    String fileName = sentence.getContent();
                    File file;
                    fileSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = fileSaver.showOpenDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        try{
                            file = fileSaver.getSelectedFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath() + "/" + fileName);
                            fileOutputStream.write(bytesArray);
                            JOptionPane.showMessageDialog(null, "Saved");
                            fileOutputStream.close();
                        }catch(Exception ex){
                            System.out.println("Error saving file: " + ex.getMessage());
                        }
                    }
                    
                }
            }
        });
        
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
        //pack.setCode(5);
        //pack.setUsername(userId);
        //sender.setChatPackage(pack);
        //System.out.println("Send friend list request");
        listFriends.setModel(friendModel);
        listFriends.setCellRenderer(new CellRenderer(userId));
        listFriends.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    sentenceModel.clear();
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
                    sentenceModel.clear();
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

        fileChooser = new javax.swing.JFileChooser();
        fileSaver = new javax.swing.JFileChooser();
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
        btnBrowse = new javax.swing.JButton();
        tfChatBox = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listSentences = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        rbOnline = new javax.swing.JRadioButton();
        rbInvisible = new javax.swing.JRadioButton();
        rbAway = new javax.swing.JRadioButton();
        lbConversationName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        fileChooser.setDialogTitle("Choose file");

        fileSaver.setDialogTitle("Choose directory");

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
                .addContainerGap(104, Short.MAX_VALUE))
        );

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

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

        jScrollPane3.setAutoscrolls(true);

        jScrollPane3.setViewportView(listSentences);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tfChatBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBrowse)
                    .addComponent(btnSend, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse))
                    .addComponent(tfChatBox)))
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

        lbConversationName.setText("Conversation name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setText("ChitChat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbConversationName)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lbConversationName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            if(tfFriendId.getText().equals(getFriendIdFromConversation(((FriendConversation)friendModel.get(i)).getConversation())))
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

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        File file;
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            
            Sentence sentence = new Sentence();
            sentence.setUserId(userId);
            sentence.setConversationId(currentConversationId);
            sentence.setContent(fileChooser.getSelectedFile().getName());
            
            byte [] byteArray  = new byte [(int)file.length()];
            try{
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                bufferedInputStream.read(byteArray,0,byteArray.length); // copied file into byteArray
            }catch(Exception ex){
                System.out.println("Error sending file: " + ex.getMessage());
            }
            sentence.setStore(byteArray);
        
            ChatPackage pack = new ChatPackage();
            pack.setCode(0);
            pack.setConversationId(currentConversationId);
            pack.setUsername("1");
            pack.setContent(sentence);

            sender.setChatPackage(pack);
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

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
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnSend;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileSaver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbConversationName;
    private javax.swing.JList<String> listConversations;
    private javax.swing.JList<String> listFriends;
    private javax.swing.JList<String> listSentences;
    private javax.swing.JRadioButton rbAway;
    private javax.swing.JRadioButton rbInvisible;
    private javax.swing.JRadioButton rbOnline;
    private javax.swing.JTextField tfChatBox;
    private javax.swing.JTextField tfFriendId;
    // End of variables declaration//GEN-END:variables


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
    public void addFriendConversation(Conversation conversation){
        FriendConversation fc = new FriendConversation();
        fc.setConversation(conversation);
        fc.setName(getFriendIdFromConversation(conversation));
        fc.setStatus("Offline");
        friendModel.addElement(fc);
        //update status
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
        for(int i=0;i<sentenceList.size();i++){
            sentenceModel.addElement(sentenceList.get(i));
        }
        this.currentConversationId = ConversationId;
    }
    void addSentence(Sentence s){
        if(s.getConversationId() != currentConversationId){
            return;
        }
        sentenceModel.addElement(s);
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
                    continue;
                }
            }
            
        }
        
    }
}


class CellRenderer implements ListCellRenderer{
    String userId;
    public CellRenderer(String userId){
        this.userId = userId;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        if(value.getClass().equals(Conversation.class)){
            String cname = ((Conversation)value).getName();
            String name = cname.substring(0,cname.indexOf(" :"));
            label.setText(name);
        }else{
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
class ConversationRenderer implements ListCellRenderer{
    String userId;
    public ConversationRenderer(String userId){
        this.userId = userId;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JTextArea label = new JTextArea();
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setWrapStyleWord(true);
        Sentence sentence = (Sentence)value;
        if(sentence.getStore() == null){
            if(sentence.getUserId().equals(userId)){
                label.setText(sentence.getContent());
                label.setBackground(Color.LIGHT_GRAY);
            }else{
                label.setText(sentence.getUserId() + " : " + sentence.getContent());
            }
        }else{
            if(sentence.getUserId().equals(userId)){
                label.setText(sentence.getContent() + "\n[File]");
                label.setBackground(new java.awt.Color(102, 204, 255));
            }else{
                label.setText(sentence.getUserId() + " : " + sentence.getContent() + "\n[File]");
                label.setBackground(new java.awt.Color(102, 204, 255));
            }
        }
        return label;
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