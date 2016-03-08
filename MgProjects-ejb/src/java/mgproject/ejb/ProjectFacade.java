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
import javax.persistence.Query;
import mgproject.entities.Project;
import mgproject.entities.Users;

/**
 *
 * @author inftel23
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {
    @PersistenceContext(unitName = "MgProjects-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectFacade() {
        super(Project.class);
    }
    public List<Project> findByNameAndUser(String name, Users u){
        
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.name = :name AND p.idAdmin = :u")
                .setParameter("name", name)
                .setParameter("u",u);
        List<Project> list = query.getResultList();
        return list;
    }
    
    public List<Project> findByUser(Users u){
        
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.idAdmin = :u")
                .setParameter("u", u);
        List<Project> list = query.getResultList();
        return list;
    }
    
    public List<Project> findColaborations(Users u){
        Query query = em.createQuery("SELECT p FROM Project p WHERE :u MEMBER OF p.usersCollection")
                .setParameter("u", u);
        List<Project> list = query.getResultList();
        return list;
               
    }
    
}
