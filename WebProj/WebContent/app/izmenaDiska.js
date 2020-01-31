Vue.component("izmena-diska", {
	data: function () {
        	return {
        		title: 'Izmena Diska',
                role: 'noRule',
                email: '',
                showErrorEmptyField: false,
    			showErrorDiscExists: false,
    			Disc: {},
    			machines: null,
                diskName: '',  // old disk name
                diskType: '',  // old disk type
                diskCapacity: '',  // old disk capacity
                diskVMName: ''  // old disk VM name
                
        	}
    },
    template:`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h2>Edit Disk</h2>
				</div>
				<div class="card-body">

					<!-- input field for disc name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Disc name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter New Disc Name" v-model="Disc.name"/>
					</div>

					<!-- input field for disc capacity -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Disc capacity</span>
						</div>
						<input type="number" min="1" class="form-control" name="capacity" id="capacity" placeholder="Enter New Disk Capacity" v-model="Disc.capacity"/>
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
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField();">Edit Disc</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    `,
    methods: {
    	readAttributesFromDisk: function() {
    		this.diskName = localStorage.getItem("imeDiska");
    		this.diskType = localStorage.getItem("tipDiska");
    		this.diskCapacity = localStorage.getItem("kapacitetDiska");
    		this.diskVMName = localStorage.getItem("nazivVMDiska");
    		this.role = localStorage.getItem("role");
    		this.email = localStorage.getItem("email");
    		
    		localStorage.removeItem('imeDiska');
    		localStorage.removeItem('tipDiska');
    		localStorage.removeItem('kapacitetDiska');
    		localStorage.removeItem('nazivVMDiska');
    	},
    	emptyField: function(){
			this.showErrorEmptyField = false;
			this.showErrorDiscExists = false;
			this.canAddDisc = false;
			
			if((this.Disc.name !== '' && this.Disc.name != undefined) && 
			(this.Disc.capacity !== '' && this.Disc.capacity != undefined && this.Disc.capacity > 0) &&
			(this.Disc.type !== '' && this.Disc.type != undefined && this.Disc.type !== 'Choose...')
			){
				this.showErrorEmptyField = false;
				console.log("Sva polja su popunjena.");
				this.canAddDisc = true;
				this.editDisc();
			}else{
				console.log("Nisu sva polja popunjena.");
				this.showErrorEmptyField = true;
				this.canAddDisc = false;
			}
		},
//		discAlreadyExists: function(){
////			this.canAddDisc = true;
//			var path = "rest/discService/checkIfDiscExist/" + this.Disc.VMName;
//			axios
//			.get(path)
//			.then(response => {
//				console.log("Smem li menjati disk ->" + response.data);
//				if (response.data == false) {
//					console.log("Ne postoji sa datim nazivom pa mozemo ici dalje.")
//					this.editDisc.call();
//				}
//			})
//			
//		},
		//SAME AS addDisk()
		editDisc: function(){
			console.log("Dosli smo i dovde: " + this.canAddDisc );
			if (this.canAddDisc == true || this.canAddDisc === true) {

				axios
            	.post('rest/discService/editDisk', {"email": this.email, "role": this.role, "oldName": this.diskName, "name": this.Disc.name, "capacity": this.Disc.capacity, "VMName": this.Disc.VMName, "type": this.Disc.type})
            	.then(response => {
            		var discSuccesfullyRegistered = response.data;
            		console.log("disk uspesno izmenjen? : " + discSuccesfullyRegistered);
            		
            		if(discSuccesfullyRegistered){
            			console.log("Disk je uspesno izmenjen.");
            			router.push({path: "/pregledDiskova"}); // Bring user back to pregledDiskova
            		}else{
            			console.log("disk nije upisan.");
            		}
            	});
			}
		},
		readMachines: function() {
			axios
			.post('rest/overview/getAllVM', {"role": this.role, "email": this.email})
			.then(response => {
				this.machines = response.data;
	    	});
		},
    },
    mounted () {  //created 
    	this.readAttributesFromDisk();
    	this.readMachines();
    },
});







