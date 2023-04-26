let appHeader;
const HEADER_TID = {
    CHECK: {value: 0, name: "check", code: "S"}
};
window.addEventListener('load', function() {
    appHeader = new Vue({
        el: '#headercontentswrap',
    });
});

Vue.component('headercontents', {
    template : "#header-template",
    data: function() {
        return {
            count : 0,
            menuList: [],
            cond: {
                menuType: 'root',
            },
            menus : [
                {
                    name : "연구분석 포탈 소개", use : true, style : {display : "none"},
                    children: [
                        {name : "국토연구원 소개", uri: "/intro/dranswer", use : true},
                        {name : "사용 가이드", uri: "/intro/guide", use : true}
                    ]
                },
                {
                    name : "연구분석 데이터 관리", use : true, style : {display : "none"},
                    children: [
                        {name : "질환데이터 저장 신청", uri: "/lndata/store/main", use : true},
                        {name : "질환데이터 공개 신청", uri: "/lndata/open/main", use : true},
                        {name : "연구분석데이터 사용 신청", uri: "/lndata/use/main", use : true}
                    ]
                },
                {
                    name : "연구분석 환경 관리", use : true, style : {display : "none"},
                    children: [
                        {name : "연구분석 환경 사용 신청", uri: "/lnenv/main", use : true}
                    ]
                },
                {
                    name : "서비스 현황", use : false, style : {display : "none"},
                    children: [
                        {name : "질환 데이터 적재 현황", uri: "/status/archived/main", use : true},
                        {name : "연구분석 데이터 활용 현황", uri: "/status/supplied/main", use : true}
                    ]
                }
            ],
            anonymousTopMenus:[
                {name: "회원가입", uri:"/signup"},
                {name: "로그인", uri:"/login"}
            ],
            authenticatedTopMenus:[
                // {name: "마이페이지", uri:"/my/management/storagepage"},
                {name: "마이페이지", uri:""},
                {name: "로그아웃", uri:"/logout"}
            ],
            messages : "",
        };
    },
    mounted:function(){

        $(".header_menu").on({
            mouseenter: function () {
                //$(".nav-cont").stop().slideDown(200);
                $(".nav-cont").show();
            }, mouseleave : function(){
                $(".nav-cont").hide ();
                //$(".nav-cont").stop().slideUp(200);
            }
        });
            this.getMenuInfo();
    },
    methods:{
        hoverParent : function (menu, b){
            if (b){
                menu.style.display = "block";
            } else {
                menu.style.display = "none";
            }
        },
        onclickMenu : function(menu){
            location.href = menu.uri;
        },
        onclickStatus : function(){
            if(MY_ROLE === "ROLE_ADMIN") {
                location.href = "/service/store/status";
            } else {
                $(".nav-cont").hide ();
                alertMsg("관리자만 조회가 가능합니다.");
                return;
            }

        },
        onclickMypage : function(){         // 로그인 권한별 마이페이지
            let pageUri;
            switch (MY_ROLE){
                case "ROLE_USER":       // 기업
                    pageUri = "/my/use/list";
                    break;
                case "ROLE_ADMIN":      // 관리자
                    pageUri = "/my/admin/memberList";
                    break;
                case "ROLE_MANAGER":    // 병원책임자
                    pageUri = "/my/store/list";
                    break;
                case "ROLE_UPLOADER":   // 병원데이터업로더
                    pageUri = "/my/store/list";
                    break;
            }

            location.href = pageUri+"?menuId=0";
        },
        onclickLogout : function(){
            location.href="/logout";
        },
        getMenuInfo: function(){
            get("menuInfo","/common/menu/list",this.cond,this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case "menuInfo":
                    if (results.success) {

//                        console.log("MENU" , results.response );
                        this.menuList = results.response;
                    } else {
					

                    }
                    break;
            }
        }

    }
});

function idxSet(idx){



}