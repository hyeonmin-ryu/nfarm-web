let appMain;
let userSeq;
const TID = {
    SEARCH: { value: 0, name: "search", code: "S" },
    INFO: { value: 0, name: "info" },
    ACCEPT: { value: 0, name: "accept" }

};

window.onload = function () {
    appMain = new Vue({
        el: '#maincontentswrap',
    });

}
Vue.component('maincontents', {
    template: "#main-template",
    data: function () {
        return {
            search: {
                page: 1,
                size: 10,
                userType: "", // 회원구분
                insttId: "",
                dissCd: "", //질환코드
                sbscrbSttusCd: "",
                chargerNm: "",
            },
            list: [],
            agencyList: [],
            userTypeCdList: getCodeList('USERTYPE',this.callback), // 회원콤보박스 리스트
            hospList: [],//병원 콤보
            compList: [],//기업 콤보
            diseaseCode: "",
            diseaseCdList : getCodeList('DISS_CD',this.callback), // 질환콤보박스 리스트
            joinStatCode: [],
            pageInfo: {
                curr: 1,
                max: 1,
                first: 1,
                last: 1,
                prev: 1,
                next: 1,
                pages: [1]
            },
            userSeq: userSeq,
            info: {
                insttNm: "",
                insttTyCd: "",
                agencyTypeName: "",
                bsnmNO: "",
                rprsntvNm: "",
                diseaseCode: "",
                diseaseName: "",
                dissRspnberYn: "",
                diseaseManagerName: "",
                joinStatCode: "",
                joinStatName: "",
                ncloudId: "",
                ncloudObjStrgeId: "",
                ncloudAccesKey: "",
                ncloudScrtyKey: "",
                email: "",
                chargerNm: "",
                chargerMoblphonNo: "",
                userId: "",
            },
            showYn: true,
            acceptYn: false,
            rejectYn: false,
            messages: "",
        };
    },
    mounted: function () {
        if (isNull(this.userSeq)) { // 목록
            codeId = "SBSCRB_STTUS_CD";
            getCodeList(codeId, this.callback);
            this.getAgencyList();
            this.onclickSearch();

        } else { // 상세
            let url = "/user/info/" + this.userSeq;
            get(TID.INFO, url, null, this.callback);
        }

        setTimeout(function () {
            loadSelect();
        }, 300);

    },
    computed: function () {
    },
    methods: {
        onKeyup: function (e) {
            if (e.keyCode == 13) {
                this.onclickSearch();
            }
        },
        getAgencyList: function () {
            get("Agency",
                "/agency/list",
                { size: 100, sort: "agencyName" },
                this.callback);
        },
        onclickSearch: function () {
            this.search.page = 1;
            this.getMemberList();
        },
        getMemberList() {
            if (this.search.userType === "01") {
                this.search.insttId = document.querySelector("#agency").getAttribute("data-value");
            } else if (this.search.userType === "03") {
                this.search.insttId = document.querySelector("#hospital").getAttribute("data-value");
            } else if (this.search.userType === "02") {
                this.search.insttId = document.querySelector("#company").getAttribute("data-value");
            }

            this.search.dissCd = document.querySelector("#dissCd").getAttribute("data-value");
            this.search.sbscrbSttusCd = document.querySelector("#sbscrbSttusCd").getAttribute("data-value");

			console.log(this.search);
            //console.log(JSON.stringify(this.search));
            get(TID.SEARCH, "/user/list", this.search, this.callback);
            
        },
        callback: function (tid, results) {

            switch (tid) {
                case TID.SEARCH:
                    this.onclickSearchCallback(results);
                    break;
                case "SBSCRB_STTUS_CD":
                    this.joinStatCode = results.response;
                    break;
                case "USERTYPE":
                    //console.log(results);
                    this.userTypeCdList = results.response;
                    break;
                case "DISS_CD":
                    //console.log(results);
                    this.diseaseCdList = results.response;
                    break;
                case "Agency":
                	console.log(results.response);
                    this.agencyList = results.response;
                    this.hospList = this.agencyList.filter(item => (item.insttTyCd != "01")); // 기업 제외(병원 리스트만)
                    this.compList = this.agencyList.filter(item => (item.insttTyCd == "01")); // 기업 list
                    
                    break;
                case TID.INFO:
                	console.log(results.response);
                    this.getUserInfoCallback(results);
                    break;
                case TID.ACCEPT:
                    this.onclickAcceptCallback(results);
                    break;
                case TID.REJECT:
                    this.onclickRejectCallback(results);
                    break;
            }
        },
        onclickSearchCallback: function (results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.list = results.response.list;
                console.log(this.list);
            } else {
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
        onclickPage: function (page) {
            if(page === this.pageInfo.curr){
            } else {
                this.search.page = page ;
                this.pageInfo.curr = page;
                this.getMemberList();
            }
        },
        onclickDetail: function (seq) {
            location.href = "/my/admin/memberView?menuId=" + myMenuId + "&userSeq=" + seq;
        },
        getUserInfoCallback: function (results) {
            
            const item = results.response;
            this.info.insttTyCd = item.insttTyCd;
            this.info.insttNm = item.insttNm;
            this.info.sbscrbSttusCd = item.sbscrbSttusCd;
            //this.info.agencyTypeCode = item.agencyTypeCode.name;
            this.info.agencyTypeName = item.agencyTypeCode;
            this.info.dissNm = item.dissNm;
            this.info.bsnmNO = item.bsnmNO;
            this.info.rprsntvNm = item.rprsntvNm;
            this.info.ncloudId = item.ncloudId;
            this.info.ncloudObjStrgeId = item.ncloudObjStrgeId;
            this.info.ncloudAccesKey = item.ncloudAccesKey;
            this.info.ncloudScrtyKey = item.ncloudScrtyKey;
            this.info.userEmail = item.userEmail;
            this.info.email = item.email;
            this.info.chargerMoblphonNo = item.chargerMoblphonNo;
            this.info.chargerNm = item.chargerNm;
            this.info.userId = item.userId;

            console.log(this.info);

            if (item.userRoleSecd === "ROLE_USER") {
                this.info.diseaseManagerName = "";
            } else if (item.dissRspnberYn === "Y") {
                this.info.diseaseManagerName = "질병책임자";
            } else if (item.dissRspnberYn === "N") {
                this.info.diseaseManagerName = "데이터업로더";
            }
            if (item.userRoleSecd === "ROLE_ADMIN") {  // 질병명, 책임자여부 숨김
                this.showYn = false;
            }

            if (this.info.sbscrbSttusCd === "신청") { // 요청만 승인버튼 노출
                this.acceptYn = true;
                this.rejectYn = true;
            }

        },
        onclickAccept: function () { // 승인
            confirmMsg("승인 하시겠습니까?", this.save);
        },

        onclickReject: function () { // 거절
            confirmMsg("거절 하시겠습니까?", this.reject);
        },

        save: function () {
            post(TID.ACCEPT, "/user/accept", this.info, this.callback);
        },
        reject: function () {
            post(TID.REJECT, "/user/reject", this.info, this.callback);
        },
        onclickAcceptCallback: function (results) {

            if (results.success) {
                alertMsgRtn("정상적으로 승인처리 되었습니다.", this.saveRtn);
            } else {
                alertMsg(results.error.message);
            }
        },

        onclickRejectCallback: function (results) {

            if (results.success) {
                alertMsgRtn("정상적으로 거절처리 되었습니다.", this.saveRtn);
            } else {
                alertMsg(results.error.message);
            }
        },

        saveRtn: function () {
            location.href = "/my/admin/memberList?menuId=0";
        },
        onclickBack: function () {
            history.back();
        },
        // 검색 selectebox 이벤트
        // selectebox 이벤트
        searchChange: function (id, data) {
            if (id == "userType") { // 회원구분 변경
                this.search.userType = data;
            }
        },
    },
});

// 검색 selectebox 이벤트
function selectChange(id) {
    const data = document.querySelector('#' + id).value;
    appMain.$refs.maincontents.searchChange(id, data);
}
