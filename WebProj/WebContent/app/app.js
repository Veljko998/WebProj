var global = {count : 0}; //Ne znam sta ovo znaci i da li treba da se nalazi ovde

var app = new Vue({
    el: "#logout",
    data: {
        fromIspis1 : 'Poruka o fromIChild1 ...'
    },
	mounted() {
		this.$root.$on('messageToParent', (text) => {
			this.fromChild1 = text + ', count : ' + global.count;
		});
	},
    methods: {
    	sendToChild1: function() {
    		global.count++;
    		this.$root.$emit('messageFromParent', 'Hello my child');
    	}
    } 
})