Vue.component("dodaj-organizaciju" ,{
	data: function() {
		return {
			title: "Dodavanje organizacije",
			role: '',
			email: '',
			machines: null,
			Org: {},
		}
	},
	template: 
		`
	<div class="container bg-secondary">
	<div class="row justify-content-center">
	<div class="col-md-8">
		<div class="card">
			<div class="card-header">
				Register
			</div>
			<div class="card-body">

				<h1> Ajde radi radi </h1>
			</div>
		</div>
	</div>
</div>
	</div>
		`,
	methods: {
		
	},
	mounted () {  //created /*
		
    },
});