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
                verified: '',
                errors: [],
                permition: false,
                un: '',
                pw: '',
                showErrorEmptyField: false,
                showErrorUsername: false,
                showErrorPassword: false
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
				<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField === true">All fields must be filled out!</br></p>
				<p class="errorMessageRegisterUser" v-if="this.showErrorUsername === true">Wrong email!</br></p>
				<p class="errorMessageRegisterUser" v-if="this.showErrorPassword === true">Wrong password!</br></p>
		</div>
	</div>
</div>
    `,
    methods: {
    	getInputValue: function(){
    		this.showErrorEmptyField = false;
            this.showErrorUsername = false;
            this.showErrorPassword = false;
            
    		this.permition = false;
    		un = this.User.username;
    		pw = this.User.password;
    		if((un != '' && pw != '') && (un !== undefined && pw !== undefined )){
    			this.permition = true;
    		}
    		else{
    			this.showErrorEmptyField = true;
    		}
    	},
        login: function(User){
        	if(this.permition === true){
        		axios
            	.post('rest/webproj/verifyUser', {"email": this.User.username, "password": this.User.password})
            	.then(response => {
            		this.verified = response.data;
            		if(this.verified === 'yes'){  //user inputs email and password correctly.
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
                        if(this.verified === 'username'){
                        	this.showErrorUsername = true;
                        }
                        else if (this.verified === 'password'){
                        	this.showErrorPassword = true;
                        }
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
    },
});

