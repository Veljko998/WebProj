Vue.component("child1", {
	data: function () {
		    return {
		    	fromParent : 'parent message placeholder'
		    }
	},
	template: ` 
<div>
	{{fromParent}} <br />
	<button v-on:click="sendToParent">Send message to parent</button> <br />
	<button v-on:click="sendToChild2">Send message to child 2</button> <br />
</div>		  
`
	, 
	mounted() {
		this.$root.$on('messageFromParent', (text) => {
			this.fromParent = text + ', count : ' + global.count;
		});
	},
	methods : {
		sendToParent : function() {
    		global.count++;
    		this.$root.$emit('messageToParent', 'Hello parent');
		},
		sendToChild2 : function() {
    		global.count++;
    		this.$root.$emit('messageFromChild1ToChild2', 'Hello sibiling');
		} 
	}
});