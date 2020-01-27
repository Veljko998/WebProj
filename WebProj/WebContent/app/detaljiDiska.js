Vue.component("detalji-diska", {
	data: function () {
        	return {
        		title: 'Disk Details',
        		Disk: null,
        		VM: null,
        		showTemplate: false
        	}
    },
    template:`
<div class="container-fluid" v-if="this.showTemplate == true">
	<h1 align="center">Pregled Diska</h1></br></br>
	<h3>
		<ul>
			<li><label style="color:red">Name:&nbsp; </label>{{ this.Disk.ime }}</li>
			<li><label style="color:red">Type:&nbsp; </label>{{ this.Disk.tip }}</li>
			<li><label style="color:red">Capacity:&nbsp; </label>{{ this.Disk.kapacitet }}</li>
			<li><label style="color:red">Virtual Machine: </label>
				<ul>
					<li><label style="color:red">Name:&nbsp; </label>{{ this.VM.ime }}</li>
					
					<li><label style="color:red">RAM:&nbsp; </label>{{ this.VM.ram }}</li>
					<li><label style="color:red">GPU:&nbsp; </label>{{ this.VM.gpu }}</li>
					<li>
						<label style="color:red">Disks: </label>
						<ul>
							<li v-for="d in this.VM.diskovi">{{ d }}</li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
	</h3>
</div>
    `,
    methods: {
    	/*
    	 * Load Disk and Virtual Machine of disk.
    	 */
    	loadDVM: function() {
    		this.Disk = JSON.parse(localStorage.getItem('storeObj'));
        	
        	this.VM = JSON.parse(localStorage.getItem('storeObj2'));
        	
        	this.showTemplate = true;
    	}
    },
    mounted () {
    	this.showTemplate = false;
    	this.loadDVM();
    },
});







