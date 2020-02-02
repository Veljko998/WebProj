Vue.component("detalji-organizacije", {
	data: function () {
        	return {
        		title: 'Detalji organizacije',
        		Organizacija: {},
        		showTemplate: false,
        		temp: {},
        		korisnici: [],
        		role: ''
        	}
    },
    template:`
<div class="container-fluid" v-if="showTemplate">
	<h1 align="center">{{ this.title }}</h1></br></br>
	<h3>
		<img style="height: 150px; width: 150px; text-align: left;" v-bind:src="this.Organizacija.logo"/>
		<ul>
			<li><label style="color:red">Ime:&nbsp; </label>{{ this.Organizacija.ime }}</li>
			<li><label style="color:red">Opis:&nbsp; </label>{{ this.Organizacija.opis }}</li>
			<li><label style="color:red">Lista korisnika: </label>
				<ul>
					<li v-for="k in this.korisnici">{{ k.ime }} {{ k.prezime }}</li>
				</ul>		
			</li>
			<li><label style="color:red">Lista resursa: </label>
				<ul>
					<li v-for="o in this.Organizacija.listaResursa">{{ o }}</li>
				</ul>	
			</li>
		</ul>
	</h3>
	<button v-if="role==='admin'" type="button" class="btn btn-lg btn-primary" v-on:click="izmeniOrganizaciju();">Izmeni organizaciju</button>
</div>
    `,
    methods: {
    	
    	izmeniOrganizaciju: function(){
    		axios
    		.post('rest/organisationService/getOrganisation', {"name": this.Organizacija.ime})
    		.then(response => {
    			localStorage.setItem('organisationDetails', JSON.stringify(response.data));

    			router.push({path: "/izmenaOrganizacije"});
    		});
    	},
    	/*
    	 * Load Disk and Virtual Machine of disk.
    	 */
    	loadOrganisation: function() {
    		if(localStorage.getItem('organisationDetails') === null){
    			this.Organizacija = JSON.parse(localStorage.getItem('backupOrganisationDetails'));
    		}
    		else{
    			this.Organizacija = JSON.parse(localStorage.getItem('organisationDetails'));
    		}
    		
    		
    		try{
    			var i;
        		for (i = 0; i < this.Organizacija.listaKorisnika.length; i++) {
        			var path = 'rest/userService/getUser/' + this.Organizacija.listaKorisnika[i]
        			axios
        	    		.get(path)
        	    		.then(response => {
        	    			this.temp = response.data;
        	    			this.korisnici.push(this.temp);
        	    			this.temp = {};
        	    		});
        		}
    		}
    		catch(error){
    			
    		}
    		
    		
        	localStorage.removeItem("organisationDetails");
        	this.showTemplate = true;
    	}
    },
    mounted () {
    	this.showTemplate = false;
    	this.role = localStorage.getItem('role');
    	this.loadOrganisation();
    },
});







