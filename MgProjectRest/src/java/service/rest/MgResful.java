/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@Stateless
@Path("mgproject.service")
public class MgResful{

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private ProjectFacade projectFacade;
    
    @GET
    @Path("project/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Project> findByNameAndUser(@PathParam("id")String id){
        Users u = usersFacade.find(id);
        if(u != null){
            return projectFacade.findByUser(u);
        }
        else 
            return null;
    }
}
