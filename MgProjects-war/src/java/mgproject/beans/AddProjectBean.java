/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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
public class AddProjectBean {

    @EJB
    private UsersFacade usersFacade;

    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

    @EJB
    private ProjectFacade projectFacade;
    
    
    
    private String name;
    private String desc;
    private boolean error = false;
    private Users admin;
    
    private String IdColaborador;
    private Users colaborador;
    private List<Users> colaboradores = new ArrayList<Users>();
    private Project project;
    private boolean exito=false;
    private boolean invitacion=false;
    private boolean error2=false;
   
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public boolean isInvitacion() {
        return invitacion;
    }

    public void setInvitacion(boolean invitacion) {
        this.invitacion = invitacion;
    }
   
    

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    

    public String getIdColaborador() {
        return IdColaborador;
    }

    public void setIdColaborador(String IdColaborador) {
        this.IdColaborador = IdColaborador;
    }

    public Users getColaborador() {
        return colaborador;
    }

    public void setColaborador(Users colaborador) {
        this.colaborador = colaborador;
    }

    public List<Users> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Users> colaboradores) {
        this.colaboradores = colaboradores;
    }


    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isError2() {
        return error2;
    }

    public void setError2(boolean error2) {
        this.error2 = error2;
    }
    
    
    
  
    public AddProjectBean() {
    }
    
    @PostConstruct
    public void doInit(){
        error = false;
        invitacion=false;
        exito=false;
        error2=false;
        admin = usersFacade.find(loginBean.getIdUser());
  
    }
    
    public String doAddProject() {

        List<Project> list = projectFacade.findByNameAndUser(name, admin);
        if (list.isEmpty()) {

            project = new Project();

            project.setName(name);
            project.setDescription(desc);
            project.setIdAdmin(admin);

            projectFacade.create(project);
            loginBean.setProject(project);
            loginBean.getProject_list().add(project);
            exito = true;
            return "project";
        } else {
            error = true;
            return "addProject";
        }

    }
    
    public String doInvitar(){
            
        colaborador = usersFacade.find(IdColaborador);
        

        project = loginBean.getProject();
        if (project == null) {
            error2 = true;
        } else {

            List<Users> colaboradores = (List<Users>) project.getUsersCollection();
            colaboradores.add(colaborador);
            project.setUsersCollection(colaboradores);
            projectFacade.edit(project);
            invitacion = true;
            
            loginBean.getUsers_list().remove(colaborador);
            
        }
       return "project";
    }
    
    
}
