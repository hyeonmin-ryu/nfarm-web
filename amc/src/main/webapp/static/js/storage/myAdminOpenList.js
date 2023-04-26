
/*
 * @name : myOpenList.js
 * @date : 2021-07-07 오전 11:05
 * @author : lsj
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
let othbcReqstSttuscd;
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
            search: {
                page: 1,
                size: 5,
                othbcDataNm: "",
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
            openStorageStatCd : "", // 선택된 콤보박스
            openStorageStatCdList : getCodeList('OTHBC_REQST_STTUSCD',this.callback), // 상태콤보박스 리스트
//            isYn : getCodeList('IsYn',this.callback)

        };
    },
    mounted:function(){
        this.getMyOpenStorageList();
        setTimeout(function() {
            loadSelect();
        },500);
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.search.othbcReqstSttuscd=this.$refs.othbcReqstSttuscd.value;
            this.getMyOpenStorageList();
        },
        // 목록 > 신청번호 클릭(화면 이동)
        onclickReq: function (othbcReqstId) {
            location.href = "/my/admin/open/view?menuId="+myMenuId+"&othbcReqstId="+othbcReqstId;
        },

        getMyOpenStorageList:function () {
            get(TID.SEARCH,
                "/storage/open/list",
                this.search,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "OTHBC_REQST_STTUSCD":
                    this.openStorageStatCdList = results.response;
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.openStorageList = results.response.list;
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
            this.search.page = page;
            this.getMyOpenStorageList();
        },
        
    }
});

