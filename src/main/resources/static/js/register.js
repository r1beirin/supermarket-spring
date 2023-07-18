document.addEventListener("DOMContentLoaded", function(){
    const form = document.querySelector("#form");

    form.onsubmit = function (e) {
        enviaForm(form);
        e.preventDefault();
    }
});

async function enviaForm(form){
    try {
        const obj = {
            method: "POST",
            body: new FormData(form)
        };

        let response = await fetch(form.getAttribute("action"), obj);
        if(!response.ok) throw new Error(response.statusText);

        const responseJS = await response.json();

        if(responseJS.valid) console.log("ok");
        else alert("Verifique os campos");
    } catch (e) {
        console.error(e);
    }
}