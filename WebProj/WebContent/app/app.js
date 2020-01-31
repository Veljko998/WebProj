const Login = { template: '<log-in></log-in>' }
const KorisnikPage = { template: '<korisnik-page></korisnik-page>'}
const AdminPage = { template: '<administrator-page></administrator-page>'}
const SuperAdministratorPage = { template: '<superadministrator-page></superadministrator-page>'}
const PregledKorisnika = { template: '<pregled-korisnika></pregled-korisnika>'}
const PregledOrganizacija = { template: '<pregled-organizacija></pregled-organizacija>'}
const DodajKorisnika = {template: '<dodaj-korisnika></dodaj-korisnika>'}
const PregledVM = {template: '<pregled-vm></pregled-vm>'}
const PregledDiskova = {template: '<pregled-diskova></pregled-diskova>'}
const DodajDisk = {template: '<dodaj-disk></dodaj-disk>'}
const DodajOrganizaciju = {template: '<dodaj-organizaciju></dodaj-organizaciju>'}
const IzmenaDiska = {template: '<izmena-diska></izmena-diska>'}
const DodajVM = {template: '<dodaj-vm></dodaj-vm>'}
const IzmeniPodatke = {template: '<izmeni-podatke></izmeni-podatke>'}
const IzmenaVM = {template: '<izmena-vm></izmena-vm>'}
const DetaljiDiska = {template: '<detalji-diska></detalji-diska>'}
const DetaljiVM = {template: "<detalji-vm></detalji-vm>"}
const DetaljiKategorije = {template: "<detalji-kategorije></detalji-kategorije>"}
const PregledKategorija = {template: '<pregled-kategorija></pregled-kategorija>'}
const DodajKategoriju = {template: '<dodaj-kategoriju></dodaj-kategoriju>'}
const IzmenaKategorije = {template: '<izmena-kategorije></izmena-kategorije>'}
const DetaljiKorisnika = {template: '<detalji-korisnika></detalji-korisnika>'}
const IzmenaKorisnika = {template: '<izmena-korisnika></izmena-korisnika>'}
const DetaljiOrganizacije = {template: '<detalji-organizacije></detalji-organizacije>'}
const IzmenaOrganizacije = {template: '<izmena-organizacije></izmena-organizacije>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login},
	    { path: '/korisnik', component: KorisnikPage},
	    { path: '/administrator', component: AdminPage},
	    { path: '/superadministrator', component: SuperAdministratorPage},
	    { path: '/pregledKorisnika', component: PregledKorisnika},
	    { path: '/pregledOrganizacija', component: PregledOrganizacija},
	    { path: '/dodajKorisnika', component: DodajKorisnika},
	    { path: '/pregledVM', component: PregledVM},
	    { path: '/pregledDiskova', component: PregledDiskova},
	    { path: '/dodajDisk', component: DodajDisk},
	    { path: '/dodajOrganizaciju', component: DodajOrganizaciju},
	    { path: '/izmenaDiska', component: IzmenaDiska},
	    { path: '/dodajVM', component: DodajVM},
	    { path: '/izmeniLicnePodatke', component: IzmeniPodatke},
	    { path: '/izmenaVM', component: IzmenaVM},
	    { path: '/detaljiDiska', component: DetaljiDiska},
	    { path: '/detaljiVM', component: DetaljiVM},
	    { path: '/detaljiKategorije', component: DetaljiKategorije},
	    { path: '/pregledKategorija', component: PregledKategorija},
	    { path: '/dodajKategoriju', component: DodajKategoriju},
	    { path: '/izmenaKategorije', component: IzmenaKategorije},
	    { path: '/detaljiKorisnika', component: DetaljiKorisnika},
	    { path: '/izmenaKorisnika', component: IzmenaKorisnika},
	    { path: '/detaljiOrganizacije', component: DetaljiOrganizacije},
	    { path: '/izmenaOrganizacije', component: IzmenaOrganizacije}
//	    { path: '/', component: }
	  ]
});


localStorage.clear();
//localStorage.setItem("isUserLogedIn", "false");
//console.log("promenili smo islogedin");

//router.beforeEach((to, from, next) => {
//	var isLoggedIn = localStorage.getItem("isUserLogedIn");
////	console.log("IDEMOOOOOOOOOOOOOOOOOO");
//	
//	axios
//	.post('rest/webproj/checkPath', {"path": to.path})
//	.then(response => {
//		var rd = response.data;
//		var userRole = localStorage.getItem("role");
////		console.log("response data: " + rd);
////		console.log("local storage: " + userRole);
//		
//		if(isLoggedIn !== "true" && to.path !== "/"){
////			console.log("Nemamo prijavljenog korisnika i ne mozemo da idemo dalje");
//			next("/");
//		}else{
//			console.log("User role when change page or whatever: " + userRole);
//			if(userRole === null){
////				console.log("AAAAAAAAAAAAAAAAAAAAAA");
//				next();
//			}else{
//				if(rd === "korisnik" && userRole === "korisnik"){
////					console.log("Imamo prijavljenog -KORISNIKA- i mozemo da idemo dalje.");
//					next();
//				}else if(rd === "admin" && userRole === "admin"){
////					console.log("Imamo prijavljenog -ADMIN- i mozemo da idemo dalje.");
//					next();
//				}else if(rd === "superadmin" && userRole === "superadmin"){
////					console.log("Imamo prijavljenog -SUPERADMIN- i mozemo da idemo dalje.");
//					next();
//				}else{
////					console.log("Ne mogu da verujem...........");
//					next();
////					console.log("da li ovde uopste moze da udje");
//					next("/");
////					next();
//				}
//			}
//		}
//	});
//})

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
