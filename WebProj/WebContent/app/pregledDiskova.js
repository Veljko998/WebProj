Vue.component("pregled-diskova", {
	data: function () {
        	return {
        		title: 'Pregled Diskova',
                User: {},
                users: null,
                role: 'noRule',
                email: '',
                disks: null,
                diskToEdit: null,
                isKorisnik: true
        	}
    },
    template:`
<div class="container-fluid">
	<div class="col text-center">
		<h2>{{ this.title }}</h2></br>
	</div>

    <div class="container-fluid">
		<div class="row">
			<div class="col">
				<label for="inputEmail4">Disc capacity: </label>
			</div>
		</div>
		<div class="row">
			<div class="col col-md-2">
				<input min="1" type="number" class="form-control" v-on:click="myFunction()" v-on:keyup="myFunction()" placeholder="From" id="inputCoreFrom">
			</div>
			<div class="col col-md-2">
				<input min="1" type="number" class="form-control" v-on:click="myFunction()" v-on:keyup="myFunction()" placeholder="To" id="inputCoreTo">
			</div>
		</div>
		
		<br>
	
		<input type="text" id="myInputVM" v-on:keyup="myFunction()" placeholder="Search for names...">
	</div>
	
	<div class="container-fluid scrollable">
		<table class="table table-hover " id="myTable">
			<thead>
				<tr v-on:keyup="helper();">
					<th scope="col">#</th>
					<th scope="col">Name</th>
					<th scope="col">Disk capacity</th>
					<th scope="col">VM name</th>
					<th scope="col">Functions</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="(d, index) in this.disks" v-on:keyup="helper();">
					<th scope="row">{{ index+1 }}</th>
					<td>{{ d.ime }}</td>
					<td>{{ d.kapacitet }}</td>
					<td>{{ d.virtualnaMasina }}</td>
					<td>
						<button v-if="isKorisnik == false" :id="d.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="editDisk();">Edit</button>
						<button v-if="isKorisnik == false" :id="d.ime" type="button" class="btn btn-sm btn-danger" v-on:click="deleteDisk();">Delete</button>
						<button :id="d.ime" type="button" class="btn btn-sm btn-secondary" v-on:click="diskDetails();">Details</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<button v-if="this.isKorisnik != true" v-on:click="goToAddDiscPage();" type="button" class="btn btn-lg btn-primary">Add New Disk</button>
</div>
    `,
    methods: {
    	diskDetails: function() {
    		console.log("Usli smo u metodu detalji diska. Ako radi obrisati ovaj komentar.");
//    		localStorage.setItem("imeDiska", event.srcElement.id);
    		var help = null;
    		
    		axios
    		.post('rest/discService/getDiskByName', {"name": event.srcElement.id})
    		.then(response => {
    			localStorage.setItem('storeObj', JSON.stringify(response.data));
    			help = response.data;
    			
    			axios
            	.post("rest/VMService/getVMByName" , {"name": help.virtualnaMasina})
            	.then(response => {
            		localStorage.setItem('storeObj2', JSON.stringify(response.data));
            		
            		router.push({path: "/detaljiDiska"});
            	});
    		});
    		
    	},
    	editDisk: function(){
    		var diskId = event.srcElement.id;
    		this.role = localStorage.getItem("role");
    		
    		axios
    		.post('rest/discService/getDiskByName', {"name": diskId})
    		.then(response => {
    			this.diskToEdit = response.data;
    			
    			localStorage.setItem("imeDiska", this.diskToEdit.ime);
    			localStorage.setItem("tipDiska", this.diskToEdit.tip);
    			localStorage.setItem("kapacitetDiska", this.diskToEdit.kapacitet);
    			localStorage.setItem("nazivVMDiska", this.diskToEdit.virtuelnaMasina);
    			
    			if (this.role == "admin" || this.role == "superadmin") {
    				router.push({path: "/izmenaDiska"});
				}else {
					console.log("Ulazi mi u deteljan pregled diska koji nisam jos uradio.");
//					router.push({path: "/detaljanPregledDiska"});
				}
    		});
    		
    	},
    	deleteDisk: function(){
    		var diskId = event.srcElement.id;
    		
    		var txt;
			if (confirm("Are you sure you want to delete disk?")) {
				axios
	    		.post('rest/discService/deleteDisk', {"name": diskId})
	    		.then(response => {
	    			var disk_deleted = response.data;
	    			
	    			if (disk_deleted == true) {
						console.log("Disk is succesfully deleted.");
						
						this.loadDisks();
					}else {
						console.log("Disk is not deleted.");
					}
	    		});
			} else {
				
			}
    		
    		//console.log('DELETE Disk with id: ' + diskId);
    	},
    	getID: function(oObject){
    		var id = oObject.id;
    		console.log("This object's ID attribute is set to \"" + id + "\".");
    	},
    	helper: function(){
    		var table = document.getElementsByTagName("table")[0];
    		var tbody = table.getElementsByTagName("tbody")[0];
    		tbody.onclick = function (e) {
    		    e = e || window.event;
    		    var data = [];
    		    var target = e.srcElement || e.target;
    		    while (target && target.nodeName !== "TR") {
    		        target = target.parentNode;
    		    }
    		    if (target) {
    		        var cells = target.getElementsByTagName("td");
    		        for (var i = 0; i < cells.length; i++) {
    		            data.push(cells[i].innerHTML);
    		        }
    		    }
    		    alert(data);
    		    console.log("USAOOOO: " + data);
    		};
    	},
    	myFunction: function(){
    		var input, filter, table, tr, td, i, txtValue;
    		var inputCoreFrom, inputCoreTo, inputRamFrom, inputRamTo, coreNumber, ramNumber, txtValue2;
    		
    		inputCoreFrom = document.getElementById("inputCoreFrom");
    		filter1 = Number(inputCoreFrom.value);
    		inputCoreTo = document.getElementById("inputCoreTo");
    		filter2 = Number(inputCoreTo.value);
    		
        	input = document.getElementById("myInputVM");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("myTable");
        	tr = table.getElementsByTagName("tr");
        	
        	// Loop through all table rows, and hide those who don't match the search query
        	for (i = 0; i < tr.length; i++){
        		td = tr[i].getElementsByTagName("td")[0]; //by VM name
        		coreNum = tr[i].getElementsByTagName("td")[1];
        		
        		if (td){
        			txtValue = td.textContent || td.innerText;
        			txtValue2 = coreNum.textContent || coreNumber.innerText;
        			coreNumber = Number(txtValue2);
        			
        			// Logic for taking event on changed fields for filtering
        			if (txtValue.toUpperCase().indexOf(filter) > -1){
        				if (filter1 == 0 && filter2 == 0) {
        					tr[i].style.display = "";
						}else if (filter1 != 0 && filter2 == 0) {
							if (filter1 <= coreNumber) {
								tr[i].style.display = "";
							}else {
								tr[i].style.display = "none";
							}
						}else if (filter1 == 0 && filter2 != 0) {
							if (filter2 >= coreNumber) {
								tr[i].style.display = "";
							}else {
								tr[i].style.display = "none";
							}
						}else if (filter1 != 0 && filter2 != 0) {
							if (filter1 <= coreNumber && filter2 >= coreNumber) {
								tr[i].style.display = "";
							}else {
								tr[i].style.display = "none";
							}
						}
        			}else{
        				tr[i].style.display = "none";
        			}
        		}
        	}
    	},
    	goToAddDiscPage: function(){
    		router.push({path: "/dodajDisk"});
    	},
    	loadDisks: function() {
        	axios
    		.post('rest/overview/getAllDiscs', {"role": this.role, "email": this.email})
    		.then(response => {
    			
    			this.disks = response.data;
    			if (this.disks === '') {
    				console.log("Nema Diskova za ispis kod ovog korisnika.");
    			}
    		});
    	},
    },
    mounted () {  //created 
    	this.role = localStorage.getItem("role");
    	this.email = localStorage.getItem("email");
    	
    	if (this.role == "korisnik") {
			this.isKorisnik = true;
		}else {
			this.isKorisnik = false;
		}
    	
    	this.loadDisks();
    },
});







