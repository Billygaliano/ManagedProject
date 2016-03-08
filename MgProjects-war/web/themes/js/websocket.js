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

    var userImage = document.createElement("img");
    userImage.setAttribute("src", message.urlImage);
    userImage.setAttribute("class", "offline");   
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
