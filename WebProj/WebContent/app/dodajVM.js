Vue.component("dodaj-vm" ,{
	data: function() {
		return {
			title: "Dodavanje Virtuelne masine",
			role: '',
			email: '',
			categories: null,
			organisations: null,
			VM: {},
			showErrorEmptyField: false,
			showErrorDiscExists: false,
			canAddDisc: false,
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

					<!-- input field for VM name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">VM name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter Name" v-model="VM.name"/>
					</div>

					<!-- input field for core number -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Number if cores</span>
						</div>
						<input type="number" min="1" class="form-control" name="capacity" id="capacity" placeholder="Enter Capacity" v-model="VM.coreNumber"/>
					</div>

					<!-- input field for RAM -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">RAM</span>
						</div>
						<input type="number" min="1" class="form-control" name="capacity" id="capacity" placeholder="Enter RAM" v-model="VM.ram"/>
					</div>

					<!-- input field for GPU -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">GPU</span>
						</div>
						<input type="number" min="1" class="form-control" name="capacity" id="capacity" placeholder="Enter GPU" v-model="VM.gpu"/>
					</div>
					
					<!-- select category from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Virtual Machines</span>
						</div>
						<select class="custom-select" data-live-search="true" id="inputGroupSelect01" v-model="VM.vmCategory">
							<option selected>Choose...</option>
							<option v-for="m in categories" :value="m.ime">{{ m.ime }}</option>
						</select>
					</div>

					<!-- select organisation from drop down menu if superadmin role -->
					<div class="input-group mb-4" v-if="this.role == 'superadmin'">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Virtual Machines</span>
						</div>
						<select class="custom-select" data-live-search="true" id="inputGroupSelect01" v-model="VM.vmOrganisationName">
							<option selected>Choose...</option>
							<option v-for="m in organisations" :value="m.ime">{{ m.ime }}</option>
						</select>
					</div>
					
					<p class="errorMessageRegisterDisc" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageDiscExists" v-if="this.showErrorDiscExists == true">Disk sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); discAlreadyExists();">Add new Virtual Machine</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		//Proveriti da li radi: v-if="this.role == 'superadmin'"
		loadCategories: function() {
			
		},
		loadOrganisations: function() {
			
		},
		emptyField: function() {
			
		},
		discAlreadyExists: function() {
			
		},
		addVM: function() {
			
		},
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		this.loadCategories();
		this.loadOrganisations();
		
//		axios
//		.post('rest/overview/getAllVM', {"role": this.role, "email": this.email})
//		.then(response => {
//			this.machines = response.data;
//    	});
    },
});