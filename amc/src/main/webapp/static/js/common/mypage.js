let appMypage;
let myMenuId;
//window.onload = function(){
window.addEventListener('load', function() {
        appMypage = new Vue({
            el: '#mypagemenuswrap',
        });
});

Vue.component('mypagemenus', {
    template : "#mypage-template",
    data: function() {
        let menuList = {
            menus_manager : [ // 병원
                {name: "저장신청 데이터 보기", uri: "/my/store/list", use: true},
                {name: "질환데이터 업로드", uri: "/my/diseaseUpload", use: true},
                {name: "공개신청 데이터 보기", uri:"/my/open/list", use : true},
                {name: "기업요청 데이터 보기", uri:"/my/use/list", use : true},
                {name: "데이터 반출신청 보기", uri:"/my/export/list", use : true},
                {name: "회원정보변경", uri:"/my/userModify", use : true},
                ],
            menus_admin : [ //관리자
                {name: "회원관리", uri: "/my/admin/memberList", use: true},
                {name: "기관관리", uri: "/my/agency", use: true},
                {name: "저장신청관리", uri: "/my/admin/store/list", use: true},
                {name: "공개신청 관리", uri:"/my/admin/open/list", use : true},
                {name: "연구분석데이터신청 관리", uri:"/my/use/list", use : true},
                {name: "데이터박스프레임 관리", uri:"/my/databoxFrame/list", use : true},
               {name: "데이터박스신청 관리", uri:"/my/databox/list", use : true},
               {name: "반출신청 관리", uri:"/my/export/list", use : true},
               {name: "과금 보기", uri:"/my/admin/demand/list", use : true},
                {name: "회원정보변경", uri:"/my/userModify", use : true},
                {name: "저장소관리", uri:"/my/storeMng/list", use : true},
                {name: "코드관리", uri:"/my/code/list", use: true},
                {name: "메뉴관리", uri:"/my/menu/list", use: true},
                {name: "메뉴권한관리", uri:"/my/menu/auth/list", use: true},
            ],
            menus_uploader : [ // 업로더
                {name: "저장신청 데이터 보기", uri: "/my/store/list", use: true},
                {name: "질환데이터 업로드", uri: "/my/diseaseUpload", use: true},
                {name: "공개신청 데이터 보기", uri:"/my/open/list", use : true},
                {name: "회원정보변경", uri:"/my/userModify", use : true},
            ],
            menus_user : [ // 기업
                {name: "데이터 사용신청 보기", uri: "/my/use/list", use: true},
               {name: "데이터박스 신청 보기", uri:"/my/databox/list", use : true},
               {name: "데이터 반출신청 보기", uri:"/my/export/list", use : true},
               {name: "과금 보기", uri:"/my/demand/list", use : true},
                {name: "회원정보변경", uri:"/my/userModify", use : true},
            ],
            messages : "",
            menuId:myMenuId,
            currentPage: {}
        };
        
        // 현재 페이지 정보 추가
        let roleArray = [
        	{name: 'ROLE_MANAGER', field: 'menus_manager'},
        	{name: 'ROLE_UPLOADER', field: 'menus_uploader'},
        	{name: 'ROLE_ADMIN', field: 'menus_admin'},
        	{name: 'ROLE_USER', field: 'menus_user'}
        ];
        
        // 권한에 따른 마이페이지 메뉴 변수 설정
        let menus = roleArray.filter((item) => item.name === role );
        
        // 메뉴 번호에 따른 현재 메뉴 정보 가져오기
        let currentPage =  (menus.length > 0 ) ? menuList[menus[0].field][myMenuId ? myMenuId : 0] : {};
                
        menuList.currentPage = currentPage ? currentPage : {}; 
               
        return menuList;

    },
    mounted:function(){
    },
    methods:{
        onclickMenu : function(menu,key){
            if(menu.use){
                location.href = menu.uri+"?menuId="+key;
            }
        },
    }
});

