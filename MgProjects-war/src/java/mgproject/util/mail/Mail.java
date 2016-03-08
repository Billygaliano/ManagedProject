/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.util.mail;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mgproject.beans.SendEmailToBean;

/**
 *
 * @author Paloma
 */
@Stateless
public class Mail {
    @ManagedProperty(value = "#{sendEmailToBean}")
    protected SendEmailToBean sendEmailToBean;
    
    String servidorSMTP = "smtp.gmail.com";
    String puerto = "587";
    String usuario = "palmargom1@gmail.com";
    String password = "estp610073016";
    
    String destiny = null;
    String subject = null;
    String mensaje = null;

    public Mail() {
    }

    public Mail(String destiny, String subject, String message) {
        this.destiny = destiny;
        this.subject = subject;
        this.mensaje = message;
    }

    public String getServidorSMTP() {
        return servidorSMTP;
    }

    public void setServidorSMTP(String servidorSMTP) {
        this.servidorSMTP = servidorSMTP;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public void sendMail() throws AddressException, MessagingException {
        destiny = "palmargom1@gmail.com";
        Properties props = new Properties();
        
        props.put("mail.debug", "false");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
        props.setProperty("mail.user", usuario);
        props.setProperty("mail.password", password);
        
        Session session = Session.getInstance(props, null);
        
        
        
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destiny));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setContent(mensaje, "text/html");
        
        Transport tr = session.getTransport("smtp");
        tr.connect(servidorSMTP, usuario, password);
        message.saveChanges();
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }
   
    
     public void sendMailTo() throws AddressException, MessagingException {
        Properties props = new Properties();
        
        props.put("mail.debug", "false");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
        props.setProperty("mail.user", usuario);
        props.setProperty("mail.password", password);
        
        Session session = Session.getInstance(props, null);
        
        
        
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destiny));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setContent(mensaje, "text/html");
        
        Transport tr = session.getTransport("smtp");
        tr.connect(servidorSMTP, usuario, password);
        message.saveChanges();
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }
    
}


