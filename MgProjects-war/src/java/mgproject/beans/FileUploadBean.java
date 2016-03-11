/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import mgproject.ejb.AttachmentFacade;
import mgproject.entities.Attachment;

/**
 *
 * @author inftel23
 */
@ManagedBean
@ViewScoped
public class FileUploadBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public FileUploadBean() {
    }

    @EJB
    private AttachmentFacade attachmentFacade;
    private Part file;
    private static final String FILES_PATH = "resources/";
    private String path = null;
    private Collection <Attachment> attach_list;

 

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Collection<Attachment> getAttach_list() {
        return attach_list;
    }

    public void setAttach_list(Collection<Attachment> attach_list) {
        this.attach_list = attach_list;
    }
    
    @PostConstruct
     public void init(){
        attach_list = loginBean.getProject().getAttachmentCollection();
     }

    public String doUpLoadFile() throws IOException {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        this.path = path + FILES_PATH + loginBean.getProject().getIdProject() + "/";
        
        InputStream inputStream = file.getInputStream();
        System.out.println(this.path + getFilename(file));
        File f = new File(this.path);
        if (!f.exists()) {
            f.mkdir();
        }
        FileOutputStream outputStream = new FileOutputStream(this.path + getFilename(file));

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();
        Attachment attach = new Attachment();
        attach.setIdProject(loginBean.getProject());
        attach.setNombre(getFilename(file));
        //No estoy convencido
        attach.setBlob("resources/"+loginBean.getProject().getIdProject() +"/"+ getFilename(file));
        attachmentFacade.create(attach);
        this.attach_list.add(attach);
        
        
        return "project";
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
    public void diRedirect(String redirect) throws IOException{
        FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect(redirect);
    }

}
