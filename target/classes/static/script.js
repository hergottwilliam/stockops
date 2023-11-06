// TODO
// add comments everywhere
// make pretty
// + and - buttons for easy inventory changes, beside stock
// could store product json info in row, not edit button column
// user authentication
// make edit form be filled with original values
// reorder level alert, red or flagged
// garbage symbol for delete, maybe an are you sure message
// get rid of x axis scroll with full screen
// page refresh looks bad

const productContainer = document.getElementById("product-container");
const newProductButton = document.getElementById("new-product-button");
const newProductForm = document.getElementById("newProductForm");
const editProductForm = document.getElementById("editProductForm");

function NewProduct(productName, stock, price, description, reorderLevel, lastUpdated) {
	this.productName = productName;
	this.price = price;
	this.stock = stock;
	this.description = description;
	this.reorderLevel = reorderLevel;
	this.lastUpdated = lastUpdated;
}

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
			// Iterate through all products fetched from backend
			data.forEach((product) => {
				const productRow = document.createElement('div');
				productRow.classList.add('row', 'product-row');
				productRow.setAttribute("id", `${product.id}`); // store the primary key in the id of the row
				
				// DEBUGGING date formatting
				// console.log(`Date data from backend: ${product.lastUpdated}`);
				
				// Columns displayed as: name, stock, price, lastupdated date, edit and delete buttons
				const productName = createProductNameColumn(product.productName, product.description);
				const productStock = createProductColumn(product.stock);
				
				// Format displayed priced to 2 decimal points
				const productPrice = createProductColumn(product.price.toFixed(2));
				const productLastUpdated = createProductColumn(product.lastUpdated);
				
				const editProductButton = createEditButtonColumn(product); // passing the current product, prior to user edit
				const deleteProductButton = createDeleteButtonColumn('Delete', 'btn-danger', () => deleteProduct(product));
				
				productRow.appendChild(productName);
				productRow.appendChild(productStock);
				productRow.appendChild(productPrice);
				productRow.appendChild(productLastUpdated);
				productRow.appendChild(editProductButton);
				productRow.appendChild(deleteProductButton);
				
				productContainer.appendChild(productRow);
			});
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

function addNewProductToDatabase(event) {
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
    column.classList.add('col');
    column.textContent = text;
    return column;
}


function createProductNameColumn(productName, productDescription) { // creates name column with info button
    const column = document.createElement('div');
    column.classList.add('col');
    column.textContent = productName;
    
    const button = document.createElement('button');
    button.textContent = "i";
    button.classList.add("btn", "btn-secondary");
    
    
    button.addEventListener('click', () => {
		$('#productDescriptionModal').modal('show'); // opens product description modal
		displayProductDescription(productDescription); // sets text content of modal to product description
		
	}); 
    
    column.appendChild(button);
    
    return column;
}

// Helper function to create a Bootstrap column with a button
function createDeleteButtonColumn(label, className, clickHandler) {
    const column = document.createElement('div');
    column.classList.add('col');
    
    const button = document.createElement('button');
    button.classList.add('btn', className);
    button.textContent = label;
    button.addEventListener('click', clickHandler);
    
    column.appendChild(button);
    return column;
}

function createEditButtonColumn(originalProduct) {
    const column = document.createElement('div');
    column.classList.add('col');
    
    const button = document.createElement('button');
    button.classList.add('btn', 'btn-primary');
    button.textContent = "Edit";
    button.addEventListener('click', () => {
		$('#editProductModal').modal('show'); // opens edit product form
		
		editProductForm.setAttribute('data-original-product', JSON.stringify(originalProduct));
		
		
	}); 
    
    column.appendChild(button);
    return column;	
}

function updateProductToDatabase(event) {
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

function displayProductDescription(productDescription) {
	const productDescriptionText = document.getElementById("productDescriptionText");
	productDescriptionText.textContent = productDescription;
}

function deleteProduct(productToDelete) {
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

function clearEditProductForm() {
    document.querySelector('#editName').value = "";
    document.querySelector('#editPrice').value = "";
    document.querySelector('#editStock').value = "";
    document.querySelector('#editDescription').value = "";
    document.querySelector('#editReorderLevel').value = "";
}

function clearNewProductForm() {
    document.querySelector('#name').value = "";
    document.querySelector('#price').value = "";
    document.querySelector('#stock').value = "";
    document.querySelector('#description').value = "";
    document.querySelector('#reorder-level').value = "";
}

console.log("js script run");
updateInventoryPage();
newProductForm.addEventListener('submit', addNewProductToDatabase);
editProductForm.addEventListener('submit', updateProductToDatabase);
