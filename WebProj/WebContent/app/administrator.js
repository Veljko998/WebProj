Vue.component("administrator-page", {
	data: function() {
		return {
			title: 'administrator',
			loading: true,
            errored: false
		}
	},
	template:
		`
<p>Stranica za ulogovanog administratora...Ovo je title: {{ title }}</p>
		`,
		
});