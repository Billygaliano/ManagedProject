/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.server.PathParam;

@ApplicationScoped
@ServerEndpoint("/actions/{idProject}")
public class MessageWebSocketServer {

    @Inject
    private MessageSessionHandler sessionHandler;

    @PostConstruct
    public void afterCreate() {
    }

    @OnOpen
    public void open(@PathParam("idProject") String idProject, Session session) {
        sessionHandler.addSocketSession(idProject, session);
    }

    @OnClose
    public void close(@PathParam("idProject") String idProject, Session session) {
        sessionHandler.removeSocketSession(idProject, session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(MessageWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(@PathParam("idProject") String idProject, String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            sessionHandler.sendToAllProjectSessions(idProject, message);
        }
    }
}
