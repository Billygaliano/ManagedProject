/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import mgproject.ejb.AttachmentFacade;
import mgproject.entities.Attachment;

/**
 *
 * @author inftel23
 */
@Named(value = "fileUploadBean")
@ViewScoped
public class FileUploadBean implements Serializable{

    @EJB
    private AttachmentFacade attachmentFacade;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
   
    
    /**
     * Creates a new instance of FilesLoadBean
     */
    private Part file;
    private List<Attachment> attachment_list;

    public List<Attachment> getAttachment_list() {
        return attachment_list;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    @PostConstruct
    public void init(){
        this.attachment_list = attachmentFacade.findByIdProject(this.loginBean.getProject().getIdProject());
    }
  
    public String doUpLoadFile(){
        
        return "project";
    }
}
