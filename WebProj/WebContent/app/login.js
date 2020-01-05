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
	<p>Bilostaa Samo da se ispise na ekranu</p>
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
