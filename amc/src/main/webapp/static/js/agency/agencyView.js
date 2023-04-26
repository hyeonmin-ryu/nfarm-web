let appMain;
let agencySeq;

const TID = {
    INFO : {value: 0, name: "search", code: "S"},
    SAVE : {value: 0, name: "save",   code: "I"},
    DELETE : {value: 0, name: "delete",   code: "I"}
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
                sort: ""
            },
            agencyInfo : {
				insttId:"",
				insttNm:"",
                blNumber:"",
                ceoName:"",
                insttTyCd :"",
            },
            agencyTypeList: getCodeList("INSTT_TY_CD", this.callBack),
            messages : "",
        };
    },
    mounted:function(){
        this.searchAgencyInfo();
    },
    methods:{
	    searchAgencyInfo : function() {
			let url ="/mypage/agency/view/"+insttId;
        	get(TID.INFO, url, null,this.callBack);
		},
        callBack: function (tid, results) {
            switch (tid) {
                case TID.INFO:
                    this.searchCallback(results);
                    break;
                case TID.SAVE:
                    this.saveCallback(results);
                    break;
                case TID.DELETE:
                    this.deleteCallback(results);
                    break;
                case "INSTT_TY_CD":
                	this.agencyTypeList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },1000);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
				this.agencyInfo.insttId = results.response.insttId;
                this.agencyInfo.insttNm = results.response.insttNm;
                this.agencyInfo.rprsntvNm = results.response.rprsntvNm;
                this.agencyInfo.bsnmNo = results.response.bsnmNo;
                this.agencyInfo.insttTyCd = results.response.insttTyCd;
            } else {
                console.log(results);
            }
        },
        saveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 저장되었습니다.", this.onclickCancel);
            } else {
                alertMsg(results.error.message, this.$refs.blNumber);
            }
        },
        onclickCancel: function () {
            location.href = "/my/agency";
        },
        onclickAgencySave: function () {
            this.saveAgencyInfo();
        },
        saveAgencyInfo : function () {
			
			let reg_name5 = /^[가-힣a-zA-Z0-9]+$/; // 한글 + 영문만
	
			if(!this.agencyInfo.insttNm) {
				alertMsg("기관명을 입력해주세요.", this.$refs.insttNm);
                return;
			}
			
			if(!this.agencyInfo.rprsntvNm) {
				alertMsg("기관대표자를 입력해주세요.", this.$refs.rprsntvNm);
                return;
			}
			if (reg_name5.test(this.agencyInfo.rprsntvNm) === false) {
				alertMsg('기관대표자를 정확히 입력해 주세요.', this.$refs.rprsntvNm);
				this.agencyInfo.rprsntvNm = '';
				return;
			}
			
			if(!this.agencyInfo.bsnmNo) {
				alertMsg("사업자번호를 입력해주세요.", this.$refs.bsnmNo);
                return;
			}
			
			if(this.agencyInfo.bsnmNo.length !== 10) {
				alertMsg("사업자번호 10자리를 입력해주세요.", this.$refs.bsnmNo);
                return;
			}
			
			var tempAgencyType = this.agencyInfo.insttTyCd;

			if(!tempAgencyType) {
				alertMsg("기관타입을 선택해주세요.", this.$refs.agencyType);
                return;
			}
			
            confirmMsg("저장하시겠습니까?",this.save);
        },
        save: function() {
            post(TID.SAVE, "/mypage/agency/save", this.agencyInfo, this.callBack);
        },
        onclickAgencyDelete :function() {
			confirmMsg("삭제하시겠습니까?", this.delete);
		},
		delete : function() {
			post(TID.DELETE, "/mypage/agency/delete", this.agencyInfo, this.callBack);	
		},
		deleteCallback : function(results) {
			if (results.success) {
                alertMsgRtn("정상적으로 삭제되었습니다.", this.onclickCancel);
            } else {
                alertMsg(results.error.message);
            }
		}
		,
        // 검색 selectebox 이벤트
        searchChange:function(data){
           this.agencyInfo.insttTyCd = data;
        }
		
    }
});


// 검색 selectebox 이벤트
function selectChange(){
	
    const data= document.querySelector("#agencyType").value;
    appMain.$refs.maincontents.searchChange(data);
}