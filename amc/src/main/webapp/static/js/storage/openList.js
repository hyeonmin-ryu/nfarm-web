
/*
 * @name : openList.js
 * @date : 2021-07-02 오후 1:05
 * @author : lsj
 * @version : 1.0.0
 * @modifyed :
 */

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
                othbcDataNm: "",
                //othbcReqstSttuscd: othbcReqstSttuscd,
                othbcReqstSttuscd: "",
                sort: ""
            },
            openStorageList: [],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            messages : "",
            //othbcReqstSttuscd : "", // 선택된 콤보박스
            openStorageStatCdList : getCodeList('OTHBC_REQST_STTUSCD',this.callback), // 상태콤보박스 리스트
            //isYn : getCodeList('IsYn',this.callback)

        };
    },
    mounted:function(){
        this.getOpenStorageList();
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            //this.cond.othbcReqstSttuscd = this.othbcReqstSttuscd;
            this.cond.othbcReqstSttuscd = this.$refs.openStorageStatCd.value;//처리상태
            this.getOpenStorageList();
        },
        // 목록 > 신청 클릭(화면 이동)
        onclickReq: function () {
            if (MY_ROLE === "ROLE_MANAGER") { // 병원-질병책임자
                location.href = "/lndata/open/req";
            } else { // USER(기업), UPLOADER(병원-업로더), ADMIN(관리자)
                alertMsg("병원 소속 질병책임자만 신청이 가능합니다.");
                return;
            }

            getUserInfo("usrInfo", this.callback);
        },

        getOpenStorageList:function () {
            get(TID.SEARCH,
                "/storage/open/list",
                this.cond,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "OTHBC_REQST_STTUSCD":
                    this.openStorageStatCdList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;
                case "usrInfo":
                    // if (results.success) {
                    //     let userRole = results.response.userRole;
                    //     if (userRole === "MANAGER") { // 병원-질병책임자
                    //         location.href = "/lndata/open/req";
                    //     } else { // USER(기업), UPLOADER(병원-업로더), ADMIN(관리자)
                    //         alertMsg("병원 소속 질병책임자만 신청이 가능합니다.");
                    //         return;
                    //     }
                    // } else {
                    //     //console.log(results);
                    //     confirmMsg("로그인 후 이용 가능합니다.\n로그인 페이지로 이동하시겠습니까?", this.login);
                    // }
                    break;
            }
        },
        login: function() {
            location.href = "/login";
        },
        searchCallback: function (results) {
            if (results.success) {
                console.log(results.response.list);
                this.makePageNavi(results.response);
                this.openStorageList = results.response.list;
            } else {
                //console.log(results);
                alertMsg(results.error.message);
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
                this.getOpenStorageList();
            }
        },
    }
});

