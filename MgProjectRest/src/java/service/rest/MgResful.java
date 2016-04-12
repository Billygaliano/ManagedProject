/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mgproject.ejb.AttachmentFacade;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.TaskFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Attachment;
import mgproject.entities.Project;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@Stateless
@Path("mgproject.service")
public class MgResful {

    @EJB
    private AttachmentFacade attachmentFacade;

    @EJB
    private TaskFacade taskFacade;

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private ProjectFacade projectFacade;

    //////////////////////////////////////////////////////////////////////////// PROJECT
    ////////////////////////////////////////////////////////////////////////////
    //Devuelve los proyectos donde el usuario es el administrador
    //
    @GET
    @Path("project/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Project> findByUser(@PathParam("id") String id) {
        Users u = usersFacade.find(id);
        if (u != null) {
            return projectFacade.findByUser(u);
        } else {
            return null;
        }
    }

    //Un administrador solo puede tener un proyecto con el mismo nombre.
    @GET
    @Path("project/{id}/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Project> findByNameAndUser(@PathParam("id") String id, @PathParam("name") String name) {
        Users u = usersFacade.find(id);
        if (u != null) {
            return projectFacade.findByNameAndUser(name, u);
        } else {
            return null;
        }
    }

    //Crear projecto
    @POST
    @Path("project")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addProject(Project entity) {
        projectFacade.create(entity);
    }

    //Editar projecto
    @PUT
    @Path("project/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addCollaborator(Users entity,@PathParam("id")Long id) {
        Project p = projectFacade.find(id);
        p.getUsersCollection().add(entity);
        projectFacade.edit(p);

    }

    @DELETE
    @Path("project/{id}")
    public void removeProject(@PathParam("id") Long id) {
        Project p = projectFacade.find(id);
        if (p != null) {
            projectFacade.remove(p);
        }
    }

    @GET
    @Path("projectCollaborations/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Project> findCollaborators(@PathParam("id") String id) {
        Users u = usersFacade.find(id);
        if (u != null) {
            return projectFacade.findColaborations(u);
        } else {
            return null;
        }
    }
    //Devuelve una colleccion de Usuarios que estan en colaboracion con el proyecto
    @GET
    @Path("collaboratorsProject/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Users> findListCollaboratosByIdProject(@PathParam("id")Long id){
        Project p = projectFacade.find(id);
        return p.getUsersCollection();
        
    }
    
    @GET
    @Path("myproject/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public HashSet<Project> findProjectById(@PathParam("id")String id){
        List<Project> projectList;
        Users u = usersFacade.find(id);
        projectList = projectFacade.findByUser(u);
        projectList.addAll(projectFacade.findColaborations(u));
        
        return new HashSet<Project>(projectList);
    }
    
    //////////////////////////////////////////////////////////////////////////// USER
    ////////////////////////////////////////////////////////////////////////////
    //Funcion login, necesita un Usuario en JSON
    //Si el usuario ya existe lo edita, sino lo crea
    @POST
    @Path("user")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createUser(Users u) {
        Users addUser = usersFacade.find(u.getIdUser());
        if (addUser != null) {
            usersFacade.edit(u);
        } else {
            usersFacade.create(u);
        }
    }

    //Devuelve todos los usuarios del sistema que no estan en colaboracion con este proyecto
    //Para mostrar en la lista de añadir colaborador
    @GET
    @Path("user/{idUser}/{idProject}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Users> findAll(@PathParam("idUser")String idUser ,@PathParam("idProject")Long idProject) {
        Users u = usersFacade.find(idUser);
        List<Users> usersList;
        usersList = usersFacade.findAll();
        usersList.removeAll(findListCollaboratosByIdProject(idProject));
        usersList.remove(u);
        return usersList;
    }

    //////////////////////////////////////////////////////////////////////////// TASK
    ////////////////////////////////////////////////////////////////////////////
    //Devuelve las tareas dado un projecto
    @GET
    @Path("task/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Task> findTaskByProjectUser(@PathParam("id") Long id) {
        Project p = projectFacade.find(id);
        if (p != null) {
            return p.getTaskCollection();
        } else {
            return null;
        }
    }

    //DELETE TASK
    @DELETE
    @Path("task/{id}")
    public void removeTask(@PathParam("id") Long id) {
        Task t = taskFacade.find(id);
        if (t != null) {
            taskFacade.remove(t);
        }
    }

    //Devuelve lista de proyecto dado un id de projecto y nombre de tarea
    @GET
    @Path("task/{id}/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Task> findTaskByNameAndIdProject(@PathParam("id") Long id, @PathParam("name") String name) {
        Project p = projectFacade.find(id);
        if (p != null) {
            return taskFacade.findTaskByNameIdproject(p, name);
        } else {
            return null;
        }
    }

    //CREAT TASK
    @POST
    @Path("task")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createTask(Task entity) {
        taskFacade.create(entity);
    }

    //EDITAR TASK
    @PUT
    @Path("task")
    @Consumes({MediaType.APPLICATION_JSON})
    public void editTask(Task entity) {
        taskFacade.edit(entity);
    }

    //////////////////////////////////////////////////////////////////////////// ATTATCHMENT
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path("attatch/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Attachment> findAttatchByIdProject(@PathParam("id") Long id) {
        Project p = projectFacade.find(id);
        if (p != null) {
            return attachmentFacade.findByIdProject(p);
        } else {
            return null;
        }
    }

    @POST
    @Path("attatch")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAttatch(Attachment entity) {
        attachmentFacade.create(entity);
    }
}

//////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////// CHAT
