Vue.component("izmena-kategorije", {
	data: function () {
        	return {
        		title: 'Izmena kategorije',
                role: 'noRule',
                email: '',
                showErrorEmptyField: false,
    			showErrorCategoryExists: false,
    			Category: {},
    			machines: null,
                CategoryName: '',  // old Category name
                CategoryCoreNumber: '',  // old Category gpu
                CategoryRam: '',  // old Category coreNumber
                CategoryGpu: ''  // old Category VM name
                
        	}
    },
    template:`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h2>Edit Category</h2>
				</div>
				<div class="card-body">

					<!-- input field for Category name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category name</span>
						</div>
						<input gpu="text" class="form-control" name="name" id="name" placeholder="Enter Category Name" v-model="Category.name"/>
					</div>

					<!-- input field for Category Core Number -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category coreNumber</span>
						</div>
						<input gpu="number" min="1" class="form-control" name="coreNumber" id="coreNumber" placeholder="Enter Category coreNumber" v-model="Category.coreNumber"/>
					</div>
					
					<!-- input field for Category ram -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category ram</span>
						</div>
						<input gpu="number" min="1" class="form-control" name="ram" id="ram" placeholder="Enter Category ram" v-model="Category.ram"/>
					</div>
					
					<!-- input field for Category gpu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category gpu</span>
						</div>
						<input gpu="number" min="1" class="form-control" name="gpu" id="gpu" placeholder="Enter Category gpu" v-model="Category.gpu"/>
					</div>
					
					<p class="errorMessageRegisterCategory" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageCategoryExists" v-if="this.showErrorCategoryExists == true">Category sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button gpu="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); CategoryAlreadyExists();">Edit Category</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    `,
    methods: {
    	readAttributesFromCategory: function() {
//    		localStorage.setItem("imeKategorije", this.catToEdit.ime);
//			localStorage.setItem("jezgraKategorije", this.catToEdit.brojJezgara);
//			localStorage.setItem("ramKategorije", this.catToEdit.ramMemory);
//			localStorage.setItem("gpuKategorije", this.catToEdit.gpu);
    		this.role = localStorage.getItem("role");
    		this.email = localStorage.getItem("email");
    		
    		//This is for initial values in input fields
    		this.CategoryName = localStorage.getItem("imeKategorije");
    		this.CategoryCoreNumber = localStorage.getItem("jezgraKategorije");
    		this.CategoryRam = localStorage.getItem("ramKategorije");
    		this.CategoryGpu = localStorage.getItem("gpuKategorije");
    		
    		localStorage.removeItem('imeKategorije');
    		localStorage.removeItem('jezgraKategorije');
    		localStorage.removeItem('ramKategorije');
    		localStorage.removeItem('gpuKategorije');
    	},
    	emptyField: function(){
			this.showErrorEmptyField = false;
			this.showErrorCategoryExists = false;
			this.canEditCategory = false;
			
			if((this.Category.name !== '' && this.Category.name != undefined) && 
			(this.Category.coreNumber !== '' && this.Category.coreNumber != undefined && this.Category.coreNumber > 0) &&
			(this.Category.ram !== '' && this.Category.ram != undefined && this.Category.ram > 0)
			){
				this.showErrorEmptyField = false;
				console.log("Sva polja su popunjena.");
				this.canEditCategory = true;
				}else{
					console.log("Nisu sva polja popunjena.");
					this.showErrorEmptyField = true;
					this.canEditCategory = false;
				}
		},
		CategoryAlreadyExists: function(){
			this.canEditCategory = true;
			var path = "rest/categoryService/checkIfCategoryExist/" + this.Category.ram;
			
			/*
			 * If new name same as old name, move on.
			 * Else check if category already exists.
			 */
			if (this.Category.ram == this.CategoryName) {
				this.editCategory.call();
			}else {
				axios
				.get(path)
				.then(response => {
					console.log("Smem li menjati Kategoriju ->" + response.data);
					if (response.data == false) {
						console.log("Mozemo menjati kategoriju.")
						this.editCategory.call();
					}else {
						console.log("Vec postoji kategorija sa ovim imenom");
					}
				});
			}
			
			
		},
		editCategory: function(){
			console.log("Dosli smo i dovde: " + this.canEditCategory );
			if (this.canEditCategory == true || this.canEditCategory === true) {

				axios
            	.post('rest/categoryService/editCategory', {"oldName": this.CategoryName, "name": this.Category.name, "coreNumber": this.Category.coreNumber, "ram": this.Category.ram, "gpu": this.Category.gpu})
            	.then(response => {
            		var CategorySuccesfullyRegistered = response.data;
            		console.log("Kategorija uspesno izmenjen? : " + CategorySuccesfullyRegistered);
            		
            		if(CategorySuccesfullyRegistered){
            			console.log("Kategorija je uspesno izmenjen.");
            			router.push({path: "/pregledKategorija"}); // Bring user back to pregledKategorija
            		}else{
            			console.log("Kategorija nije upisan.");
            		}
            	});
			}
		},
//		readMachines: function() {
//			axios
//			.post('rest/overview/getAllVM', {"role": this.role, "email": this.email})
//			.then(response => {
//				this.machines = response.data;
//	    	});
//		},
    },
    mounted () {  //created 
    	this.readAttributesFromCategory();
//    	this.readMachines();
    },
});


