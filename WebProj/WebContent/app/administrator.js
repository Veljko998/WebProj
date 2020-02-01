Vue.component("administrator-page", {
	data: function() {
		return {
			title: 'administrator',
			loading: true,
            errored: false,
            showUsersTable: false,
            someMessage: '',
            organisations: null,
            organizacija: {}
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
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showPregledOrganizacije();">Pregled organizacije</button>
		</div>
		<div class="div-inline">
			<button class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click="showPersonalInfo();">Moj profil</button>
			<button class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click="removeRole();">Logout</button>
		</div>
	</nav>
	</br>
</div>
		`,
	methods: {
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
			router.push({path: "/pregledKorisnika"});
		},
		showPregledDiskova: function(){
			router.push({path: "/pregledDiskova"});
		},
		showPersonalInfo: function(){
			router.push({path: "/izmeniLicnePodatke"});
		},
		showPregledOrganizacije: function(){		
			var role = localStorage.getItem('role');
			var email = localStorage.getItem('email');
//			var path = 'rest/userService/test/' + email
//			axios
//    		.get(path)
//    		.then(response => {
//    		localStorage.setItem('organisationDetails', JSON.stringify(response.data));
			router.push({path: "/pregledOrganizacija"});
//    		});
		}
	},
		
});