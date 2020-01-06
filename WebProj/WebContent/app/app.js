const Login = { template: '<log-in></log-in>' }
const KorisnikPage = { template: '<korisnik-page></korisnik-page>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login},
	    { path: '/korisnik', component: KorisnikPage}
	  ]
});

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
