Vue.component("child2", {
	data: function () {
		    return {
		    	fromChild1 : 'child 1 message placeholder'
		    }
	},
	template: ` 
<div>
	{{fromChild1}} <br />
</div>		  
`
	,
	mounted() {
		this.$root.$on('messageFromChild1ToChild2', (text) => {
			this.fromChild1 = text + ', count : ' + global.count;
		});
	},

});