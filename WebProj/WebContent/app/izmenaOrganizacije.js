Vue.component("izmena-organizacije" ,{
	data: function() {
		return {
			title: "Izmena organizacije",
			role: '',
			email: '',
			organisations: null,
			resources: null,
			Organisation: {},
			Organizacija: {},
			showErrorEmptyField: false,
			showErrorOrganisationExists: false,
			showTemplate: false
		}
	},
	template: 
	`
	<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					Izmena organizacije
				</div>
				<div class="card-body">

					<!-- Dodavanje imena organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Ime organizacije:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Unesite ime organizacije" v-model="Organizacija.ime"/>
					</div>

					<!-- Dodavanje opisa organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Opis organizacije:</span>
						</div>
						<textarea class="form-control" id="description" name="description" placeholder="Unesite opis organizacije" v-model="Organizacija.opis" rows="3"></textarea>
					</div>

					<!-- Dodavanje upload-a slike -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Logo:</span>
						</div>
						<input type="file" class="form-control-file" name="logo" id="logo"/>
					</div>
					
					<p class="errorMessageRegisterDisc" v-if="this.showErrorEmptyField == true">Polje sa imenom organizacije je obavezno!</br></p>
					<p class="errorMessageOrganisationExists" v-if="this.showErrorOrganisationExists == true">Organizacija sa tim imenom vec postoji!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="checkData()">Izmeni organizaciju</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		loadOrganisation: function() {
    		this.Organizacija = JSON.parse(localStorage.getItem('organisationDetails'));
    		
        	localStorage.removeItem("organisationDetails");
        	this.showTemplate = true;
    	},
		
		organisationExists: function(){
			var path = 'rest/organisationService/organisationAlreadyExists/' + this.Organisation.name;
	    
			axios
	    		.get(path)
	    		.then(response => {
	    			this.showErrorOrganisationExists = response.data
	    		});
		},
		
		checkData: function(){
			if((this.Organizacija.ime !== '' && this.Organizacija.ime !== undefined)){
					this.showErrorEmptyField = false;
					
					this.organisationExists.call();
					
					
				}
				else{
					this.showErrorEmptyField = true;
				}
					
				if(this.showErrorOrganisationExists === false && this.showErrorEmptyField === false){
					console.log("evo ovo moze");
					
				}
		}
		
	},
	mounted () {   
		this.showTemplate = false;
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		
    	this.loadOrganisation();
    },
});