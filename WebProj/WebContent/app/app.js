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
	    { path: '/dodajDisk', component: DodajDisk}
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
