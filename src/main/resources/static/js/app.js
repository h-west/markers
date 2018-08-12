(function(g) {
	Vue.use(VueRouter);
	var vm = new Vue({
		el : '#app',
		data : {
			add : false
		},
		creared : function() {
		},
		router : new VueRouter({
			routes : [
				  { 
					  path: '/', 
					  component: { 
						  template: '<div>_root</div>' 
					  }
				  },
				  { 
					  path: '/write', 
					  component: { 
						  template: '<div>_write</div>' 
					  }
				  }
				]
		}),
		method : {
			write : function(){
				console.log('!!');
			}
		}
	});

	/*********
	 * Map 을 정의함.
	 */
	g.map = new naver.maps.Map('map', {
		zoom : 8,
		logoControl : false
	});
	$.get('/api/markers',function(features){
		map.data.addGeoJson({
			type : 'FeatureCollection',
		    features : features
		});
	});

	/*********
	 * 작성버튼 생성 
	 */
	var writeMarkerBtn = new naver.maps.CustomControl('<a href="#">쓰기</a>', {
		position : naver.maps.Position.RIGHT_BOTTOM
	});writeMarkerBtn.setMap(map);
	naver.maps.Event.addDOMListener(writeMarkerBtn.getElement(), 'click', showWriteMarker);
	
	// 작성버튼 클릭하면 중앙에 위치 마커 생성 
	var writeMarker = new naver.maps.Marker({
		position : map.getCenter(),
		clickable: true
	});
	
	// 중앙 위치 마커 클릭 이벤트 
	naver.maps.Event.addListener(writeMarker, 'click', function() { showWriteMarker();
		//var name = prompt('이름?');
		var p = writeMarker.getPosition();
		$.post( '/api/marker/point/'+p._lng+'/'+p._lat, function( feature ) {
			map.data.addFeature(new naver.maps.Feature(feature));
		});
	});
	
	// 중앙 위치 마커 표시 온/오프 
	function showWriteMarker(){
		if(writeMarker.getMap()){
			writeMarker.setMap(null);
			naver.maps.Event.clearListeners(map,'center_changed');
		}else{
			writeMarker.setPosition(map.getCenter());
			writeMarker.setMap(map);
			naver.maps.Event.addListener(map, 'center_changed', function(e) {
				writeMarker.setPosition(e);
			});
		}
	}
	

	// map.setCenter(new naver.maps.LatLng(37.3595953, 127.1053971));
	// naver.maps.Event.clearListeners(map,'center_changed');

})(this);