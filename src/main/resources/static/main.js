let id = 0
let username = ""
let firstname = ""
let lastname = ""
let token=""
const button = document.querySelector('#button_registry');
const my_profile_button = document.getElementById('my_profile_button')



async function signin() {

    const form = document.getElementById('myForm');
    const signin_data = new FormData(form);

    const url = '/signup';

    const new_data =
        {"username": signin_data.get("username"), "password": signin_data.get("password")}


    try {
        const response = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(new_data),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const json = await response.json();
        token = json.token;
        id = json.id



        console.log('Успех:', JSON.stringify(json));

        document.getElementById('signUp').classList.add('hidden');
        document.getElementById('profile').style.display = "flex"
        this.username = new_data.username



    } catch (error) {
        console.error('Ошибка:', error);
    }


}

async function registry() {

    const form = document.getElementById('myFormRegistry');
    const registry_data = new FormData(form);

    const url = '/registry'

    const new_data =
        {"username": registry_data.get("username"),
            "password": registry_data.get("password"),
            "firstname": registry_data.get("firstname"),
            "lastname": registry_data.get("lastname"),
            "email": registry_data.get("email"),
            "phone": registry_data.get("phone")
        }

    if (new_data.username.includes(' ') || new_data.password.includes(' ')){

        alert('не должно быть пробелов')
    }
    else {


        try {
            const response = await fetch(url, {
                method: 'POST',
                body: JSON.stringify(new_data),
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const json = await response.json();
            const token = json.token;
            window.location.href = 'index.html';

            console.log('Успех:', JSON.stringify(json));
            alert(new_data)

        } catch (error) {
            console.error('Ошибка:', error);
            alert(new_data)
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
        console.log(id)

        const response = await fetch(`/users/${id}`);


        if (!response.ok) {
            throw new Error(`Ошибка: ${response.status} ${response.statusText}`);
        }


        const userData = await response.json();


        console.log(userData)
        fcs.innerHTML = userData.username + ' ' + userData.lastname

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

my_profile_button.addEventListener('click', getUser)
button.addEventListener('click', callback);





