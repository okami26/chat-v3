
let username = ""
let firstname = ""
let lastname = ""


let recipient2;

const button = document.querySelector('#button_registry');
const my_profile_button = document.getElementById('my_profile_button')
const feed_button = document.getElementById('feed_button')
const new_note_button = document.getElementById('new_note_button')
const message_button = document.getElementById("message_button")
const test_button = document.getElementById("test_button")
const message_button_submit = document.getElementById("message_button_submit")
let fcs;
let json;
let id;

var stompClient = null;
async function signin() {

    const form = document.getElementById('myForm');
    const signin_data = new FormData(form);

    const url = '/signup';
    const url2 = "/feed"

    const new_data = {
        username: signin_data.get("username"),
        password: signin_data.get("password")
    };
    try {

        const response = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(new_data),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        json = await response.json();
        const token = json.token;
        id = json.id;
        console.log('Успех:', JSON.stringify(json));


        document.getElementById('signUp').classList.add('hidden');
        document.getElementById('profile').style.display = "flex";


        username = new_data.username;

    } catch (error) {

        console.error('Ошибка:', error);
        alert('Произошла ошибка при входе. Пожалуйста, проверьте введенные данные и попробуйте снова.');
    }


    await getUser();
    await connect()

}


async function registry() {
    const form = document.getElementById('myFormRegistry');
    const registry_data = new FormData(form);
    const url = '/registry';

    const new_data = {
        "username": registry_data.get("username"),
        "password": registry_data.get("password"),
        "firstname": registry_data.get("firstname"),
        "lastname": registry_data.get("lastname"),
        "email": registry_data.get("email"),
        "phone": registry_data.get("phone")
    };

    const image = document.getElementById("inputFileToLoad");
    const isImage = image.files.length > 0 &&
        (image.files[0].type === "image/jpeg" || image.files[0].type === "image/png");

    if (new_data.username.includes(' ') || new_data.password.includes(' ')) {
        alert('не должно быть пробелов');
    } else if (!isImage) {
        alert('Пожалуйста, загрузите изображение в формате JPEG или PNG.');
    } else {
        try {
            const response = await fetch(url, {
                method: 'POST',
                body: JSON.stringify(new_data),
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const json = await response.json();

            if (json.status == 400) {
                alert(json.message);
            } else {

                try {
                    await upload_image(new_data.username);
                    window.location.href = 'index.html';
                    console.log('Успех:', JSON.stringify(json));
                } catch (error) {
                    alert("Ошибка при загрузке изображения: " + error.message);
                }
            }

        } catch (error) {
            console.error('Ошибка:', error);
            alert('Произошла ошибка при регистрации пользователя.');
        }
    }
}


const callback = () => {


    document.getElementById('signUp').classList.add('hidden');
    document.getElementById('registry').classList.remove('hidden')


}


async function getUser () {
    let fcs = document.getElementById('fcs')
    document.getElementById('feed').classList.add('hidden')
    document.getElementById('messages').classList.add('hidden');
    await disconnect()
    console.log(id)
    try {


        const response = await fetch(`/users/${id}`);


        if (!response.ok) {
            throw new Error(`Ошибка: ${response.status} ${response.statusText}`);
        }


        const userData = await response.json();

        document.getElementById('profile2').classList.remove('hidden');
        console.log(userData)
        fcs.innerHTML = userData.firstname + ' ' + userData.lastname
        downloadUserImage(userData.username);

    } catch (error) {

        console.error("Произошла ошибка при получении пользователя:", error);
        return null;
    }
}

async function getFeed() {

    let response = await fetch("/feed")
    let data = await response.json()
    let container = document.querySelector(".feed")

    document.getElementById('feed').classList.remove('hidden')
    document.getElementById('profile2').classList.add('hidden')
    document.getElementById('messages').classList.add('hidden');
    await disconnect()
    container.innerHTML=""
    for (let item of data) {


        let container_2 = document.createElement("div")
        container_2.classList.add("container-fluid", "note")
        let row = document.createElement("div")
        let row2 = document.createElement("div")
        row.classList.add("row")
        row2.classList.add("row")
        let col = document.createElement('div')
        let col2 = document.createElement('div')
        col.classList.add("col-md-12", "top_feed")

        col2.classList.add("col-md-12", "top_feed")

        row.appendChild(col)
        let fcs = document.createElement("p")
        let fcs_text = document.createTextNode(item.sender)
        let content = document.createElement("p")
        let content_text = document.createTextNode(item.content)
        let img = document.createElement('img')
        img.src = await downloadUser(item.sender)
        fcs.appendChild(fcs_text)
        content.appendChild(content_text)
        col.appendChild(img)
        col.appendChild(fcs)
        col2.appendChild(content)
        row2.appendChild(col2)

        container_2.appendChild(row)
        container_2.appendChild(row2)
        container.appendChild(container_2)
    }
}






function encodeImageFileAsURL() {
    const form = document.getElementById('myFormRegistry');
    var filesSelected = document.getElementById("inputFileToLoad").files;
    if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
            var srcData = fileLoadedEvent.target.result; // <--- data: base64
            var image = document.createElement("img")
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}

async function upload_image(username){
    const form = document.getElementById('myFormRegistry');
    const registry_data = new FormData(form);
    const new_image = new FormData();
    const image = document.getElementById("inputFileToLoad")
    new_image.append("file", image.files[0]);
    new_image.append("username", username);

    if (image.files[0].type === "image/jpeg" || image.files[0].type === "image/png") {
        try {
            const response = await fetch("/upload", {
                method: "POST",
                body: new_image // Отправляем FormData
            });


            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }

            const responseData = await response.json();
            console.log("Успешно загружено:", responseData);
        } catch (error) {
            console.error("Ошибка при загрузке файла:", error);
        }
    }
    else {
        throw new Error("Файл не является изображением")

    }
}



async function downloadUserImage (username) {
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


        const imgElement = document.getElementById('userImage');


        imgElement.src = imageUrl;

        imgElement.alt = `Image of ${username}`;
    } catch (error) {
        console.error('Ошибка при загрузке изображения:', error);
        alert('Не удалось загрузить изображение. Пожалуйста, попробуйте позже.');
    }
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


async function connect() {





    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({"username": username}, onConnected);


}

function onConnected() {

    stompClient.subscribe('/topic/feed', onMessageReceived);

}


async function onMessageReceived(payload) {

    let container = document.querySelector(".feed")
    let item = JSON.parse(payload.body);
    let container_2 = document.createElement("div")
    container_2.classList.add("container-fluid", "note")
    let row = document.createElement("div")
    let row2 = document.createElement("div")
    row.classList.add("row")
    row2.classList.add("row")
    let col = document.createElement('div')
    let col2 = document.createElement('div')
    col.classList.add("col-md-12", "top_feed")

    col2.classList.add("col-md-12", "top_feed")

    row.appendChild(col)
    let fcs = document.createElement("p")
    let fcs_text = document.createTextNode(item.sender)
    let content = document.createElement("p")
    let content_text = document.createTextNode(item.content)
    let img = document.createElement('img')
    img.src = await downloadUser(item.sender)
    fcs.appendChild(fcs_text)
    content.appendChild(content_text)
    col.appendChild(img)
    col.appendChild(fcs)
    col2.appendChild(content)
    row2.appendChild(col2)

    container_2.appendChild(row)
    container_2.appendChild(row2)
    container.prepend(container_2)


}

async function new_note(){

    let text_input = document.getElementById("new_note")
    stompClient.send("/app/feed.addNote",
        {},
        JSON.stringify({
            content: text_input.value,
            sender: json.username,
            type: "NOTE",
            date: "12.12.1234 12:12"
        }))

    const url2 = "/feed"
    try {
        let data2 = {
            content: text_input.value,
            sender: json.username,
            type: "NOTE",
            date: "12.12.1234 12:12"

        }
        const response2 = await fetch(url2, {
            method: "POST",
            body: JSON.stringify(data2),
            headers: {
                'Content-Type': 'application/json'
            }

        })

        if (!response2.ok) {
            throw new Error(`HTTP error! status: ${response2.status}`);
        }


    } catch (error){
        console.error('Ошибка:', error);

    }
    text_input.value = ""

}

new_note_button.addEventListener('click', new_note)
my_profile_button.addEventListener('click', getUser)
feed_button.addEventListener('click', getFeed)
button.addEventListener('click', callback);
message_button.addEventListener('click', getUsersMessage)

message_button_submit.addEventListener('click', sendMessage)



async function getUsersMessage(){
    let container = document.querySelector(".messages")
    let response = await fetch("/users")
    let data = await response.json()
    await disconnect()
    document.getElementById('feed').classList.add('hidden')
    document.getElementById('profile2').classList.add('hidden')
    document.getElementById('messages').classList.remove('hidden');

    container.innerHTML=""
    for (let item of data) {


        let container_2 = document.createElement("div")
        container_2.addEventListener('click', (event) => {
            sender = username;
            recipient = item.username
            connectChat(username, item.username)
        })
        container_2.classList.add("container-fluid", "note")
        let row = document.createElement("div")
        let row2 = document.createElement("div")
        row.classList.add("row")
        row2.classList.add("row")
        let col = document.createElement('div')
        let col2 = document.createElement('div')
        col.classList.add("col-md-12", "top_feed")

        col2.classList.add("col-md-12", "top_feed")

        row.appendChild(col)
        let fcs = document.createElement("p")
        let fcs_text = document.createTextNode(item.username)


        let img = document.createElement('img')
        img.src = await downloadUser(item.username)
        fcs.appendChild(fcs_text)

        col.appendChild(img)
        col.appendChild(fcs)

        row2.appendChild(col2)

        container_2.appendChild(row)
        container_2.appendChild(row2)
        container.appendChild(container_2)
    }


}


let subscriptions = []; // Массив для хранения подписок

async function connectChat(sender, recipient) {
    // Отписываемся от предыдущих подписок
    for (let sub of subscriptions) {
        sub.unsubscribe();
    }
    subscriptions = []; // Очищаем массив подписок

    // Подписываемся на новые каналы
    const senderSubscription = stompClient.subscribe('/topic/message/' + sender, onMessageReceivedSender);
    const recipientSubscription = stompClient.subscribe('/topic/message/' + recipient, onMessageReceivedRecipient);

    // Сохраняем подписки в массив
    subscriptions.push(senderSubscription);
    subscriptions.push(recipientSubscription);

    console.log(sender, recipient);
    await chat(sender, recipient);
}

async function onMessageReceivedSender(payload){

    let data = JSON.parse(payload.body)

    let message_input = document.getElementById('message_input')

    let message_text = message_input.value

    let container = document.querySelector('.message-mid')

    let text = document.createTextNode(data.sender + ': ' + data.content)

    let text_p = document.createElement('p')

    text_p.appendChild(text)

    container.appendChild(text_p)

    console.log('sender')



}

async function onMessageReceivedRecipient(payload){

    let data = JSON.parse(payload.body)



    let container = document.querySelector('.message-mid')

    let text = document.createTextNode(data.sender + ': ' + data.content)



    let text_p = document.createElement('p')

    text_p.appendChild(text)

    container.appendChild(text_p)

    console.log('recipient')

}
async function sendMessage() {
    let message_input = document.getElementById('message_input')

    let message_text = message_input.value
    stompClient.send("/app/message.addMessageSender",
        {},
        JSON.stringify({

            content: message_text,
            sender: json.username,
            type: "NOTE",
            date: "12.12.1234 12:12",
            recipient: recipient2
        }))
    stompClient.send("/app/message.addMessageRecipient",
        {},
        JSON.stringify({

            content: message_text,
            sender: json.username,
            type: "NOTE",
            date: "12.12.1234 12:12",
            recipient: recipient2
        }))
}

async function chat(sender, recipient) {

    document.getElementById('messages').classList.add('hidden');


    document.getElementById('chat').classList.remove('hidden')

    let users = document.getElementById('users')
    users.innerHTML = sender + ", " + recipient

}

async function disconnect() {
    // Очистка сообщений
    let mes = document.getElementById('m');
    mes.innerHTML = "";

    // Очистка списка пользователей
    let users = document.getElementById('users');
    users.innerHTML = "";

    // Скрытие чата
    document.getElementById('chat').classList.add('hidden');

    recipient2=""

    // Проверка существования stompClient перед отпиской

    let message_input = document.getElementById('message_input')

    message_input.value = ""
}