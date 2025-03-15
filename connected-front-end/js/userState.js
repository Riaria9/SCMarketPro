// Get logged in flag
const isLoggedIn = sessionStorage.getItem('isLoggedIn')

// Check if the flag exists
if (!isLoggedIn || isLoggedIn == null)
{
	// clears anything in the session storage 
	sessionStorage.clear();
	
    // If isLoggedIn doesn't exist in session storage, create it and set it to false
    sessionStorage.setItem('isLoggedIn', false)
}

// If logged in, display different nav bar
if (isLoggedIn === 'true')
{
    // Hide the log in link
    document.getElementById('loginLink').style.display = 'none'

    // Hide the sign up link
    document.getElementById('signupLink').style.display = 'none'

    // Show bookmarks link
    document.getElementById('bookmarksLink').style.display = 'block'

    // Show sign out link
    document.getElementById('signoutLink').style.display = 'block'

    // Show my products link
    document.getElementById('myproductsLink').style.display = 'block'
}
else // Do the opposite
{
    document.getElementById('loginLink').style.display = 'block'
    document.getElementById('signupLink').style.display = 'block'
    document.getElementById('bookmarksLink').style.display = 'none'
    document.getElementById('signoutLink').style.display = 'none'
    document.getElementById('myproductsLink').style.display = 'none'
}

// Handles user sign out when it's clicked from the nav bar
document.getElementById('signoutLink').addEventListener('click', function(event)
{
	// clears anything in the session storage 
	sessionStorage.clear();

})