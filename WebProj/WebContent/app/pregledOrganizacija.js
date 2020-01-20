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
	<h2> {{this.title}} </h2>
		<table class="table table-hover table-striped" >
		  <thead>
		    <tr>
		      <th scope="col"></th>
		      <th scope="col">Ime</th>
		      <th scope="col">Opis</th>
		      <th scope="col">Logo</th>
		    </tr>
		  </thead>
		  <tbody>
	
		  	<tr v-for="(o, index) in organisations">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ o.ime }}</td>
				<td>{{ o.opis }}</td>
				<td><img style="height: 50px; width: 50px; text-align: left;" v-bind:src="o.logo"/></td>
		  	</tr>
		  </tbody>
		</table>
		<button  type="button" class="btn btn-lg btn-primary">Add New Organisation</button>
</div>
	`,
	methods: {
		
	},
	mounted () {  
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/data/getOrganisations/' + this.role + '/' + this.email
    
		axios
    		.get(path)
    		.then(response => (this.organisations = response.data))
		
    },
});