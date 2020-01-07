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
<p>Stranica za ulogovanog superadministratora...Ovo je title: {{ title }}</p>
		`,
		
});