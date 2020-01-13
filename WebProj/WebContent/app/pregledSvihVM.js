Vue.component("pregled-vm", {
	data: function () {
        	return {
        		title: 'Pregled Virtuelnih masina',
                User: {},
                users: null,
                role: 'noRule',
                machines: null
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
			<input min="1" type="number" class="form-control" placeholder="From" id="inputCoreFrom">
		</div>
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="To" id="inputCoreTo">
		</div>
	</div>

	<div class="row">
		<div class="col">
			<label for="inputEmail4">RAM capacity: </label>
		</div>
	</div>
	<div class="row">
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="From" id="inputRamFrom">
		</div>
		<div class="col col-md-2">
			<input min="1" type="number" class="form-control" placeholder="To" id="inputRamTo">
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
				<th scope="col" v-if="this.role === 'superadmin'">Organisation name</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(m, index) in this.machines">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ m.ime }}</td>
				<td>{{ m.brojJezgara }}</td>
				<td>{{ m.ram }}</td>
				<td>{{ m.gpu }}</td>
				<td v-if="this.role === 'superadmin'">{{ m.organisationName }}</td>
			</tr>
		</tbody>
	</table>
	
	<button type="button" class="btn btn-lg btn-primary">Add VM</button>
</div>
    `,
    methods: {
    	myFunction: function(){
    		var input, filter, table, tr, td, i, txtValue;
    		var inputCoreFrom, inputCoreTo, inputRamFrom, inputRamTo, coreNumber, ramNumber, txtValue2, txtValue3;
    		
    		inputCoreFrom = document.getElementById("inputCoreFrom");
    		filter1 = Number(inputCoreFrom.value);
    		inputCoreTo = document.getElementById("inputCoreTo");
    		filter2 = Number(inputCoreTo.value);
    		inputRamFrom = document.getElementById("inputRamFrom");
    		filter3 = Number(inputRamFrom.value);
    		inputRamTo = document.getElementById("inputRamTo");
    		filter4 = Number(inputRamTo.value);
    		
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	// Loop through all table rows, and hide those who don't match the search query
        	for (i = 0; i < tr.length; i++){
        		td = tr[i].getElementsByTagName("td")[0]; //by VM name
        		coreNum = tr[i].getElementsByTagName("td")[1];
//        		coreNumber = Number(coreNum);
        		
        		ramNum = tr[i].getElementsByTagName("td")[2];
//        		ramNumber = Number(ramNum);
        		
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			txtValue2 = coreNum.textContent || coreNumber.innerText;
        			txtValue3 = ramNum.textContent || ramNumber.innerText;
        			coreNumber = Number(txtValue2);
        			ramNumber = Number(txtValue3);
        			if (txtValue.toUpperCase().indexOf(filter) > -1 && (filter1 <= coreNumber && filter2 >= coreNumber) && (filter3 <= ramNumber && filter4 >= ramNumber)){
        				tr[i].style.display = "";
        			}else{
        				tr[i].style.display = "none";
        			}
        		}
        	}
    	}
    },
    mounted () {  //created 
    	var role = localStorage.getItem("role");
    	var email = localStorage.getItem("email");
    	
    	axios
		.post('rest/overview/getAllVM', {"role": role, "email": email})
		.then(response => {
			this.machines = response.data;
			if (this.machines === '') {
				console.log("Nema masina za ispis kod ovog korisnika.");
			}
		});
    },
});







