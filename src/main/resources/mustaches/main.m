<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no">
<title>MARKERS</title>
<link rel="icon" type="image/png"
	href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAD7SURBVEhL5dQ7CsJAFIXhbMFCC0vBJdi5Cx9LEFyG6CLUHdjYugFrdQluwLeNjfofmMAwXHESY5UDHwyTe2/ChCQpRWoYY4O7o7X2qvgpPdzw+uCKLnKljyeswT7VdJApOhY9nTXQckGm45ogHHLGFDO3Dq+PEJ0d/OYjGkijdXiTLaITHo+ePMwcfo16onOA36xhYcIbqCc6a/jNJ3w7IvVERx+R3ywaqBesJ7desnqi00TMN5BSrXoyZQVrmEW1mdNC7Jes2lxZwBrqU03u1KHfgDVYdE01P2UAa7joWiFZIhyuvcJSwR7pcK21V2jaeDha/yVDpzRJkjfHk6gI8gIfOAAAAABJRU5ErkJggg==">
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=qVm5EJRqH7QHtnN0CXSv"></script>
<script type="text/javascript" src="https://unpkg.com/jquery"></script>
<script type="text/javascript" src="https://unpkg.com/vue"></script>
<script type="text/javascript" src="https://unpkg.com/vue-router"></script>

<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/facebook.js"></script>

</head>
<body>
	<header></header>
	<section id="app">
		<div id="map" style="width: 100%; height: 100%;"></div>
		<img id="writeMarker" src="https://ssl.pstatic.net/static/maps/mantle/1x/marker-default.png" style="position: absolute; display:none"/>
		<router-view></router-view>
	</section>
	<script type="text/javascript" src="/js/app.js"></script>
</body>
</html>