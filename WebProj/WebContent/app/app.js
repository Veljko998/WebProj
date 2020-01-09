const Login = { template: '<log-in></log-in>' }
const KorisnikPage = { template: '<korisnik-page></korisnik-page>'}
const AdminPage = { template: '<administrator-page></administrator-page>'}
const SuperAdministratorPage = { template: '<superadministrator-page></superadministrator-page>'}
const PregledKorisnika = { template: '<pregled-korisnika></pregled-korisnika>'}
const PregledOrganizacija = { template: '<pregled-organizacija></pregled-organizacija>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login},
	    { path: '/korisnik', component: KorisnikPage},
	    { path: '/administrator', component: AdminPage},
	    { path: '/superadministrator', component: SuperAdministratorPage},
	    { path: '/pregledKorisnika', component: PregledKorisnika},
	    { path: '/pregledOrganizacija', component: PregledOrganizacija}
//	    { path: '/', component: }
	  ]
});

router.beforeEach((to, from, next) => {
	var isLoggedIn;
	
	axios
	.get("rest/webproj/ensureLogin")
	.then(response =>{
		isLogedIn = response.data.isLogedIn;
		
//		if (!isLogedIn && to.path !== "/") {
//			next("/");  // ...idi na login page.
//		}
//		else {
////			localStorage.setItem("role", response.data.role);
////          localStorage.setItem("email", response.data.email);
			next();
//		}
	})
})

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
