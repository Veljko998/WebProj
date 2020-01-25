Vue.component("dodaj-kategoriju" ,{
	data: function() {
		return {
			title: "Dodavanje kategorije",
			role: '',
			email: '',
			Category: {},
			showErrorEmptyField: false,
			showErrorCategoryExists: false,
			canAddCategory: false
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

					<!-- input field for Category name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category name</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" placeholder="Enter Category Name" v-model="Category.name"/>
					</div>

					<!-- input field for Category Core Number -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category coreNumber</span>
						</div>
						<input type="number" min="1" class="form-control" name="coreNumber" id="coreNumber" placeholder="Enter Category coreNumber" v-model="Category.coreNumber"/>
					</div>
					
					<!-- input field for Category ram -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category ram</span>
						</div>
						<input type="number" min="1" class="form-control" name="ram" id="ram" placeholder="Enter Category ram" v-model="Category.ram"/>
					</div>
					
					<!-- input field for Category gpu -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Category gpu</span>
						</div>
						<input type="number" min="1" class="form-control" name="gpu" id="gpu" placeholder="Enter Category gpu" v-model="Category.gpu"/>
					</div>

					
					<p class="errorMessageRegisterCategory" v-if="this.showErrorEmptyField == true">Sva polja moraju biti popunjena !!!</br></p>
					<p class="errorMessageCategoryExists" v-if="this.showErrorCategoryExists == true">Kategorija sa tim imenom vec postoji !!!</br></p>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="emptyField(); CategoryAlreadyExists();">Add new Category</button>
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
			this.showErrorCategoryExists = false;
			this.canAddCategory = false;
			
			if((this.Category.name !== '' && this.Category.name != undefined) && 
					(this.Category.coreNumber !== '' && this.Category.coreNumber != undefined && this.Category.coreNumber > 0) &&
					(this.Category.ram !== '' && this.Category.ram != undefined && this.Category.ram > 0)){
					this.showErrorEmptyField = false;
					console.log("Sva polja su popunjena.");
					this.showErrorEmptyField = false;
//					this.CategoryAlreadyExists.call();
				}else{
					console.log("Nisu sva polja popunjena.");
					this.showErrorEmptyField = true;
				}
		},
		CategoryAlreadyExists: function(){
			console.log("Usao u CategoryAlreadyExists");
			if (this.showErrorEmptyField == false) {
				
				var path = "rest/categoryService/checkIfCategoryExist/" + this.Category.name;
				
				axios
				.get(path)
				.then(response => {
					if (response.data == false || response.data == 'false') {
						this.canAddCategory = true;
						console.log("Dodajemo novu kategoriju.");
						this.showErrorCategoryExists = false;
						this.addCategory.call();
					}else {
						this.showErrorCategoryExists = true;
						console.log("Ne dodajemo kategoriju jer vec postoji kategorija sa ovim imenom");
					}
				});
			}
		},
		addCategory: function(){
			console.log("Dosli smo i dovde: " + this.canAddCategory);
			if (this.canAddCategory == true || this.canAddCategory === true) {

				axios
            	.post('rest/categoryService/addCategory', {"name": this.Category.name, "coreNumber": this.Category.coreNumber, "ram": this.Category.ram, "gpu": this.Category.gpu})
            	.then(response => {
            		var CategorySuccesfullyRegistered = response.data;
            		console.log("Kategorija uspesno upisan? : "+CategorySuccesfullyRegistered);
            		
            		if(CategorySuccesfullyRegistered){
            			console.log("Category je uspesno upisana.");
            			router.push({path: "/pregledKategorija"}); // Bring user back to pregledKategorija
            		}else{
            			console.log("Kategorija nije upisana.");
            		}
            	});
			}
		}
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
//		var path = "rest/overview/getAllVM";// + this.role + '/' + this.email
    
//		axios
//		.post('rest/overview/getAllVM', {"role": this.role, "email": this.email})
//		.then(response => {
//			this.machines = response.data;
//    	});
    },
});