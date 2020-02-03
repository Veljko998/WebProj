Vue.component("pregled-organizacija" ,{
	data: function() {
		return {
			title: "Pregled organizacija",
			organisations: null,
			role: '',
			email: ''
		}
	},
	template: 
	`
<div class="container-fluid">
	<div class="col text-center">
		<h2>{{ this.title }}</h2></br>
	</div>
	
	<div class="container-fluid">
		<br>
		<input type="text" id="myInputVM" v-on:keyup="myFunction()" placeholder="Search for email, name or surname...">
	</div>
	
	<div class="container-fluid scrollable">
		<table class="table table-hover table-striped" id="myTable">
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col">Ime</th>
					<th scope="col">Opis</th>
					<th scope="col">Logo</th>
					<th scope="col">Funkcije</th>
				</tr>
			</thead>
			
			<tbody>
				<tr v-for="(o, index) in organisations">
					<th scope="row">{{ index+1 }}</th>
					<td>{{ o.ime }}</td>
					<td>{{ o.opis }}</td>
					<td><img style="height: 50px; width: 50px; text-align: left;" v-bind:src="o.logo"/></td>
					<td>
						<button :id="o.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="edit();">Edit</button>
						<button :id="o.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="details();">Details</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
		<button  type="button" v-on:click="goToAddPage();" class="btn btn-lg btn-primary">Dodaj novu organizaciju</button>
</div>
	`,
	methods: {
		myFunction: function(){
    		var input, filter, table, tr, td, i, txtValue;
    		
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	// Loop through all table rows, and hide those who don't match the search query
        	for (i = 0; i < tr.length; i++){
        		td = tr[i].getElementsByTagName("td")[0]; //by org name
        		
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			
        			if (txtValue.toUpperCase().indexOf(filter) > -1){
        					tr[i].style.display = "";
        			}else{
        				tr[i].style.display = "none";
        			}
        		}
        	}
    	},
		edit: function(){
			axios
    		.post('rest/organisationService/getOrganisation', {"name": event.srcElement.id})
    		.then(response => {
    			localStorage.setItem('organisationDetails', JSON.stringify(response.data));

    			router.push({path: "/izmenaOrganizacije"});
    		});
			
		},
		
		details: function() {
    		axios
    		.post('rest/organisationService/getOrganisation', {"name": event.srcElement.id})
    		.then(response => {
    			localStorage.setItem('organisationDetails', JSON.stringify(response.data));

    			router.push({path: "/detaljiOrganizacije"});
    		});
		},
		
		goToAddPage: function(){
			router.push({path: "/dodajOrganizaciju"});		
    	},
		
	},
	mounted () {  
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/organisationService/getOrganisations/' + this.role + '/' + this.email
    
		axios
    		.get(path)
    		.then(response => {
    			this.organisations = response.data;
    			
    			//Sort 
    			if (this.organisations != "") {
    				this.organisations.sort((a, b) => a.ime.localeCompare(b.ime));
				}
    		});
		
    },
});