
let appMain;
const TID = {
    SEARCH      : {value: 0, name: "search", code: "S"}
};
window.onload = function(){
    appMain = new Vue({
        el: '#maincontentswrap',
    });
}

Vue.component('maincontents', {
    template : "#main-template",
    data: function() {
        return {
            cond: {
                page: 1,
                size: 5,
                farmNm: "",
                exnmInstNm: "",
                sort: ""
            },
            farmList: [],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1],
                total: 1
            },
            messages : "",
            //reqStoreStatCd : "", // 선택된 콤보박스
/*            reqStoreStatCdList : getCodeList('STRGE_REQ_STTUS_CD',this.callback), // 콤보박스 리스트
*/
        };
    },
    mounted:function(){
        this.getFarmList();

    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            
            //this.cond.strgeReqSttusCd = this.$refs.strgeReqSttusCd.value;//처리상태
            this.getFarmList();
        },
        // 목록 > 신청 클릭(화면 이동)
        onclickReg: function () {
            location.href = "/farm/reg";
            /*if (MY_ROLE === "ROLE_MANAGER") { // 병원-질병책임자
                location.href = "/farm/list/reg";
            } else { // USER(기업), UPLOADER(병원-업로더), ADMIN(관리자)
                alertMsg("미지원.");
                return;
            }*/
        },

        getFarmList:function () {
            get(TID.SEARCH,
                "/farm/list/main",
                this.cond,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                /*case "STRGE_REQ_STTUS_CD":
                    //console.log(results.response);
                    this.reqStoreStatCdList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;*/
/*
                case "usrInfo":
                    if (results.success) {
                        let userRole = results.response.userRole;
                        if (userRole === "MANAGER") { // 병원-질병책임자
                            location.href = "/lndata/store/req";
                        } else { // USER(기업), UPLOADER(병원-업로더), ADMIN(관리자)
                            alertMsg("병원 소속 질병책임자만 신청이 가능합니다.");
                            return;
                        }
                    } else {
                        //console.log(results);
                        confirmMsg("로그인 후 이용 가능합니다.\n로그인 페이지로 이동하시겠습니까?", this.login);
                    }
                    break;*/
            }
        },
        login: function() {
            location.href = "/login";
        },
        searchCallback: function (results) {
            if (results.success) {
                //console.log(results);
                this.makePageNavi(results.response);
                this.farmList = results.response.list;

            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable) {

            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;
            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;

            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            console.log(pageable.total);
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            //this.total = Math.ceil(total / pageable.size);
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page;
                this.pageInfo.curr = page;
                this.getfarmList();
            }
        },
    }
});

