
/**
 * Fetches a post by its id
 * @param {int} id Post id
 */
function fetchPost(id) {
  try {
   fetch(`http://localhost:8080/posts/get-post?post_id=${id}`,{
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
        let description = data.description
        let price = data.price
        let name = data.name
        let username = data.user.username
        let email = data.user.email
        let imageLinks = await fetchImages(id)
        
        buildPost(name,email,description,price,imageLinks,username)
      })
    .catch(error =>{
        console.log(error)
      })

  } catch (error) {
    console.log(error) 
  }
}

/**
 * Gets all the images of a certain post
 * @param {int} id Post id
 * @returns Array of image links
 */
async function fetchImages(id) {
  try {
    const imageLinks = []
    const response = await fetch(`http://localhost:8080/images/get-images?post_id=${id}`, {
        method: "GET",
    })

    if (!response.ok) {
        throw new Error("Backend response was not ok");
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
/**
 * Will dynamically build the post on the front end
 * @param {string} name 
 * @param {string} email 
 * @param {string} description 
 * @param {double} price 
 * @param {[string]} images 
 * @param {string} username 
 */
function buildPost(name,email,description,price,images,username) {

  // Get the log in flag
  let isLoggedIn = sessionStorage.getItem("isLoggedIn")

  // Set the product name
  document.getElementById('product-name').innerHTML = `${name}`
  // Get the div for details
  const rightDetailsDiv = document.getElementById('right-info');

  // Create and populate all details
  const sellerNameP = document.createElement('p');
  sellerNameP.className = 'seller-name';
  sellerNameP.textContent = `Seller: ${username}`;

  const sellerEmailP = document.createElement('p');
  sellerEmailP.className = 'seller-email';
  sellerEmailP.textContent = `Contact: ${email}`;

  const priceP = document.createElement('p');
  priceP.className = 'price';
  priceP.textContent = `$${price}`;

  const descriptionP = document.createElement('p');
  descriptionP.className = 'description';
  descriptionP.textContent = `${description}`;

  // Add all p tags to detail div
  rightDetailsDiv.appendChild(sellerNameP);
  
  // Only show email if user is logged in
  if(isLoggedIn === 'true')
    rightDetailsDiv.appendChild(sellerEmailP)
  
  rightDetailsDiv.appendChild(priceP)
  rightDetailsDiv.appendChild(descriptionP)

  // Only append the add to bookmarks button if a user is logged in
  
  if(isLoggedIn === 'true')
  {
    const bookmarkButton = document.createElement('button');
    bookmarkButton.textContent = 'Add to Bookmarks';
    bookmarkButton.className = 'add-to-bookmarks';
    bookmarkButton.onclick = function() {
      window.location.href = 'bookmark.html';
    };

    rightDetailsDiv.appendChild(bookmarkButton);
  }
 

  // Build the carosel
  const carouselInner = document.querySelector('.carousel-inner');
  const indicatorsContainer = document.querySelector('.carousel-indicators');

  images.forEach((imageSrc, index) => {
    const carouselItemDiv = document.createElement('div');
    carouselItemDiv.classList.add('carousel-item');
    if (index === 0) {
      carouselItemDiv.classList.add('active');
    }

    const imageElement = document.createElement('img');
    imageElement.src = imageSrc;
    imageElement.classList.add('d-block', 'w-100');
    imageElement.alt = `Image ${index + 1}`;

    carouselItemDiv.appendChild(imageElement);
    carouselInner.appendChild(carouselItemDiv);

    const indicatorButton = document.createElement('button');
    indicatorButton.setAttribute('type', 'button');
    indicatorButton.setAttribute('data-bs-target', '#carouselExampleIndicators');
    indicatorButton.setAttribute('data-bs-slide-to', index.toString());
    if (index === 0) {
      indicatorButton.classList.add('active');
      indicatorButton.setAttribute('aria-current', 'true');
    }
    indicatorButton.setAttribute('aria-label', `Slide ${index + 1}`);

    indicatorsContainer.appendChild(indicatorButton);
  });

  const carousel = new bootstrap.Carousel(document.querySelector('.carousel'), {
    // Disable automatic cycling
    interval: false 
  });

  
  const prevButton = document.querySelector('.carousel-control-prev');
  prevButton.addEventListener('click', () => {
    carousel.prev();
  });

  const nextButton = document.querySelector('.carousel-control-next');
  nextButton.addEventListener('click', () => {
    carousel.next();
  });

}
