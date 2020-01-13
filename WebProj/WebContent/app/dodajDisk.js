Vue.component("dodaj-disk" ,{
	data: function() {
		return {
			title: "Dodavanje diska",
			role: '',
			email: '',
			machines: null,
			Disc: {},
			showErrorEmptyField: false,
			showErrorDiscExists: false,
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
					Register
				</div>
				<div class="card-body">

					<!-- input field for disc name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Disc name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter Disc Name" v-model="Disc.name"/>
					</div>

					<!-- input field for disc capacity -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Disc capacity</span>
						</div>
						<input type="number" min="1" class="form-control" name="capacity" id="capacity" placeholder="Enter your Surname" v-model="Disc.capacity"/>
					</div>

					<!-- select type of disc from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Types</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="Disc.type">
							<option selected>Choose...</option>
							<option value="SSD">SSD</option>
							<option value="HDD">HDD</option>
						</select>
					</div>
					
					<!-- select organisation from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Virtual Machines</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="Disc.VMName">
							<option selected>Choose...</option>
							<option v-for="m in machines" :value="m.ime">{{ m.ime }}</option>
						</select>
					</div>
					
					<p class="errorMessageRegisterDisc" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageDiscExists" v-if="this.showErrorDiscExists == true">Disk sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); discAlreadyExists();">Add new disc</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		emptyField: function(){
			this.showErrorEmptyField = false;
			this.showErrorDiscExists = false;
			this.canAddDisc = false;
			
			console.log("Printujemo sve");
			console.log(this.Disc.name);
			console.log(this.Disc.capacity);
			console.log(this.Disc.VMName);
			console.log(this.Disc.type);
			
			if((this.Disc.name !== '' && this.Disc.name != undefined) && 
					(this.Disc.capacity !== '' && this.Disc.capacity != undefined && this.Disc.capacity > 0) &&
					(this.Disc.VMName !== '' && this.Disc.VMName != undefined && this.Disc.VMName !== 'Choose...') &&
					(this.Disc.type !== '' && this.Disc.type != undefined && this.Disc.type !== 'Choose...')){
					this.showErrorEmptyField = false;
					console.log("Sva polja su popunjena.");
				}else{
					console.log("Nisu sva polja popunjena.");
					this.showErrorEmptyField = true;
				}
		},
		discAlreadyExists: function(){
			if (this.showErrorEmptyField == false) {
				
				var path = "rest/discService/checkIfDiscExist/" + this.Disc.name;
				
				axios
				.get(path)
				.then(response => {
					if (response.data == false || response.data == 'false') {
						this.canAddDisc = true;
						console.log("Dodajemo novog korisnika");
						this.addDisc.call();
					}
				});
			}
		},
		addDisc: function(){
			console.log("Dosli smo i dovde: " +this.canAddDisc );
			if (this.canAddDisc == true || this.canAddDisc === true) {

				console.log(this.Disc.name);
				console.log(this.Disc.capacity);
				console.log(this.Disc.VMName);
				console.log(this.Disc.type);
				
				axios
            	.post('rest/discService/addDisc', {"name": this.Disc.name, "capacity": this.Disc.capacity, "VMName": this.Disc.VMName, "type": this.Disc.type})
            	.then(response => {
            		var discSuccesfullyRegistered = response.data;
            		console.log("disk uspesno upisan? : "+discSuccesfullyRegistered);
            		
            		if(discSuccesfullyRegistered){
            			console.log("Disk je uspesno upisan.");
            			router.push({path: "/pregledDiskova"}); // Bring user back to pregledDiskova
            		}else{
            			console.log("disk nije upisan.");
            		}
            	});
			}
		}
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = "rest/overview/getAllVM";// + this.role + '/' + this.email
    
		axios
		.post('rest/overview/getAllVM', {"role": this.role, "email": this.email})
		.then(response => {
			this.machines = response.data;
    	});
    },
});