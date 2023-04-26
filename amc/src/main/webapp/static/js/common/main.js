let appMain;
const TID = {
    MAIN: {value: 0, name: "main", code: "S"}
};
const hospitalList1 = [
   /* {url:"/images/main/ho_logo01.png", name: "서울아산병원", link:"http://www.amc.seoul.kr"},
    {url:"/images/main/ho_logo02.png", name: "아주대학교의료원", link:"http://www.ajoumc.or.kr"},
    {url:"/images/main/ho_logo03.png", name: "영남대학교의료원", link:"http://yumc.ac.kr:8443"},
    {url:"/images/main/ho_logo04.png", name: "이대목동병원", link:"http://mokdong.eumc.ac.kr"},
    {url:"/images/main/ho_logo05.png", name: "경북대학교병원", link:"http://knuh.kr"},
    {url:"/images/main/ho_logo06.png", name: "고려대학교 안산병원", link:"http://ansan.kumc.or.kr"},
    {url:"/images/main/ho_logo07.png", name: "고려대학교 구로병원", link:"http://guro.kumc.or.kr"},
    {url:"/images/main/ho_logo08.png", name: "국립암센터", link:"http://www.ncc.re.kr"},
    {url:"/images/main/ho_logo09.png", name: "부산대병원", link:"http://www.pnuh.or.kr"},
    {url:"/images/main/ho_logo10.png", name: "부천성모병원", link:"http://www.cmcbucheon.or.kr"},
    {url:"/images/main/ho_logo11.png", name: "삼성서울병원", link:"http://www.samsunghospital.com"},
    {url:"/images/main/ho_logo12.png", name: "서울대학교병원", link:"http://www.snuh.org"},*/
];

const hospitalList2 = [
    /*{url:"/images/main/ho_logo13.png", name: "분당서울대학교병원", link:"http://www.snubh.org"},
    {url:"/images/main/ho_logo14.png", name: "가천대길병원", link:"http://www.gilhospital.com"},
    {url:"/images/main/ho_logo15.png", name: "가톨릭대학교서울성모병원", link:"http://www.cmcseoul.or.kr"},
    {url:"/images/main/ho_logo16.png", name: "건국대학교병원", link:"http://www.kuh.ac.kr"},
    {url:"/images/main/ho_logo17.png", name: "일산백병원", link:"http://www.paik.ac.kr/ilsan"},
    {url:"/images/main/ho_logo18.png", name: "전남대학교병원", link:"http://www.cnuh.com"},
    {url:"/images/main/ho_logo19.png", name: "청주성모병원", link:"http://www.ccmc.or.kr"},
    {url:"/images/main/ho_logo20.png", name: "충북대병원", link:"http://www.cbnuh.or.kr"},*/
];


window.onload = function () {
    appMain = new Vue({
        el: '#maincontentswrap',
    });
    /*
    // main visual
	var swiper1 = new Swiper('.main-page .main-visual .swiper-container', {
	    slidesPerView: 1,
	    spaceBetween: 0,
	    autoplay: {
	        delay: 5000,
	        disableOnInteraction: true
	    },
	    loop:true,
	    navigation: {
	        nextEl: ".swiper-button-next",
    	    prevEl: ".swiper-button-prev",
      	},	    
	    pagination: {
        	el: ".swiper-pagination",
        	type: "fraction"
        },
	});
	
	// 질환 카테고리
	var swiper2 = new Swiper('.main-page .main-section02 .swiper-container', {
	    slidesPerView: 6,
	    spaceBetween: 0,
	    autoplay: {
	        delay: 5000,
	        disableOnInteraction: false
	    },
	    loop:true,
	    navigation: {
	        nextEl: ".swiper-button-next",
    	    prevEl: ".swiper-button-prev",
      	},
	});  	
	
	// 최신 질환 데이터
	var swiper3 = new Swiper('.main-page .main-section03 .swiper-container', {
	    slidesPerView: 3,
	    spaceBetween: 0,
	    autoplay: {
	        delay: 5000,
	        disableOnInteraction: false
	    },
	    loop:true,
	    navigation: {
	        nextEl: ".swiper-button-next",
    	    prevEl: ".swiper-button-prev",
      	},
      	observer: true,
		observeParents: true,
	}); 	
	
	setTimeout(()=>{
		swiper3.update();
		swiper3.loopCreate();
	}, 2000);   		
	

	// 참여 기관
	var swiper4 = new Swiper('.main-page .main-section05 .swiper-container', {
	    slidesPerView: 1,
	    spaceBetween: 0,
	    autoplay: {
	        delay: 5000,
	        disableOnInteraction: false
	    },
	    loop:true,
	    pagination: {
        	el: ".swiper-pagination",
        	clickable: true,
        },
	}); 	    
    // main slide pause, play
    $('.main-page .main-visual .pause-play').click(function(){
    	if ($('.main-page .main-visual .pause-play').hasClass('pause')) {
    		swiper1.autoplay.start();
    		$('.main-page .main-visual .pause-play').removeClass('pause');
    	} else {
    		swiper1.autoplay.stop();
    		$('.main-page .main-visual .pause-play').addClass('pause');
    	}
    });
    */
}

Vue.component('maincontents', {
    template: "#main-template",
    data: function () {
        return {
            cond: {
                page: 1,
                size: 5,
                othbcReqstSttuscd: "O_ACC",
                sort: ""
            },
            messages: "",
            noticeList: [],
            faqList:[],
            openDataList: [],
            hospitalList1: hospitalList1,
            hospitalList2: hospitalList2,

            topCate1: true,
            topCate2: false,
            btmCate1: true,
            btmCate2: false,
        };
    },
    mounted: function () {
        //this.loadHospital();
        this.loadOpenData();
        this.loadNoticeData();
		this.loadFaqData();
    },
    methods: {

        onclickCate: function (cate) {

            if ("topCate1" === cate) {
                this.topCate1 = true;
                this.topCate2 = false;
            } else if ("topCate2" === cate) {
                this.topCate1 = false;
                this.topCate2 = true;
            } else if ("btmCate1" === cate) {
                this.btmCate1 = true;
                this.btmCate2 = false;
            } else if ("btmCate2" === cate) {
                this.btmCate1 = false;
                this.btmCate2 = true;
            }

        },
        loadHospital: function () {
            let param = {url: "/images/icon_noimage.png", name: ""};
            for (let i = 0; i < 20; i++) {
                if (this.hospitalList1.length <= i) {
                    this.hospitalList1.push(param);
                }
            }

            for (let i = 0; i < 20; i++) {
                if (this.hospitalList2.length <= i) {
                    this.hospitalList2.push(param);
                }
            }
        },
        loadOpenData: function () {
			this.cond.size = 4;
            get(TID.SEARCH,
                "/storage/open/list",
                this.cond,
                this.openDataCallBack);
        },
        openDataCallBack: function (tid, results) {

            if (results.success) {
                let data = results.response.list;
                let list = [];


                for (let i = 0; i < data.length; i++) {

                    let code = data[i].dissCd;
                    let obj = {}
                    obj.id = data[i].othbcReqstId;
                    obj.name = data[i].insttNm;
                    obj.code = data[i].dissNm;
                    obj.img = "/images/icon_" + code + ".png";
                    obj.title = data[i].othbcDataNm;
                    list.push(obj);
                }
                this.openDataList = list;
                this.loadScroller(list);
            } else {
                console.log(results);
            }

        },
        loadNoticeData: function () {
            this.cond.size = 3;
            get(TID.SEARCH,
                "/board/notice/list",
                this.cond,
                this.noticeDataCallBack);
        },
        loadFaqData: function (){
        	this.cond.size = 3;
        	get(TID.FAQ,
        		"/board/faq/list",
        		this.cond,
        		this.faqDataCallBack);
        },
        noticeDataCallBack: function (tid, results) {
            if (results.success) {
                this.noticeList = results.response.list;
            } else {
                console.log(results);
            }
        },
        faqDataCallBack : function (tid, results){
        	if(results.success){
        		this.faqList = results.response.list;
        	} else {
        		console.log(results);
        	}
        },
        // 공지사항(상세보기)
        onclickView: function (noticeNo) {
            location.href = "/board/notice/view?noticeNo="+noticeNo;
        },
        onclickFaqView: function(questNo){
        	location.href = "/board/faq/view?questNo="+questNo;
        },
        loadScroller: function (list) {

            setTimeout(function(){

                $("#data ul").carouFredSel({
                    align: "left",
                    width: 580, // 가로길이
                    height: 200, // 세로길이
                    items: {
                        visible: list.length-1 // 보여지는 갯수 (5개가 보여진다면 +1을 하여 버블링 효과를 막는다.)
                    },
                    scroll: {
                        items: 1, // 롤링넘어가는 갯수
                        duration: 500, //롤링 속도
                        pauseOnHover: false // 마우스 오버시 롤링멈춤 true, 롤링작동 false
                    },
                    next: "#data_right", // 오른쪽으로 이동 버튼
                    prev: "#data_left", // 왼쪽으로 이동 버튼
                    direction: "left" // 롤링 방향
                });

            }, 500);

        },
        onclickLinkPage: function(url){
            window.open(url);
        }
    }
});
