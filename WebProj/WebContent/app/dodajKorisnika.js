Vue.component("dodaj-korisnika" ,{
	data: function() {
		return {
			title: "Dodavanje korisnika",
			role: '',
			email: '',
			organisations: null,
			User: {},
			showErrorEmptyField: false,
			showErrorPassword: false
		}
	},
	template: 
	`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					Register
				</div>
				<div class="card-body">

					<!-- input field for first name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Your name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter your Name" v-model="User.name"/>
					</div>

					<!-- input field for last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Your surname</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" placeholder="Enter your Surname" v-model="User.surname"/>
					</div>

					<!-- input field for email -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Your email</span>
						</div>
						<input type="text" class="form-control" name="email" id="email" placeholder="Enter your Email" v-model="User.email"/>
					</div>

					<!-- input field for password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Users password</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" placeholder="Enter your Password" v-model="User.password"/>
					</div>

					<!-- input field for check password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="confirm_password" class="form-control" name="password" id="password" placeholder="Confirm your Password" v-model="User.passwordToConfirm"/>
					</div>

					<!-- select role from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Roles</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="User.role">
							<option selected>Choose...</option>
							<option value="1">User</option>
							<option value="2">Admin</option>
						</select>
					</div>
					
					<!-- select organisation from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organisations</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="User.organisationName">
							<option selected>Choose...</option>
							
							<option v-for="o in organisations" :value="o">{{ o.ime }}</option>
						</select>
					</div>
					
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena!!!</br></p>
					<p class="errorMessageRegisterUser" v-if="this.showErrorPassword == true">Password nije isti!!!!!!</p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="addUser()">Register user</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		addUser: function(){
			if((this.User.name !== '' && this.User.name != undefined) && 
				(this.User.surname !== '' && this.User.surname != undefined) &&
				(this.User.email !== '' && this.User.email != undefined) &&
				(this.User.password !== '' && this.User.password != undefined) &&
				(this.User.passwordToConfirm !== '' && this.User.passwordToConfirm != undefined) &&
				(this.User.organisationName !== '' && this.User.organisationName != undefined && this.User.organisationName !== 'Choose...') &&
				(this.User.password !== this.User.passwordToConfirm)){
				this.showErrorEmptyField = false;
				if(this.User.password !== this.User.passwordToConfirm){
					this.showErrorPassword = true;
					console.log("Sifre se ne podudaraju.");
				}else {
					
				}
				console.log("Sva polja su popunjena.");
				
				axios
            	.post('rest/userService/registerUser', {"name": this.User.name, "surname": this.User.surname, "email": this.User.email, "password": this.User.password, "organisationName": this.User.organisationName, "role": this.User.role})
            	.then(response => {
            		var userSuccesfullyRegistered = response.data;
            		
            		if(userSuccesfullyRegistered){
            			console.log("Korisnik je uspesno registrovan.");
            		}else{
            			console.log("Korisnik nije registrovan.");
            		}
            	});
				
			}else{
				console.log("Nisu sva polja popunjena.");
				this.showErrorEmptyField = true;
			}

		}
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/overview/getJustOrganisations/' + this.role + '/' + this.email
    
		axios
    		.get(path)
    		.then(response => {
    			this.organisations = response.data
    		});
    },
});