<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no">
<title>MARKERS</title>
<link rel="icon" type="image/png"
	href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAD7SURBVEhL5dQ7CsJAFIXhbMFCC0vBJdi5Cx9LEFyG6CLUHdjYugFrdQluwLeNjfofmMAwXHESY5UDHwyTe2/ChCQpRWoYY4O7o7X2qvgpPdzw+uCKLnKljyeswT7VdJApOhY9nTXQckGm45ogHHLGFDO3Dq+PEJ0d/OYjGkijdXiTLaITHo+ePMwcfo16onOA36xhYcIbqCc6a/jNJ3w7IvVERx+R3ywaqBesJ7desnqi00TMN5BSrXoyZQVrmEW1mdNC7Jes2lxZwBrqU03u1KHfgDVYdE01P2UAa7joWiFZIhyuvcJSwR7pcK21V2jaeDha/yVDpzRJkjfHk6gI8gIfOAAAAABJRU5ErkJggg==">

<link rel="stylesheet" href="/css/main.css">

<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=qVm5EJRqH7QHtnN0CXSv"></script>
<script type="text/javascript" src="https://unpkg.com/jquery"></script>
<script type="text/javascript" src="https://unpkg.com/vue"></script>
<script type="text/javascript" src="https://unpkg.com/vue-router"></script>

<script type="text/javascript" src="/js/facebook.js"></script>

<!-- <script type="text/javascript" src="/js/lib/jquery.popupoverlay.js"></script> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> -->

</head>
<body>
	<header></header>
	<section id="app">
		<div id="map" style="width: 100%; height: 100%;"></div>
		<img id="writeMarker" src="https://ssl.pstatic.net/static/maps/mantle/1x/marker-default.png" style="position: absolute; display:none"/>
		<router-view></router-view>
		<!-- <div id="create_marker" class="popup">
			<h4>작성하기</h4>
			<marker-info>
				<li><span>제목</span><input type="text" v-model="marker.title"></li>
			</marker-info>
			<button @click="write()">작성</button>
			<button class="create_marker_open" style="display:none;"></button>
			<button class="create_marker_close" style="display:none;"></button>
		</div> -->
		
		<!-- <marker-detail title="작성하기" id="create_marker">
			<template slot-scope="props" slot="items">
				<li><span>타입</span>
					<select v-model="marker.type">
					  <option value="board">게시판</option>
					</select>
				</li>
				<li><span>제목</span><input type="text" v-model="marker.title"></li>
				<li><span>내용</span><input type="text" v-model="marker.cts.contents"></li>
			</template>
			<template slot-scope="props" slot="buttons">
				<button  @click="write()">작성</button>
			</template>
		</marker-detail> -->
	</section>
	<script type="text/javascript" src="/js/app.js"></script>
</body>
</html>