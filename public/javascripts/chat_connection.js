var socket;

function send() {
    var text = document.getElementById("newMessage").value;
    socket.send(JSON.stringify({text: text}));
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
        var msgJson = JSON.parse(message.data);

        var template = document.getElementById('template_msg').innerHTML;

        Mustache.parse(template);
        var rendered = Mustache.render(template,
            {
                fstColumn: msgJson.fromMe ? "block" : "none",
                columnStyle: msgJson.fromMe ? "is-warning" : "is-primary",
                author: msgJson.author,
                time: msgJson.time,
                text: msgJson.text
            });

        var c = document.createElement("div");
        c.innerHTML = rendered;
        var messages = document.getElementById("messages");
        messages.appendChild(c);
        messages.scrollTop = messages.scrollHeight;
    };

    socket.onopen = function () {
        console.log("Connected");
    };
}

