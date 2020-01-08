Vue.component("korisnik-page", {
	data: function() {
		return {
			title: 'korisnik',
			loading: true,
            errored: false
		}
	},
	template:
		`
<div class="container">
	<button class="position-absolute">Logout</button>
</div>
		`,
		
});