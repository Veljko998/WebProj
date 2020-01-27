Vue.component("izmeni-podatke" ,{
	data: function() {
		return {
			title: "Izmena licnih podataka",
			role: '',
			email: '',
			organisations: null,
			User: {},
			showErrorEmptyField: false,
			showErrorPassword: false,
			showErrorEmailExists: false,
			loaded: false,
			lozinka1: '',
			lozinka2: '',
			showAddingSucceed: false
		}
	},
	template: 
	`
<div class="container bg-secondary">
	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					Moj profil
				</div>
				<div class="card-body">

					<!-- shows readonly organisation -->
					<div class="input-group mb-4" v-if="role !== 'superadmin'">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Organizacija:</span>
						</div>
						<input type="text" class="form-control" name="organisation" id="organisation" v-bind:placeholder="User.organizacija" readonly="readonly"/>
					</div>
				
					<!-- first name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Ime:</span>
						</div>
						<input type="text" class="form-control" name="name" id="name" v-model:value="User.ime"/>
					</div>

					<!-- last name -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Prezime:</span>
						</div>
						<input type="text" class="form-control" name="surname" id="surname" v-model:value="User.prezime"/>
					</div>

					<!-- email -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Mejl adresa:</span>
						</div>
						<input type="text" class="form-control" name="email" id="email" v-model:value="User.email"/>
					</div>

					<!-- password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Lozinka:</span>
						</div>
						<input type="password" class="form-control" name="password" id="password" placeholder="Unesite staru ili novu lozinku" v-model="lozinka1"/>
					</div>

					<!-- confirm password -->
					<div class="input-group mb-4">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroup-sizing-default">Confirm password</span>
						</div>
						<input type="password" class="form-control" name="passwordConf" id="passwordConf" placeholder="Potvrdite lozinku" v-model="lozinka2"/>
					</div>
					
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmptyField === true">Sva polja moraju biti popunjena!</br></p>
					<p class="errorMessageRegisterUser" v-if="this.showErrorPassword === true">Lozinka nije ista!</p>
					<p class="errorMessageRegisterUser" v-if="this.showErrorEmailExists === true">Korisnik sa ovim mejlom vec postoji!</p>
					
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-lg btn-block login-button" v-on:click="checkData()">Izmeni podatke</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	`,
	methods: {
		userExists: function(){
			this.role = localStorage.getItem('role');
			this.email = localStorage.getItem('email');
			var path = 'rest/userService/userAlreadyExists/' + this.email + '/' + this.User.email
	    
			axios
	    		.get(path)
	    		.then(response => {
	    			this.showErrorEmailExists = response.data
	    		});
		},
		
		checkData: function(){
			if((this.User.ime !== '' && this.User.ime !== undefined) && 
					(this.User.prezime !== '' && this.User.prezime !== undefined) &&
					(this.User.email !== '' && this.User.email !== undefined) &&
					(this.lozinka1 !== '' && this.lozinka1 !== undefined) &&
					(this.lozinka2 !== '' && this.lozinka2 !== undefined)){
					this.showErrorEmptyField = false;
					
					this.userExists.call();
					
					if(this.lozinka2 !== this.lozinka1){
						this.showErrorPassword = true;
						this.lozinka1 = '';
						this.lozinka2 = '';
					}
					else{
						this.showErrorPassword = false;
					}
				}
				else{
					this.showErrorEmptyField = true;
				}
					
				if(this.showErrorPassword === false && this.showErrorEmptyField === false && this.showErrorEmailExists === false){
					console.log("evo ovo moze");
					this.changeData.call();
				}
		},
		
		changeData: function(){
			
			axios
        	.post('rest/userService/addUser', {"name": this.User.ime, "surname": this.User.prezime, "email": this.User.email, "password": this.lozinka1, "organisationName": this.User.organizacija.ime, "role": this.role})
        	.then(response => {
        		 this.showAddingSucceed = response.data;
        		 
        		 if(this.showAddingSucceed){
         			console.log("Podaci su uspesno izmenjeni");
         			localStorage.setItem('email', this.User.email);
         			if(this.role === 'admin'){
         				router.push({path: "/administrator"});
         			}
         			else if(this.role === 'superadmin'){
         				router.push({path: "/superadministrator"});
         			}
         			else{
         				router.push({path: "/korisnik"});
         			}
         		}
        		
        	});
			
		}
	},
	mounted () {  
		this.role = localStorage.getItem('role');
		this.email = localStorage.getItem('email');
		var path = 'rest/userService/getUser/' + this.email
    
		axios
    		.get(path)
    		.then(response => {
    			this.User = response.data
    		});
		
		this.loaded = true;
		
    },
});