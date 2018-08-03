<html>
<head>
<meta charset="utf-8">
<title>MARKERS - LOGIN</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>

</head>
<body>

	<fb:login-button scope="public_profile,email" onlogin="fbLogin();"></fb:login-button>
	<div id="fb-root"></div>
	<script src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v3.1&appId=460148807795921" id='facebook-jssdk'></script>
	<script>
		(function(g) {
			g.fbLogin = function() {
				FB.getLoginStatus(function(response) {
					if (response.status === 'connected') {
						/*FB.api('/me?fields=email,picture{url}', function(response) {
						    console.log(JSON.stringify(response));
						});
						*/
						 $.post('/api/login/fb', response.authResponse).done(
								function(data) {
									console.dir(data);
									//location.href = '/';
							}); 
					} else {
						FB.login();
					}
				});
			}
		})(this);
	</script>
</body>

</html>