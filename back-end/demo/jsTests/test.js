const user_id = 3
const submitForm  = () =>{

    const formData = new FormData(document.getElementById("productForm"));
    formData.append('user_id', user_id);

    // Example categories
    formData.append('categories',['Supplies','Dorm'])

    fetch("http://localhost:8080/posts/upload-post", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.text();
    })
    .then(data => {
        console.log("Success:", data);
    })
    .catch(error => {
        console.error("Error:", error);
    });

}

const deletePost = () => {
    const userId = 1
    const postId = 12
    
    fetch(`http://localhost:8080/posts/delete-post?user_id=${userId}&post_id=${postId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (response.ok) {
            // Handle successful deletion
            console.log("Post has been deleted.");
        } else {
            // Handle errors if any
            console.error("Failed to delete the post.");
        }
    })
    .catch(error => {
        // Handle network errors
        console.error("Network error:", error);
    });
}

const deleteImage = () =>{
    const imageId = 8
    
    fetch(`http://localhost:8080/images/delete-image?imageId=${imageId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (response.ok) {
            // Handle successful deletion
            console.log("Image has been deleted.");
        } else {
            // Handle errors if any
            console.error("Failed to delete the post.");
        }
    })
    .catch(error => {
        // Handle network errors
        console.error("Network error:", error);
    });
}

const postImage = () =>{
    const postId = 11
    const formData = new FormData(document.getElementById("single-image"));
    formData.append('post_id', postId);

    fetch("http://localhost:8080/images/upload-image", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.text();
    })
    .then(data => {
        console.log("Success:", data);
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

const getPost = () => {
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
        console.log(JSON.stringify(data))

        const description = data.description
        const price = data.price
        const productName = data.name
        const username = data.user.username
        const email = data.user.email
        const imageLinks = await getImages(postId) // This waits for getImages to call the backend and then return the links 

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