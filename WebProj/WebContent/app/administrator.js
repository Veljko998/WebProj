Vue.component("administrator-page", {
	data: function() {
		return {
			title: 'administrator',
			loading: true,
            errored: false,
            showUsersTable: false
		}
	},
	template:
		`
<div class="container-fluid">
	<nav class="navbar navbar-dark bg-dark">
		<a class="navbar-brand" href="#">administrator</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="#">Home option<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#" v-on:click="showUsersTable = !showUsersTable">Pregled korisnika</a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link" href="#/">Logout</a>
				</li>
			</ul>
		</div>

	</nav>
	</br>
	<pregled-korisnika v-if="showUsersTable"></pregled-korisnika>
</div>
		`
		
});