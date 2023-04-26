
/*
 * @name : storageList.js
 * @date : 2021-06-23 오후 1:05
 * @author : lsj
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
let today = new Date();
let yearmonth = today.getFullYear() + "-" + numberPad((today.getMonth() + 1), 2);

var myChart;
		
const TID = {
    SEARCH      : {value: 0, name: "search", code: "S"},
    CHART      : {value: 0, name: "search", code: "S"}
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
                startMonth: "",
                endMonth: "",
                monthType:"A",
                sort: ""
            },
            demandList: [],
            tempList:[],
            xlabel:[],
            ylabel:[],
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
		
        this.getDemandList();
        this.onclickChart();
        this.getDemandChart();
        
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
        onclickSearch: function () {
            this.cond.page = 1;
            this.getDemandList();
        },
        getDemandList : function () {
			
			if(this.$refs.monthType.value === 'Z' && ($("#startMonth").val() > $("#endMonth").val())) {
				alertMsg("조회월을 확인해주세요.", this.$refs.monthType );
                return false;
			}
			
			this.cond.startMonth = $("#startMonth").val().replace('-', '');
			this.cond.endMonth = $("#endMonth").val().replace('-', '');
	
            get(TID.SEARCH, "/agency/demand/list", this.cond, this.callback);
        },
        excelDownload : function () {
			
			if(this.$refs.monthType.value === 'Z' && ($("#startMonth").val() > $("#endMonth").val())) {
				alertMsg("조회월을 확인해주세요.", this.$refs.monthType );
                return false;
			}
			
			this.cond.startMonth = $("#startMonth").val().replace('-', '');
			this.cond.endMonth = $("#endMonth").val().replace('-', '');
	
            axios({
                url: "/agency/demand/excel/list?dataName=" + this.cond.dataName + "&startMonth=" + this.cond.startMonth + "&endMonth=" + this.cond.endMonth,
                method: "GET",
                responseType: "blob",
            }).then((response) => {
				var tempDate = new Date();
				const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', "이용내역" + "_" + this.cond.endMonth + "_" + tempDate.getHours() + tempDate.getMinutes() + tempDate.getSeconds() + ".xlsx");
                document.body.appendChild(link);
                link.click(); // Start download
                document.body.removeChild(link); // Clean up and remove the link
            });
        },


        onclickChart: function () {
			
			if(this.$refs.monthType.value === 'Z' && ($("#startMonth").val() > $("#endMonth").val())) {
				alertMsg("조회월을 확인해주세요.", this.$refs.monthType );
                return false;
			}
			
			this.xlabel = [];
			this.ylabel = [];
	
			this.cond.startMonth = $("#startMonth").val().replace('-', '');
			this.cond.endMonth = $("#endMonth").val().replace('-', '');
			
            get(TID.CHART, "/agency/demand/chart", this.cond, this.callback);
        },
        getDemandChart : function () {
			// 차트를 그럴 영역을 dom요소로 가져온다.
			var chartArea = document.getElementById('myChart').getContext('2d');
			// 차트를 생성한다. 
			myChart = new Chart(chartArea, {
			    type: 'bar',
			    data: {
			        labels: this.xlabel, //'Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'
			        datasets: [{
			            label: '이용금액',
			            data: this.ylabel, //12, 19, 3, 5, 2, 3
			            backgroundColor: 'lightblue',
			            borderColor: 'rgba(54, 162, 235, 1)',
			            borderWidth: 1
			        }]
			    },
			    options: {
					title: {
			            display: true,
			            text: "chart"
			        },
			        scales: {
			            y: {
			                beginAtZero: false
			            }
			        },
			        legend: {
						position: 'bottom'
					},
			    }
			});
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case TID.CHART:
                	console.log(results);
                    this.chartCallback(results);
                    break;
            }
        },
        login: function() {
            location.href = "/login";
        },
        searchCallback: function (results) {
            if (results.success) {
            
                this.makePageNavi(results.response);
                this.demandList = results.response.list;
                console.log(this.demandList);
            } else {
                //console.log(results);
                alertMsg(results.error.message);
            }
        },
        chartCallback: function (results) {
	
            if (results.success) {
                this.tempList = results.response;
                
                for(var i = 0; this.tempList.length > i; i++) {
					this.xlabel.push(this.tempList[i].rqestYm.substr(0,4) + '.' + this.tempList[i].rqestYm.substr(4,2));
					this.ylabel.push(this.tempList[i].totRqestAdupAmount);
				}
				
				myChart.data.labels = this.xlabel;
				myChart.data.datasets[0].data = this.ylabel;
				myChart.update();

            } else {
                console.log(results);
            }
            
            
        },
        onclickDetail : function(seq){
            location.href="/my/agency/demandView?menuId="+myMenuId+"&loginId="+seq;
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
    }
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
