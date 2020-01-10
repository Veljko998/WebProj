Vue.component("log-in", {
	data: function () {
        	return {
        		title: 'Login',
                User: {},
                users: null, //[]
                name: '',
                role: 'nema uloge',
                loading: true,
                errored: false,
                verified: false,
                errors: []
        	}
    },
    template:`
<div class="container-fluid">
	<h3>{{ title }}</h3>
	
	<p v-if="errors.length">
	    <b>Please correct the following error(s):</b>
	    <ul>
	    	<li v-for="error in errors">{{ error }}</li>
	    </ul>
  </p>
	
	<label>Username: </label>
	<input name="username" type="text" v-model="User.username"/><br/>
	<label>Password: </label>
	<input id="password-input" name="password" type="text" v-model="User.password"/></br>
	<input type="checkbox" name="remember" />Remember me<br/>
	<button v-on:click="login(User);checkForm();">Login</button></br>
	
	<!--
	<p>Ovo je samo da bi smo videli da li radi llogovanje. Obrisati kasnije.</p>
	<table border="1">
		<tr bgcolor="lightgrey">
			<th>Ime</th><th>Prezime</th>
		</tr>
		<tr v-for="i in users">
		<td> {{i.ime}}</td>
		<td> {{i.prezime}}</td>
		</tr>
		</table>
	-->
</div>
    `,
    methods: {
    	checkForm: function(){
    		if(this.User.username && this.User.password){
    			return true;
    		}
    		
    		this.errors = [];
    		
    		if(!this.User.username){
    			this.errors.push('Username or email required.');
    		}
    		if(!this.User.password){
    			this.errors.push('Password equired.');
    		}
    	},
        login: function(User){
        	axios
        	.post('rest/webproj/verifyUser', {"email": this.User.username, "password": this.User.password})
        	.then(response => {
        		this.verified = response.data;
        		if(this.verified){  //user inputs email and password correctly.
        			axios
	            		.post('rest/webproj/checkRole', {"email": this.User.username, "password": this.User.password})
	            		.then(response => {
	            			this.role = response.data;
	            			localStorage.setItem("role", response.data);
	            			localStorage.setItem("email", this.User.username);
	            			localStorage.setItem("isUserLogedIn", "true");
	            			
	            			if (this.role == "korisnik") {
	            				router.push({path: "/korisnik"});
	            			}else if (this.role == "admin") {
								router.push({path: "/administrator"});
							}else if (this.role == "superadmin"){
								router.push({path: "/superadministrator"});
							}
	            		});
        		}
        		else {
                    toast("Wrong email or password. Please try again.");
                }
        	});
        }
    },
    mounted () {  //created 
    	axios
    		.get('rest/webproj/getJustUsers')
    		.then(response => (this.users = response.data))
    	
	//    	.catch(error => {
	//        console.log(error)
	//        this.errored = true
	//    	})
	//    	.finally(() => this.loading = false)
    },
});

