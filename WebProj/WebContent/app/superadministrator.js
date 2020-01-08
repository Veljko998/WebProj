Vue.component("superadministrator-page", {
	data: function() {
		return {
			title: 'superadministrator',
			loading: true,
            errored: false
		}
	},
	template:
		`
<div class="container-fluid">
	<nav class="navbar navbar-dark bg-dark">
		<a class="navbar-brand" href="#">Superadministrator</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="#">Home option<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Option1</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Option2</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Option3</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#/">Logout</a>
				</li>
			</ul>
		</div>

	</nav>
	<p>Ovde ce ici iz home dugmeta. valjda</p>
</div>
		`,
		
});