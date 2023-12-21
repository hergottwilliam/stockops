
const productContainer = document.getElementById("product-container"); // main container with all products
const newProductButton = document.getElementById("new-product-button"); // "New Product" button
const newProductForm = document.getElementById("newProductForm"); // Pop-up form for new product
const editProductForm = document.getElementById("editProductForm"); // Pop-up form for edit product

// New products do not have an id as they have yet to be put into mySQL, primary key is auto-generated
function NewProduct(productName, stock, price, description, reorderLevel, lastUpdated) {
	this.productName = productName;
	this.price = price;
	this.stock = stock;
	this.description = description;
	this.reorderLevel = reorderLevel;
	this.lastUpdated = lastUpdated;
}

// Model for product objects pulled from backend, has an id
function Product(id, productName, stock, price, description, reorderLevel, lastUpdated) {
	this.id = id;
	this.productName = productName;
	this.stock = stock;
	this.price = price;
	this.description = description;
	this.reorderLevel = reorderLevel;
	this.lastUpdated = lastUpdated;
}

function updateInventoryPage() { // populate page with all products in database
	// clear displayed products first
	clearPage();
	
	fetch('http://localhost:8080/api/products/all') // calls method to fetch ALL products
		.then(response => response.json())
		.then((data) => {
			
			const fragment = document.createDocumentFragment();
			// Iterate through all products fetched from backend
			data.forEach((product) => {
				const productRow = document.createElement('div');
				productRow.classList.add('row', 'product-row');
				productRow.setAttribute("id", `${product.id}`); // store the primary key in the id of the row
				
				// DEBUGGING date formatting
				// console.log(`Date data from backend: ${product.lastUpdated}`);
				

				
				// Columns displayed as: name, stock, price, lastupdated date, edit and delete buttons
				const productName = createProductNameColumn(product.productName, product.description);
				const productStock = createProductStockColumn(product);
				
				// Format displayed priced to 2 decimal points
				const productPrice = createProductColumn(product.price.toFixed(2));
				const productLastUpdated = createProductColumn(product.lastUpdated);
				
				const editProductButton = createEditButtonColumn(product); // passing the current product, prior to user edit
				const deleteProductButton = createDeleteButtonColumn('btn-danger', () => deleteProduct(product)); 
				
				// add all columns to row
				productRow.appendChild(productName);
				productRow.appendChild(productStock);
				productRow.appendChild(productPrice);
				productRow.appendChild(productLastUpdated);
				productRow.appendChild(editProductButton);
				productRow.appendChild(deleteProductButton);
				
				fragment.appendChild(productRow); // add row to main container
			});
			productContainer.appendChild(fragment);
		})
		.catch((error) => {
			console.error('Error fetching all products:', error);
			
		});
}

function clearPage() { // removes all products on the page to prevent duplicates
	const productsToBeDeleted = document.querySelectorAll(".product-row");
	for (let i = 0; i < productsToBeDeleted.length; i++) {
		productsToBeDeleted[i].remove();
	}
}

function addNewProductToDatabase(event) { // add a new product to database using new product form
	event.preventDefault();
	console.log("addNewProductToDatabase method called.")
	const apiUrl = 'http://localhost:8080/api/products';
	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split("T")[0];
	
	let createdNewProduct = new NewProduct(
		document.querySelector('#name').value,
		document.querySelector('#stock').value,
		document.querySelector('#price').value,
		document.querySelector('#description').value,
		document.querySelector('#reorder-level').value,
		formattedDate // formatted YYYY-MM-DD
	)
	
	fetch(apiUrl, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(createdNewProduct)
	})
	.then(response => response.json())
	.then(data => {
		console.log("New product added:", data);
		clearNewProductForm();
		updateInventoryPage();
	})
	.catch(error => {
		console.error("Error adding new product:", error);
	});
	
	$('#addProductModal').modal('hide');
	
	return false;
}


function createProductColumn(text) { // creates column with text content (used for price and date)
    const column = document.createElement('div');
    column.classList.add('col-md-1');
    column.textContent = text;
    return column;
}


function createProductNameColumn(productName, productDescription) {
    // Create the column
    const column = document.createElement('div');
    column.classList.add('col-md-4');

    // Create a paragraph for the product name
    const productNameParagraph = document.createElement('p');
    productNameParagraph.textContent = productName;
    productNameParagraph.classList.add('text-right'); // Add 'text-right' class for right alignment
    column.appendChild(productNameParagraph);

    // Create the info button
    const button = document.createElement('button');
    button.textContent = "i";
    button.classList.add("btn", "btn-info");

    // Add click event listener to the button
    button.addEventListener('click', () => {
        $('#productDescriptionModal').modal('show'); // opens product description modal
        displayProductDescription(productDescription); // sets text content of modal to product description
    });

    // Append the button to the column
    column.appendChild(button);

    // Return the column
    return column;
}

function createProductStockColumn(product) { // creates column with stock and + , - buttons
    const column = document.createElement('div');
    column.classList.add('col-md-2');
    column.textContent = product.stock;
    
    if (product.stock <= product.reorderLevel) { // If product stock is below or equal to the reorder level, give notice
		column.style.backgroundColor = "red";
	}
    
    // + button increase stock by 1
    const increaseButton = document.createElement('button');
    increaseButton.textContent = "+";
    increaseButton.classList.add("btn", "btn-light");
    
    increaseButton.addEventListener('click', () => {
		event.preventDefault();
		increaseStockByOne(product);
	});
	
	column.appendChild(increaseButton);
    
    // - button descrease stock by 1
    const decreaseButton = document.createElement('button');
    decreaseButton.textContent = "-";
    decreaseButton.classList.add("btn", "btn-light");
    
    decreaseButton.addEventListener('click', () => {
		event.preventDefault();
		decreaseStockByOne(product);
	});
	
	column.appendChild(decreaseButton);
    
    return column;
}


function increaseStockByOne(product) { // triggered by "+" button
	const apiUrl = 'http://localhost:8080/api/products';
	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split("T")[0];
	
	updatedProduct = new Product(
		product.id,
		product.productName,
		product.stock + 1,
		product.price,
		product.description,
		product.reorderLevel,
		formattedDate // formatted YYYY-MM-DD
	);
	
	fetch(apiUrl, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updatedProduct)
	})
	.then(response => response.json())
	.then(data => {
		console.log("Product updated:", data);
		
		updateInventoryPage();
	})
	.catch(error => {
		console.error("Error updating product:", error);
	});
	
}

function decreaseStockByOne(product) { // triggered by "-" button
	const apiUrl = 'http://localhost:8080/api/products';
	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split("T")[0];
	
	updatedProduct = new Product(
		product.id,
		product.productName,
		product.stock - 1,
		product.price,
		product.description,
		product.reorderLevel,
		formattedDate // formatted YYYY-MM-DD
	);
	
	fetch(apiUrl, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updatedProduct)
	})
	.then(response => response.json())
	.then(data => {
		console.log("Product updated:", data);
		
		updateInventoryPage();
	})
	.catch(error => {
		console.error("Error updating product:", error);
	});
}

function createDeleteButtonColumn(className, clickHandler) { // create delete button for each product
    const column = document.createElement('div');
    column.classList.add('col-md-1');
    
    const button = document.createElement('button');
    button.classList.add('btn', className);
    
    const trashIcon = document.createElement('i');
    trashIcon.classList.add('fas', 'fa-trash-alt'); // Using the Font Awesome trash can icon
    
    button.appendChild(trashIcon);
    
    
    button.addEventListener('click', clickHandler);
    
    column.appendChild(button);
    return column;
}

function createEditButtonColumn(originalProduct) { // create edit button for each product, triggers popup form
    const column = document.createElement('div');
    column.classList.add('col-md-1');
    
    const button = document.createElement('button');
    button.classList.add('btn', 'btn-warning');
    button.textContent = "Edit";
    button.addEventListener('click', () => {
		$('#editProductModal').modal('show'); // opens edit product form
		
		editProductForm.setAttribute('data-original-product', JSON.stringify(originalProduct)); // store original data in the form temporarily
		
		// populate edit form with current values
		document.querySelector('#editName').value = originalProduct.productName;
		document.querySelector('#editPrice').value = originalProduct.price;
		document.querySelector('#editStock').value = originalProduct.stock;
		document.querySelector('#editDescription').value = originalProduct.description;
		document.querySelector('#editReorderLevel').value = originalProduct.reorderLevel;
		
	}); 
    
    column.appendChild(button);
    return column;	
}

function updateProductToDatabase(event) { // after product is editted, update database with new info
	event.preventDefault();
	const apiUrl = 'http://localhost:8080/api/products';
	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split("T")[0];
	
	const originalProduct = JSON.parse(editProductForm.getAttribute('data-original-product'));
	
	updatedProduct = new Product(
		originalProduct.id,
		document.querySelector('#editName').value,
		document.querySelector('#editStock').value,
		document.querySelector('#editPrice').value,
		document.querySelector('#editDescription').value,
		document.querySelector('#editReorderLevel').value,
		formattedDate // formatted YYYY-MM-DD
	);
	
	fetch(apiUrl, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updatedProduct)
	})
	.then(response => response.json())
	.then(data => {
		console.log("Product updated:", data);
		clearEditProductForm();
		updateInventoryPage();
	})
	.catch(error => {
		console.error("Error updating product:", error);
	});
	
	$('#editProductModal').modal('hide'); // closes edit product form	
}

function displayProductDescription(productDescription) { // pop up when "i" button is clicked, displays description of product
	const productDescriptionText = document.getElementById("productDescriptionText");
	productDescriptionText.textContent = productDescription;
}

function deleteProduct(productToDelete) { // delete product from database
	const apiUrl = "http://localhost:8080/api/products";
	
	fetch(apiUrl, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(productToDelete) 
	})
	.then((response) => {
		if (response.ok) {
			console.log(`Product with ID ${productToDelete.id} deleted successfully.`);
			updateInventoryPage();
		} else {
			console.error(`Error deleting product with ID ${productToDelete.id}`);
		}
	})
	.catch((error) => {
		console.error(`Error deleting product with ID ${productToDelete.id}`, error);
	});
}

function clearEditProductForm() { // manually reset edit product form
    document.querySelector('#editName').value = "";
    document.querySelector('#editPrice').value = "";
    document.querySelector('#editStock').value = "";
    document.querySelector('#editDescription').value = "";
    document.querySelector('#editReorderLevel').value = "";
}

function clearNewProductForm() { // manually reset new product form
    document.querySelector('#name').value = "";
    document.querySelector('#price').value = "";
    document.querySelector('#stock').value = "";
    document.querySelector('#description').value = "";
    document.querySelector('#reorder-level').value = "";
}

console.log("js script run"); // confirm code has run
updateInventoryPage(); // update the page first with all products
newProductForm.addEventListener('submit', addNewProductToDatabase); // when new product form is submitted
editProductForm.addEventListener('submit', updateProductToDatabase); // when edit product form is submitted
