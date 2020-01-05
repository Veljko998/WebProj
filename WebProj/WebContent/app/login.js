Vue.component("log-in", {
	data: function () {
        	return {
        		title: 'Login',
                User: {},
                users: null, //[]
                name: ''
        	}
    },
    template:`
<div>
	<h3>{{ title }}</h3>
	<label>Username: </label>
	<input name="username" type="text" v-model="User.username"/><br/>
	<label>Password: </label>
	<input name="password" type="text" v-model="User.password"/>
	<input type="checkbox" name="remember" />Remember me<br/>
	<a v-bind:href="'rest/webproj/login/' + User.username +'/' + User.password"><button v-on:click="checkUser(User)">Login</button></a>
</div>
`
    ,
    methods: {
        addUser: function(User){
        	this.users.push(User)
        	this.name = User.username
        },
        checkUser: function(User){
        	
        }
    },
});
