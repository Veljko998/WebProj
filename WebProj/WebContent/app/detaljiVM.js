Vue.component("detalji-vm", {
	data: function () {
        	return {
        		title: 'Virtual Machine Details',
        		VM: null,
        		disks: [] ,// list of disk Objects
        		activity: [],
        		showTemplate: false,
        		nesto: {},
        		role: null,
        		vmIsOn: false,
        		doTxt: "Turn on"
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
			<li v-if="this.role != 'korisnik'">
				<label style="color:red">Turn on/off:&nbsp; </label>
				<button v-if="this.role != 'korisnik'" type="button" class="btn btn-sm btn-secondary" v-on:click="turnOnOffMachine();">{{this.doTxt}}</button>
			</li>
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
			<li v-for="d in this.activity"><label style="color:red">Activity:&nbsp; </label>
				<ul>
					<li><label style="color:red">Date of turning on:&nbsp; </label>{{ d.first }}&nbsp;&nbsp;&nbsp;<button v-if="this.role != 'korisnik'" type="button" class="btn btn-sm btn-secondary">delete</button></li>
					
					<li><label style="color:red">Date of turning off:&nbsp; </label>{{ d.second }}&nbsp;&nbsp;<button v-if="this.role != 'korisnik'" type="button" class="btn btn-sm btn-secondary">delete</button></li>
					
				</ul>
			</li>
		</ul>
	</h3>
</div>
    `,
    methods: {
    	checkIsVMOnOff: function(){
    		axios
			.post("rest/VMService/checkIsVMOnOff", {"name": this.VM.ime})
			.then(response => {
				this.vmIsOn = response.data;
				if (this.vmIsOn == false) {
					this.doTxt = "Turn on";
					console.log("Palimo masinu");
				}else {
					this.doTxt = "Turn off";
					console.log("Gasimo masinu");
				}
			});
    	},
    	turnOnOffMachine: function(){
    		if (this.vmIsOn == false) { //  If VM is off
				axios
				.post("rest/VMService/turnOn", {"name": this.VM.ime})
				.then(response =>{
					this.vmIsOn = true;
					this.doTxt = "Turn off";
				});
			}else { //  If VM is on
				axios
				.post("rest/VMService/turnOff", {"name": this.VM.ime})
				.then(response =>{
					this.vmIsOn = false;
					this.doTxt = "Turn on";
				});
			}
    		this.loadActivity.call();
    	},
    	loadVMD: function() {
    		this.VM = JSON.parse(localStorage.getItem('storeObj3VM'));

    		var i;
    		for (i = 0; i < this.VM.diskovi.length; i++) {
    		  axios
    		  .post("rest/discService/getDiskByName", {"name": this.VM.diskovi[i]})
    		  .then(response => {
    			  this.nesto = response.data;
    			  this.disks.push(this.nesto);
    			  this.nesto = {};
    		  });
    		}

//    		this.showTemplate = true;
    	},
    	loadActivity: function(){
    		axios
  		  .post("rest/discService/getActivities", {"name": this.VM.ime})
  		  .then(response => {
  			  this.activity = response.data;
  			  this.showTemplate = true;
  		  });
    		console.log("Aktivnost:  "+this.activity);
    	},
    },
    mounted () {  //created 
    	this.showTemplate = false;
    	this.role = localStorage.getItem("role");
    	
    	this.loadVMD();
    	this.checkIsVMOnOff();
    	this.loadActivity();
    },
});







