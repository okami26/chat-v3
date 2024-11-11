



var stompClient = null;
let username = 'aaaa'
let container = document.getElementById("container")
getFeed()

async function getFeed(){

    let response = await fetch("/feed")
    let data = await response.json()
    let url = await downloadUser('antonfedorov')
    data.forEach(item => {

        let p = document.createElement('p')
        let img = document.createElement('img')
        let p_text = document.createTextNode(item.content)
        img.src=url
        p.appendChild(p_text)
        container.appendChild(p)
        container.appendChild(img)
        console.log(item.content)
    })

    for (let item of data) {
        let url = await downloadUser(item.sender)
        let p = document.createElement('p')
        let img = document.createElement('img')
        let p_text = document.createTextNode(item.content)
        img.src=url
        p.appendChild(p_text)
        container.appendChild(p)
        container.appendChild(img)
        console.log(item.content)
    }
}



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
    container.prepend(textElement)

}




async function downloadUser (username) {
    const url = `/user_image/${username}`;
    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'image/jpeg'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const imageBlob = await response.blob();


        const imageUrl = URL.createObjectURL(imageBlob);







        return imageUrl;
    } catch (error) {
        console.error('Ошибка при загрузке изображения:', error);
        alert('Не удалось загрузить изображение. Пожалуйста, попробуйте позже.');
    }
}

