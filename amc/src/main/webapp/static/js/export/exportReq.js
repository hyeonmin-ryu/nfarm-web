let appMain;
const TID = {
    SEARCH_USER   : {value: 0, name: "search", code: "S"},
    SAVE          : {value: 0, name: "save",   code: "I"}
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
            userInfo : [],
            saveInfo: {
                dtbxNm : "",
                dtbxSn : "",
                exportApproverInfo:[],
                userSeqList:[],
                exportReason : "",
                exportPath :""
            },
            bucketListMain: [], //연구분석데이터신청 목록
            bucketListTemp: []
        };
    },
    mounted:function(){
        this.getReqUser();
        
        setTimeout(function() {
                        loadSelect();
                    },300);
    },
    methods:{
        // 신청화면에 출력될 데이타 조회
        getReqUser:function (){
            get(TID.SEARCH_USER, "/data/box/user", {}, this.callback);
        },
        // 취소 클릭(목록 이동)
        onclickCancel: function () {
            location.href = "/data/out/main";
        },
        // 신청 클릭(신청)
        onclickReqSave: function () {
            this.saveReqExport();
        },
        // 신청 메소드 호출
        saveReqExport : function () {
	
			if(this.bucketListMain.length === 0) {
				alertMsg("반출 대상 데이터박스 추가는 필수입니다.", this.$refs.dataBoxBtn );
                return false;
			}
			
			if(!this.saveInfo.tkoutResn){
                alertMsg("반출사유는 필수입니다.", this.$refs.tkoutResn );
                return false;
            }
            
            if(!this.saveInfo.tkoutTrgetCours){
                alertMsg("반출 대상 파일 경로는 필수입니다.", this.$refs.tkoutTrgetCours );
                return false;
            }
            console.log(this.bucketListTemp);
            console.log(this.saveInfo);
            for(var i = 0; this.bucketListTemp.length > i; i++) {
            	let obj={};
            	obj.othbcDataNm = this.bucketListTemp[i].othbcDataNm;
            	obj.insttNm = this.bucketListTemp[i].insttNm;
            	obj.chargerNm = this.bucketListTemp[i].chargerNm;
            	obj.insttId = this.bucketListTemp[i].insttId;
            	obj.dissRspnberUserId = this.bucketListTemp[i].dissRspnberUserId;
            	this.saveInfo.exportApproverInfo.push(obj);
				//this.saveInfo.bucketList.push(this.bucketListTemp[i].bucketInfo);
			}
			
			this.saveInfo.chargerNm = this.userInfo.chargerNm;
			
            confirmMsg("신청하시겠습니까?",this.save);
        },
        save: function() {
            post(TID.SAVE, "/export/reg", this.saveInfo, this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH_USER:
                	console.log(results.response);
                    this.searchCallback(results);
                    break;
                case TID.SAVE:
                    this.saveCallback(results);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                this.userInfo      = results.response;
            } else {
                alertMsg(results.error.message);
            }
        },
        saveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("신청 내용 저장 후 관리자에게 이메일이 발송 되었습니다.",this.onclickCancel);
            } else {
                alertMsg(results.error.message);
            }
        },
        onclickDataBoxAdd : function () {
			fnOpenPopup('exportModal');
		},
		returnPop : function(item) {
			this.bucketListMain = item;
			
			this.saveInfo.dtbxSn = this.bucketListMain[0].dataBoxSeq;
			this.saveInfo.dtbxNm = this.bucketListMain[0].dataBoxName;
			this.bucketListTemp = this.bucketListMain[0].bucketList;
			console.log(this.bucketListTemp);
		},
    }
});
