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
import mgproject.entities.Attachment;

/**
 *
 * @author inftel23
 */
@Stateless
public class AttachmentFacade extends AbstractFacade<Attachment> {

    @PersistenceContext(unitName = "MgProjects-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttachmentFacade() {
        super(Attachment.class);
    }

    public List<Attachment> findByIdProject(Long idProject) {
        List<Attachment> attach_list;
        attach_list = em.createNamedQuery("Attachment.findByIdProject").setParameter("idProject", idProject).getResultList();
        return attach_list;
    }
}
