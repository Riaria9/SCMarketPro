/**
 * 
 */
function login(event){

    // Prevent page from reloading 
    event.preventDefault()

    // Grab values
    const emailValue = document.getElementById('email').value
    const passwordValue = document.getElementById('password').value
    console.log(emailValue)
    console.log(passwordValue)

    try {
        fetch(`http://localhost:8080/users/signin?email=${emailValue}&password=${passwordValue}`,{
            method: "POST"
        })
        .then(response =>{
            return response.json()
        }).then(data => {
            console.log(data)

            // Store user info in storage
            sessionStorage.setItem('userId',data.id)
            sessionStorage.setItem('username',data.username)
            sessionStorage.setItem('isLoggedIn',true)

            // Redirect to home page
            window.location.href = 'home.html'
        })
        .catch(error => {
            
            alert("Invalid credentials.")
            
            sessionStorage.clear()
            sessionStorage.setItem('isLoggedIn',false)

            location.reload()
        })
    } catch (error) {
        
        alert("Invalid credentials.")

        sessionStorage.clear()
        sessionStorage.setItem('isLoggedIn',false)
        
        location.reload()
        
    }

    
}