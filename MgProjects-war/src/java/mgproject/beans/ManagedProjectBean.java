/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mgproject.ejb.ChatFacade;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.TaskFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Chat;
import mgproject.entities.Project;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author inftel22
 */
@ManagedBean
@ViewScoped
public class ManagedProjectBean implements Serializable {

    @EJB
    private ChatFacade chatFacade;
    @EJB
    private ProjectFacade projectFacade;
    @EJB
    private TaskFacade taskFacade;
    @EJB
    private UsersFacade usersFacade;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private Collection<Users> list_colaborators;

    private int taskAcu;
    private int taskRep;
    private int taskPln;
    private int taskAcc;
    private boolean error;
    private boolean admin;

    /**
     * Creates a new instance of ManagedProjectBean
     */
    public ManagedProjectBean() {
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Collection<Users> getList_colaborators() {
        return list_colaborators;
    }

    public void setList_colaborators(Collection<Users> list_colaborators) {
        this.list_colaborators = list_colaborators;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public TaskFacade getTaskFacade() {
        return taskFacade;
    }

    public void setTaskFacade(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public int getTaskAcu() {
        return taskAcu;
    }

    public void setTaskAcu(int taskAcu) {
        this.taskAcu = taskAcu;
    }

    public int getTaskRep() {
        return taskRep;
    }

    public void setTaskRep(int taskRep) {
        this.taskRep = taskRep;
    }

    public int getTaskPln() {
        return taskPln;
    }

    public void setTaskPln(int taskPln) {
        this.taskPln = taskPln;
    }

    public int getTaskAcc() {
        return taskAcc;
    }

    public void setTaskAcc(int taskAcc) {
        this.taskAcc = taskAcc;
    }

    @PostConstruct
    public void init() {

        admin = false;
        if (loginBean.getIdUser().equals(loginBean.getProject().getIdAdmin().getIdUser())) {
            admin = true;
        }

        Users user = usersFacade.find(loginBean.getIdUser());
        list_colaborators = loginBean.getProject().getUsersCollection();

        if (list_colaborators.isEmpty()) {
            error = true;
        }

        List<Task> list_task = taskFacade.findTaskByProjectUser(this.loginBean.getProject());

        for (Task task : list_task) {
            if (task.getPriority().equals("acuciante")) {
                taskAcu++;
            }
            if (task.getPriority().equals("repentino")) {
                taskRep++;
            }
            if (task.getPriority().equals("plani")) {
                taskPln++;
            }
            if (task.getPriority().equals("accesorio")) {
                taskAcc++;
            }
        }
    }

    public String doDeleteProject(Project project) {
        Collection<Task> tasks = project.getTaskCollection();
        Collection<Chat> chats = project.getChatCollection();

        for (Chat chat : chats) {
            chatFacade.remove(chat);
        }

        for (Task task : tasks) {
            taskFacade.remove(task);
        }

        projectFacade.remove(project);
        loginBean.setProject_list(projectFacade.findByUser(usersFacade.find(loginBean.getIdUser())));

        return ("profile");
    }
}
