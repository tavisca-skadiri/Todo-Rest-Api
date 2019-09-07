let todoArray = [];

function createDatalistOption(value){
	let node = document.createElement('option');
    let val = document.createTextNode(value);
    node.appendChild(val);
    document.getElementById('todo-datalist').appendChild(node);
}
function createTodoRow(value){
	let editButton = document.createElement('input')
	editButton.type = "button"
	editButton.value = "Edit";
	editButton.onclick = function() {
		editTodo(this);
	}
	let deleteButton = document.createElement('input');
	deleteButton.type = "button";
	deleteButton.value = "Delete";
	deleteButton.onclick = function() {
		deleteTodo(this);
	}
    let todo_list = document.getElementById('todo-list');
	let new_row = todo_list.insertRow();
    new_row.insertCell(0).appendChild(document.createTextNode(value)); 
    new_row.insertCell(1).appendChild(editButton); 
    new_row.insertCell(2).appendChild(deleteButton); 
}
function getTodos(){
	fetch('http://localhost:9090/todos/', { 
		method: 'get' 
	}).then(response => {
    	return response.json()
	}).then(data => {
	    todoArray = data.todos;
		for(let i=0;i<todoArray.length;i++){
     		createDatalistOption(todoArray[i]);
			createTodoRow(todoArray[i])
        }
	});   
}
function addTodo(){
    let todo_name = document.getElementById('todo-name').value;
    if(todo_name == "")  
        alert("Enter a value to search");
    else{
		let requestBody = { todoname : todo_name };
		fetch('http://localhost:9090/todos/', {
		 	method: 'post',
		    body: JSON.stringify(requestBody)
		}).then(response => {
	    	return response.json()
		}).then(data => {
			createTodoRow(todo_name);
		});
    }
}
function searchTodo(value) { 
    let table = document.getElementById('todo-list');
    let tr = table.getElementsByTagName('tr');
    let str = document.getElementById('todo-name').value.toUpperCase();
    document.getElementById('todo-datalist').innerHTML = ''; 
    for(let i=0; i<tr.length; i++) {
        let td = tr[i].getElementsByTagName("td")[0];
        if (td) {
        	let txtValue = td.textContent || td.innerText;
          	tr[i].style.display = txtValue.toUpperCase().indexOf(str) > -1 ? "" : "none";
        }
    }
	for (let i = 0; i<todoArray.length; i++)
     	if(((todoArray[i].toLowerCase()).indexOf(value.toLowerCase()))>-1) 
     		createDatalistOption(todoArray[i]);
}
function editTodo(todobtn){
    let textbox = document.createElement('input');
    textbox.placeholder = "Edit todo name";
    textbox.type = "text";
    textbox.size = 10;
    
    let todo_name = todobtn.parentNode.parentNode.getElementsByTagName('td')[0];
    todo_name.innerHTML = '';
    todo_name.appendChild(textbox);

    todobtn.value = "Save";
    todobtn.onclick = function(){
        let todo_list = document.getElementById('todo-list');
		let requestBody = { todoname : textbox.value };
    	let url = 'http://localhost:9090/todos/' + (todobtn.parentNode.parentNode.rowIndex-1);
		fetch(url, {
		 	method: 'put',
		    body: JSON.stringify(requestBody)
		}).then(response => {
	    	return response.json()
		}).then(data => {
			todobtn.value = "Edit";
	        let val = document.createTextNode(textbox.value);
	        todo_name.appendChild(val); 
	        todo_name.removeChild(textbox);
	        todobtn.onclick = function() {
	            editTodo(this);
	        }
		});   
    }
}
function deleteTodo(todobtn){
	let todo_list = document.getElementById('todo-list');
	let row_to_delete = todobtn.parentNode.parentNode;
	let url = 'http://localhost:9090/todos/' + (row_to_delete.rowIndex-1);
	fetch(url, {
	 	method: 'delete'
	}).then(response => {
    	return response.json()
	}).then(data => {
	    todo_list.deleteRow(row_to_delete.rowIndex);
	});
}