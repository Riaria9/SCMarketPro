// document.getElementById('create-btn').addEventListener('click', function(event) {
//     // Stops page from reloading
//     event.preventDefault()

//     const form = new FormData(document.getElementById('post-form'))
//     console.log(form)

//     // Iterate through form entries and print their values
//     for (const entry of form.entries()) {
//         console.log(entry);
//         // If you want to print the values separately
//         console.log(`Name: ${entry[0]}, Value: ${entry[1]}`);
//     }
// });

function validateDecimal(input) {
    if (!/^(\d+(\.\d{0,2})?)?$/.test(input.value)) {
      input.value = input.value.substring(0, input.value.length - 1);
    }
}

document.getElementById('post-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission
    
    submitForm()
    // const form = new FormData(this);
  
    // for (const entry of form.entries()) {
    //   console.log(`Name: ${entry[0]}, Value: ${entry[1]}`);
    // }
    // console.log(document.getElementById('itemName').value)
    // console.log(document.getElementById('description').value)
    // // If you want to see the FormData object in the console
    // console.log(form);
  });


const submitForm  = () =>{

    const formData = new FormData(document.getElementById("post-form"));
    formData.append('user_id', parseInt(sessionStorage.getItem('userId'),10));
   

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