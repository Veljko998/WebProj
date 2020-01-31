Vue.component("detalji-kategorije", {
	data: function () {
        	return {
        		title: 'Detajli Kategorije',
        		Category: {},
        		showTemplate: false
        	}
    },
    template:`
    <div class="container-fluid" v-if="this.showTemplate == true">
	<h1 align="center">{{ this.title }}</h1></br></br>
	<h3>
		<ul>
			<li><label style="color:red">Name:&nbsp; </label>{{ this.Category.ime }}</li>
			<li><label style="color:red">Core number:&nbsp; </label>{{ this.Category.brojJezgara }}</li>
			<li><label style="color:red">RAM:&nbsp; </label>{{ this.Category.ramMemory }}</li>
			<li><label style="color:red">GPU:&nbsp; </label>{{ this.Category.gpu }}</li>
		</ul>
	</h3>
</div>
    `,
    methods: {
    	loadCategory: function() {
    		this.Category = JSON.parse(localStorage.getItem('storeObj4'));
    		console.log("Bilo sta: " + this.Category.ime)
    		
//    		axios
//    		.post('rest/categoryService/getCategoryByName', {"name": categoryName})
//    		.then(response => {
//    			this.Category = response.data;
//    		});
    		
    		this.showTemplate = true;
    	}
    },
    mounted () {  //created 
    	this.loadCategory();
    },
});







