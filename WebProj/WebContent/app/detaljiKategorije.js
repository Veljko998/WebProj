Vue.component("detalji-kategorije", {
	data: function () {
        	return {
        		title: 'Detajli Kategorije',
        		Category: {}
        	}
    },
    template:`
    <p>{{this.title}}</p>
    `,
    methods: {
    	loadCategory: function() {
    		var categoryName = localStorage.getItem("imeKategorije");
    		localStorage.removeItem("imeKategorije");
    		
    		axios
    		.post('rest/categoryService/getCategoryByName', {"name": categoryName})
    		.then(response => {
    			this.Category = response.data;
    		});
    	}
    },
    mounted () {  //created 
    	this.loadCategory();
    },
});







