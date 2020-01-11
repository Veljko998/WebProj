Vue.component("dodaj-korisnika" ,{
	data: function() {
		return {
			title: "Dodavanje korisnika",
			role: '',
			email: '',
			organisations: null
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
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter your Name" />
					</div>

					<!-- input field for last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Your surname</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" placeholder="Enter your Surname" />
					</div>

					<!-- input field for email -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Your email</span>
						</div>
						<input type="text" class="form-control" name="email" id="email" placeholder="Enter your Email" />
					</div>

					<!-- input field for password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Users password</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" placeholder="Enter your Password" />
					</div>

					<!-- input field for check password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="confirm_password" class="form-control" name="password" id="password" placeholder="Confirm your Password" />
					</div>

					<!-- select role from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Roles</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01">
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
						<select class="custom-select" id="inputGroupSelect01">
							<option selected>Choose...</option>
							
							<option v-for="o in organisations" :value="o">{{ o.ime }}</option>
						</select>
					</div>

					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button">Register user</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		
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