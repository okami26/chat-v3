
let button = document.getElementById("xui")


async function test() {

    let url = "/feed"

    try {

        let response = await fetch(url);

        let userdata = response.json();

        console.log(userdata)



    }catch (error){
        console.log('hui')
    }

}