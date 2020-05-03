document.getElementById("password").addEventListener("focus", function(event) {
    document.getElementById("confirmPasswordError").innerHTML = "";
});

document.getElementById("confirmPassword").addEventListener("focus", function(event) {
    document.getElementById("confirmPasswordError").innerHTML = "";
});

document.getElementById("signupForm").addEventListener("submit", function (event) {
    event.preventDefault();

    let password = document.getElementById("password");
    let confirmPassword = document.getElementById("confirmPassword");

    if (password.value === confirmPassword.value) {
        document.getElementById("signupForm").submit();
    } else {
        document.getElementById("confirmPasswordError").innerHTML = "<p>The password doesn't match the confirmation.</p>";
    }
});