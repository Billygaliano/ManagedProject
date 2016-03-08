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
import mgproject.entities.Chat;

/**
 *
 * @author inftel23
 */
@Stateless
public class ChatFacade extends AbstractFacade<Chat> {
    @PersistenceContext(unitName = "MgProjects-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChatFacade() {
        super(Chat.class);
    }
    
    public List<Chat> findByIdProject(Long idProject){
        Query query = em.createQuery("SELECT c FROM Chat c WHERE c.idProject.idProject = :idProject")
                .setParameter("idProject", idProject);
        List<Chat> list = query.getResultList();
        return list;
    }
    
}
