let appMain;
let dataBoxSeq;
let menuId;
let detailMain;
let today = new Date();
let yearmonth = today.getFullYear() + "-" + numberPad((today.getMonth() + 1), 2);

const TID = {
    INFO : {value: 0, name: "search", code: "S"}
};
window.onload = function(){
    appMain = new Vue({
        el: '#maincontentswrap',
    });
    detailMain = new Vue({
        el: '#detailcontentswrap',
    });
}

Vue.component('maincontents', {
    template : "#main-template",
    data: function() {
        return {
	        cond: {
                page: 1,
                size: 5,
                monthType: "A",
                startMonth: "",
                endMonth: "",
                sort: "",
                loginId : loginId
            },
            productDemandList: [],
            monthTypeList: [{name: "A", desc: "6개월"}, {name: "B", desc: "12개월"}, {name: "Z", desc: "직접입력"}],
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
		let nowday = new Date();
    
    	nowday.setMonth(nowday.getMonth() - 4);
		$("#startMonth").val(nowday.getFullYear() + "-" + numberPad(nowday.getMonth(), 2));
		$("#endMonth").val(yearmonth);
		
        this.onclickSearch();
        setTimeout(function() {
                        loadSelect();
                    },300);
    },
    methods:{
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },

        // 취소 클릭(목록 이동)
        onclickList: function () {
            location.href = "/my/demand/list";
        },        
        onclickSearch: function () {
	
            this.cond.page = 1;
            this.cond.monthType = this.$refs.monthType.value;
            
            if(this.$refs.monthType.value === 'Z' && ($("#startMonth").val() > $("#endMonth").val())) {
				alertMsg("조회월을 확인해주세요.", this.$refs.monthType );
                return false;
			}
			
			this.cond.startMonth = $("#startMonth").val().replace('-', '');
			this.cond.endMonth = $("#endMonth").val().replace('-', '');
            
            this.getDemandList();
        },
        getDemandList : function () {
            get(TID.SEARCH, "/agency/demand/view", this.cond, this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                console.log(results.response);
                    this.searchCallback(results);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.productDemandList = results.response.list;
            } else {
                console.log(results);
            }
        },
        onclickDetail : function(seq){
            location.href="/my/agency/demandView?menuId="+myMenuId+"&dataBoxSeq="+seq;
        },
        makePageNavi: function (pageable, total) {
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
        onclickUploderPop : function(loginId, demandMonth, userSeq){
			detailMain.$refs.detailcontents.loadUploader(loginId, demandMonth, userSeq);
			fnOpenPopup('detailModal');
        },
    }
});

Vue.component('detailcontents', {
    template: "#detail-template",
    data: function () {
        return {
            search : {
                page: 0,
                size: 1000,
                joinStat :"",
                userName :"",
            },
            popResult :{},
            listLength : 0,
            joinStatCode: [],
            demandMonth: "",
            dataBoxSeq: "",
            loginId : "",
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            showYn:true,
            acceptYn:false,
            messages: "",
        };
    },
    mounted: function () {
        /*31424*/
    },
    methods: {
        callback: function(tid, results){
            switch (tid) {
                case TID.SEARCH:
                    this.onclickSearchCallback(results);
                    break;
            }
        },
        loadUploader: function(loginId, demandMonth, userSeq){
			this.reqstYm = demandMonth;
			this.dataBoxSeq = dataBoxSeq;
			this.nloudId = loginId;
			
            get(TID.SEARCH,"/agency/demand/pop?loginId=" + loginId + "&demandMonth=" + demandMonth  + "&userSeq=" + userSeq, this.search, this.callback);
        },
        onclickSearchCallback : function(results) {
            if (results.success) {
                this.popResult = results.response;
                
                this.listLength = this.popResult.productDemandCostList.length;
                
                console.log(this.popResult);
            } else {
                alertMsg(results.error.message);
            }

        },
        onclickDownload : function(){
            axios({
                url: "/agency/demand/excel?dataBoxSeq=" + this.dataBoxSeq + "&demandMonth=" + this.reqstYm + "&loginId=" + this.nloudId + "&orgName=" + this.popResult.demOrgName,
                method: "GET",
                responseType: "blob",
            }).then((response) => {
				var tempDate = new Date();
				const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', this.popResult.demOrgName + "_" + this.demandMonth + "_" + tempDate.getHours() + tempDate.getMinutes() + tempDate.getSeconds() + ".xlsx");
                document.body.appendChild(link);
                link.click(); // Start download
                document.body.removeChild(link); // Clean up and remove the link
            });
        },
        onclickSendMail : function(){
            fnOpenPopup('mailModal');
        },
    },
});

function numberPad(n, width) {
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

// selectebox 이벤트
function selectedChange(id){
    const data = document.querySelector('#'+id).value;
    
    let nowday = new Date();
    
    if(data === "A") { //6개월
    	nowday.setMonth(nowday.getMonth() - 4);
		$("#startMonth").val(nowday.getFullYear() + "-" + numberPad(nowday.getMonth(), 2));
		$("#endMonth").val(yearmonth);
		
		$("#startMonth").attr("disabled", true);
		$("#endMonth").attr("disabled", true);
	} else if (data === "B") { //12개월
		nowday.setMonth(nowday.getMonth() - 10);
		$("#startMonth").val(nowday.getFullYear() + "-" + numberPad(nowday.getMonth(), 2));
		$("#endMonth").val(yearmonth);
		
		$("#startMonth").attr("disabled", true);
		$("#endMonth").attr("disabled", true);
	} else { //직접입력
		$("#startMonth").val(yearmonth);
		$("#endMonth").val(yearmonth);
		
		$("#startMonth").attr("disabled", false);
		$("#endMonth").attr("disabled", false);
	}
};
