Vue.component("detalji-korisnika", {
	data: function () {
        	return {
        		title: 'User Details',
        		User: null,
        		showTemplate: false,
        		role: null,
        		org: null
        	}
    },
    template:`
<div class="container-fluid" v-if="this.showTemplate == true">
	<h1 align="center">{{ this.title }}</h1></br></br>
	<h3>
		<ul>
			<li><label style="color:red">Email:&nbsp; </label>{{ this.User.email }}</li>
			<li><label style="color:red">Password:&nbsp; </label>{{ this.User.lozinka }}</li>
			<li><label style="color:red">First Name:&nbsp; </label>{{ this.User.ime }}</li>
			<li><label style="color:red">Last Name:&nbsp; </label>{{ this.User.prezime }}</li>
			<li><label style="color:red">Role:&nbsp; </label>{{ this.User.uloga }}</li>
			<li><label style="color:red">Organisation: </label>
				<ul>
					<li><label style="color:red">Name:&nbsp; </label>{{ this.User.organizacija.ime }}</li>
					<li><label style="color:red">Description:&nbsp; </label>{{ this.User.organizacija.opis }}</li>
					<li><label style="color:red">Logo:&nbsp; </label>OVDE STAVITI LOGO - IVANA</li>
				</ul>
			</li>
		</ul>
	</h3>
</div>
    `,
    methods: {
    	/*
    	 * Load Disk and Virtual Machine of disk.
    	 */
    	loadU: function() {
    		this.role = localStorage.getItem("role");
    		this.User = JSON.parse(localStorage.getItem('storeObjUser'));
    		
    		localStorage.removeItem("storeObjUser");
    		
//    		var path = "rest/organisationService/getOrganisationByName/" + this.User.
//    		axios
//    		.get()
        	this.showTemplate = true;
    	}
    },
    mounted () {
    	this.showTemplate = false;
    	this.loadU();
    },
});







