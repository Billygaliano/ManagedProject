/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private ProjectFacade projectFacade;

    @EJB
    private UsersFacade usersFacade;
    
    private String idUser;
    private String nickName;
    private String urlImage;
    private String payload;
    private String email;
    private boolean singIn;
    private List<Project> project_list;
    private Project project;
    private Task editTask;
    private List<Project> list_colaborators;
    private List<Users> users_list;

    public List<Project> getList_colaborators() {
        return list_colaborators;
    }

    public void setList_colaborators(List<Project> list_colaborators) {
        this.list_colaborators = list_colaborators;
    }

    public Task getEditTask() {
        return editTask;
    }

    public void setEditTask(Task editTask) {
        this.editTask = editTask;
    }

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }    

    public List<Users> getUsers_list() {
        return users_list;
    }

    public void setUsers_list(List<Users> users_list) {
        this.users_list = users_list;
    }
    
    public List<Project> getProject_list() {
        return project_list;
    }

    public void setProject_list(List<Project> project_list) {
        this.project_list = project_list;
    }

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
    }

    public boolean isSingIn() {
        return singIn;
    }

    public void setSingIn(boolean singIn) {
        this.singIn = singIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LoginBean() {
    }

    public void init() {
        if (this.idUser == null) {
            this.singIn = false;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public String doLogin(){
        Users user = usersFacade.find(this.idUser);
        
        if( user == null ){
            Users newUser = new Users();
            newUser.setIdUser(this.idUser);
            newUser.setNick(this.nickName);
            newUser.setUrlImage(this.urlImage);
            usersFacade.create(newUser);
        }else{
            user.setIdUser(this.idUser);
            user.setNick(this.nickName);
            user.setUrlImage(this.urlImage);
            usersFacade.edit(user);
        }
        this.singIn = true;
        this.project_list = projectFacade.findByUser(user);
        this.users_list = usersFacade.findAll();
        
        return "index";
    }
    
    public String doLogout() {
        this.singIn = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }	
    
    public String doRedirectToProject(Project project){
        this.project = project;
        return "project";
    }
}
