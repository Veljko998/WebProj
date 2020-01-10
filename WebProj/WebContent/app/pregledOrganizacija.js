Vue.component("pregled-organizacija" ,{
	data: function() {
		return {
			title: "Pregled organizacija",
			organisations: null,
		}
	},
	template: 
	`
<div class="container-fluid">
	<h2>Pregled organizacija</h2>
	<table class="table table-hover" >
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
			<td class="text-center"><img class="img-fluid" v-bind:src="o.logo"/></td>
	  	</tr>
	  	
	  </tbody>
	</table>
</div>
	`,
	methods: {
		
	},
	mounted () {   
    	axios
    		.get('rest/data/getOrganisations')
    		.then(response => (this.organisations = response.data))
    },
});