Vue.component("log-in", {
	data: function () {
        	return {
        		title: 'Login',
                User: {},
                users: [],
                name: ''
        	}
    },
    template:`
<div>
    <h1>Ajmooooo vise</h1>
	<h3>{{ title }}</h3>
</div>
`
    ,
    methods: {
        addUser: function(User){
        	this.users.push(User)
        	this.name = User.username
        }
    },
});
