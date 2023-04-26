/*
 * @name : myUseList.js
 * @date : 2021-08-10 오후 3:32
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
                useReqstSttusSecd: useReqstSttusSecd, // 처리상태
                othbcDataNm:"",
                sort: ""
            },
            useStorageList: [],
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
            useStrgeId:"",
            useStoreStatCdList : getCodeList('USE_REQST_STTUS_SECD',this.callback),
        };
    },
    mounted:function(){
        this.getMyUseStorageList();
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.cond.useReqstSttusSecd = this.$refs.useReqstSttusSecd.value;

            this.getMyUseStorageList();
        },
        // 목록 > 신청번호 클릭(화면 이동)
        onclickReq: function (useStrgeId) {
            let uri = "/my/use/view?menuId="+myMenuId+"&useStrgeId=";
            location.href = uri + useStrgeId;
        },

        getMyUseStorageList:function () {
            let pageUri="";
            switch (MY_ROLE){
                case "ROLE_USER":       // 기업
                    pageUri = "/my/management/storage/use/list";
                    break;
                case "ROLE_MANAGER":    // 병원책임자
                    pageUri = "/mine/management/storage/use/list";
                    break;
                case "ROLE_ADMIN":    // 관리자
                    pageUri = "/management/storage/use/list";
                    break;
            }
            get(TID.SEARCH,
                pageUri,
                this.cond,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "USE_REQST_STTUS_SECD":
                    //console.log(results.response);
                    this.useStoreStatCdList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                //console.log(results.response);
                this.makePageNavi(results.response);
                this.useStorageList = results.response.list;
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
                this.getMyUseStorageList();
            }
        },
    }
});
