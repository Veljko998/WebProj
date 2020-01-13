Vue.component("pregled-diskova", {
	data: function () {
        	return {
        		title: 'Pregled Diskova',
                User: {},
                users: null,
                role: 'noRule',
                email: '',
                disks: null
        	}
    },
    template:`
<div class="container-fluid">
	<div class="col text-center">
		<h2>{{ this.title }}</h2></br>
	</div>


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
	
	<table class="table table-hover " id="myTable">
		<thead>
			<tr v-on:keyup="helper();">
				<th scope="col">#</th>
				<th scope="col">Name</th>
				<th scope="col">Dick capacity</th>
				<th scope="col">VM name</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(d, index) in this.disks" v-on:keyup="helper();">
				<th scope="row">{{ index+1 }}</th>
				<td>{{ d.ime }}</td>
				<td>{{ d.kapacitet }}</td>
				<td>{{ d.virtualnaMasina }}</td>
			</tr>
		</tbody>
	</table>
	
	<button v-if="this.role !== 'korisnik'" v-on:click="goToAddDiscPage();" type="button" class="btn btn-lg btn-primary">Add New Disk</button>
</div>
    `,
    methods: {
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
    	}
    },
    mounted () {  //created 
    	var role = localStorage.getItem("role");
    	var email = localStorage.getItem("email");
    	
    	this.role = role;
    	this.email = email;
    	
    	axios
		.post('rest/overview/getAllDiscs', {"role": role, "email": email})
		.then(response => {
			
			this.disks = response.data;
			if (this.disks === '') {
				console.log("Nema Diskova za ispis kod ovog korisnika.");
			}
		});
    	
    },
});







