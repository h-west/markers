<html>
<head>
<meta charset="utf-8">
<link rel="icon" type="image/png" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAD7SURBVEhL5dQ7CsJAFIXhbMFCC0vBJdi5Cx9LEFyG6CLUHdjYugFrdQluwLeNjfofmMAwXHESY5UDHwyTe2/ChCQpRWoYY4O7o7X2qvgpPdzw+uCKLnKljyeswT7VdJApOhY9nTXQckGm45ogHHLGFDO3Dq+PEJ0d/OYjGkijdXiTLaITHo+ePMwcfo16onOA36xhYcIbqCc6a/jNJ3w7IvVERx+R3ywaqBesJ7desnqi00TMN5BSrXoyZQVrmEW1mdNC7Jes2lxZwBrqU03u1KHfgDVYdE01P2UAa7joWiFZIhyuvcJSwR7pcK21V2jaeDha/yVDpzRJkjfHk6gI8gIfOAAAAABJRU5ErkJggg==">
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