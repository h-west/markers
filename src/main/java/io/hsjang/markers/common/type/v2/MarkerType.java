package io.hsjang.markers.common.type.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MarkerType {

//@formatter:off
	MARKER_TYPE(null, "root"),
	
/* 마커 */
	MARKER(MARKER_TYPE, "마커"),
		MARKER_BOARD(MARKER, "게시판"),
		MARKER_TALK(MARKER, "대화방"),
		MARKER_SHOP(MARKER, "샵"),
	
/* 지역정보 */
	LOCAL(MARKER_TYPE, "지역정보"),
		// 건강
		LOCAL_01(LOCAL, "건강"),
			//  1. 의료기관
			LOCAL_01_01(LOCAL_01, "의료기관"),
				LOCAL_01_01_01(LOCAL_01_01, "병원"),
				LOCAL_01_01_02(LOCAL_01_01, "의원"),
				LOCAL_01_01_03(LOCAL_01_01, "부속의료기관"),
				LOCAL_01_01_04(LOCAL_01_01, "산후조리업"),
				LOCAL_01_01_05(LOCAL_01_01, "안전상비의약품 판매업소"),
				LOCAL_01_01_06(LOCAL_01_01, "약국"), 
				LOCAL_01_01_07(LOCAL_01_01, "응급환자이송업"),
				LOCAL_01_01_08(LOCAL_01_01, "의료법인"),
				LOCAL_01_01_10(LOCAL_01_01, "의료유사업"),
			// 2. 의료기기
			LOCAL_01_02(LOCAL_01, "의료기기"),
				LOCAL_01_02_01(LOCAL_01_02, "안경업"),
				LOCAL_01_02_02(LOCAL_01_02, "의료기기수리업"),
				LOCAL_01_02_03(LOCAL_01_02, "의료기기판매(임대)업"),
				LOCAL_01_02_04(LOCAL_01_02, "치과기공소"),
		
		// 동물
		LOCAL_02(LOCAL, "동물"),
			//  3. 동물
			LOCAL_02_03(LOCAL_02, "동물"),
				LOCAL_02_03_01(LOCAL_02_03, "동물병원"),
				LOCAL_02_03_02(LOCAL_02_03, "동물약국"),
				LOCAL_02_03_03(LOCAL_02_03, "동물용의료용구판매업"),
				LOCAL_02_03_04(LOCAL_02_03, "동물용의약품도매상"),
				LOCAL_02_03_05(LOCAL_02_03, "동물장묘업"),
				LOCAL_02_03_06(LOCAL_02_03, "동물판매업"),
			//  4. 축산
			LOCAL_02_04(LOCAL_02, "축산"),
				LOCAL_02_04_01(LOCAL_02_04, "가축사육업"),
				LOCAL_02_04_02(LOCAL_02_04, "가축인공수정소"),
				LOCAL_02_04_03(LOCAL_02_04, "도축업"),
				LOCAL_02_04_04(LOCAL_02_04, "부화업"),
				LOCAL_02_04_05(LOCAL_02_04, "사료제조업"),
				LOCAL_02_04_06(LOCAL_02_04, "종축업"),
		
		// 문화
		LOCAL_03(LOCAL, "문화"),
			//  5. 게임
			LOCAL_03_05(LOCAL_03, "게임"),
				LOCAL_03_05_01(LOCAL_03_05, "게임물배급업"),
				LOCAL_03_05_02(LOCAL_03_05, "게임물제작업"),
				LOCAL_03_05_03(LOCAL_03_05, "복합영상물제공업"),
				LOCAL_03_05_04(LOCAL_03_05, "복합유통게임제공업"),
				LOCAL_03_05_05(LOCAL_03_05, "인터넷컴퓨터게임시설제공업"),
				LOCAL_03_05_06(LOCAL_03_05, "일반게임제공업"),
				LOCAL_03_05_07(LOCAL_03_05, "청소년게임제공업"),
			//  6. 공연
			LOCAL_03_06(LOCAL_03, "공연"),
				LOCAL_03_06_01(LOCAL_03_06, "공연장"),
				LOCAL_03_06_02(LOCAL_03_06, "관광공연장업"),
				LOCAL_03_06_03(LOCAL_03_06, "관광극장유흥업"),
			//  7. 관광
			LOCAL_03_07(LOCAL_03, "관광"),
				LOCAL_03_07_01(LOCAL_03_07, "관광궤도업"),
				LOCAL_03_07_02(LOCAL_03_07, "관광사업자"),
				LOCAL_03_07_03(LOCAL_03_07, "관광유람선업"),
				LOCAL_03_07_04(LOCAL_03_07, "국제회의시설업"),
				LOCAL_03_07_05(LOCAL_03_07, "박물관, 미술관"),
				LOCAL_03_07_06(LOCAL_03_07, "시내순환관광업"),
				LOCAL_03_07_08(LOCAL_03_07, "유원시설업(기타)"),
				LOCAL_03_07_09(LOCAL_03_07, "일반유원시설업"),
				LOCAL_03_07_10(LOCAL_03_07, "전문휴양업"),
				LOCAL_03_07_11(LOCAL_03_07, "전통사찰"),
				LOCAL_03_07_12(LOCAL_03_07, "종합유원시설업"),
				LOCAL_03_07_13(LOCAL_03_07, "종합휴양업"),
				LOCAL_03_07_14(LOCAL_03_07, "지방문화원"),
			//  8. 문화기획
			LOCAL_03_08(LOCAL_03, "문화기획"),	
				LOCAL_03_08_01(LOCAL_03_08, "국제회의기획업"),
				LOCAL_03_08_02(LOCAL_03_08, "대중문화예술기획업"),
				LOCAL_03_08_03(LOCAL_03_08, "문화예술법인"),
			//  9. 노래방
			LOCAL_03_09(LOCAL_03, "노래방"),	
				LOCAL_03_09_01(LOCAL_03_09, "노래연습장업"),
			//  10. 비디오
			LOCAL_03_10(LOCAL_03, "비디오"),	
				LOCAL_03_10_01(LOCAL_03_10, "비디오물감상실업"),
				LOCAL_03_10_02(LOCAL_03_10, "비디오물배급업"),
				LOCAL_03_10_03(LOCAL_03_10, "비디오물소극장업"),
				LOCAL_03_10_04(LOCAL_03_10, "비디오물시청제공업"),
				LOCAL_03_10_05(LOCAL_03_10, "미디오물제작업"),
			//  11. 숙박
			LOCAL_03_11(LOCAL_03, "숙박"),	
				LOCAL_03_11_01(LOCAL_03_11, "관광숙박업"),
				LOCAL_03_11_02(LOCAL_03_11, "관광펜션업"),
				LOCAL_03_11_03(LOCAL_03_11, "숙박업"),
				LOCAL_03_11_04(LOCAL_03_11, "외국인관광도시민박업"),
				LOCAL_03_11_05(LOCAL_03_11, "자동차야영장업"),
				LOCAL_03_11_06(LOCAL_03_11, "한옥체험업"),
			//  12. 여행
			LOCAL_03_12(LOCAL_03, "여행"),	
				LOCAL_03_12_01(LOCAL_03_12, "국내여행업"),
				LOCAL_03_12_02(LOCAL_03_12, "국외여행업"),
				LOCAL_03_12_03(LOCAL_03_12, "일반여행업"),
			//  13. 영화
			LOCAL_03_13(LOCAL_03, "영화"),	
				LOCAL_03_13_01(LOCAL_03_13, "영화배급업"),
				LOCAL_03_13_02(LOCAL_03_13, "영화상영관"),
				LOCAL_03_13_03(LOCAL_03_13, "영화상영업"),
				LOCAL_03_13_04(LOCAL_03_13, "영화수입업"),
				LOCAL_03_13_05(LOCAL_03_13, "영화제작업"),
			//  14. 음악
			LOCAL_03_14(LOCAL_03, "음악"),	
				LOCAL_03_14_01(LOCAL_03_14, "온라인음악스비스제공업"),
				LOCAL_03_14_02(LOCAL_03_14, "음반.음악영상물배급업"),
				LOCAL_03_14_03(LOCAL_03_14, "음반.음악영상물제작업"),
				LOCAL_03_14_04(LOCAL_03_14, "음반물배급업"),
				LOCAL_03_14_05(LOCAL_03_14, "음반물제작업"),
	
		// 식품
		LOCAL_07(LOCAL, "식품"),	
			//  21. 급식
			LOCAL_07_21(LOCAL_07, "급식"),	
				LOCAL_07_21_01(LOCAL_07_21, "위탁급식영업"),
				LOCAL_07_21_02(LOCAL_07_21, "집단급식소"),
			//  22. 식품 제조/가공/판매
			LOCAL_07_22(LOCAL_07, "식품제조/가공/판매"),	
				LOCAL_07_22_01(LOCAL_07_22, "집단급식소식품판매업"),
				LOCAL_07_22_02(LOCAL_07_22, "건강기능식품유통전문판매업"),
				LOCAL_07_22_03(LOCAL_07_22, "건강기능식품일반판매업"),
				LOCAL_07_22_04(LOCAL_07_22, "축산판매업"),
				LOCAL_07_22_05(LOCAL_07_22, "축산가공업"),
				LOCAL_07_22_06(LOCAL_07_22, "식육포장처리업"),
				LOCAL_07_22_07(LOCAL_07_22, "식품냉동냉장업"),
				LOCAL_07_22_08(LOCAL_07_22, "식품소분업"),
				LOCAL_07_22_09(LOCAL_07_22, "식품운반업"),
				LOCAL_07_22_10(LOCAL_07_22, "식품자동판매기업"),
				LOCAL_07_22_11(LOCAL_07_22, "식품제조가공업"),
				LOCAL_07_22_12(LOCAL_07_22, "식품첨가물제조업"),
				LOCAL_07_22_13(LOCAL_07_22, "식품판매업(기타)"),
				LOCAL_07_22_14(LOCAL_07_22, "옹기류제조업"),
				LOCAL_07_22_15(LOCAL_07_22, "용기·포장지제조업"),
				LOCAL_07_22_16(LOCAL_07_22, "용기냉동기특정설비"),
				LOCAL_07_22_17(LOCAL_07_22, "유통전문판매업"),
				LOCAL_07_22_18(LOCAL_07_22, "제과점영업"),
				LOCAL_07_22_19(LOCAL_07_22, "즉석판매제조가공업"),
				LOCAL_07_22_20(LOCAL_07_22, "집유업"),
				LOCAL_07_22_21(LOCAL_07_22, "식용얼음판매업"),
				LOCAL_07_22_24(LOCAL_07_22, "축산물보관업"),
				LOCAL_07_22_25(LOCAL_07_22, "축산물운반업"),
			//  23. 유흥주점/단란주점
			LOCAL_07_23(LOCAL_07, "유흥주점/단란주점"),	
				LOCAL_07_23_01(LOCAL_07_23, "단란주점영업"),
				LOCAL_07_23_02(LOCAL_07_23, "유흥주점영업"),
			//  24. 음식점
			LOCAL_07_24(LOCAL_07, "음식점"),
			LOCAL_07_24_01(LOCAL_07_24, "관광식당"),
			LOCAL_07_24_02(LOCAL_07_24, "관광유흥음식점업"),
			LOCAL_07_24_03(LOCAL_07_24, "외국인전용유흥음식점업"),
			LOCAL_07_24_04(LOCAL_07_24, "일반음식점"),
			LOCAL_07_24_05(LOCAL_07_24, "휴게음식점"),
		
		// 0. 생활
		LOCAL_05(LOCAL, "생활"),	
			//  18. 미용
			LOCAL_05_18(LOCAL_05, "미용"),	
				LOCAL_05_18_01(LOCAL_05_18, "미용업"),
			//  19. 이용
			LOCAL_05_19(LOCAL_05, "이용"),
				LOCAL_05_19_01(LOCAL_05_19, "이용업"),
			//  20. 세탁소/빨래방
			LOCAL_05_20(LOCAL_05, "세탁소/빨래방"),
				LOCAL_05_20_01(LOCAL_05_20, "세탁업"),
				LOCAL_05_20_02(LOCAL_05_20, "의료기관세탁물처리업"),
			//  25,26. 유통
			LOCAL_08(LOCAL_05, "유통"),
				LOCAL_08_25_01(LOCAL_08, "대규모점포"),
				LOCAL_08_26_01(LOCAL_08, "다단계판매업체"),
				LOCAL_08_26_02(LOCAL_08, "방문판매업"),
				LOCAL_08_26_03(LOCAL_08, "전화권유판매업"),
				LOCAL_08_26_04(LOCAL_08, "통신판매업"),
				LOCAL_08_26_05(LOCAL_08, "후원방문판매업체"),
			//  31-42. 체육
			LOCAL_10(LOCAL_05, "체육"),
				LOCAL_10_31_01(LOCAL_10, "골프연습장업"),
				LOCAL_10_31_02(LOCAL_10, "골프장"),
				LOCAL_10_31_03(LOCAL_10, "등록체육시설업"),
				LOCAL_10_32_01(LOCAL_10, "당구장업"),
				LOCAL_10_33_01(LOCAL_10, "무도장업"),
				LOCAL_10_33_02(LOCAL_10, "무도학원업"),
				LOCAL_10_34_01(LOCAL_10, "빙상장업"),
				LOCAL_10_35_01(LOCAL_10, "수영장업"),
				LOCAL_10_36_01(LOCAL_10, "스키장"),
				LOCAL_10_37_01(LOCAL_10, "종합체육시설업"),
				LOCAL_10_38_01(LOCAL_10, "승마장업"),
				LOCAL_10_39_01(LOCAL_10, "썰매장업"),
				LOCAL_10_40_01(LOCAL_10, "요트장업"),
				LOCAL_10_41_01(LOCAL_10, "체육도장업"),
				LOCAL_10_42_01(LOCAL_10, "체력단련장업"),
			//  44. 목욕탕/찜질방/사우나
			LOCAL_11_44(LOCAL_05, "목욕탕/찜질방/사우나"),
				LOCAL_11_44_01(LOCAL_11_44, "목욕장업"),
			
		// 자원환경
		LOCAL_09(LOCAL, "자원환경"),
			//  27. 목재
			LOCAL_09_27(LOCAL_09, "목재"),
				LOCAL_09_27_01(LOCAL_09_27, "목재수입유통업"),
				LOCAL_09_27_02(LOCAL_09_27, "원목생산업"),
				LOCAL_09_27_03(LOCAL_09_27, "제재업"),
			//  28. 에너지
			LOCAL_09_28(LOCAL_09, "에너지"),
				LOCAL_09_28_01(LOCAL_09_28, "계량기수리업"),
				LOCAL_09_28_02(LOCAL_09_28, "계량기수입업"),
				LOCAL_09_28_03(LOCAL_09_28, "계량기제조업"),
				LOCAL_09_28_04(LOCAL_09_28, "계량기증명업"),
				LOCAL_09_28_05(LOCAL_09_28, "고압가스업"),
				LOCAL_09_28_06(LOCAL_09_28, "석연탄제조업"),
				LOCAL_09_28_07(LOCAL_09_28, "석유및석유대체연료판매업체"),
				LOCAL_09_28_08(LOCAL_09_28, "석유판매업"),
				LOCAL_09_28_09(LOCAL_09_28, "액화석유가스용품제조업체"),
				LOCAL_09_28_10(LOCAL_09_28, "일반도시가스업체"),
				LOCAL_09_28_11(LOCAL_09_28, "전기사업업체"),
				LOCAL_09_28_12(LOCAL_09_28, "전력기술감리업체"),
				LOCAL_09_28_13(LOCAL_09_28, "전력기술설계업체"),
				LOCAL_09_28_14(LOCAL_09_28, "특정고압가스업"),
			//  29. 지하수
			LOCAL_09_29(LOCAL_09, "지하수"),
				LOCAL_09_29_01(LOCAL_09_29, "지하수시공업체"),
				LOCAL_09_29_02(LOCAL_09_29, "지하수영향조사기관"),
				LOCAL_09_29_03(LOCAL_09_29, "지하수정화업체"),
			//  6-4. 환경관리
			LOCAL_09_30(LOCAL_09, "환경관리"),
				LOCAL_09_30_01(LOCAL_09_30, "가축분뇨 수집운반업"),
				LOCAL_09_30_02(LOCAL_09_30, "가축분뇨배출시설관리업(사업장)"),
				LOCAL_09_30_03(LOCAL_09_30, "개인하수처리시설관리업(사업장)"),
				LOCAL_09_30_04(LOCAL_09_30, "건물위생관리업"),
				LOCAL_09_30_05(LOCAL_09_30, "건설폐기물처리업"),
				LOCAL_09_30_06(LOCAL_09_30, "급수공사대행업"),
				LOCAL_09_30_07(LOCAL_09_30, "단독정화조오수처리시설설계시공업"),
				LOCAL_09_30_08(LOCAL_09_30, "대기오염물질배출시설설치사업장"),
				LOCAL_09_30_09(LOCAL_09_30, "배출가스전문정비사업자"),
				LOCAL_09_30_10(LOCAL_09_30, "분뇨수집운반업"),
				LOCAL_09_30_11(LOCAL_09_30, "소독업"),
				LOCAL_09_30_12(LOCAL_09_30, "수질오염원설치시설(기타)"),
				LOCAL_09_30_13(LOCAL_09_30, "쓰레기종량제봉투판매업"),
				LOCAL_09_30_14(LOCAL_09_30, "저수조청소업"),
				LOCAL_09_30_15(LOCAL_09_30, "환경관리대행기관"),
				LOCAL_09_30_16(LOCAL_09_30, "환경전문공사업"),
				LOCAL_09_30_17(LOCAL_09_30, "환경측정대행업"),
				LOCAL_09_30_18(LOCAL_09_30, "환경컨설팅회사"),
		
		// 기타
		LOCAL_11(LOCAL, "기타"),
			// 15,16,17. 미디어
			LOCAL_04(LOCAL_11, "미디어"),
				LOCAL_04_15_01(LOCAL_04, "옥외광고업"),
				LOCAL_04_16_01(LOCAL_04, "인쇄사"),
				LOCAL_04_17_01(LOCAL_04, "출판사"),
			//  43. 담배
			LOCAL_11_43(LOCAL_11, "담배"),
				LOCAL_11_43_01(LOCAL_11_43, "담배도매업"),
				LOCAL_11_43_02(LOCAL_11_43, "담배소매업"),
				LOCAL_11_43_03(LOCAL_11_43, "담배수입판매업체"),
			//  45. 물류
			LOCAL_11_45(LOCAL_11, "물류"),
				LOCAL_11_45_01(LOCAL_11_45, "물류주선업(국제)"),
				LOCAL_11_45_02(LOCAL_11_45, "물류창고업체"),
			//  46. 민방위
			LOCAL_11_46(LOCAL_11, "민방위"),
				LOCAL_11_46_01(LOCAL_11_46, "민방위급수시설"),
				LOCAL_11_46_02(LOCAL_11_46, "민방위대피시설"),
			//  47. 상조
			LOCAL_11_47(LOCAL_11, "상조"),
				LOCAL_11_47_01(LOCAL_11_47, "상조업"),
			//  48. 엘리베이터
			LOCAL_11_48(LOCAL_11, "엘리베이터"),
				LOCAL_11_48_01(LOCAL_11_48, "승강기유지관리업체"),
				LOCAL_11_48_02(LOCAL_11_48, "승강기제조및수입업체"),
			//  49. 전문교육기관
			LOCAL_11_49(LOCAL_11, "전문교육기관"),
				LOCAL_11_49_01(LOCAL_11_49, "요양보호사교육기관"),
				LOCAL_11_49_02(LOCAL_11_49, "장례지도사 교육기관"),
			//  50. 직업소개소
			LOCAL_11_50(LOCAL_11, "직업소개소"),
				LOCAL_11_50_01(LOCAL_11_50, "무료직업소개소"),
				LOCAL_11_50_02(LOCAL_11_50, "유료직업소개소"),
		
	DEFAULT(null, "기본");
//@formatter:off
	
	static Map<String,MarkerType> typeMap = new HashMap<String, MarkerType>();
	static {
		Arrays.stream(values()).forEach((t)->{
			typeMap.put(t.name(), t);
		});
		
		//Arrays.stream(values()).filter((t)->t.getParent()!=null && this == t.getParent()).forEach((t)->children.add(t));
	}
	public static MarkerType getType(String type) {
		return typeMap.containsKey(type)? typeMap.get(type): MarkerType.DEFAULT;
	}
	
	//String icon = "./images/icon/MAKER.svg";
	MarkerType parent;
	List<MarkerType> children = new ArrayList<MarkerType>();
	String markerName;
	MarkerType(MarkerType parent, String markerName) {
		this.parent = parent;
		this.markerName = markerName;
		if(parent!=null){
			parent.addChilren(this);
		}
	}
	
	public MarkerType getParent() {
		return parent;
	}
	
	public List<MarkerType> getChildren() {
		return children;
	}
	
	public String getMarkerName() {
		return markerName;
	}
	
	public void addChilren(MarkerType mt) {
		children.add(mt);
	}
	
	public MarkerType getNode1() {
		MarkerType parent = getParent(); 
		while(parent.getParent()!=null) {
			parent = parent.getParent();
		}
		return parent;
	}
}
