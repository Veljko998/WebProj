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
                errors: [],
                permition: false,
                un: '',
                pw: ''
        	}
    },
    template:`
<div class="container">
    <div class="wrapper">
    	<div class="form-signin">       
    		<h3 class="form-signin-heading">Welcome Back! Please Sign In</h3>
    		
    			<input type="email" class="form-control" name="username" placeholder="Username" required="" autofocus="" v-model="User.username"/>
				<input type="password" class="form-control" name="password" placeholder="Password" required="" v-model="User.password"/>  
				<button class="btn btn-lg btn-primary btn-block" v-on:click="getInputValue(); login(User);">Login</button>		
		</div>
	</div>
</div>
    `,
    methods: {
    	getInputValue: function(){
    		this.permition = false;
    		console.log("USERNAME: " + this.User.username);
    		console.log("PASSWORD: " + this.User.password);
    		un = this.User.username;
    		pw = this.User.password;
    		if((un != '' && pw != '') && (un !== undefined && pw !== undefined )){
    			this.permition = true;
    			console.log("permition je true");
    		}else{
    			console.log("permition je false");
    		}
    	},
//    	checkForm: function(){
//    		if(this.User.username && this.User.password){
//    			return true;
//    		}
//    		
//    		this.errors = [];
//    		
//    		if(!this.User.username){
//    			this.errors.push('Username or email required.');
//    		}
//    		if(!this.User.password){
//    			this.errors.push('Password equired.');
//    		}
//    	},
        login: function(User){
        	if(this.permition === true){
        		console.log("usao sam ovde");
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
                        this.User.username = '';
                        this.User.password = '';
                    }
            	});
        	}
        	
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

