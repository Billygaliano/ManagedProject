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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Users;

/**
 *
 * @author alejandroruiz
 */
@ManagedBean
@RequestScoped
public class ProjectListBean implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    
    @EJB
    private UsersFacade usersFacade;
    
    @EJB
    private ProjectFacade projectFacade;

    private List<Project> list;
    private boolean error=false;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Project> getList() {
        return list;
    }

    public void setList(List<Project> list) {
        this.list = list;
    }
    
    @PostConstruct
    public void init(){
        
       Users user = usersFacade.find(loginBean.getIdUser());
       list = (List<Project>) projectFacade.findColaborations(user);
       if(list.isEmpty()){
           error=true;
       }
       loginBean.setList_colaborators(list);
        
    }
    

    /**
     * Creates a new instance of projectListBean
     */
    public ProjectListBean() {
    }
    
}
