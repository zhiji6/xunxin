package com.xunxin.config;

import java.security.GeneralSecurityException;
import java.util.Date;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;
import com.xunxin.util.MD5_UTIL;
import com.xunxin.util.PeriodsUtil;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月19日 -- 下午1:38:46
 * @Version 1.0
 * @Description         邮件工具类
 */
public class SendEmail {  
     
//   public static final String HOST = "smtp.aliyun.com";       //普通邮箱
    public static final String HOST = "smtp.mxhichina.com";    //企业邮箱
//   public static final String HOST = "qiye.aliyun.com";    //企业邮箱
   public static final String PROTOCOL = "smtp";                //协议类型
//   public static final int PORT = 25;                           //端口号
   public static final int TIMELIMIT = 1000*60*60*24;           //激活邮件过期时间24小时
   public static final String FROM = "rz@xunxinkeji.cn";        //发件人的email  
   public static final String PWD = "XXKj.0000";                //发件人密码  
   
   /** 
    * 获取Session 
    * @return 
 * @throws GeneralSecurityException 
    */  
   private static Session getSession() throws GeneralSecurityException {  
       Properties props = new Properties();  
       props.put("mail.smtp.host", HOST);//设置服务器地址  
       props.put("mail.store.protocol" , PROTOCOL);//设置协议  
//       props.put("mail.smtp.port", PORT);//设置端口  
       props.put("mail.smtp.auth" , true);  
         
       //-------当需使用SSL验证时添加，邮箱不需SSL验证时删除即可（测试SSL验证使用QQ企业邮箱）
       String SSL_FACTORY="javax.net.ssl.SSLSocketFactory"; 
       props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
       props.put("mail.smtp.socketFactory.fallback", "false");
       props.put("mail.smtp.socketFactory.port", "465");
       props.put("mail.smtp.port", "465"); //google使用465或587端口
       MailSSLSocketFactory sf = new MailSSLSocketFactory();
       sf.setTrustAllHosts(true); 
       props.put("mail.smtp.ssl.socketFactory", sf);
       
       Authenticator authenticator = new Authenticator() {  
 
           @Override  
           protected PasswordAuthentication getPasswordAuthentication() {  
               return new PasswordAuthentication(FROM, PWD);  
           }  
             
       };  
       Session session = Session.getDefaultInstance(props , authenticator);  
         
       return session;  
   }  
     
   public static void send(String toEmail , String content) throws GeneralSecurityException {  
       Session session = getSession();  
       try {  
           System.out.println("--send--"+content);  
           // Instantiate a message  
           Message msg = new MimeMessage(session);  
  
           //Set message attributes  
           msg.setFrom(new InternetAddress(FROM));  
           InternetAddress[] address = {new InternetAddress(toEmail)};  
           msg.setRecipients(Message.RecipientType.TO, address);  
           msg.setSubject("循心APP邮箱认证");             //邮件标题
           msg.setSentDate(new Date());  
           msg.setContent(content , "text/html;charset=utf-8");  
  
           //Send the message  
           Transport.send(msg);  
       }  
       catch (MessagingException mex) {  
           mex.printStackTrace();  
       }  
   }  
   
   
   public static void main(String[] args) throws GeneralSecurityException {
       String mail = "noseparte@aliyun.com";
       String ID = "93431970";
       String name = "Noseparte";
       int TIMELIMIT = 1000*60*60*24; //激活邮件过期时间24小时
       //当前时间戳
       Long curTime = System.currentTimeMillis();
       //激活的有效时间    
       Long activateTime = curTime+TIMELIMIT;
       String URL = "http://localhost/app-api/personal";
       String token = MD5_UTIL.string2MD5(mail+curTime);
//       String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入循心!<br><br>帐户需要激活才能使用，赶紧激活成为循心正式的一员吧:)<br><br>请在24小时内点击下面的链接立即激活帐户："
//               +"<br><a href='"+URL+"/activatemail/?token="+token+"&email="+mail+"'>"
//               +URL+"/activatemail/?token="+token+"&email="+mail+"</a></p>";
       String content = "<p>亲爱的"+name+",您好。<br><br>您于:"+ PeriodsUtil.getWholeTime(new Date()) +"通过帐号:"+ID+"申请了循心APP的邮箱认证，请点击如下链接完成认证."
               +"<br><a href='"+URL+"/activate_mail/?token="+token+"&email="+mail+"'>"
               +URL+"/activate_mail/?token="+token+"&email="+mail+"</a><br><br>(如果您无法点击此链接，请将其复制到浏览器地址栏后访问)<br>为了保障您帐号的安全性，请在24小时内完成认证，此链接将在认证后失效！</p>";
       SendEmail.send(mail, content);
   }
   
 
}  