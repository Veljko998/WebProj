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
                verified: false
        	}
    },
    template:`
<div>
	<h3>{{ title }}</h3>
	<label>Username: </label>
	<input name="username" type="text" v-model="User.username"/><br/>
	<label>Password: </label>
	<input name="password" type="text" v-model="User.password"/></br>
	<input type="checkbox" name="remember" />Remember me<br/>
	<button v-on:click="login(User)">Login</button></br>
	
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
</div>
`
    ,
    methods: {
//    	checkRole: function(){
//    		axios
//    		.post('rest/webproj/checkRole', {"email": this.User.username, "password": this.User.password})
//    		.then(response => {
//    			this.role = response.data;
//    		});
//    	},
        addUser: function(User){
        	this.users.push(User)
        	this.name = User.username
        },
        login: function(User){
        	axios
        	.post('rest/webproj/verifyUser', {"email": this.User.username, "password": this.User.password})
        	.then(response => {
        		this.verified = response.data;
        		toast(this.verified);
        		toast(response.data);
        		if(this.verified){  //user inputs email and password correctly
        			axios
	            		.post('rest/webproj/checkRole', {"email": this.User.username, "password": this.User.password})
	            		.then(response => {
	            			this.role = response.data;
	            			
	            			toast(this.role);
	            			if (this.role == "korisnik") {
	            				router.push({path: "/korisnik"});
	            			}else if (this.role == "administrator") {
								router.push({path: "/administrator"});
							}else if (this.role == "superadministrator"){
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

