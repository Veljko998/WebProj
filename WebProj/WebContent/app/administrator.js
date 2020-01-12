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
		<a class="navbar-brand">administrator</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link">Home option<span class="sr-only">(current)</span></a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link" v-on:click="showUsersTable = !showUsersTable">Pregled korisnika</a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link" v-on:click="GoToPregledVM();">Nova stranica za pregled VM</a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link" v-on:click="doNothing()">Do nothing</a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link" href="#/" v-on:click="removeRole()">Logout(proveriti zbog href =...)</a>
				</li>
			</ul>
		</div>

	</nav>
	</br>
	<pregled-korisnika></pregled-korisnika>
</div>
		`,
	methods: {
		doNothing: function(){
			someMessage = localStorage.getItem('role');
			console.log(someMessage);
		},
		removeRole: function(){
			localStorage.setItem("role", "noRole");
		},
		GoToPregledVM: function(){
			router.push({path: "/pregledVM"});
		}
	},
		
});