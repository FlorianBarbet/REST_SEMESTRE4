let BASE_URL = "http://frene20.iut-infobio.priv.univ-lille1.fr:8080/jobber";
let username = "Guest";
let logged = false;
let home = $('.masthead').html();
let annonces = 0;
let annoncesArray;
let start = false;
let employeur = true;
let pswd = "";

console.log($('input[name="majinfo"]').val());
majEvent();

$('.tbg-0').hide();
$('.tbg-1').hide();
$('.tbg-2').hide();
$('.tbg-3').hide();
$('.tbg-bone').hide();
$('.profil-page').hide();
$('.annonce-page').hide();
$('.annonce-modif').hide();

function addLike(){
      $.ajax({
            type: "PUT",
            url: "http://frene20.iut-infobio.priv.univ-lille1.fr:8080/jobber/offer/"+annoncesArray[annonces-1].id+'"',
            contentType: 'application/json',
            data:'{"age": "age"}',
            dataType: 'json',
            headers: {
                Accept: "application/json"
            }
        }).done(function( response, status, jqXHR ) {
            console.log('Update successful', response);
        }).fail(function( response ) {
            console.log('Update failed: ', response);
        });
}

function loadVars(){
    if(localStorage.getItem("logged"))
        logged = JSON.parse(localStorage.getItem("logged"));
    if(localStorage.getItem("username") == null)
        localStorage.setItem("username", "Guest");
    else
        username = localStorage.getItem("username");
    employeur = JSON.parse(localStorage.getItem("employeur"));
}

function checkConnect(){
    if(logged === false){
        $('a[href="#connexion"]').trigger("click");
        return false;
    }
}

function majEvent(){
    $('input[name="majinfo"]').click( (ev) => {
        ev.preventDefault();
        console.log($("input[name='user-age']").val());
        $.ajax({
            type: "PUT",
            url: BASE_URL +'/app/'+username,
            contentType: 'application/json',
            data:'{"age": "'+$("input[name='user-age']").val()+
            '", "password": "'+$("input[name='user-pswd']").val()+
            '", "nom": "'+$("input[name='user-name']").val()+
            '", "login": "'+$("input[name='user-login']").val()+
            '", "email": "'+$("input[name='user-mail']").val()+
            '"}',
            dataType: 'json',
            headers: {
                Accept: "application/json"
            }
        }).done(function( response, status, jqXHR ) {
            console.log('Update successful', response);
        }).fail(function( response ) {
            console.log('Update failed: ', response);
        });
    });
}

function majAnnonce() {
    $('.masthead').html($('.tbg-bone').html());
    $('#tname').html(annoncesArray[annonces-1].libelle);
    $('#type').html(annoncesArray[annonces-1].type);
    $("#tgb-img").attr("src",annoncesArray[annonces-1].img);
    click();
}

function click() {
    $('.tick').click(() => {
        console.log("tick");
        
        if(!start){
            start=true;
            $('.masthead').empty();
            $('.masthead').html($('.tbg-0').html());
            
            console.log("started");
        } else {
        
        checkConnect();
        $('.tbg-0').hide();
        console.log("connect : true");
        annonces++;
        majAnnonce();
        start = true;
        }
    });

    $('.cross').click(() => {
        console.log("cross");
        if(!start){
            start=true;
            $('.masthead').empty();
            $('.masthead').html($('.tbg-0').html());
            console.log("started");
        } 
        
        checkConnect()
        $('.tbg-0').hide();
        console.log("connect : true");
        annonces++;
        majAnnonce();
        start = true;
    });
}

function displayLogin(){
    $('.for-logged').show();
    $('.for-logoff').hide();
    if(employeur)
        $('.for-employeur').show();
    else
        $('.for-applicant').show();
}

function displayLogoff(){
    $('.for-logged').hide();
    $('.for-logoff').show();
    $('.for-employeur').hide();
    $('.for-applicant').hide();
}

$('input[name="login"]').click( (e) => {
    e.preventDefault();
    let usernameTemp = $('#login input[name="user"]').val();
    console.log(usernameTemp);
    let pass = $('#login input[name="pass"]').val();
    $.ajax({
        url: BASE_URL+"/app/"+usernameTemp+"/"+pass,
        type: "GET",
        dataType: "json"
    })
    .done( (res) => {
        if(res.success == "false")
            $('.login-message').html('<div class="alert alert-danger"><strong>Identifiant ou mot de passe incorrect.</strong></div>');
        else{
            $(".modal").modal("hide");
            username = usernameTemp;
            localStorage.setItem("username", username);
            logged = true;
            localStorage.setItem("logged", true);
            employeur = (res.type == "employer");
            localStorage.setItem("employeur", employeur);
            displayLogin();
        }
    });
    click();
});

$('input[name="signup"]').click( (e) => {
    e.preventDefault();
    let usernameTemp = $('#signup input[name="user"]').val();
    let pass1 = $('#signup input[name="pass1"]').val();
    let pass2 = $('#signup input[name="pass2"]').val();
    if(pass1 != pass2){
        $('.signup-message').html('<div class="alert alert-danger"><strong>Les deux mots de passe ne sont pas identiques</strong></div>');
        return;
    }else{
        let type = $('#signup .onoffswitch input[name="onoffswitch"]').is(':checked');
        $.ajax({
            url: BASE_URL + (type ? "/emp/" : "/app/"),
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                email: $('#signup input[name="email"]').val(),
                login: usernameTemp,
                password: pass1
            })
        })
        .done( (e) => {
            $(".modal").modal("hide");
            employeur = type;
            username = usernameTemp;
            logged = true;
            localStorage.setItem("logged", true);
            displayLogin();
        })
        .fail( (e) => {
           $('.signup-message').html('<div class="alert alert-danger"><strong>Ce nom d\'utilisateur est déjà pris !</strong></div>');
        });
    }
});

$('input[name="forgot"]').click( (e) => {
    e.preventDefault();
    
});

$('a[href="#connexion"]').click( (e) => {
    $('a[target="#login"]').trigger("click");
});

$('a[href="#logoff"]').click( (e) => {
    logged = false;
    localStorage.setItem("logged", false);
    username = "Guest";
    localStorage.setItem("username", "Guest");
    displayLogoff();
    $.ajax(BASE_URL+"/app/logout");
});

$('a[target="#login"]').click( (e) => {
	
    e.preventDefault();
    $('.forgot').hide();
    $('.signup').hide();
    $('.login').show();
});

$('a[target="#inscription"]').click( (e) => {
    e.preventDefault();
    $('.forgot').hide();
    $('.login').hide();
    $('.signup').show();
});

$('a[target="#forgot"]').click( (e) => {
    e.preventDefault();
    $('.signup').hide();
    $('.login').hide();
    $('.forgot').show();
});

    
$('.tbg-' + annonces).hide();

$('.container .img-fluid').click(() => {
    $('.masthead').empty();
    $('.masthead').html($('.tbg-0').html());
    console.log($('.masthead').html());
        $.ajax({url: BASE_URL = "/offer", success: function(result){
        console.log(result);
        annoncesArray = result;
    }});
    click();
});

$('.navbar-brand').click(() => {
    console.log("home");
    annonces = 0;
    $('.masthead').html(home);

    $('.container .img-fluid').click(() => {
        $('.masthead').empty();
        $('.tbg-' + annonces).show();
        $('.masthead').html($('.tbg-' + annonces).html());
        console.log($('.masthead').html());
        
        $.ajax({url: BASE_URL + "/offer", success: function(result){
            console.log(result);
            annoncesArray = result;
        }});
        click();
        //$('.tbg-0').show();
        $('.tbg-0').hide();
    });
});


$('.profil').click(() => {
    $('.masthead').empty();
    $('.masthead').html($('.profil-page').html());
    console.log($('.masthead').html());
    majEvent();
    console.log("username : "+username);
    if(username.length>0){
    console.log("true");
    $.ajax({url: BASE_URL + "/app/"+username, success: function(result){
        console.log("in ajax");
        console.log(result);
        $('input[name=user-login]').val(result.login);
        $('input[name=user-name]').val(result.nom);
        $('input[name=user-age]').val(result.age);
        $('input[name=user-location]').val(result.adresse);
        $('input[name=user-mail]').val(result.email);
        $('input[name=user-pswd]').val(result.password);
        
        
        $('input[name=user-diploma]').val(result.diplome);
        $('input[name=user-xp]').val(result.formation);
        
    }});
    }
});

$('.resultats').click(() => {
    $('.masthead').empty();
	$.ajax(BASE_URL + "/app").done(function(data){

		console.log(data);
		

    });

    $('.masthead').html("<ul><li>"+$('.tbg-' +1).html()+"</li><li>"+$('.tbg-' +2).html()+"</li></ul>");
});

$('.annonce').click(() => {
    $('.masthead').html($('.annonce-page').html());
    creatEvent();
    //$('.annonce-page').show();

});

$('.modif').click(() => {
    $('.masthead').html($('.annonce-modif').html());
    modifEvent();

});

function creatEvent (){
$('input[name="create"]').click( (e) => {
    e.preventDefault();
    let typeTemp = $('input[name="type"]').val();
    console.log(typeTemp);
    let libelle = $('input[name="libelle"]').val();
    let img = $('input[name="img"]').val();
	console.log(libelle);
	console.log(username);
	$.ajax({
            url: BASE_URL + "/offer/",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                login:username,
				libelle:libelle,
				img:img,
				type:typeTemp
            })
        })
        .done( (e) => {
            console.log("done");
        })
        .fail( (e) => {
        });
    click();
});
}

loadVars();

if(!logged)
    displayLogoff();
else
    displayLogin();

$('.tbg-0').hide();

