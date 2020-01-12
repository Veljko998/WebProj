Vue.component("administrator-page", {
	data: function() {
		return {
			title: 'administrator',
			loading: true,
            errored: false,
            showUsersTable: false,
            someMessage: ''
		}
	},
	template:
		`
<div class="container-fluid">
	<nav class="navbar navbar-dark bg-dark">
		<div class="div-inline">
			<button class="btn btn-light" type="button" v-on:click="GoToPregledVM();">Pregled VM</button>
			<button class="btn btn-sm btn-outline-secondary" type="button"  v-on:click="showPregledKor();">Pregled korisnika</button>
		</div>
		
		<button class="btn btn-outline-success my-2 my-sm-0" type="button" v-on:click="removeRole();">Logout</button>
	</nav>
	</br>
	<pregled-korisnika v-if="this.showUsersTable"></pregled-korisnika>
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
			this.showUsersTable = !this.showUsersTable;
		}
	},
		
});