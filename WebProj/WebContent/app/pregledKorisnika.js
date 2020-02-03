Vue.component("pregled-korisnika" ,{
	data: function() {
		return {
			title: "Pregled korisnika",
			users: null,
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
		      <th scope="col">#</th>
		      <th scope="col">Email</th>
		      <th scope="col">Name</th>
		      <th scope="col">Surname</th>
		      <th scope="col" v-if="this.role === 'superadmin'">Organisation</th>
		      <th scope="col">Functions</th>
		    </tr>
		  </thead>
		  <tbody v-if="this.role === 'superadmin'">
		  	<tr v-for="(u, index) in users">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ u.email }}</td>
				<td>{{ u.ime }}</td>
				<td>{{ u.prezime }}</td>
				<td>{{ u.organizacija.ime }}</td>
				<td>
					<button :id="u.email" type="button" class="btn btn-sm btn-secondary" v-on:click="editUser();">Edit</button>
					<button :id="u.email" type="button" class="btn btn-sm btn-danger" v-on:click="deleteUser();">Delete</button>
					<button :id="u.email" type="button" class="btn btn-sm btn-secondary" v-on:click="userDetails();">Details</button>
				</td>
		  	</tr>
		  </tbody>
		  <!-- Isto sto i super admin samo bez org. v-if ne radi unutar v-for petlje.  -->
		  <tbody v-if="this.role === 'admin'">
		  	<tr v-for="(u, index) in users">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ u.email }}</td>
				<td>{{ u.ime }}</td>
				<td>{{ u.prezime }}</td>
				<td>
					<button :id="u.email" type="button" class="btn btn-sm btn-secondary" v-on:click="editUser();">Edit</button>
					<button :id="u.email" type="button" class="btn btn-sm btn-danger" v-on:click="deleteUser();">Delete</button>
					<button :id="u.email" type="button" class="btn btn-sm btn-secondary" v-on:click="userDetails();">Details</button>
				</td>
		  	</tr>
		  </tbody>
		</table>
	</div>
	
	<button type="button" class="btn btn-lg btn-primary" v-on:click="changeRouter();">Add user</button>
</div>

	`,
	methods: {
		myFunction: function(){
    		var input, filter, table, tr, td, i, txtValue;
    		var txtValue2, name, surname;
    		
    		
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	// Loop through all table rows, and hide those who don't match the search query
        	for (i = 0; i < tr.length; i++){
        		td = tr[i].getElementsByTagName("td")[0]; //by Email
        		name = tr[i].getElementsByTagName("td")[1]; //by Name
        		surname = tr[i].getElementsByTagName("td")[2]; //by surname
        		
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			txtValue2 = name.textContent || name.innerText;
        			txtValue3 = surname.textContent || surname.innerText;
        			
        			if (txtValue.toUpperCase().indexOf(filter) > -1){
        					tr[i].style.display = "";
        			}else{
        				tr[i].style.display = "none";
        				
        				if (txtValue2.toUpperCase().indexOf(filter) > -1){
	        				tr[i].style.display = "";
	        			}else {
	        				tr[i].style.display = "none";
	        				
	        				if (txtValue3.toUpperCase().indexOf(filter) > -1){
		        				tr[i].style.display = "";
		        			}else {
		        				tr[i].style.display = "none";
							}
						}
        			}
        		}
        	}
    	},
		editUser: function() {
			localStorage.setItem("oldEmail", event.srcElement.id);
			
			/*
			 * Get old password
			 */
			var path = "rest/userService/getPassword/" + event.srcElement.id;
			axios
			.get(path)
			.then(response => {
				localStorage.setItem("oldPassword", response.data);
				
				router.push({path: "/izmenaKorisnika"});
			});
		},
		deleteUser: function() {
			var path2 = "rest/userService/deleteUser/" + event.srcElement.id;

			if (confirm("Are you sure you want to delete user?")) {
				axios
	    		.get(path2)
	    		.then(response => {
	    			if (response.data == true) {
						console.log("Korisnik je uspesno obrisan");
						this.loadUsers.call();
					}
	    		});
			} else {
				
			}
		},
		userDetails: function() {
			var path = "rest/userService/getUser/" + event.srcElement.id;
    		axios
    		.get(path)
    		.then(response => {
    			localStorage.setItem('storeObjUser', JSON.stringify(response.data));
    			router.push({path: "/detaljiKorisnika"});
    		});
		},
		changeRouter: function(){
			router.push({path: '/dodajKorisnika'});
		},
		loadUsers() {
			console.log("Role: " + this.role);
			console.log("Email: " + this.email);
			var path = 'rest/overview/getJustUsers/' + this.role + '/' + this.email
			
			axios
    		.get(path)
    		.then(response => {
    			this.users = response.data

    			//Sort 
    			if (this.users != "") {
    				this.users.sort((a, b) => a.ime.localeCompare(b.ime));
				}
    		});
		},
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
    
		this.loadUsers();
    },
});