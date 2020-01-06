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
<p>Stranica za ulogovanog korisnika...Ovo je title: {{ title }}</p>
		`,
		
});