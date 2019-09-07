window.onload = function() {
    document.getElementById('profile').style.display = 'none';
    document.getElementById('settings').style.display = 'none';  
	getTodos();
}
function loadTab(tabname){
	var todotab = document.getElementById('todo');	
	var profiletab = document.getElementById('profile');	
	var settingstab = document.getElementById('settings');	
	switch(tabname){
		case "todo":
			todotab.style.display = '';
			profiletab.style.display = 'none';
			settingstab.style.display = 'none';
		break;		
		case "profile":
			profiletab.style.display = '';
			todotab.style.display = 'none';
			settingstab.style.display = 'none';
		break;		
		case "settings":
			settingstab.style.display = '';
			profiletab.style.display = 'none';
			todotab.style.display = 'none';
		break;		
	}
}