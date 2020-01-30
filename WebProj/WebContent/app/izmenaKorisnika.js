Vue.component("izmena-korisnika", {
	data: function () {
        	return {
        		title: 'Edit User',
                role: 'noRule',
                email: '',
                showErrorEmptyField: false,
    			showErrorUserExists: false,
    			User: {},
    			machines: null,
                oldEmail: '',
                canAddUser: false
                
        	}
    },
    template:`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					{{ this.title }}
				</div>
				<div class="card-body">

					<!-- input field for first name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">new name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter new Name" v-model="User.name"/>
					</div>

					<!-- input field for last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">new surname</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" placeholder="Enter new Surname" v-model="User.surname"/>
					</div>

					<!-- input field for surname -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Users password</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" placeholder="Enter new password" v-model="User.password"/>
					</div>

					<!-- input field for check surname -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="password" class="form-control" name="passwordToConfirm" id="passwordToConfirm" placeholder="Confirm new password" v-model="User.passwordToConfirm"/>
					</div>

					<!-- select role from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Roles</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="User.role">
							<option selected>Choose...</option>
							<option value="KORISNIK">User</option>
							<option value="ADMIN">Admin</option>
						</select>
					</div>
					
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena!!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); editUser();">Edit user</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    `,
    methods: {
    	load: function() {
    		this.oldEmail = localStorage.getItem("oldEmail");
    		
    		this.role = localStorage.getItem("role");
    		this.email = localStorage.getItem("email");
    		
    		localStorage.removeItem('oldEmail');
    	},
    	emptyField: function(){
			this.showErrorEmptyField = false;
			this.showErrorUserExists = false;
			this.canAddUser = false;
			
			if((this.User.name !== '' && this.User.name != undefined) && 
			(this.User.surname !== '' && this.User.surname != undefined) &&
			(this.User.password !== '' && this.User.password != undefined) &&
			(this.User.passwordToConfirm !== '' && this.User.passwordToConfirm != undefined) &&
			(this.User.role !== '' && this.User.role != undefined && this.User.role !== 'Choose...')){
				if (this.User.password != this.User.passwordToConfirm) {
					console.log("Lozinke se ne podudaraju.")
				}else {
					this.showErrorEmptyField = false;
					console.log("Sva polja su popunjena.");
					this.canAddUser = true;
				}
			}else{
				console.log("Nisu sva polja popunjena.");
				this.showErrorEmptyField = true;
				this.canAddUser = false;
			}
		},
		editUser: function(){
			console.log("Dosli smo i dovde: " + this.canAddUser );
			if (this.canAddUser == true || this.canAddUser === true) {

				axios
            	.post('rest/userService/editUser', {"oldEmail": this.oldEmail, "role": this.User.role, "name": this.User.name, 
            		"surname": this.User.surname, "password": this.User.password, "passwordToConfirm": this.User.passwordToConfirm})
            	.then(response => {
            		var UserSuccesfullyRegistered = response.data;
            		console.log("korisnik uspesno izmenjen? : " + UserSuccesfullyRegistered);
            		
            		if(UserSuccesfullyRegistered){
            			console.log("korisnik je uspesno izmenjen.");
            			router.push({path: "/pregledKorisnika"}); // Bring user back to pregledkorisnikova
            		}else{
            			console.log("korisnik nije upisan.");
            		}
            	});
			}
		},
    },
    mounted () {  //created 
    	this.load();
    },
});







