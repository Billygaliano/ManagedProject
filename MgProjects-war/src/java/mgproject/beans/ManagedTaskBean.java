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
import mgproject.ejb.TaskFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@ViewScoped
public class ManagedTaskBean implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private TaskFacade taskFacade;
    private String name;
    private Long idproject;
    private String priority;
    private int time;
    private String timeType;
    private List<Task> task_list;
    private String userid;
    private boolean taskNoAdded;
    private String editUser;

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
    }

    public TaskFacade getTaskFacade() {
        return taskFacade;
    }

    public void setTaskFacade(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    public boolean isTaskNoAdded() {
        return taskNoAdded;
    }

    public void setTaskNoAdded(boolean taskNoAdded) {
        this.taskNoAdded = taskNoAdded;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Task> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<Task> task_list) {
        this.task_list = task_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdproject() {
        return idproject;
    }

    public void setIdproject(Long idproject) {
        this.idproject = idproject;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    /**
     * Creates a new instance of ManagedTaskBean
     */
    public ManagedTaskBean() {
    }

    @PostConstruct
    public void init() {
        List<Task> list_task = taskFacade.findTaskByProjectUser(this.loginBean.getProject());
        Users user = usersFacade.find(this.loginBean.getIdUser());
        this.task_list = list_task;
        this.taskNoAdded = false;
    }

    public String doAddTask() {
        this.taskNoAdded = false;
        Users user = usersFacade.find(this.userid);

        List<Task> tasksadded = taskFacade.findTaskByNameIdproject(loginBean.getProject(), this.name);

        Task addTask = new Task();
        addTask.setName(this.name);
        addTask.setTime(this.time);
        addTask.setTimetype(this.timeType);
        addTask.setPriority(this.priority);
        addTask.setIdProject(loginBean.getProject());

        if (tasksadded.isEmpty()) {
            taskFacade.create(addTask);
            Collection<Users> collectionUser = addTask.getUsersCollection();
            collectionUser.add(user);
            taskFacade.edit(addTask);
            this.task_list.add(addTask);
        } else {
            this.taskNoAdded = true;
        }
        return "project";
    }

    public String doDeleteTask(Task task) {
        this.taskNoAdded = false;
        Task eraseTask = taskFacade.find(task.getIdTask());
        taskFacade.remove(eraseTask);
        this.task_list.remove(eraseTask);
        return "project";
    }

    public String doEditTask(Task task) {
        Task editTask = task;
        Users user = usersFacade.find(this.editUser);
        Collection<Users> collecttion_user = editTask.getUsersCollection();
        collecttion_user.clear();
        collecttion_user.add(user);
        editTask.setUsersCollection(collecttion_user);
        taskFacade.edit(editTask);
        return "project";
    }
}
