Vue.component("pregled-kategorija", {
	data: function () {
        	return {
        		title: 'Pregled kategorija',
                categories: null,
                vmToEdit: null
        	}
    },
    template:`
<div class="container-fluid">
	<div class="col text-center">
		<h2>{{ this.title }}</h2></br>
	</div>


	<div class="row">
		<div class="col">
			<label for="inputEmail4">Number of cores: </label>
		</div>
	</div>
	<div class="row">
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="From" id="inputCoreFrom" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="To" id="inputCoreTo" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
	</div>

	<div class="row">
		<div class="col">
			<label for="inputEmail4">RAM capacity: </label>
		</div>
	</div>
	<div class="row">
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="From" id="inputRamFrom" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="To" id="inputRamTo" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
	</div>
	
	<div class="row">
		<div class="col">
			<label for="inputEmail4">GPU capacity: </label>
		</div>
	</div>
	<div class="row">
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="From" id="inputGpuFrom" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="To" id="inputGpuTo" v-on:click="myFunction()" v-on:keyup="myFunction()">
		</div>
	</div>
	
	<br>

	<input type="text" id="myInputVM" v-on:keyup="myFunction()" placeholder="Search for names...">
	
	<table class="table table-hover " id="myTable">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Name</th>
				<th scope="col">Core number</th>
				<th scope="col">RAM</th>
				<th scope="col">GPU</th>
				<th scope="col">Functions</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(c, index) in this.categories">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ c.ime }}</td>
				<td>{{ c.brojJezgara }}</td>
				<td>{{ c.ramMemory }}</td>
				<td>{{ c.gpu }}</td>
				<td>
					<button :id="c.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="editCategory();">Edit</button>
					<button :id="c.ime" type="button" class="btn btn-sm btn-danger" v-on:click="deleteCategory(); myFunction();">Delete</button>
					<button :id="c.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="categoryDetails();">Details</button>
				</td>
			</tr>
		</tbody>
	</table>
	
	<button type="button" class="btn btn-lg btn-primary" v-on:click="addNewCategory();">Add VM</button>
</div>
    `,
    methods: {
    	categoryDetails: function() {
    		console.log("Usli smo u pregled Kategorija. Ako radi obrisati ovaj komentar.");
    		localStorage.setItem("imeKategorije", event.srcElement.id);
    		router.push({path: "/detaljiKategorije"});
    	},
    	editCategory: function(){
    		//TODO: 
    		console.log("You pressed edit button.")
//    		var vmName = event.srcElement.id;
//   		
//    		axios
//    		.post('rest/VMService/getVMByName', {"name": vmName})
//    		.then(response => {
//    			this.vmToEdit = response.data;
//    			
//    			/*
//    			 * Define everything what we will need in /izmenaVM 
//    			 */
//    			localStorage.setItem("imeVM", this.vmToEdit.ime);
////    			localStorage.setItem("VM", this.vmToEdit.);
//    			
//    			
//    			if (this.role == "admin" || this.role == "superadmin") {
//    				router.push({path: "/izmenaVM"});
//				}else {
//					console.log("Ulazi mi u deteljan pregled VM koji nisam jos uradio.");
////					router.push({path: "/detaljanPregledVM"});
//				}
//    		});
    		
    	},
    	deleteCategory: function(){
    		//TODO:
    		var catName = event.srcElement.id;
    		
    		axios
    		.post('rest/categoryService/deleteCategory', {"name": catName})
    		.then(response => {
    			var category_deleted = response.data;
    			
    			if (category_deleted == true) {
					console.log("Category is succesfully deleted.");
					this.loadCategories();
				}else {
					console.log("Category is not deleted.");
				}
    		});
    	},
    	/*
    	 * Function for searching and filtering data.
    	 */
    	myFunction: function() {
    		var input, table, tr, td, i;
    		var txtValue, txtValue2, txtValue3, txtValue4;
    		var filter, filter2, filter3, filter4, filter5;
    		var inputCoreFrom, inputCoreTo, inputRamFrom, inputRamTo, inputGpuFrom, inputGpuTo;
    		var coreNumber, ramNumber, gpuNumber,  coreNum, ramNum, gpuNum;
    		
    		inputCoreFrom = document.getElementById("inputCoreFrom");
    		filter1 = Number(inputCoreFrom.value);
    		inputCoreTo = document.getElementById("inputCoreTo");
    		filter2 = Number(inputCoreTo.value);
    		inputRamFrom = document.getElementById("inputRamFrom");
    		filter3 = Number(inputRamFrom.value);
    		inputRamTo = document.getElementById("inputRamTo");
    		filter4 = Number(inputRamTo.value);
    		inputGpuFrom = document.getElementById("inputGpuFrom");
    		filter5 = Number(inputGpuFrom.value);
    		inputGpuTo = document.getElementById("inputGpuTo");
    		filter6 = Number(inputGpuTo.value);
    		
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	// Loop through all table rows, and hide those who don't match the search query
        	for (i = 0; i < tr.length; i++){
        		var helpMe = true;
        		td = tr[i].getElementsByTagName("td")[0]; 		//by VM name
        		coreNum = tr[i].getElementsByTagName("td")[1]; 	//by core
        		ramNum = tr[i].getElementsByTagName("td")[2];	//by ram
        		gpuNum = tr[i].getElementsByTagName("td")[3];	//by gpu
        		
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			txtValue2 = coreNum.textContent || coreNum.innerText;
        			txtValue3 = ramNum.textContent || ramNum.innerText;
        			txtValue4 = gpuNum.textContent || gpuNum.innerText;
        			
        			coreNumber = Number(txtValue2);
        			ramNumber = Number(txtValue3);
        			gpuNumber = Number(txtValue4);
        			
        			// Logic for taking event on changed fields for filtering
        			
        			/*
        			 * Filtering for input number of cores.
        			 */
        			if (txtValue.toUpperCase().indexOf(filter) > -1){
        				if (filter1 == 0 && filter2 == 0 && helpMe == true) {
        					tr[i].style.display = "";
        					helpMe = true;
						}if (filter1 != 0 && filter2 == 0 && helpMe == true) {
							if (filter1 <= coreNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter1 == 0 && filter2 != 0 && helpMe == true) {
							if (filter2 >= coreNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter1 != 0 && filter2 != 0 && helpMe == true) {
							if (filter1 <= coreNumber && filter2 >= coreNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}
        				/*
        				 * Filtering for input RAM
        				 */
						if (filter3 == 0 && filter4 == 0 && helpMe == true) {
        					tr[i].style.display = "";
							helpMe = true;
						}if (filter3 != 0 && filter4 == 0 && helpMe == true) {
							if (filter3 <= ramNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter3 == 0 && filter4 != 0 && helpMe == true) {
							if (filter4 >= ramNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter3 != 0 && filter4 != 0 && helpMe == true) {
							if (filter3 <= ramNumber && filter4 >= ramNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}
						/*
        				 * Filtering for input GPU
        				 */
						if (filter5 == 0 && filter6 == 0 && helpMe == true) {
        					tr[i].style.display = "";
							helpMe = true;
						}if (filter5 != 0 && filter6 == 0 && helpMe == true) {
							if (filter5 <= gpuNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter5 == 0 && filter6 != 0 && helpMe == true) {
							if (filter6 >= gpuNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}if (filter5 != 0 && filter6 != 0 && helpMe == true) {
							if (filter5 <= gpuNumber && filter6 >= gpuNumber) {
								tr[i].style.display = "";
								helpMe = true;
							}else {
								tr[i].style.display = "none";
								helpMe = false;
							}
						}
        			}else{
        				tr[i].style.display = "none";
        			}
        		}
        	}
    	},
    	addNewCategory: function() {
    		//TODO:
//    		router.push({path: "/dodajKategoriju"});
    	},
    	loadCategories: function() {
    		console.log("CITAMO KATEGORIJE.");
    		axios
    		.post('rest/categoryService/getAllCategories2')
    		.then(response => {
    			this.categories = response.data;
    			if (this.categories === '') {
    				console.log("Nema kategorija za ispis kod ovog korisnika.");
    			}
    		});
    	},
    },
    mounted () {  //created 
    	this.role = localStorage.getItem("role");
    	this.email = localStorage.getItem("email");
    	
    	this.loadCategories();
    },
});


