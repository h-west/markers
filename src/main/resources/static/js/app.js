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
			this.initLogin();
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
			},
			initLogin : function () {
				var mkt = this.$mkt.get();
				this.isLogin = mkt.isLogin;
				this.user = mkt.user;
			}
		},
		router : new VueRouter({ // router.push (with history) router.replace
									// (without history) router.go(n) -1
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
												var $this = this;
												FB.login(function(response) {
													if (response.authResponse){
														$.post('/api/login/fb', response.authResponse).done(function(data){
															$this.$root.initLogin();
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
														
														<ul>
															<li>
																<span>제목</span><input type="text" v-model="marker.title">
															</li>
															<li>
																<span>타입</span>
																<select v-model="marker.type">
																	<option value="board">게시판</option>
																	<option value="talk">대화방</option>
																	<option value="shop">숍</option>
																	<option value="aaa">다른거</option>
																</select>
															</li>
															<li>
																<span>이미지</span>
																<div>
																	<input type="file" id="file" ref="file" @change="uploadFile()">
																</div>
															</li>
														</ul>

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
											uploadFile : function() {
												var formData = new FormData();
												formData.append('file', this.$refs.file.files[0]);
												// var data = new
												// FormData(this.$refs.file.files[0]);
												$.ajax({
													type : 'POST',
													url : '/api/file/upload',
													enctype : 'multipart/form-data',
													data : formData,
													processData: false,  // Important!
											        contentType: false,
											        cache: false,
													success : (file) => {
														/*
														 * headers:{content-disposition:
														 * ["form-data;
														 * name="file";
														 * filename="3.jpg""],
														 * content-type:
														 * ["image/jpeg"]}
														 * id:"5b7a0f2bb1a90b44f8bf7900"
														 * originFileName:"3.jpg"
														 * savefileName:"/data/upload/880f5e52-55e1-4d72-ba39-00e6d1c3a6b2.jpg"
														 * url:"/upload-image/880f5e52-55e1-4d72-ba39-00e6d1c3a6b2.jpg"
														 * userId:"F1940361676003655"
														 */
														this.marker.image = file.url;
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
														<img :src="marker.image" style="width:50px;height:50px">
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
												$.get('/api/marker/' + markerId, (marker) => {
													this.marker = marker;
													vm.$router.replace({name:marker.type, params:{markerId:this.$route.params.markerId}});
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
																  <li v-for="board in boards" @click="getDetail(board.id)">
																    {{ board.title }}
																  </li>
																  <button @click="reg()">신규</button>
																</ul>
															</div>`,
												created : function(){
													this.getBoards(this.markerId);
												},
												data : function(){
													return {
														boards : [],
														markerId : this.$route.params.markerId
													}
												},
												methods : {
													getBoards : function(markerId) {
														$.get('/api/marker/' + markerId + '/board', (boards)=> {
															this.boards = boards;
														});
													},
													getDetail : function(boardId){
														vm.$router.replace({name:'board-detail', params:{boardId:boardId}});
													},
													reg : function(){
														vm.$router.replace({name:'board-new', params:{markerId:this.markerId}});
													}
												}
											},
										},
										{
											path : 'board-new',
											name : 'board-new',
											component : {
												template : `<div>
																<h4>신규 게시판 작성</h4>
																<div>
																	<input type="text" v-model="board.title"><br>
																	<textarea v-model="board.contents"/>
																</div>
																<button @click="reg()">저장</button>
																<button @click="list()">취소</button>
															</div>`,
												data : function(){
													return {
														board : {},
														markerId : this.$route.params.markerId
													}
												},
												methods : {
													reg : function() {
														$.ajax({
															type : 'POST',
															url : '/api/marker/' + this.markerId + '/board',
															contentType : 'application/json',
															data : JSON.stringify(this.board),
															dataType : 'json',
															success : function(board) {
																vm.$router.replace({name:'board'});
															}
														});
													},
													list : function() {
														vm.$router.replace({name:'board'});
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
																	<div v-if="!edit.board">
																		제목 : {{ board.title }} <br/>
																		상세 : {{ board.contents }} <br/>
																		<button  @click="edit.board=true" v-if="board.board.user.userId==userId">수정</button>
																	    <button  @click="delBoard()" v-if="board.board.user.userId==userId">삭제</button>
																	</div>
																	<div v-if="edit.board">
																		제목 : <input type="text" v-model="boardTemp.title"> <br>
																		상세 : <textarea v-model="boardTemp.contents"></textarea><br>
																		<button  @click="updBoard()" v-if="board.board.user.userId==userId">수정완료</button>
																		<button  @click="edit.board=false" v-if="board.board.user.userId==userId">수정쥐소</button>
																	</div>
																</div>
																<hr/>
																[댓글]<br>
																<input type="text" v-model="comment.contents"><button  @click="regComment()">댓글등록</button><br>
																<ul>
																  <li v-for="comment in comments">
																  	<div v-if="!edit.comment || comment.id!=commentTemp.id">
																	    <img :src="comment.user.image" style="width:30px;height:30px"> {{ comment.contents }} 
																		<button  @click="showCommentEdit(comment)" v-if="comment.user.userId==userId">수정</button>
																	    <button  @click="delComment(comment)" v-if="comment.user.userId==userId">삭제</button>
																	 </div>
																	 <div v-if="edit.comment && comment.id==commentTemp.id">
																	 	<input type="text" v-model="commentTemp.contents"> <br>
																	 	<button  @click="updComment(comment)" v-if="comment.user.userId==userId">수정완료</button>
																	    <button  @click="edit.comment=false" v-if="comment.user.userId==userId">수정취소</button>
																	 </div>
																  </li>
																</ul>
																<button  @click="list()">돌아가기</button>
															</div>`,
												created : function(){
													this.getBoards();
													
													// 게시판 가져올때 같이 한번 가져옴. 나중에
													// 다음페이지는 따로 가져옴.
													// this.getComments();
												},
												data : function(){
													return {
														boardId : this.$route.params.boardId,
														userId : this.$root.user.userId,
														board : { board : {user : {userId:''}} },
														boardTemp : {},
														comments : [],
														comment : {},
														commentTemp : {},
														edit : {
															board : false,
															comment : false,
															commentIdx : -1
														}
													}
												},
												methods : {
													getBoards : function() {
														$.get('/api/marker/board/'+this.boardId, (board) => {
															this.board = board;
															this.boardTemp = {
																title : this.board.title,
																contents : this.board.contents,
																boardId : this.board.boardId
															}
															this.comments = board.comments;
															this.edit.board=false;
														});
													},
													getComments : function() {
														$.get('/api/marker/board/comment/'+this.boardId, (comments) => {
															this.comments = comments;
														});
													},
													regComment : function() {
														$.ajax({
															type : 'POST',
															url : '/api/marker/board/comment/' + this.boardId,
															contentType : 'application/json',
															data : JSON.stringify(this.comment),
															dataType : 'json',
															success : (comment) => {
																this.getComments();
																this.comment={};
															}
														});
													},
													showCommentEdit : function(comment,idx){
														this.edit.comment=true;
														this.commentTemp = $.extend(true, {}, comment);
														this.edit.commentIdx = idx;
													},
													updBoard : function() {
														$.ajax({
															type : 'PUT',
															url : '/api/marker/board',
															contentType : 'application/json',
															data : JSON.stringify(this.boardTemp),
															dataType : 'json',
															success : () => {
																this.getBoards();
															}
														});
													},
													delBoard : function() {
														$.ajax({
															type : 'DELETE',
															url : '/api/marker/board',
															contentType : 'application/json',
															data : JSON.stringify(this.boardTemp),
															dataType : 'json',
															success : () => {
																vm.$router.replace({name:'board'});
															}
														});
													},
													updComment : function(comment) {
														$.ajax({
															type : 'PUT',
															url : '/api/marker/comment',
															contentType : 'application/json',
															data : JSON.stringify(this.commentTemp),
															dataType : 'json',
															success : () => {
																Vue.set(comment,'contents', this.commentTemp.contents)
																this.edit.comment=false;
															}
														});
													},
													delComment : function(comment) {
														$.ajax({
															type : 'DELETE',
															url : '/api/marker/comment',
															contentType : 'application/json',
															data : JSON.stringify(comment),
															dataType : 'json',
															success : () => {
																this.comments.splice(this.comments.indexOf(comment), 1);
															}
														});
													},
													list : function(){
														vm.$router.replace({name:'board'});
													}
												}
											},
										},
										{
											path : 'board-update',
											name : 'board-update',
											component : {
												template : `<div>
																<h4>게시판 상세</h4>
																<div>
																	상세 : {{ board.contents }}
																</div>
																<hr/>
																[댓글]
																<ul>
																  <li v-for="comment in comments">
																    {{ comment.contents }}
																  </li>
																</ul>
																<button  @click="list()">돌아가기</button>
															</div>`,
												created : function(){
													this.getBoards(this.$route.params.boardId);
													
													// 게시판 가져올때 같이 한번 가져옴. 나중에
													// 다음페이지는 따로 가져옴.
													// this.getComments(this.$route.params.boardId);
												},
												data : function(){
													return {
														board : {},
														comments : []
													}
												},
												methods : {
													getBoards : function(boardId) {
														$.get('/api/marker/board/'+boardId, (board) => {
															this.board = board;
															this.comments = board.comments;
														});
													},
													getComments : function(boardId) {
														$.get('/api/marker/board/comment/'+boardId, (comments) => {
															this.comments = comments;
														});
													},
													list : function(){
														vm.$router.replace({name:'board'});
													}
												}
											},
										},
										{
											path : 'talk',
											name : 'talk',
											component : {
												template : `<div>
																<h4>대화방</h4>
																<ul>
																  <li v-for="talk in talks">
																    {{ talk.contents }}
																  </li>
																</ul>
															</div>`,
												created : function(){
													this.getTalks(this.$route.params.markerId);
												},
												data : function(){
													return {
														talks : []
													}
												},
												methods : {
													getTalks : function(markerId) {
														$.get('/api/marker/' + markerId + '/talk', (talks)=> {
															this.talks = talks;
														});
													},
													close : function(){
														vm.$router.push('/');
													}
												}
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
		toggleWriteMarker({},true);
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
	function toggleWriteMarker(e,close) {
		if (close || $writeMarker.is(':visible')){
			$writeMarker.hide();
		} else{
			vm.login(function(){
				$writeMarker.css('top', map.getSize().height / 2 - $writeMarker.height());
				$writeMarker.css('left', (map.getSize().width - $writeMarker.width()) / 2);
				$writeMarker.show();
				
				if (infoWindow.getMap()){
					infoWindow.close();
				}
			})
		}
	}

	
	/***************************************************************************
	 * 글로벌 등록
	 */
	g.vm = vm;
	g.map = map;

})(this);