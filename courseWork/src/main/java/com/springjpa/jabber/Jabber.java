package com.springjpa.jabber;
/*
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;*/


import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class Jabber {


    private static final String serverLogin = "Ekaterina98" ;
    private static final String serverPass = "";
    private static final String jabberHost = "jabber.ru";


    public static void sendJabber( String messageToClient,String userJabberLogin ) {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(serverLogin, serverPass)
                    .setXmppDomain(jabberHost)
                    .setResource("Smack")
                    .setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String arg0, SSLSession arg1) {
                            return true;
                        }
                    })
                    .setPort(5222)
                    .build();
            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect().login();
            Message message = new Message(userJabberLogin,
                    messageToClient);
            connection.sendStanza(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
