(function(g) {
	
	// plugin
	Vue.use(VueRouter);
	Vue.use({
        install: function (Vue) {
            Vue.prototype.$mkt = this;
            Vue.mkt = this;
        },
        get: function() {
        	var name = "mkt=";
        	var decodedCookie = decodeURIComponent(document.cookie);
            var ca = decodedCookie.split(';');
            var val = "", mkt = "";
            for(var i = 0; i <ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                	val = c.substring(name.length, c.length);
                	break;
                }
            }
            var mkt = !!val? decodeURIComponent(Array.prototype.map.call(atob(val.split('.')[1]), function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
            }).join('')):'';
            return {
            	isLogin : !!mkt,
            	user : !!mkt?JSON.parse(mkt):{}
            }
        }
    });
	
	var vm = new Vue({
		el : '#app',
		created: function () {
			var mkt = this.$mkt.get();
			this.isLogin = mkt.isLogin;
			this.user = mkt.user;
		},
		data : {
			isLogin : false,
			user : {}
		},
		methods : {
			login : function(cb){
				if(!this.isLogin){
					vm.$router.replace('/pop/login');
				}
				if(cb && typeof cb == 'function') cb();
			},
			detail : function(id) {
				vm.$router.push('/pop/marker/'+id);
				
				// 말품선이 떠있으면 없애기  
				if (infoWindow.getMap()){
					infoWindow.close();
				}
			}
		},
		router : new VueRouter({ // router.push (with history) router.replace (without history) router.go(n) -1
			routes : [
					{
						path : '/',
						component : {
							template : '<div></div>'
						}
					}, {
						path : '/pop',
						component : {
							template : `<div>
											<div class="pop_dimd"></div>
											<div class="pop_layer">
												<div class="popup">
													<router-view></router-view>
												</div>
												<div class="pop_align"></div>
											</div>
										</div>`
						},
						children : [
								{
									path : 'login',
									component : {
										template : '<div><h4>로그인</h4><a @click="login()">FACEBOOK으로 로그인하기</a></div>',
										methods : {
											login : function(){
												FB.login(function(response) {
													if (response.authResponse){
														$.post('/api/login/fb', response.authResponse).done(function(data) {
															vm.$router.replace('/');
														});
													} else{
														console.log('User cancelled login or did not fully authorize.');
													}
												});
											}
										}
									},
								}, 
								{
									path : 'marker/new',
									component : {
										template : `<div>
														<h4>신규마커</h4>
														
														<span>제목</span><input type="text" v-model="marker.title">
														<span>타입</span>
															<select v-model="marker.type">
																<option value="board">게시판</option>
																<option value="talk">대화방</option>
																<option value="aaa">다른거</option>
															</select>

														<ul v-if="marker.type=='board'">
															<li><span>내용</span><input type="text" v-model="marker.cts.contents"></li>
														</ul>
														<ul v-if="marker.type=='talk'">
															<li><span>TALK 내용</span><input type="text" v-model="marker.cts.contents"></li>
														</ul>
														<ul v-if="marker.type=='aaa'">
															<li><span>TALK 내용</span><input type="text" v-model="marker.cts.contents"></li>
														</ul>
														<button  @click="write()">작성</button>
														<button  @click="cancel()">취소</button>
													</div>`,
										data : function(){
											return {
												marker : {
													type : 'board',
													cts : {}
												}
											}
										},
										methods : {
											write : function() {
												var p = map.getCenter();
												$.ajax({
													type : 'POST',
													url : '/api/marker/point/' + p.lng() + '/' + p.lat(),
													contentType : 'application/json',
													data : JSON.stringify(this.marker),
													dataType : 'json',
													success : function(feature) {
														map.data.addFeature(new naver.maps.Feature(feature));
														vm.$router.replace('/');
													}
												});
											},
											cancel : function(){
												vm.$router.replace('/');
											}
										}
									},
									beforeEnter : function(to, from, next){
										if(vm){
											vm.login(next);
										}else{
											location.href="/";
										}
									}
								}, {
									path : 'marker/:markerId',
									component : {
										template : `<div>
														<h4>상세내용</h4>	
														{{ $route.params.markerId }}
														<div>
															{{marker.title}}
															{{marker.cts.contents}}
														</div>
														<hr/>
														<router-view></router-view>
														<hr/>
														<button  @click="close()">닫기</button>
													</div>`,
										data : function(){
											return {
												marker : {
													cts : {}
												}
											}
										},
										created : function(){
											this.detail(this.$route.params.markerId);
										},
										methods : {
											detail : function(markerId) {
												var $vm = this;
												$.get('/api/marker/' + markerId, function(detail) {
													$vm.$set($vm, 'marker', detail);
													vm.$router.replace({name:'board', params:{markerId:$vm.$route.params.markerId}});
												});
											},
											close : function(){
												vm.$router.push('/');
											}
										}
									},
									children : [
										{
											path : 'board',
											name : 'board',
											component : {
												template : `<div>
																<h4>게시판</h4>
																<ul>
																  <li v-for="board in boards" @click="getDetail('{{board.id}}')">
																    {{ board.title }}
																  </li>
																</ul>
															</div>`,
												created : function(){
													this.getBoards(this.$route.params.markerId);
												},
												data : function(){
													return {
														boards : []
													}
												},
												methods : {
													getBoards : function(markerId) {
														
														// 나중에 다른걸로 변경. (this 쓸수있는 것으로)
														var $vm = this;
														$.get('/api/marker/' + markerId + '/board', function(boards) {
															$vm.$set($vm, 'boards', boards);
														});
													},
													getDetail : function(boardId){
														vm.$router.push({name:'board-detail', params:{boardId:boardId}});
													},
													close : function(){
														vm.$router.push('/');
													}
												}
											},
										},
										{
											path : 'board-detail',
											name : 'board-detail',
											component : {
												template : `<div>
																<h4>게시판 상세</h4>
																<div>
																	상세 : {{ board.contents }}
																</div>
																<ul>
																  <li v-for="comment in comments">
																    {{ comment.title }}
																  </li>
																</ul>
																<button  @click="list()">돌아가기</button>
															</div>`,
												created : function(){
													//this.getBoards(this.$route.params.markerId);
													
													console.log('게시판 상세정보와  코멘트 정보를 조회함.');
												},
												data : function(){
													return {
														board : {},
														comments : []
													}
												},
												methods : {
													getBoards : function(id) {
														
														// 나중에 다른걸로 변경. (this 쓸수있는 것으로)
														var $vm = this;
//														$.get('/api/marker/' + id + '/board', function(boards) {
//															$vm.$set($vm, 'boards', boards);
//														});
													},
													close : function(){
														vm.$router.push('/');
													},
													list : function(){
														vm.$router.go(-1);
													}
												}
											},
										},
										{
											path : 'talk',
											name : 'talk',
											component : {
												template : '<div><h4>TALK</h4></div>'
											},
										}
									]
								}
						]
					}
			]
		})
	});

	
	/***************************************************************************
	 * Functions
	 */
	function markers() {
		$.get('/api/markers/' + map.getZoom() + '/' + map.getCenter().lng() + '/' + map.getCenter().lat(), function(features) {
			map.data.addGeoJson({
				type : 'FeatureCollection',
				features : features
			});
		});
	}

	
	/***************************************************************************
	 * Map 정의 및 데이터 관련 설정, 이벤트
	 */
	var map = new naver.maps.Map('map', {
		zoom : 8,
		logoControl : false
	});
	map.data.setStyle(function(feature) {
		return {
			icon : feature.getProperty('icon')
		};
	});
	markers(); // 정보조회

	// 마커클릭 이벤트 리스너 => 말풍선
	var infoWindow = new naver.maps.InfoWindow(); // https://navermaps.github.io/maps.js/docs/naver.maps.InfoWindow.html
	var contentArray = [
			'<div style="width:150px;text-align:center;padding:10px;" onclick="vm.detail(\'', '', '\')">', '', '</div>'
	];
	infoWindow.setCustom = function(id, str) {
		contentArray[1] = id;
		contentArray[3] = str;
		this.setContent(contentArray.join(''));
	}
	map.data.addListener('click', function(e) {
		infoWindow.setCustom(e.feature.id, e.feature.property_title);
		infoWindow.open(map, e.overlay);
	});

	
	/***************************************************************************
	 * MAP 이벤트
	 */
	// 맵 클릭 리스너 => 말풍선 제거
	naver.maps.Event.addListener(map, 'click', function() {
		if (infoWindow.getMap()){
			infoWindow.close();
		}
	});
	// 드레그하거나 핀치가 종료되면 마커 조회
	naver.maps.Event.addListener(map, 'dragend', function() {
		markers();
	});
	naver.maps.Event.addListener(map, 'pinchend', function() {
		markers();
	});

	
	/***************************************************************************
	 * 작성버튼 생성 (중앙 마커)
	 */
	var $writeMarker = $('#writeMarker');
	var writeMarkerBtn = new naver.maps.CustomControl('<img src="/images/add.svg">', {
		position : naver.maps.Position.RIGHT_BOTTOM
	});
	writeMarkerBtn.setMap(map);
	naver.maps.Event.addDOMListener(writeMarkerBtn.getElement(), 'click', toggleWriteMarker);

	$writeMarker.click(function() {
		vm.$router.replace('/pop/marker/new');
		toggleWriteMarker();
	});

	// 중앙 위치 마커 표시 온/오프
	function toggleWriteMarker() {
		if ($writeMarker.is(':visible')){
			$writeMarker.hide();
		} else{
			vm.login(function(){
				$writeMarker.css('top', map.getSize().height / 2 - $writeMarker.height());
				$writeMarker.css('left', (map.getSize().width - $writeMarker.width()) / 2);
				$writeMarker.show();
			})
		}
	}

	
	/***************************************************************************
	 * 글로벌 등록
	 */
	g.vm = vm;
	g.map = map;

})(this);