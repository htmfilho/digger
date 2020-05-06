let passwordField = document.getElementById("password");
if(passwordField != null) {
    passwordField.addEventListener("focus", function(event) {
        document.getElementById("confirmPasswordError").innerHTML = "";
    });
}

let confirmPassword = document.getElementById("confirmPassword");
if(confirmPassword != null) {
    confirmPassword.addEventListener("focus", function(event) {
        document.getElementById("confirmPasswordError").innerHTML = "";
    });
}

let signupForm = document.getElementById("signupForm");
if(signupForm != null) {
    signupForm.addEventListener("submit", function (event) {
        event.preventDefault();
    
        let password = document.getElementById("password");
        let confirmPassword = document.getElementById("confirmPassword");
    
        if (password.value === confirmPassword.value) {
            document.getElementById("signupForm").submit();
        } else {
            document.getElementById("confirmPasswordError").innerHTML = "<p>The password doesn't match the confirmation.</p>";
        }
    });
}