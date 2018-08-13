(function(g) {
	Vue.use(VueRouter);
	Vue.component('marker-detail', {
	  template: `<div :id="id" class="popup">\
					<h4>{{title}}</h4>
					<ul>
						<slot name="items"></slot>
					</ul>
					<slot name="buttons"></slot>
					<button :class="openClass" style="display:none;"></button>
					<button :class="closeClass" style="display:none;"></button>
		  		</div>`,
	  props : ['id','title'],
	  data: function () {
	    return {
	    	openClass: this.id + '_open',
	    	closeClass: this.id + '_close'
	    }
	  },
	  mounted : function(){
		  $('#'+this.id).popup({transition: 'all 0.3s'});
	  }
	})
	var vm = new Vue({
		el : '#app',
		data : {
			marker : {title:'무제'}
		},
		creared : function() {
		},
		router : new VueRouter({
			routes : [
				  { 
					  path: '/', 
					  component: { 
						  template: '<div class="root"></div>' 
					  }
				  },
				  { 
					  path: '/write', 
					  component: { 
						  template: '<div class="write"></div>' 
					  }
				  }
				]
		}),
		methods : {
			write : function(){
				var p = writeMarker.getPosition();
				/*$.post( , this.marker, function( feature ) {
					map.data.addFeature(new naver.maps.Feature(feature));
					$('.create_marker_close').click();
				});*/
				$.ajax({
			        type : 'POST',
			        url : '/api/marker/point/'+p._lng+'/'+p._lat,
			        contentType : 'application/json',
			        data : JSON.stringify(this.marker),
			        dataType : 'json',
			        success : function(feature){
			        	map.data.addFeature(new naver.maps.Feature(feature));
						$('.create_marker_close').click();
			        }
			    });
			}
		}/*,
		components : {
			'marker-info' : {
				template: '<ul><slot></slot></ul>'
			}
		}*/
	});
	
	

	/***************************************************************************
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

	/***************************************************************************
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
//		var p = writeMarker.getPosition();
//		$.post( '/api/marker/point/'+p._lng+'/'+p._lat, function( feature ) {
//			map.data.addFeature(new naver.maps.Feature(feature));
//		});
		$('.create_marker_open').click();
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