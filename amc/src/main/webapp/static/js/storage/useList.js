
/*
 * @name : useList.js
 * @date : 2021-07-28 오후 4:12
 * @author : lsj
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
let loading = true;
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
                dissCd: "",
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
            diseaseCdList : getCodeList('DISS_CD',this.callback) // 질환콤보박스 리스트

        };
    },
    mounted:function(){
        this.getUseStorageList();
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.cond.dissCd = this.$refs.selectedDisease.value;
            this.getUseStorageList();
        },
        // 목록 클릭(화면 이동)
        onclickReq: function (othbcReqstId) {
            this.othbcReqstId = othbcReqstId;

                if (MY_ROLE === "ROLE_USER") { // 기업병원-질병책임자
                    location.href = "/lndata/use/req?othbcReqstId="+this.othbcReqstId;
                } else { // MANAGER(병원-질병책임자), UPLOADER(병원-업로더), ADMIN(관리자)
                    alertMsg("기업 사용자만 신청이 가능합니다.");
                    return;
                }
            // getUserInfo("usrInfo", this.callback);
        },

        getUseStorageList:function () {
            get(TID.SEARCH,
                "/storage/use/list",
                this.cond,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "DISS_CD":
                    //console.log(results.response);
                    this.diseaseCdList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;
                case "usrInfo":
                    // if (results.success) {
                    //     let userRole = results.response.userRole;
                    //     if (userRole === "USER") { // 기업병원-질병책임자
                    //         location.href = "/lndata/use/req?othbcReqstId="+this.othbcReqstId;
                    //     } else { // MANAGER(병원-질병책임자), UPLOADER(병원-업로더), ADMIN(관리자)
                    //         alertMsg("기업 사용자만 신청이 가능합니다.");
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
                console.log(results.response.content);
                this.makePageNavi(results.response);
                this.openStorageList = results.response.list;
                this.loading = false;
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
                this.getUseStorageList();
            }
        },
    }
});
