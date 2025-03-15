function submitForm(event)
{
    event.preventDefault()

    let email = document.getElementById("email").value
    let username = document.getElementById("username").value
    let password = document.getElementById("password").value

    // Check that email ends with usc.edu
    let emailPattern = /@usc\.edu$/
    if (!emailPattern.test(email)) {
        alert("Please enter a USC email address ending with @usc.edu")
        return
    }

    console.log("usc email valid")
}