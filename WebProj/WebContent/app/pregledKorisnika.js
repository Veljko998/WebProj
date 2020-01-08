Vue.component("pregled-korisnika" ,{
	data: function() {
		return {
			title: "Pregled korisnika",
			users: null,
			errored: false
		}
	},
	template: 
	`
<div class="container-fluid">
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

	  	<tr v-for="u in users">
			<th scope="row">{{ $index }}</th>
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
	    	.catch(error => {
	        console.log(error)
	        this.errored = true
	    	})
	    	.finally(() => this.loading = false)
    },
});