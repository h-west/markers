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
			marker : {type:'board',cts:{}}
		},
		creared : function() {
		},
//		mounted : function(){
//			this.markers();
//		},
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
			markers : function(){
				$.get('/api/markers/'+map.getZoom()+'/'+map.getCenter().lng()+'/'+map.getCenter().lat(),function(features){
					map.data.addGeoJson({
						type : 'FeatureCollection',
						features : features
					});
				});
			},
			write : function(){
				var p = map.getCenter();
				/*$.post( , this.marker, function( feature ) {
					map.data.addFeature(new naver.maps.Feature(feature));
					$('.create_marker_close').click();
				});*/
				$.ajax({
			        type : 'POST',
			        url : '/api/marker/point/'+p.lng()+'/'+p.lat(),
			        contentType : 'application/json',
			        data : JSON.stringify(this.marker),
			        dataType : 'json',
			        success : function(feature){
			        	map.data.addFeature(new naver.maps.Feature(feature));
						$('.create_marker_close').click();
			        }
			    });
			},
			detail : function(id){
				var $vm = this;
				$.get('/api/marker/'+id,function(detail){
					$vm.$set($vm, 'marker', detail);
					$('.create_marker_open').click();
					if (infoWindow.getMap()) {
						infoWindow.close();
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
	 * Meta 분
	 */
	var MARKER_ICONS = {
		T1 : '/images/t1.svg',
		T2 : '/images/t2.svg',
		T3 : '/images/t3.svg'
	}

	/***************************************************************************
	 * Map 을 정의함.
	 */
	var map = new naver.maps.Map('map', {
		zoom : 8,
		logoControl : false
	});
	map.data.setStyle(function(feature) {
        return {
        	icon : feature.getProperty('icon')
        	//,title : feature.getProperty('title')
        };
    });
	vm.markers();
	
	var infoWindow = new naver.maps.InfoWindow();
	var contentArray = ['<div style="width:150px;text-align:center;padding:10px;" onclick="vm.detail(\'','','\')">','','</div>'];
	infoWindow.setCustom = function(id, str){
		contentArray[1] = id;
		contentArray[3] = str;
		this.setContent(contentArray.join(''));
	}
	// 마커클릭 이벤트 리스너  => 말풍선 
	map.data.addListener('click', function(e) {
//		if (infoWindow.getMap()) {
//			infoWindow.close();
//	    } else {
	    	infoWindow.setCustom(e.feature.id,e.feature.property_title);
	    	infoWindow.open(map, e.overlay);
//	    }
    });
	
	// 맵 클릭 리스너 => 말풍선 제거 
	naver.maps.Event.addListener(map, 'click', function() {
		if (infoWindow.getMap()) {
			infoWindow.close();
		}
	});
	
	// 드레그하거나 핀치가 종료되면 마커 조회 
	naver.maps.Event.addListener(map, 'dragend', function() {
		vm.markers();
	});
	naver.maps.Event.addListener(map, 'pinchend', function() {
		vm.markers();
	});
	

	/***************************************************************************
	 * 작성버튼 생성
	 */
	var writeMarkerBtn = new naver.maps.CustomControl('<img src="/images/add.svg">', {
		position : naver.maps.Position.RIGHT_BOTTOM
	});writeMarkerBtn.setMap(map);
	naver.maps.Event.addDOMListener(writeMarkerBtn.getElement(), 'click', toggleWriteMarker);
	
	// 작성버튼 클릭하면 중앙에 위치 마커 생성
//	var writeMarker = new naver.maps.Marker({
//		position : map.getCenter(),
//		clickable: true
//	});
	
	// 중앙 위치 마커 클릭 이벤트
//	naver.maps.Event.addListener(writeMarker, 'click', function() { showWriteMarker();
////		var p = writeMarker.getPosition();
////		$.post( '/api/marker/point/'+p._lng+'/'+p._lat, function( feature ) {
////			map.data.addFeature(new naver.maps.Feature(feature));
////		});
//		$('.create_marker_open').click();
//	});
	
	$('#writeMarker').click(function(){
		$('.create_marker_open').click();
		toggleWriteMarker();
	});
	
	// 중앙 위치 마커 표시 온/오프
	function toggleWriteMarker(){
		if($('#writeMarker').is(':visible')){
			$('#writeMarker').hide();
		}else{
			$('#writeMarker').css('top', map.getSize().height/2 - $('#writeMarker').height());
			$('#writeMarker').css('left', (map.getSize().width - $('#writeMarker').width()) / 2);
			$('#writeMarker').show();
		}
//		if(writeMarker.getMap()){
//			writeMarker.setMap(null);
//			naver.maps.Event.clearListeners(map,'center_changed');
//		}else{
//			writeMarker.setPosition(map.getCenter());
//			writeMarker.setMap(map);
//			naver.maps.Event.addListener(map, 'center_changed', function(e) {
//				writeMarker.setPosition(e);
//			});
//		}
	}
	

	// map.setCenter(new naver.maps.LatLng(37.3595953, 127.1053971));
	// naver.maps.Event.clearListeners(map,'center_changed');

	g.vm = vm;
	g.map = map;
})(this);