```js
const getPost = () => {
    // Hardcoded
    const postId = 2;

    fetch(`http://localhost:8080/posts/get-post?post_id=${postId}`, {
    method: "GET",
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json(); 
    })
    .then(async data => {
        const description = data.description
        const price = data.price
        const productName = data.name
        const username = data.user.username
        const email = data.user.email
        const imageLinks = await getImages(postId) // This waits for getImages to call the backend and then return the links 

        // Print post, user, and images info
        console.log(description + " " + price + " " + productName + " " + username + " " + email)
        console.log(imageLinks)
    })
    .catch(error => {
        console.error("Error:", error);
    });

}

const getImages = async (postId) => { 
    try {
        const imageLinks = []
        const response = await fetch(`http://localhost:8080/images/get-images?post_id=${postId}`, {
            method: "GET",
        });

        if (!response.ok) {
            throw new Error("Network response was not ok");
        }

        const data = await response.json(); 
        data.forEach(item => {
            imageLinks.push(item.link);
        });

        return imageLinks; 

    } catch (error) {
        throw new Error("Error:", error);
    }
}
```

```json

JSON from getPost function
{
  "id": 2,
  "description": "This is a chair.",
  "price": 9.99,
  "name": "Chair",
  "user": {
    "id": 1,
    "username": "Angel",
    "password": "Password123",
    "email": "aarojas@usc.edu"
  },
  "date": "2023-11-09T17:45:15"
}

JSON from getImages function (using different postId than JSON directly above)
[
  {
    "id": 5,
    "link": "https://images.pexels.com/photos/14998047/pexels-photo-14998047/free-photo-of-photo-of-a-camera-with-opened-lens.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "post_id": {
      "id": 1,
      "description": "d",
      "price": 1,
      "name": "t",
      "user": {
        "id": 1,
        "username": "Angel",
        "password": "Password123",
        "email": "aarojas@usc.edu"
      },
      "date": "2023-11-15T00:11:42"
    }
  },
  {
    "id": 6,
    "link": "https://images.unsplash.com/photo-1608204683146-0f7e909dca59?auto=format&fit=crop&q=80&w=1000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fG11bHRpbWVkaWF8ZW58MHx8MHx8fDA%3D",
    "post_id": {
      "id": 1,
      "description": "d",
      "price": 1,
      "name": "t",
      "user": {
        "id": 1,
        "username": "Angel",
        "password": "Password123",
        "email": "aarojas@usc.edu"
      },
      "date": "2023-11-15T00:11:42"
    }
  }
]

```