(function(g) {

	var map = new naver.maps.Map('map', {
		zoom : 8,
		logoControl : false
	});

	var locationBtnHtml = '<a href="#">쓰기</a>';
	var customControl = new naver.maps.CustomControl(locationBtnHtml, {
		position : naver.maps.Position.RIGHT_BOTTOM
	});

	customControl.setMap(map);

	var domEventListener = naver.maps.Event.addDOMListener(customControl.getElement(), 'click', function() {
		// map.setCenter(new naver.maps.LatLng(37.3595953, 127.1053971));
		var marker = new naver.maps.Marker({
			position : map.getCenter(),
			map : map
		});
		naver.maps.Event.addListener(map, 'center_changed', function(e) {
			marker.setPosition(e);
		});
		// naver.maps.Event.clearListeners(map,'center_changed');
	});

	var vm = new Vue({
		el : '#app',
		data : {
			map:
		},
		computed : {
			// 계산된 getter
			reversedMessage : function() {
				// `this` 는 vm 인스턴스를 가리킵니다.
				return this.message.split('').reverse().join('')
			}
		}

	})

})(this);