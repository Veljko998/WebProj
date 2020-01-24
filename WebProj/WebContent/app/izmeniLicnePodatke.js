Vue.component("izmeni-podatke" ,{
	data: function() {
		return {
			title: "Izmena licnih podataka",
			role: '',
			email: '',
			organisations: null,
			User: null,
			NewUser:{},
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

					<!-- first name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Ime:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" v-bind:placeholder="User.ime" v-model="NewUser.ime"/>
					</div>

					<!-- last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Prezime:</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" v-bind:placeholder="User.prezime" v-model="NewUser.prezime"/>
					</div>

					<!-- email -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Mejl adresa:</span>
						</div>
						<input type="text" class="form-control" name="email" id="email" v-bind:placeholder="User.email" v-model="NewUser.email"/>
					</div>

					<!-- password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Lozinka:</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" v-bind:placeholder="User.lozinka" v-model="NewUser.lozinka"/>
					</div>

					<!-- confirm password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="confirm_password" class="form-control" name="password" id="password" v-bind:placeholder="User.lozinka" v-model="NewUser.lozinkaPonovo"/>
					</div>
					
					<!-- shows readonly organisation -->
					<div class="input-group mb-4" v-if="role !== 'superadmin'">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organizacija:</span>
						</div>
						<input type="text" class="form-control" name="organisation" id="organisation" v-bind:placeholder="User.organizacija" readonly="readonly"/>
					</div>
					
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena!</br></p>
					<p class="errorMessageRegisterUser" v-if="this.showErrorPassword == true">Password nije isti!</p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="addUser()">Izmeni podatke</button>
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
            			var currentRole = localStorage.getItem("role");
            			if(currentRole === 'administrator'){
            				router.push({path: "/administrator"});
            			}else if (currentRole === 'superadministrator') {
            				router.push({path: "/superadministrator"});
						}
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