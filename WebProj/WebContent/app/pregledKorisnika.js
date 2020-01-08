Vue.component("pregled-korisnika" ,{
	data: function() {
		return {
			title: "Pregled korisnika",
			users: null,
		}
	},
	template: 
	`
<div class="container-fluid">
	<h2>Pregled korisnika.</h2>
	<table class="table table-hover">
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
    	axios
    		.get('rest/webproj/getJustUsers')
    		.then(response => (this.users = response.data))
    },
});