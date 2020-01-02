const Login = { template: '<log-in></log-in>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: Login}
	  ]
});

var app = new Vue({
	router,
	el: '#webProj'
}).$mount('#webProj');

//var app = new Vue({ 
//    el: '#webproj',
//    data: {
//        title: 'Login',
//        User: {},
//        users: [],
//        name: 'rest/webproj/idemooo'
//    },
////    template: `
////        <div id="proba">
////            <p>Idemo nis uspeli smo</p>
////        </div>
////    `,
//    methods: {
//        addUser: function(User){
//        	this.users.push(User)
//        	this.name = User.username
//        }
//    }
//});