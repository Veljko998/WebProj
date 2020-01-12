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
	<h2>{{ this.title }}</h2>
	<input type="text" id="myInputVM" v-on:keyup="myFunction()" placeholder="Search for names..">
	
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
				<td>{{ m.name }}</td>
				<td>{{ m.coreNumber }}</td>
				<td>{{ m.ram }}</td>
				<td>{{ m.gpu }}</td>
				<td v-if="this.role === 'superadmin'">{{ m.organisationName }}</td>
			</tr>
		</tbody>
	</table>
	
	<button type="button" class="btn btn-lg btn-primary" v-on:click="changeRouter();">Add VM</button>
</div>
    `,
    methods: {
    	myFunction: function(){
    		var input, filter, table, tr, td, i, txtValue;
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	for (i = 0; i < tr.length; i++){
        		td = tr[i].getElementsByTagName("td")[0];
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			if (txtValue.toUpperCase().indexOf(filter) > -1){
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







