Vue.component("pregled-korisnika" ,{
	data: function() {
		return {
			title: "Pregled korisnika",
			users: null,
			role: '',
			email: '',
			nista: true
		}
	},
	template: 
	`
<div class="container-fluid">
	<h2>Pregled korisnika.</h2>
	
	<div class="container-fluid scrollable">
		<table class="table table-hover table-striped">
		  <thead>
		    <tr>
		      <th scope="col">#</th>
		      <th scope="col">Email</th>
		      <th scope="col">Name</th>
		      <th scope="col">Surname</th>
		      <th scope="col" v-if="this.role === 'superadmin'">Organisation</th>
		      <th scope="col" v-if="this.role === 'superadmin'">Functions</th>
		    </tr>
		  </thead>
		  <tbody v-if="this.role === 'superadmin'">
		  	<tr v-for="(u, index) in users" >
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
		  	<tr v-for="(u, index) in users" >
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
		loadUsers() {
			var path = 'rest/overview/getJustUsers/' + this.role + '/' + this.email
			
			axios
    		.get(path)
    		.then(response => {
    			this.users = response.data
    		});
		},
		editUser: function() {
			console.log("Editovanje korisnika");
			
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
			console.log(path2);
			
			axios
    		.get(path2)
    		.then(response => {
    			console.log("USAO BRTTTT");
    			if (response.data == true) {
					console.log("Korisnik je uspesno obrisan");
//					this.loadUsers.call();
				}
    		});
			
//			axios
//    		.post("rest/userService/deleteUser", {"name": event.srcElement.id})
//    		.then(response => {
//    			if (response.data == true) {
//					console.log("Korisnik je uspesno obrisan");
//					this.loadUsers.call();
//				}
//    		});
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
		}
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
//		var path = 'rest/overview/getJustUsers/' + this.role + '/' + this.email
    
		this.loadUsers();
//		axios
//    		.get(path)
//    		.then(response => (this.users = response.data));
    },
});