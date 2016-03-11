/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var socket = new WebSocket("ws://localhost:8080/MgProjects-war/actions/"+document.getElementById("id_project").value);
socket.onmessage = onMessage;

function stopRKey(evt) { 
var evt = (evt) ? evt : ((event) ? event : null); 
var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
if ((evt.keyCode === 13) && (node.type === "text"))  {return false;} 
} 

document.onkeypress = stopRKey;

function onMessage(event) {
    var message = JSON.parse(event.data);
    $(".chat").animate({ scrollTop: $(document).height() }, "slow");
    
    for (var i in message){
        printMessageElement(message[i]);
    }
}

function addMessage(user, urlImage, description) {
    var MessageAction = {
        user: user,
        description: description,
        urlImage: urlImage
    };
    socket.send(JSON.stringify(MessageAction));
}

function printMessageElement(message) {
    var content = document.getElementById("chat-box");
    
    var messageDiv = document.createElement("div");
    messageDiv.setAttribute("class", "item ");
    content.appendChild(messageDiv);

    if(message.user === document.getElementById("nick_user").value){ 
        var userImage = document.createElement("img");
        userImage.setAttribute("src", message.urlImage);
        userImage.setAttribute("class", "on line");   
        userImage.setAttribute("alt", "user image");     
        messageDiv.appendChild(userImage);
        
        var messageinfo = document.createElement("p");
        messageinfo.setAttribute("class", "message");      
        messageinfo.innerHTML = message.user;
        messageDiv.appendChild(messageinfo);

        var messageUser = document.createElement("span");
        messageUser.setAttribute("class", "name");
        messageUser.innerHTML = message.description;
        messageinfo.appendChild(messageUser);
    }else{
        var parragraphDiv = document.createElement("div");
        parragraphDiv.setAttribute("style", "width: 89%;display: inline-block;text-align: right;");
        messageDiv.appendChild(parragraphDiv);
    
        var messageinfo = document.createElement("p");
        messageinfo.setAttribute("class", "message");      
        messageinfo.innerHTML = message.user;
        parragraphDiv.appendChild(messageinfo);

        var messageUser = document.createElement("span");
        messageUser.setAttribute("style", "display: block;font-weight: 600;");
        messageUser.setAttribute("class", "name");
        messageUser.innerHTML = message.description;
        messageinfo.appendChild(messageUser);        
        
        var imgDiv = document.createElement("div");
        imgDiv.setAttribute("style", "width: 11%;float: right;display: inline-block;");
        messageDiv.appendChild(imgDiv);
        
        var userImage = document.createElement("img");
        userImage.setAttribute("src", message.urlImage);
        userImage.setAttribute("style", "width: 40px;height: 40px;border-radius: 50%;border: 2px solid #dd4b39;");  
        userImage.setAttribute("align", "right")
        userImage.setAttribute("alt", "user image");     
        imgDiv.appendChild(userImage);
    
    }
}

function chatSubmit() {
    var form = document.getElementById("addMessageChat");
    form.scrollTop = form.scrollHeight;
    var user = form.elements["nick_user"].value;
    var urlImage = form.elements["url_image"].value;
    var description = form.elements["message_description"].value; 
    if (description !== "") {
        document.getElementById("addMessageChat").reset();
        addMessage(user, urlImage, description);
    }
}
