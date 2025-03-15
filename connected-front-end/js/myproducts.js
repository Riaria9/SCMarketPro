// get the user id from session storage
const userId = sessionStorage.getItem('userId') == undefined ? 3 : null;

// Fetch all posts straight after loading into this page
fetchAllPosts(userId)


/**
 * This function will fetch all the posts for a given user id
 * @param {int} id The id of user
 */
function fetchAllPosts(id)
{
    try {
        fetch(`http://localhost:8080/posts/get-all-user-posts?user_id=${id}`,{
            method: 'GET',
        }) 
        .then(response => {
            if(!response.ok){
            throw new Error("Backend response was not ok")
            }

            return response.json()
        })
        .then(async data => {
            console.log(data)
            let allPosts = []

            // Build a post (id,link) for all user posts
            await Promise.all(data.map(async post => {
                // Get a single image for this post
                const link = await fetchImages(post.id);
                
                // Build an object with this post id and its image
                const postObject = {
                    post_id: post.id,
                    post_link: link,
                }
                
                // Add to array containing all the posts
                allPosts.push(postObject)
            }))

            // After getting all posts images, build them on the page
            buildPosts(allPosts)
        })
        .catch(error =>{
            console.log(error)
        })
 
    } catch (error) {
        
    }
}

/**
 * This function will make a fetch call to get all of the images associated with a postId but only return 1
 * @param {int} id 
 * @returns image link
 */
async function fetchImages(id) {
    try {
      const response = await fetch(`http://localhost:8080/images/get-images?post_id=${id}`, {
          method: "GET",
      })
  
      if (!response.ok) {
          throw new Error("Backend response was not ok");
      }
  
      const data = await response.json()
      // Return just the first link
      return data[0].link
  
      
  
    } catch (error) {
        // In case of any error, return blank link
        return ''
    }
    
}

/**
 * Deletes a post from database
 * @param {int} id 
 */
function deletePost(id)
{
    try {
        fetch(`http://localhost:8080/posts/delete-post?user_id=${userId}&post_id=${id}`,{
            method: "DELETE"
        })
        .then(response => {
            if(!response.ok){
                throw new Error("Backend response was not ok")
            }

            
        })
        .catch(error =>{
            console.log(error)
        })
    } catch (error) {
        console.log(error)
    }
}

/**
 * Dynamically creates all posts previews on the front end
 * @param {Array} allPosts Array contaning objects (postId,postImage)
 */
function buildPosts(allPosts)
{

    const postsContainer = document.getElementById('posts-container');

    allPosts.forEach(post => {
        // Create elements
        const productPreview = document.createElement('div')
        const productPreviewOptions = document.createElement('div')
        const deleteButton = document.createElement('button')
        const updateButton = document.createElement('button')
        const viewButton = document.createElement('button')

        // Set attributes and classes
        productPreview.classList.add('product-preview')
        productPreview.id = post.post_id
        productPreviewOptions.classList.add('product-preview-options')
        deleteButton.type = 'button'
        deleteButton.classList.add('btn', 'bg-danger')
        deleteButton.textContent = 'Delete'
        updateButton.type = 'button'
        updateButton.classList.add('btn', 'bg-primary')
        updateButton.textContent = 'Update'

        viewButton.type = 'button'
        viewButton.classList.add('btn', 'bg-secondary')
        viewButton.textContent = 'View'

        // Append elements
        productPreviewOptions.appendChild(deleteButton)
        productPreviewOptions.appendChild(updateButton)
        productPreviewOptions.appendChild(viewButton)
        productPreview.appendChild(productPreviewOptions)
        postsContainer.appendChild(productPreview)
        

        // Set background image
        productPreview.style.backgroundImage = `url(${post.post_link})`

        
        deleteButton.addEventListener('click', () => {
            const confirmation = confirm('Are you sure you want to delete this post?');
    
            if (confirmation) {
                // Remove product from page
                productPreview.remove();
                deletePost(post.post_id)

            } else {
                // Do nothing
            }
        })

        updateButton.addEventListener('click', () => { 
            // Set the postId in session storage
            sessionStorage.setItem('postId',post.post_id)

            // Redirect to update page
            window.location.href = 'update.html'
        })

        viewButton.addEventListener('click',()=> {
            // Store the post id in session storage
            sessionStorage.setItem('postId',post.post_id)

            // Redirect to product page
            window.location.href = 'product.html'
        })

    })   
}