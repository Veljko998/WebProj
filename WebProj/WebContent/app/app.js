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

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
