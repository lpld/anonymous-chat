var socket;

function send() {
    var text = document.getElementById("newMessage").value;
    socket.send(text);
}

window.onload = function () {
    var url = document.getElementById("socketUrl").value;
    // var url = "@routes.ChatController.connect().webSocketURL()";
    connect(url);
};

document.getElementById("sendBtn").addEventListener("click", send);

function connect(url) {

    socket = new WebSocket(url);
    console.log(url);

    socket.onmessage = function (message) {
        console.log(message);
        var p = document.createElement("p");
        var msg = document.createTextNode(message.data);
        p.appendChild(msg);
        document.getElementById("messages").appendChild(p);
    };

    socket.onopen = function () {
        console.log("Connected");
    };
}

