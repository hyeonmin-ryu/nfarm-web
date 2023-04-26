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
                dataName: "",
                sort: ""
            },
            agencyList: [],
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
        };
    },
    mounted:function(){
        this.getAgencyList();
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.getAgencyList();
        },
        getAgencyList : function () {
            get(TID.SEARCH, "/mypage/agency/list", this.cond, this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
            }
        },
        login: function() {
            location.href = "/login";
        },
        searchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.agencyList = results.response.list;
            } else {
                console.log(results);
            }
        },
        onclickDetail : function(seq){
            location.href="/my/agency/view?menuId="+myMenuId+"&insttId="+seq;
        },
        makePageNavi: function (pageable) {
            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;
            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;

            last = last > max ? max : last;
            let prev = first - 1;
            prev = prev < 1 ? 1 : prev;
            let next = last + 1;
            next = next > max ? max : next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            //this.total = Math.ceil(total / pageable.size);
            this.pageInfo.total = pageable.total;


            this.pageInfo.pages = new Array();
            for (let i = first; i <= last; i++) {
                this.pageInfo.pages.push(i);
            }

        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page;
                this.pageInfo.curr = page;
                this.getAgencyList();
            }
        },
        onClickSave :function () {
			location.href = "/my/agency/view?menuId="+myMenuId;
		}
    }
});