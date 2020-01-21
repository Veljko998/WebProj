Vue.component("dodaj-organizaciju" ,{
	data: function() {
		return {
			title: "Dodavanje organizacije",
			role: '',
			email: '',
			organisations: null,
			Organisation: {},
			showErrorEmptyField: false,
			showErrorOrganisationExists: false,
			canAddDisc: false
		}
	},
	template: 
	`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					Add organisation
				</div>
				<div class="card-body">

					<!-- Dodavanje imena organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organisation name:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter Organisation Name" v-model="Organisation.name"/>
					</div>

					<!-- Dodavanje opisa organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organisation description:</span>
						</div>
						<textarea class="form-control" id="description" name="description" placeholder="Enter description for organisation" v-model="Organisation.description" rows="3"></textarea>
					</div>

					<!-- Dodavanje upload-a slike -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organisation logo:</span>
						</div>
						<input type="file" class="form-control-file" name="logo" id="logo" v-model="Organisation.logo"/>
					</div>
					
					<p class="errorMessageRegisterDisc" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageOrganisationExists" v-if="this.showErrorOrganisationExists == true">Organizacija sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" >Add new organisation</button>
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
		var path = 'rest/data/getOrganisations/' + this.role + '/' + this.email
    
		axios
    		.get(path)
    		.then(response => (this.organisations = response.data))
    },
});