
let username = ""
let firstname = ""
let lastname = ""
let token=""
const button = document.querySelector('#button_registry');
const my_profile_button = document.getElementById('my_profile_button')


let id;
async function signin() {

    const form = document.getElementById('myForm');
    const signin_data = new FormData(form);

    const url = '/signup';

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

        const json = await response.json();
        const token = json.token;
        id = json.id;
        console.log('Успех:', JSON.stringify(json));


        document.getElementById('signUp').classList.add('hidden');
        document.getElementById('profile').style.display = "flex";

        this.username = new_data.username;

    } catch (error) {

        console.error('Ошибка:', error);
        alert('Произошла ошибка при входе. Пожалуйста, проверьте введенные данные и попробуйте снова.');
    }
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
                // Если пользователь успешно создан, загружаем изображение
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
    try {


        const response = await fetch(`/users/${id}`);


        if (!response.ok) {
            throw new Error(`Ошибка: ${response.status} ${response.statusText}`);
        }


        const userData = await response.json();

        document.getElementById('my_profile').classList.remove('hidden');
        console.log(userData)
        fcs.innerHTML = userData.username + ' ' + userData.lastname
        downloadUser(userData.username);

    } catch (error) {

        console.error("Произошла ошибка при получении пользователя:", error);
        return null;
    }
}


async function get_token() {
    console.log(token)
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

            // Проверяем статус ответа
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

async function test(){
    const image = document.getElementById("inputFileToLoad")
    console.log(image.files[0].type)
}

async function downloadUser (username) {
    const url = `/user_image/${username}`; // Формируем URL для запроса
    try {
        const response = await fetch(url, {
            method: 'GET', // Указываем метод GET для загрузки изображения
            headers: {
                'Accept': 'image/jpeg' // Указываем, что ожидаем изображение в формате JPEG
            }
        });
        // Проверяем, был ли успешным ответ
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        // Получаем данные изображения в виде Blob
        const imageBlob = await response.blob();

        // Создаем URL для изображения
        const imageUrl = URL.createObjectURL(imageBlob);

        // Находим элемент img на странице, куда будем вставлять изображение
        const imgElement = document.getElementById('userImage'); // Предполагаем, что у вас есть <img id="userImage">

        // Устанавливаем src для элемента img
        imgElement.src = imageUrl;
        // Если нужно, можно также установить атрибут alt
        imgElement.alt = `Image of ${username}`;
    } catch (error) {
        console.error('Ошибка при загрузке изображения:', error);
        alert('Не удалось загрузить изображение. Пожалуйста, попробуйте позже.');
    }
}

my_profile_button.addEventListener('click', getUser)
button.addEventListener('click', callback);





