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
<div class="container-fluid scrollable">
	<h2>Pregled korisnika.</h2>
	<table class="table table-hover table-striped">
	  <thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">Email</th>
	      <th scope="col">Name</th>
	      <th scope="col">Surname</th>
	    </tr>
	  </thead>
	  <tbody>

	  	<tr v-for="(u, index) in users">
			<th scope="row">{{ index+1 }}</th>
			<td>{{ u.email }}</td>
			<td>{{ u.ime }}</td>
			<td>{{ u.prezime }}</td>
	  	</tr>
	  	
	  </tbody>
	</table>
</div>
	`,
	methods: {
		
	},
	mounted () {  //created 
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/overview/getJustUsers/' + this.role + '/' + this.email
    
		axios
    		.get(path)
    		.then(response => (this.users = response.data))
    },
});