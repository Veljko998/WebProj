Vue.component("detalji-vm", {
	data: function () {
        	return {
        		title: 'Virtual Machine Details',
        		VM: null,
        		disks: [] ,// list of disk Objects
        		showTemplate: false,
        		nesto: {},
        		role: null
        	}
    },
    template:`
<div class="container-fluid" v-if="this.showTemplate == true">
	<h1 align="center">{{ this.title }}</h1></br></br>
	<h3>
		<ul>
			<li><label style="color:red">Name:&nbsp; </label>{{ this.VM.ime }}</li>
			<li><label style="color:red">Category name:&nbsp; </label>{{ this.VM.kategorjia.ime }}</li>
			<li><label style="color:red">Core number:&nbsp; </label>{{ this.VM.brojJezgara }}</li>
			<li><label style="color:red">RAM:&nbsp; </label>{{ this.VM.ram }}</li>
			<li><label style="color:red">GPU:&nbsp; </label>{{ this.VM.gpu }}</li>
			<li><label style="color:red">Disks: </label>
				<ul>
					<li v-for="d in this.disks"><label style="color:red">Name:&nbsp; </label> {{ d.ime }}
						<ul>
							<li><label style="color:red">Type:&nbsp; </label>{{ d.tip }}</li>
							<li><label style="color:red">Capacity:&nbsp; </label>{{ d.kapacitet }}</li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
	</h3>
</div>
    `,
    methods: {
    	loadVMD: function() {
    		this.VM = JSON.parse(localStorage.getItem('storeObj3'));
    		console.log("BILO sta: " + this.VM.ime);
//        	
////        	this.VM = JSON.parse(localStorage.getItem('storeObj2'));
//    		
    		var i;
    		for (i = 0; i < this.VM.diskovi.length; i++) {
    		  console.log("idemo: hahah: " + this.VM.diskovi[i]);
    		  axios
    		  .post("rest/discService/getDiskByName", {"name": this.VM.diskovi[i]})
    		  .then(response => {
    			  this.nesto = response.data;
    			  this.disks.push(this.nesto);
    			  this.nesto = {};
    		  });
    		}
    		localStorage.removeItem("storeObj3");
    		this.showTemplate = true;
    	}
    },
    mounted () {  //created 
    	this.showTemplate = false;
    	this.role = localStorage.getItem("role");
    	
    	this.loadVMD();
    },
});







