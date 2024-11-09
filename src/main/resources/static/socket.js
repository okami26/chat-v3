var stompClient = null;
let username = 'aaaa'
let container = document.getElementById("container")
function connect(event) {





    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);


}

function onConnected() {

    stompClient.subscribe('/topic/feed', onMessageReceived);
    stompClient.send("/app/feed.addUser",
        {},
        JSON.stringify({
            content: "aaaaa",
            sender: username,
            type: 'JOIN',
            date: "09.11.2024 19:30"})
    )
}



function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    if (message.type === "JOIN"){
        messageText = document.createTextNode(message.sender + " " + "JOINED")
    }
    let textElement = document.createElement('p');
    textElement.appendChild(messageText);
    container.appendChild(textElement)

}






