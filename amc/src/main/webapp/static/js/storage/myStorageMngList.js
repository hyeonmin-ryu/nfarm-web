
/*
 * @name : myStorageMngList.js
 * @date : 2021-07-21 오전 10:54
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
                //selected: "",
                insttNm: "", // 기관명
                storgeNm: "", // 데이터명
                dissCd: diseaseCode, // 질환코드
                strgeReqSttusCd: reqStorageStatCode,
                sort: ""
            },
            storageList: [],
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
            selected:"agencyName", // 선택된 콤보박스(default:기관명)
            selectList:[
                {name: "agencyName", desc:"기관명"},
                {name: "disease", desc:"질환명"},
                {name: "dataName", desc:"데이터명"},
            ],
            searchVal : "", // 입력된 검색어
            selectedDisease : "", // 질환콤보박스 선택된 값
            diseaseCdList : getCodeList('DISS_CD',this.callback) // 질환콤보박스 리스트
        };
    },
    mounted:function(){
        this.getStorageList();

    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            if(this.selected === "agencyName") {
                this.cond.insttNm = this.searchVal;
            } else if(this.selected === "dataName") {
                this.cond.storgeNm = this.searchVal;
            } else if(this.selected === "disease") {
                this.cond.dissCd = this.$refs.selectedDisease.value;
            }
            this.getStorageList();
        },
        // 목록 > 상세보기 클릭(화면 이동)
        onclickView: function (storgeReqstId) {
            let uri = "/my/storeMng/View?menuId="+myMenuId+"&storgeReqstId=";
            location.href = uri + storgeReqstId;
        },

        getStorageList:function () {
            this.cond.strgeReqSttusCd = "S_ACC"; // 저장신청승인만 조회
            get(TID.SEARCH,
                "/storage/req/list",
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
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                console.log(results.response.list);
                this.makePageNavi(results.response);
                this.storageList = results.response.list;
            } else {
                console.log(results);
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
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            // this.cond.page = page - 1;
            // this.getStorageList();
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page ;
                this.pageInfo.curr = page;
                this.getStorageList();
            }
        },
        // 검색 selectebox 이벤트
        searchChange:function(data){
            this.selected = data;

            this.selectedDisease = "";
            this.searchVal = "";

            this.cond.dissCd = this.selectedDisease;
            this.cond.insttNm = this.searchVal ;
            this.cond.storgeNm = this.searchVal ;

        }
    }
});

// 검색 selectebox 이벤트
function selectChange(){
    const data= document.querySelector("#selected").value;
    appMain.$refs.maincontents.searchChange(data);
}