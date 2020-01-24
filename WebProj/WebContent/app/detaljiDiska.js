Vue.component("detalji-diska", {
	data: function () {
        	return {
        		title: 'Detajli Diska',
        		Disk: {}
        	}
    },
    template:`
    <p>{{this.title}}</p>
    `,
    methods: {
    	loadDisk: function() {
    		var diskName = localStorage.getItem("imeDiska");
    		localStorage.removeItem("imeDiska");
    		
    		axios
    		.post('rest/discService/getDiskByName', {"name": diskName})
    		.then(response => {
    			this.Disk = response.data;
    		});
    	}
    },
    mounted () {  //created 
    	this.loadDisk();
    },
});







