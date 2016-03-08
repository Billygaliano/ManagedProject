/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;

public class Message {

    private int id;
    private String user;
    private String description;
    private String urlImage;

    public Message() {
    }
    
    public Message(String user, String message, String urlImage){
        this.user = user;
        this.description = message;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
