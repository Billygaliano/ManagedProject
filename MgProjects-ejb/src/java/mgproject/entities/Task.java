/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author inftel22
 */
@Entity
@Table(name = "TASK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByIdTask", query = "SELECT t FROM Task t WHERE t.idTask = :idTask"),
    @NamedQuery(name = "Task.findByName", query = "SELECT t FROM Task t WHERE t.name = :name"),
    @NamedQuery(name = "Task.findByTime", query = "SELECT t FROM Task t WHERE t.time = :time"),
    @NamedQuery(name = "Task.findByTimetype", query = "SELECT t FROM Task t WHERE t.timetype = :timetype"),
    @NamedQuery(name = "Task.findByPriority", query = "SELECT t FROM Task t WHERE t.priority = :priority"),
    @NamedQuery(name = "Task.findByProjectUser", query = "SELECT t FROM Task t WHERE t.idProject = :idproject"),
    @NamedQuery(name = "Task.findByNameAnProject", query = "SELECT t FROM Task t WHERE t.name = :name AND t.idProject = :idproject")})
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="TASK_SEQUENCE") 
    @SequenceGenerator(name="TASK_SEQUENCE",sequenceName="task_seq", allocationSize=1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TASK")
    private Long idTask;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 140)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    private int time;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIMETYPE")
    private String timetype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PRIORITY")
    private String priority;
    @JoinTable(name = "TASK_GROUP", joinColumns = {
        @JoinColumn(name = "ID_TASK", referencedColumnName = "ID_TASK")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")})
    @ManyToMany
    private Collection<Users> usersCollection;
    @JoinColumn(name = "ID_PROJECT", referencedColumnName = "ID_PROJECT")
    @ManyToOne(optional = false)
    private Project idProject;

    public Task() {
    }

    public Task(Long idTask) {
        this.idTask = idTask;
    }

    public Task(Long idTask, String name, int time, String timetype, String priority) {
        this.idTask = idTask;
        this.name = name;
        this.time = time;
        this.timetype = timetype;
        this.priority = priority;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    public Project getIdProject() {
        return idProject;
    }

    public void setIdProject(Project idProject) {
        this.idProject = idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTask != null ? idTask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.idTask == null && other.idTask != null) || (this.idTask != null && !this.idTask.equals(other.idTask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mgproject.entities.Task[ idTask=" + idTask + " ]";
    }
    
}
