/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mgproject.entities.Project;
import mgproject.entities.Task;

/**
 *
 * @author inftel23
 */
@Stateless
public class TaskFacade extends AbstractFacade<Task> {

    @PersistenceContext(unitName = "MgProjects-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaskFacade() {
        super(Task.class);
    }
    //Devuelve las tareas dado un proyecto
    public List<Task> findTaskByProjectUser(Project project) {
        List<Task> lista_task;
        lista_task = em.createNamedQuery("Task.findByProjectUser").setParameter("idproject", project).getResultList();
        return lista_task;
    }
    //Devuelven tareas

    public List<Task> findTaskByNameIdproject(Project project, String name) {
        List<Task> task_list = em.createNamedQuery("Task.findByNameAnProject").setParameter("idproject", project).setParameter("name", name).getResultList();
        return task_list;
    }
}
