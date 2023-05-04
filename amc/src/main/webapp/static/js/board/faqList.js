
/*
 * @name : faqList.js
 * @date : 2021-08-17 오전 9:32
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
                keyword: "",
                questionType:"",
                sort: ""
            },
            faqList: [],
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
            userInfo:[],
            questionTypeList : getCodeList('INQRY_TYCD',this.callback), // 콤보박스 리스트

        };
    },
    mounted:function(){
        this.getFaqList();
        this.getUserInfo();
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.cond.questRspnsTycd = this.$refs.questionType.value;
            this.getFaqList();

        },
        getUserInfo: function(){
            get("usrInfo","/user/my/info",{},this.callback);
        },
        // 목록 > 등록 클릭(화면 이동)
        onclickReg: function () {
            location.href = "/farm/reg";
        },
        // 목록 > 제목 클릭(상세보기)
        onclickView: function (questNo) {
            location.href = "/board/faq/view?questNo="+questNo;
        },
        getFaqList:function () {
            get(TID.SEARCH,
                "/board/faq/list",
                this.cond,
                this.callback);

        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "usrInfo":
                    if (results.success) {
                        this.userInfo = results.response;
                        
                        // setTimeout(function() {
	                    //     loadSelect();
	                    // },1000);
                    } else {
                        console.log(results);
                        //alertMsg(results.error.message);
                    }
                    break;
               case "INQRY_TYCD":
                    this.questionTypeList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },1000);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.faqList = results.response.list;
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
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page ;
                this.pageInfo.curr = page;
                this.getFaqList();
            }
        },
        searchChange:function(data){
           this.agencyInfo.agencyTypeCode = data;
        }
    }
});

