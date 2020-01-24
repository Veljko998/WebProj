Vue.component("izmeni-podatke" ,{
	data: function() {
		return {
			title: "Izmena licnih podataka",
			role: '',
			email: '',
			organisations: null,
			User: {},
			showErrorEmptyField: false,
			showErrorPassword: false,
			loaded: false,
			lozinkaPonovo: ''
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

					<!-- shows readonly organisation -->
					<div class="input-group mb-4" v-if="role !== 'superadmin'">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organizacija:</span>
						</div>
						<input type="text" class="form-control" name="organisation" id="organisation" v-bind:placeholder="User.organizacija" readonly="readonly"/>
					</div>
				
					<!-- first name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Ime:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" v-model:value="User.ime"/>
					</div>

					<!-- last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Prezime:</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" v-model:value="User.prezime"/>
					</div>

					<!-- email -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Mejl adresa:</span>
						</div>
						<input type="text" class="form-control" name="email" id="email" v-model:value="User.email"/>
					</div>

					<!-- password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Lozinka:</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" v-model:value="User.lozinka"/>
					</div>

					<!-- confirm password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="confirm_password" class="form-control" name="password" id="password" v-model:value="lozinkaPonovo"/>
					</div>
					
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField === true">Sva polja moraju biti popunjena!</br></p>
					<p class="errorMessageRegisterUser" v-if="this.showErrorPassword === true">Password nije isti!</p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="changeData()">Izmeni podatke</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		changeData: function(){
			
			console.log("pass	" + this.User.lozinka);
			console.log("ponov	" + this.lozinkaPonovo);

		}
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/userService/getUser/' + this.email
    
		axios
    		.get(path)
    		.then(response => {
    			this.User = response.data
    		});
		
		this.loaded = true;
		this.lozinkaPonovo = this.User.lozinka;
    },
});