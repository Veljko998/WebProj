Vue.component("superadministrator-page", {
	data: function() {
		return {
			title: 'superadministrator',
			loading: true,
            errored: false,
            showUsersTable: false,
            showOrganisationsTable: false,
            showCategoriesTable: false
		}
	},
	template:
		`
<div class="container-fluid">
	<nav class="navbar navbar-dark bg-dark">
		<div class="div-inline">
			<button class="btn btn-light" type="button" v-on:click="GoToPregledVM();">Pregled VM</button>
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showPregledKor();">Pregled korisnika</button>
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showPregledDiskova();">Pregled diskova</button>
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showOrganisations();">Pregled organizacija</button>
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showCategories();">Pregled Kategorija</button>
			
		</div>
		<div class="div-inline">
			<button class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click="showPersonalInfo();">Moj profil</button>
			<button class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click="removeRole();">Logout</button>
		</div>
	</nav>
	</br>
	<pregled-korisnika v-if="showUsersTable"></pregled-korisnika>
	<pregled-organizacija v-if="showOrganisationsTable"></pregled-organizacija>
	<pregled-kategorija v-if="showCategoriesTable"></pregled-kategorija>
	
</div>
		`,
	methods: {
		showCategories: function() {
			this.showCategoriesTable = !this.showCategoriesTable;
		},
		doNothing: function(){
			someMessage = localStorage.getItem('role');
			console.log(someMessage);
		},
		removeRole: function(){
			localStorage.setItem("role", "noRole");
			router.push({path: "/"});
		},
		GoToPregledVM: function(){
			router.push({path: "/pregledVM"});
		},
		showPregledKor: function(){
			this.showUsersTable = !this.showUsersTable;
		},
		showPregledDiskova: function(){
			router.push({path: "/pregledDiskova"});
		},
		showOrganisations: function(){
			this.showOrganisationsTable = !this.showOrganisationsTable;
		},
		showPersonalInfo: function(){
			router.push({path: "/izmeniLicnePodatke"});
		}
	},
});