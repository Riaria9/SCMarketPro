
 const userId = sessionStorage.getItem('userId');

 try {
    fetch(`https://scmarketplace.up.railway.app/all-user-bookmarks?user_id=${userId}`,{
        method: "GET"
    })
    .then(response =>{
        console.log(response)
        return response.json()
    })
    .then(async data => {
        console.log(data)
        let allPosts = []

        // Build a post (id,link) for all user posts
        await Promise.all(data.map(async bookmark => {
            console.log(bookmark.post.id)
            // Get a single image for this post
            const link = await fetchImages(bookmark.post.id);
            
            // Build an object with this post id and its image
            const postObject = {
                post_id: bookmark.post.id,
                post_link: link,
                bookmark_id: bookmark.id,
                post_description: bookmark.post.description,
                post_name: bookmark.post.name,
                post_price: bookmark.post.price
            }
            
            // Add to array containing all the posts
            allPosts.push(postObject)
        }))

        // After getting all posts images, build them on the page
        buildPosts(allPosts)
    })
    .catch(error => {
        
    })
} catch (error) {
    
    alert("Bookmark.js error")

    sessionStorage.clear()
    sessionStorage.setItem('isLoggedIn',false)
    
    location.reload()
    
}

/**
 * This function will make a fetch call to get all of the images associated with a postId but only return 1
 * @param {int} id 
 * @returns image link
 */
async function fetchImages(id) {
    try {
      const response = await fetch(`https://scmarketplace.up.railway.app/images/get-images?post_id=${id}`, {
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
 * Dynamically creates all posts previews on the front end
 * @param {Array} allPosts Array contaning objects (postId,postImage)
 */
function buildPosts(allPosts)
{

    const postsContainer = document.getElementById('bookmarks');

    allPosts.forEach(post => {
        // Create elements
        const productPreview = document.createElement('div')
        const productPreviewOptions = document.createElement('div')
        const deleteButton = document.createElement('button')
        const updateButton = document.createElement('button')
        const viewButton = document.createElement('button')
        const description = document.createElement('div')
        const name = document.createElement('div')
        const price = document.createElement('div')

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
        description.textContent = post.post_description
        name.textContent = post.post_name
        price.textContent = '$' + post.post_price

        viewButton.type = 'button'
        viewButton.classList.add('btn', 'bg-secondary')
        viewButton.textContent = 'View'

        // Append elements
        productPreviewOptions.appendChild(deleteButton)
        //productPreviewOptions.appendChild(updateButton)
        productPreviewOptions.appendChild(viewButton)
        productPreview.appendChild(productPreviewOptions)
        postsContainer.appendChild(productPreview)
        postsContainer.appendChild(name)
        postsContainer.appendChild(price)
        postsContainer.appendChild(description)

        

        // Set background image
        productPreview.style.backgroundImage = `url(${post.post_link})`

        
        deleteButton.addEventListener('click', () => {
            const confirmation = confirm('Are you sure you want to delete this post?');
    
            if (confirmation) {
                // Remove product from page
                productPreview.remove();
                deleteBookmark(post.bookmark_id)

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

/**
 * Deletes a post from database
 * @param {int} id 
 */
function deleteBookmark(id)
{
    try {
        fetch(`https://scmarketplace.up.railway.app/delete-bookmark?user_id=${userId}&bookmark_id=${id}`,{
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