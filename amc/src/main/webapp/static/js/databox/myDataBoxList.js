
/*
 * @name : storageList.js
 * @date : 2021-06-23 오후 1:05
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
                dtbxNm: "",
                dtbxReqstSttusCd: "",
                sort: "",
                pageGubun:"mypage"
            },
            dataBoxList: [],
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
            dataBoxReqStatCodeList : getCodeList('DTBX_REQST_STTUS_CD',this.callback), // 콤보박스 리스트

        };
    },
    mounted:function(){
        this.getDataBoxList();

    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.cond.dtbxReqstSttusCd = this.$refs.dtbxReqstSttusCd.value;//처리상태
            this.getDataBoxList();
        },
        // 목록 > 신청 클릭(화면 이동)
        onclickReq: function () {
            getUserInfo("usrInfo", this.callback);
        },

        getDataBoxList:function () {
            get(TID.SEARCH,
                "/data/box/list",
                this.cond,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "DTBX_REQST_STTUS_CD":
                    this.dataBoxReqStatCodeList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;

                case "usrInfo":
                    if (results.success) {
                        let userRole = results.response.userRole;
                        if (userRole === "USER") { // USER(기업)
                            location.href = "/data/box/req";
                        } else { // USER(기업), UPLOADER(병원-업로더), ADMIN(관리자)
                            alertMsg("기업사용자만 신청이 가능합니다.");
                            return;
                        }
                    } else {
                        confirmMsg("로그인 후 이용 가능합니다.\n로그인 페이지로 이동하시겠습니까?", this.login);
                    }
                    break;
            }
        },
        login: function() {
            location.href = "/login";
        },
        onclickDetail : function(seq){
            location.href="/my/admin/databoxView?menuId="+myMenuId+"&dtbxReqNo="+seq;
        },
        searchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.dataBoxList = results.response.list;
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
                this.getDataBoxList();
            }
        },
    }
});

