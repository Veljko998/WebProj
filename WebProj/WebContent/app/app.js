var app = new Vue({ 
    el: '#webproj',
    data: {
        users: null,
        title: 'listanje korisnika kad se ulogujes',
        mode: "BROWSE",
        selectedUsers: {},
        searchField: ""
    },
    methods: {
        updateUser : function(user){
            axios
            .post("rest/webproj/adduser", user)
            .then(response => toast('User ' + user.username + " " + user.password + " successfuly saved."));
            this.mode = "BROWSE";
        }
    }
});