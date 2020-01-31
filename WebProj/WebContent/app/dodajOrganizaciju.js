Vue.component("dodaj-organizaciju" ,{
	data: function() {
		return {
			title: "Dodavanje organizacije",
			role: '',
			email: '',
			organisations: null,
			resources: null,
			Organisation: {},
			showErrorEmptyField: false,
			showErrorOrganisationExists: false,
			showAddingSucceed: false
		}
	},
	template: 
	`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					Dodavanje organizacije
				</div>
				<div class="card-body">

					<!-- Dodavanje imena organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Ime organizacije:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Unesite ime organizacije" v-model="Organisation.name"/>
					</div>

					<!-- Dodavanje opisa organizacije -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Opis organizacije:</span>
						</div>
						<textarea class="form-control" id="description" name="description" placeholder="Unesite opis organizacije" v-model="Organisation.description" rows="3"></textarea>
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
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="checkData()">Dodaj novu organizaciju</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		organisationExists: function(){
			var path = 'rest/organisationService/organisationAlreadyExists/' + this.Organisation.name;
	    
			axios
	    		.get(path)
	    		.then(response => {
	    			this.showErrorOrganisationExists = response.data
	    		});
		},
		
		checkData: function(){
			if((this.Organisation.name !== '' && this.Organisation.name !== undefined)){
					this.showErrorEmptyField = false;
					
					this.organisationExists.call();
					
					
				}
				else{
					this.showErrorEmptyField = true;
				}
					
				if(this.showErrorOrganisationExists === false && this.showErrorEmptyField === false){
					console.log("evo ovo moze");
					this.changeData.call();
				}
		},
		
		changeData: function(){
			
			axios
        	.post('rest/organisationService/addOrganisation', {"name": this.Organisation.name, "details": this.Organisation.details, "logo": null})
        	.then(response => {
        		 this.showAddingSucceed = response.data;
        		 
        		 if(this.showAddingSucceed){
         			console.log("Podaci su uspesno izmenjeni");
         			router.push({path: "/pregledOrganizacija"});
         		}
        		
        	});
			
		}
		
		
	},
	mounted () {   
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		
    		
    },
});