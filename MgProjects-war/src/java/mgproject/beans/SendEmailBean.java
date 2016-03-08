/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import mgproject.util.mail.Mail;

/**
 *
 * @author Paloma
 */
@ManagedBean
@RequestScoped
public class SendEmailBean {

    @EJB
    private Mail mail;

    private String message;
    private String subject;
    private String name;
    protected boolean exito = false;
    protected String template;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    

    /**
     * Creates a new instance of sendEmailBean
     */
    public SendEmailBean() {
    }

    public void doSendMail() throws MessagingException {
        
                template = "<div style='background-color:#ececec;padding:0;margin:0;font-weight:200;width:100%!important'><span class='im'><div style='overflow:hidden;color:transparent;width:0;font-size:0;min-height:0'> El usuario con nombre" + name +":&nbsp; ha enviado el siguiente mensaje: </div> </span><table align='center' border='0' cellspacing='0' cellpadding='0' style='table-layout:fixed;font-weight:200;font-family:Helvetica,Arial,sans-serif' width='100%'><tbody> <td align='center'><center style='width:100%'>"
                + "<table bgcolor='#FFFFFF' border='0' cellspacing='0' cellpadding='0' style='margin:0 auto;max-width:512px;font-weight:200;width:inherit;font-family:Helvetica,Arial,sans-serif' width='512'> "
                + "<tbody> <td bgcolor='#F3F3F3' width='100%' style='background-color:#f3f3f3;padding:12px;border-bottom:1px solid #ececec'>"
                + "<table border='0' cellspacing='0' cellpadding='0' style='font-weight:200;width:100%!important;font-family:Helvetica,Arial,sans-serif;min-width:100%!important' width='100%'>"
                + "<tbody> <tr> <td align='left' valign='middle'><img border='0' src='https://upload.wikimedia.org/wikipedia/commons/c/c3/MP_Icon.png' height='32' width='38' style='outline:none;color:#ffffff;display:block;text-decoration:none;border-color:#ececec;border-width:1px;border-style:solid' class='CToWUd'></td>"
                + "<td width='1'>&nbsp;</td> </tr> </tbody> </table></td> </tr><tr> <td align = 'left'> "
                + "<table border  = '0' cellspacing  = '0' cellpadding  = '0' style = 'font-weight:200;font-family:Helvetica,Arial,sans-serif' width = '100%'> "
                + "<tbody>  <td width  = '100%'> <table border  = '0' cellspacing  = '0' cellpadding  = '0' style  = 'font-weight:200;font-family:Helvetica,Arial,sans-serif' width = '100%'> "
                + "<tbody>  <td width  = '100%' style  = 'padding:24px;color:#434649' > <table bgcolor  = '#FFFFFF' border  = '0' cellspacing  = '0' cellpadding  = '0' style  = 'font-weight:200;font-family:Helvetica,Arial,sans-serif' width  = '100%'> <tbody>  "
                + "<h1 style = 'padding:0;margin:0;font-weight:normal;padding-bottom:4px;font-size:20px;font-family:Helvetica Neue,Helvetica,Arial;line-height:24px' > El usuario con nombre  <strong>" + name +"</strong> ha enviado el siguiente mensaje: <strong>" + message+"</strong></h1><h2 style = 'padding:0;margin:0;font-weight:normal;font-size:20px;font-family:Helvetica Neue,Helvetica,Arial;line-height:24px'> </h2> </td> </tr> <tr><td style='padding:0 0 20px 0'> <table border  = '0' cellspacing  = '0' cellpadding  = '0' style = 'font-weight:200;font-family:Helvetica,Arial,sans-serif' width = '100%'> <tbody><tr>"
                        + "</tbody></table></td></tr></tbody></table></td> </tr> </tbody> </table></td></tr> </tbody> </table></td> </tr> <td align='left'> "
                + "<table bgcolor='#ECECEC' border='0' cellspacing='0' cellpadding='0' style='padding:0 24px;color:#999999;font-weight:200;font-family:Helvetica,Arial,sans-serif' width='100%'>  </table></td> </tbody> </table> </center></td> </tr> </tbody> </table> </div>";
        
        mail = new Mail(mail.getDestiny(), subject, template);
        mail.sendMail();
        
        exito = true;
        name = "";
        message = "";
        subject = "";

    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

}
