const Login = { template: '<log-in></log-in>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login}
	  ]
});

var app = new Vue({
	router,
	el: '#login'
}); //.$mount('#login');
