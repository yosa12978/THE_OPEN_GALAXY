function checkPassword(form) {
    username = form.username.value;
    password1 = form.password.value;
    password2 = form.password_confrim.value;
    email = form.email.value;
    err = document.getElementById("matchError");

    if(username.trim() == ''){
        err.innerHTML = "Username is required";
        showElement(err);
        return false;
    }
    if(email.trim() == ""){
        err.innerHTML = "Email is required";
        showElement(err);
        return false;
    }
    if (password1.trim() == ''){
        err.innerHTML = "Password is required";
        showElement(err);
        return false;
    }
    if (password2.trim() == ''){
        err.innerHTML = "Password confirm is required";
        showElement(err);
        return false;
    }
    if (password1.trim() != password2.trim()) {
        err.innerHTML = "Passwords doesn't match";
        showElement(err);
        return false;
    }
}

function showElement(elem) {
    elem.style.display = "block";
}

function checkProject(form){
    title = form.title.value;
    url = form.url.value;
    err = document.getElementById("matchError");
    if(title.trim() == ""){
        err.innerHTML = "Title required";
        showElement(err);
        return false;
    }
    if(url.trim() == ""){
        err.innerHTML = "Url required";
        showElement(err);
        return false;
    }
}