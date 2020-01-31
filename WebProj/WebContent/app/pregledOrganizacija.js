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
		<button  type="button" v-on:click="goToAddPage();" class="btn btn-lg btn-primary">Dodaj novu organizaciju</button>
</div>
	`,
	methods: {
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
    		.then(response => (this.organisations = response.data))
		
    },
});