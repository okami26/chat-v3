
let clickDiv = document.getElementById("clickDiv")
username1 = 'antonfedorov'
username2 = 'antonfedorov1'
function a() {
    alert('aaaa')
}

clickDiv.addEventListener('click', connect)

function connect(event) {





    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({"username": username1}, onConnected);


}

function onConnected() {

    stompClient.subscribe('/topic/message/' + username1, onMessageReceived);
    stompClient.subscribe('/topic/message/' + username2, onMessageReceived);

    stompClient.send("/app/message.addMessage", {}, JSON.stringify(
        {
            id: 0,
            content: "aaaaa",
            sender: username1,
            type: 'JOIN',
            date: "09.11.2024 19:30",
            recipient: username2

    }))


}


let container = document.getElementById("container")


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    messageText = document.createTextNode(message.sender + " " + "JOINED")

    let textElement = document.createElement('p');
    textElement.appendChild(messageText);
    container.appendChild(textElement)

}


