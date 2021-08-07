function saveuser(){
	if(document.getElementById("Check").checked){
		Cookies.set('user', document.getElementById('InputEmail').value, { expires: 30, path: '/' });
	} else {
		Cookies.remove('user', { path: '/' });
	}
}