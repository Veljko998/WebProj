Vue.component("detalji-vm", {
	data: function () {
        	return {
        		title: 'Detajli VM',
        		VM: {}
        	}
    },
    template:`
    <p>{{this.title}}</p>
    `,
    methods: {
    	loadVM: function() {
    		var diskName = localStorage.getItem("imeVM");
    		localStorage.removeItem("imeVM");
    		
    		axios
    		.post('rest/VMService/getVMByName', {"name": diskName})
    		.then(response => {
    			this.VM = response.data;
    		});
    	}
    },
    mounted () {  //created 
    	this.loadVM();
    },
});







