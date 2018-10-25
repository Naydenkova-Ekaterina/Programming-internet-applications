new Vue({
    el: '#header',
    data: {
        message: 'Kovalyova A.A., Naidenkova E.D.,P3200, variant: 90232'
    }
})


var nav = new Vue({
    el: '#fake-nav',
    methods: {
        open: function(which, e) {
            e.preventDefault();
            modal.active = which;
        },
    }
});

var modal_submit_register = 'Register';
var modal_submit_password = 'Reset Password';
var modal_submit_login = 'Login';

var modal = new Vue({
    el: '#login-modal',
    data: {
        active: null,
        submitted: null,

        // Submit button text
        registerSubmit: modal_submit_register,
       // passwordSubmit: modal_submit_password,
        loginSubmit: modal_submit_login,

        // Modal text fields
        registerName: '',
        registerPassword: '',
        loginUser: '',
        loginPassword: '',
       // passwordEmail: '',

        // Modal error messages
        registerError: '',
        loginError: '',
        passwordError: '',
    },
    methods: {
        close: function(e) {
            e.preventDefault();
            if (e.target === this.$el) {
                this.active = null;
            }
        },
        flip: function(which, e) {
            e.preventDefault();
            if (which !== this.active) {
                this.active = which;
            }
        },
        submit: function(which, e) {
            e.preventDefault();
            this.submitted = which
            var data = {
                form: which
            };

            switch (which) {
                case 'register':
                    data.name = this.registerName;
                    data.password = this.registerPassword;

                   // axios.post('/springJPA-postgreSQL-0.0.1-SNAPSHOT/users', {username:data.name,password:data.password} )
                    axios.post('/users', {username:data.name,password:data.password} )

                        .then(function (response) {
                            console.log(response);
                        })
                        .catch(function (error) {

                            console.log(error.message);
                        });


                    break;
                case 'login':
                   data.user = this.loginUser;
                    data.password = this.loginPassword;

                    var passwordHash;
                    alert(data.user);
                   // axios.post('/springJPA-postgreSQL-0.0.1-SNAPSHOT/users/encodePassword', {username:data.user,password:data.password} )
                    axios.post('/users/encodePassword', {username:data.user,password:data.password} )

                        .then(function (response) {
                            console.log(response);
                            passwordHash=response.data;
                            console.log(data.user);
                            alert(passwordHash);
                            if(passwordHash=="error"){
                                alert("Wrong username or password")
                            }else {
                                document.getElementById("user").value = data.user;
                                document.getElementById("pas").value = passwordHash;
                                document.getElementById("auth").click();
                            }
                        })
                        .catch(function (error) {

                            console.log(error.message);
                        });

                    break;

            }

        }
    }
});

