var app = new Vue({ 
    el: '#webproj',
    data: {
        title: 'Login',
        User: {},
        users: [],
        name: 'rest/webproj/idemooo'
    },
    methods: {
        addUser: function(User){
        	this.users.push(User)
        	this.name = User.username
        }
    }
});