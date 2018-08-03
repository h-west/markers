<html>
<head>
<meta charset="utf-8">
<title>MARKERS - LOGIN</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>
<script src="https://connect.facebook.net/en_US/sdk.js"
	id='facebook-jssdk'></script>
<script>
	(function(g) {
		g.fbAsyncInit = function() {
			FB.init({
				appId : '460148807795921',
				cookie : true,
				xfbml : true,
				version : 'v3.1'
			});
		}
		g.fbLogin = function() {
			FB.getLoginStatus(function(response) {
				if (response.status === 'connected') {
					$.post('/api/login/fb', response.authResponse).done(
							function(data) {
								location.href = '/';
							});
				} else {
					FB.login();
				}
			});
		}
	})(this);
</script>
</head>
<body>

	<fb:login-button scope="public_profile,email" onlogin="fbLogin();"></fb:login-button>

</body>

</html>