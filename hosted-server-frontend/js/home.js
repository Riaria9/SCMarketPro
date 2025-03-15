document.addEventListener('DomLoadContent', function(){
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
})

function disappear(data){
	for(let i = 0; i < data.length; i++){
		data[i].style.display = 'none';
	}
}

function appear(data){
	for(let i = 0; i < data.length; i++){
		data[i].style.display = 'inline-block';
	}
}

let textbook = document.getElementsByClassName('textbook');
let electronics = document.getElementsByClassName('electronics');
let apparel = document.getElementsByClassName('apparel');
let dorm = document.getElementsByClassName('dorm');
let supplies = document.getElementsByClassName('supplies');

function textbookDisplay(){
	appear(textbook);
	disappear(electronics);
	disappear(apparel);
	disappear(dorm);
	disappear(supplies);
}

function electronicsDisplay(){
	appear(electronics);
	disappear(textbook);
	disappear(apparel);
	disappear(dorm);
	disappear(supplies);
}

function electronicsDisplay(){
	appear(electronics);
	disappear(textbook);
	disappear(apparel);
	disappear(dorm);
	disappear(supplies);
}

function apparelDisplay(){
	appear(apparel);
	disappear(textbook);
	disappear(electronics);
	disappear(dorm);
	disappear(supplies);
}

function dormDisplay(){
	appear(dorm);
	disappear(textbook);
	disappear(electronics);
	disappear(apparel);
	disappear(supplies);		
}

function suppliesDisplay(){
	appear(supplies);
	disappear(textbook);
	disappear(electronics);
	disappear(apparel);
	disappear(dorm);
}

function featured(){
	appear(supplies);
	appear(textbook);
	appear(electronics);
	appear(apparel);
	appear(dorm);
}

function showDetail(num){
    sessionStorage.setItem('postId', num);
    window.location.href = 'product.html';
}