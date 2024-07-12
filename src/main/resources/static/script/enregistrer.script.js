function enregistrer(idRecette){
    var xhr = create_HttpRequest();

    var icon = document.getElementById("recette-"+idRecette);
    console.log("idRecette : "+idRecette)
    var response;
    xhr.addEventListener("load", function(event) {    
        response = xhr.responseText;
        console.log(response);
    });
    xhr.open("GET", "/user/recette/"+idRecette+"/enregistrer",false);
    xhr.send(null);
    if(response == "redirect"){
        window.location.href ="/user/login/form"
    }else{
        icon.setAttribute("class",response);
    }
}   