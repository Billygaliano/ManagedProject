/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mgproject.ejb.ChatFacade;
import mgproject.ejb.ProjectFacade;
import mgproject.entities.Chat;
import mgproject.entities.Project;

/**
 *
 * @author inftel22
 */
public class ProjectChat {

    ProjectFacade projectFacade = lookupProjectFacadeBean();
    ChatFacade chatFacade = lookupChatFacadeBean();

    private Gson gson = new Gson();
    private String projectId;
    private ArrayList<Message> mychat = new ArrayList<>();

    /**
     * Creates a new instance of ProjectChatManagedBean
     */
    public ProjectChat() {
    }

    public ProjectChat(String projectId) {
        this.projectId = projectId;
        Chat chat = null;
        long idProjectLong = Long.parseLong(projectId);
        if (chatFacade.findByIdProject(idProjectLong).isEmpty()) {
            mychat.add(new Message("System", "Chat creado", "https://upload.wikimedia.org/wikipedia/commons/c/c3/MP_Icon.png"));
            Project project = projectFacade.find(idProjectLong);
            Chat newChat = new Chat();
            newChat.setIdProject(project);
            chatFacade.create(newChat);
        } else {
            List<Chat> chats = chatFacade.findByIdProject(idProjectLong);
            chat = chats.get(0);
            byte[] oldchat = chat.getMessages();
            mychat = gson.fromJson(new String(oldchat), mychat.getClass());
        }
    }

    public ChatFacade getChatFacade() {
        return chatFacade;
    }

    public void setChatFacade(ChatFacade chatFacade) {
        this.chatFacade = chatFacade;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ArrayList<Message> getMychat() {
        return mychat;
    }

    public void setMychat(ArrayList<Message> mychat) {
        this.mychat = mychat;
    }

    public void addMessageToChat(String message) {
        Message mc = gson.fromJson(message, Message.class);
        mychat.add(mc);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.projectId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectChat other = (ProjectChat) obj;
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        return true;
    }

    public void saveChat() {
        long idProjectLong = Long.parseLong(projectId);
        List<Chat> chats = chatFacade.findByIdProject(idProjectLong);
        Chat chat = chats.get(0);
        String toByteArray = gson.toJson(mychat);
        byte[] newchat = toByteArray.getBytes();
        chat.setMessages(newchat);
        chatFacade.edit(chat);
    }

    private ChatFacade lookupChatFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ChatFacade) c.lookup("java:global/MgProjects/MgProjects-ejb/ChatFacade!mgproject.ejb.ChatFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ProjectFacade lookupProjectFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ProjectFacade) c.lookup("java:global/MgProjects/MgProjects-ejb/ProjectFacade!mgproject.ejb.ProjectFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
