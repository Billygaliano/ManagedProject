/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest;

import java.util.List;
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
    @Path("project")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addCollaborator(Project entity) {
        projectFacade.edit(entity);

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
    public List<Project> findCollaborators(@PathParam("id") String id) {
        Users u = usersFacade.find(id);
        if (u != null) {
            return projectFacade.findColaborations(u);
        } else {
            return null;
        }
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
            System.out.println("Edita usuario");
        } else {
            usersFacade.create(u);
            System.out.println("Crea usuario");
        }
    }

    //Devuelve todos los usuarios del sistema
    //Para mostrar en la lista de añadir colaborador
    @GET
    @Path("user")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Users> findAll() {
        return usersFacade.findAll();
    }

    //////////////////////////////////////////////////////////////////////////// TASK
    ////////////////////////////////////////////////////////////////////////////
    //Devuelve las tareas dado un projecto
    @GET
    @Path("task/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Task> findTaskByProjectUser(@PathParam("id") Long id) {
        Project p = projectFacade.find(id);
        if (p != null) {
            return taskFacade.findTaskByProjectUser(p);
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
