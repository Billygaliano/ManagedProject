/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;


import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.websocket.Session;

@ApplicationScoped
public class MessageSessionHandler {
    private final ArrayList<SocketSession> socketSessions = new ArrayList<>();
    private final HashSet<ProjectChat> projectChats = new HashSet<>(); 
    
    @PostConstruct
    public void afterCreate() {
        System.out.println("MessageSessionHandler creado");
    }
    
    public void addSocketSession(String idProject, Session session) {
        SocketSession ss = new SocketSession();
        ss.setIdproject(idProject);
        ss.setSession(session);
        socketSessions.add(ss);
        ProjectChat pc = new ProjectChat();
        pc.setProjectId(idProject);
        if(!projectChats.contains(pc)){
            pc = new ProjectChat(idProject);
            projectChats.add(pc);
        }else{//Si existe hay que enviar el chat acumulado al usuario por si se conecta mientras hay una conversación en curso
            for(ProjectChat chatp : projectChats){
                if(chatp.equals(pc)){
                    pc.setMychat(chatp.getMychat());
                }
            }
        }
        sendChatToSession(pc, session);
    }
    
    public void removeSocketSession(String idProject, Session session){
        SocketSession ss = new SocketSession();
        ss.setIdproject(idProject);
        ss.setSession(session);
        socketSessions.remove(ss);
        if(!socketSessions.contains(ss)){
            ProjectChat pc = new ProjectChat();
            pc.setProjectId(idProject);
            saveChat(idProject);
            projectChats.remove(pc);
        }
    }
    
    protected void sendToAllProjectSessions(String idProject, String message) {
        addMessageToChat(idProject, message);
        Gson gson = new Gson();
        ArrayList<Message> mc = new ArrayList<>();
        mc.add(gson.fromJson(message, Message.class));  
        JsonReader reader = Json.createReader(new StringReader(gson.toJson(mc)));
        JsonArray myArray = reader.readArray();        
        System.out.println("Mensajes: "+ myArray.toString());
        for (SocketSession ss : socketSessions) {
            if(ss.getIdproject().equalsIgnoreCase(idProject)){
                sendToSession(ss.getSession(), myArray);
            }
        }
    }

    private void saveChat(String idProject) {
        System.out.println("Saving Chat: " + idProject);
        for(ProjectChat chat : projectChats){
            if(chat.getProjectId().equalsIgnoreCase(idProject)){
                chat.saveChat();
            }
        }
    }

    private void addMessageToChat(String idProject, String message) {
        for(ProjectChat chat : projectChats){
            if(chat.getProjectId().equalsIgnoreCase(idProject)){
                chat.addMessageToChat(message);
            }
        }
    }

    private void sendChatToSession(ProjectChat pc, Session session) {
        Gson gson = new Gson();
        JsonReader reader = Json.createReader(new StringReader(gson.toJson(pc.getMychat())));
        JsonArray jsonMessage = reader.readArray();
        sendToSession(session, jsonMessage);
    }

    private void sendToSession(Session session, JsonArray message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(MessageSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}