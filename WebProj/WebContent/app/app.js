const Login = { template: '<log-in></log-in>' }
const KorisnikPage = { template: '<korisnik-page></korisnik-page>'}
const AdminPage = { template: '<administrator-page></administrator-page>'}
const SuperAdministratorPage = { template: '<superadministrator-page></superadministrator-page>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login},
	    { path: '/korisnik', component: KorisnikPage},
	    { path: '/administrator', component: AdminPage},
	    { path: '/superadministrator', component: SuperAdministratorPage}
//	    { path: '/', component: },
	  ]
});

router.beforeEach((to, from, next) => {
	var isLoggedIn;
	
	axios
	.get("rest/webproj/ensureLogin")
	.then(response =>{
		isLogedIn = response.data.isLogedIn;  // response.data je objekat LoginToEnsure.
		if (!isLogedIn && to.path !== "/") {  // ako je uneta bilo koja druga putanja koja nije login page...
			next("/");  // ...idi na login page.
		}
		else {
			//  Ovde bi bilo da sacuvamo ulogu i email.
//			localStorage.setItem("role", response.data.role);
//          localStorage.setItem("email", response.data.email);
			next();  // Ako je sve uspesno idi tamo gde trebas(home page jedne od uloga).
		}
	})
})

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
