Vue.component("izmena-vm", {
	data: function () {
        	return {
        		title: 'Izmena VM',
                role: 'noRule',
                email: '',
                disks: [],
                showErrorEmptyField: false,
    			showErrorVMExists: false,
    			currentVMName: "",
    			VM: {},
    			nis: [],
    			categories: [],
    			disks: null,
        	}
    },
    template:`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h2>Edit VM</h2>
				</div>
				<div class="card-body">

					<!-- input field for VM name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">VM name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter New VM Name" v-model="VM.name"/>
					</div>

					<!-- select category from drop down menu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Categories</span>
						</div>
						<select class="custom-select" id="inputGroupSelect01" v-model="VM.category">
							<option selected>Choose...</option>
							<option v-for="c in categories" :value="c">{{ c }}</option>
						</select>
					</div>
					
					<!-- Select disk/s -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Select new disks</span>
						</div>
						<select multiple class="form-control" id="resources" name="inputGroupSelect03" v-model="nis">
							<option v-for="d in disks" :value="d">{{ d }}</option>
						</select>
					</div>
					
					<p class="errorMessageRegisterVM" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageVMExists" v-if="this.showErrorVMExists == true">VM sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); VMAlreadyExists();">Edit VM</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    `,
    methods: {
    	readAttributesFromVM: function() {
    		this.role = localStorage.getItem("role");
    		this.email = localStorage.getItem("email");
    		
    		this.currentVMName = localStorage.getItem("imeVM");

    		localStorage.removeItem('imeVM');
    	},
    	emptyField: function(){
			this.showErrorEmptyField = false;
			this.showErrorVMExists = false;
			this.canAddVM = false;
			
			console.log("Printujemo bzvz. -> izmenaVM <-");
			
			if((this.VM.name !== '' && this.VM.name != undefined) && 
			(this.VM.category !== '' && this.VM.category != undefined && this.VM.category !== 'Choose...')){
				this.showErrorEmptyField = false;
				console.log("Sva polja koja trebaju biti popunjena su popunjena.");
			}else{
				console.log("Nisu sva polja popunjena bato.");
				this.showErrorEmptyField = true;
			}
		},
		VMAlreadyExists: function(){
			this.canAddVM = true;
			this.editVM.call();
		},
		editVM: function(){
			console.log("E sada editujemo masincugu: " + this.canAddVM );
			if (this.canAddVM == true || this.canAddVM === true) {

				axios
            	.post('rest/VMService/editVM', {"email": this.email, "role": this.role, "oldName": this.currentVMName, "name": this.VM.name, "category": this.VM.category, "disks": this.nis})
            	.then(response => {
            		var VMSuccesfullyRegistered = response.data;
            		
            		if(VMSuccesfullyRegistered){
            			console.log("VM je uspesno izmenjen.");
            			router.push({path: "/pregledVM"}); // Bring user back to pregledVM
            		}else{
            			console.log("VM nije upisana.");
            		}
            	});
			}
		},
		/*
		 * get disks which have not VM and disks from current VM.
		 */
		readDisks: function() {
			axios
			.post('rest/VMService/getDisks', {"name": this.currentVMName})
			.then(response => {
				this.disks = response.data;
	    	});
		},
		/*
		 * take list of all category names -> List<String>
		 */
		readCategories: function(){
			axios
			.post('rest/categoryService/getAllCategories')
			.then(response => {
				this.categories = response.data;
	    	});
		},
    },
    mounted () {  //created 
    	this.readAttributesFromVM();
    	this.readDisks();
    	this.readCategories();
    },
});







