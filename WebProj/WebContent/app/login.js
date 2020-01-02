Vue.component("log-in", {
	data: function () {
        	return {
        		title: 'Login',
                User: {},
                users: [],
                name: ''
        	}
    },
    template: `
<div>
	<p>Bilostaa Samo da se ispise na ekranu</p>
	
</div>
    `,
    methods: {
        addUser: function(User){
        	this.users.push(User)
        	this.name = User.username
        }
    },
});


//data: {
//    title: 'Login',
//    User: {},
//    users: [],
//    name: '',
//    function () {
//    	return {
//    		veljko: null
//    	}
//    }
//},


//<h1>{{ title }}</h1>
//<label>Username: </label>
//<input name="username" type="text" v-model="User.username"/><br/>
//<label>Password: </label>
//<input name="password" type="text" v-model="User.password"/><br/>
//<!--	<input type="checkbox" name="remember" />Remember me<br/> -->
//<!--<a v-bind:href="'rest/webproj/login/' + User.username +'/' + User.password"><button v-on:click="addUser(User)">Login</button></a>-->
//<button v-on:click="'login/' + User.username +'/' + User.password" >Login</button>