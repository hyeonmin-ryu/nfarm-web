let appMain;
let tkoutReqstNo;
let menuId;

const TID = {
    APPROVE : {value: 0, name: "save",   code: "I"},
    REJECT : {value: 0, name: "save",   code: "I"},
    ACCEPT : {value: 0, name: "save",   code: "I"},
    CANCEL : {value: 0, name: "save",   code: "I"},
    DELETE : {value: 0, name: "save",   code: "I"},
    INFO : {value: 0, name: "search", code: "S"}
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
	        tkoutReqstNo : tkoutReqstNo,
	        exportInfo : {
				tkoutReqstNo : "",
				exportPath : "",
				exportReason : "",
				exportReqStatCodeDesc : "",
				exportReqStatCodeName : "",
				dataBoxReq : {},
				requestDate : "",
				acceptDate : "",
				approveDate : "",
				cancelDate : "",
				rejectReason :"",
				exportApproverInfo :[],
				userInfo :"",
				agencyInfo :""
			},
			apprInfo : {
				tkoutReqstNo : "",
				tkoutSvrIpAdres : "",
				tkoutSvrUserId : "",
				tkoutSvrUserPassword : "",
			},
			saveInfo : {
				tkoutReqstNo : "",
				tkoutConfmSn :"",
				rejectReason : "",
				lastPang : "",
				userInfo : {}
			}
        };
    },
    mounted:function(){
        let url ="/mypage/export/view/"+this.tkoutReqstNo;
        get(TID.INFO, url, null,this.callback);
    },
    methods:{
        onclickList: function () { // 목록 클릭
            location.href = "/my/export/list?menuId="+myMenuId;
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.INFO:
                    this.infoCallback(results);
                    break;
                case TID.ACCEPT:
                    this.acceptCallback(results);
                    break;
                case TID.REJECT:
                    this.rejectCallback(results);
                    break;
                case TID.APPROVE:
                    this.approveCallback(results);
                    break;
            }
        },
        infoCallback : function(results){
        	console.log(results.response);
            const item = results.response;
            
            this.exportInfo.tkoutReqstNo = item.tkoutReqstNo;
            this.exportInfo.userId = item.userId;
            this.exportInfo.dtbxSn = item.dtbxSn;
            this.exportInfo.dtbxNm = item.dtbxNm;
            this.exportInfo.tkoutTrgetCours = item.tkoutTrgetCours;
            this.exportInfo.tkoutResn = item.tkoutResn;
            this.exportInfo.tkoutProcessSttusCd = item.tkoutProcessSttusCd;
            this.exportInfo.exportReqStatCodeName = item.exportReqStatCodeName;
            this.exportInfo.reqstDt = item.reqstDt;
			this.exportInfo.rceptDt = item.rceptDt;
			this.exportInfo.processDt = item.processDt;
			this.exportInfo.canclDt = item.canclDt;
			this.exportInfo.rejectResn = item.rejectResn;
			this.exportInfo.tkoutTrgetCours = item.tkoutTrgetCours;
			this.exportInfo.chargerNm = item.chargerNm;
			this.exportInfo.chargerMoblphonNo = item.chargerMoblphonNo;
			this.exportInfo.email = item.email;
			this.exportInfo.insttNm = item.insttNm;
			this.exportInfo.exportApproverInfo = item.exportApproverInfo;
			
			//Admin
			this.exportInfo.userInfo = item.userInfo;
			this.exportInfo.agencyInfo = item.agencyInfo;
			
			console.log(this.exportInfo);
			this.apprInfo.tkoutSvrIpAdres = item.tkoutSvrIpAdres;
			this.apprInfo.tkoutSvrUserId = item.tkoutSvrUserId;
			this.apprInfo.tkoutSvrUserPassword = item.tkoutSvrUserPassword;
        },
        cancelCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 취소되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
        onclickAccept : function () { // 접수 클릭
        	
        	if(!this.apprInfo.exportServerIp){
                alertMsg("반출서버 접속 IP는 필수입니다.", this.$refs.exportServerIp );
                return false;
            }
            
            if(!this.apprInfo.tkoutSvrUserId){
                alertMsg("반출서버 접속 ID는 필수입니다.", this.$refs.exportServerId );
                return false;
            }
            
            if(!this.apprInfo.tkoutSvrUserPassword){
                alertMsg("반출서버 접속 암호는 필수입니다.", this.$refs.exportServerPw );
                return false;
            }
        
        	this.apprInfo.tkoutReqstNo = this.exportInfo.tkoutReqstNo;
            confirmMsg("반출서버 등록을 하시겠습니까?", this.saveAccept);
        },
        saveAccept :function() {
			post(TID.ACCEPT, "/my/admin/export/accept", this.apprInfo, this.callback);
		},
		acceptCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 접수되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
        onclickApprove : function (idx, seq) { //승인 클릭
        
        	this.saveInfo.tkoutReqstNo = this.tkoutReqstNo;
            this.saveInfo.tkoutConfmSn = seq;
        	
        	let totCnt = this.exportInfo.exportApproverInfo.length;
        	let appCnt = this.exportInfo.exportApproverInfo.filter(x => 'APPROVE' === x.tkoutProcessSttusCdNm).length;
        	
        	if((appCnt + 1) === totCnt) {
				this.saveInfo.lastPang = 'Y';
				
				this.saveInfo.email = this.exportInfo.email;
				this.saveInfo.chargerNm = this.exportInfo.chargerNm;
				this.saveInfo.dtbxSn = this.exportInfo.dtbxSn;
				this.saveInfo.dtbxNm = this.exportInfo.dtbxNm;
				
				console.log(this.saveInfo);
				confirmMsg("최종 승인 하시겠습니까?", this.saveApprove);
			} else {
				this.saveInfo.lastPang = 'N';
				console.log(this.saveInfo);
				confirmMsg("사용 승인 하시겠습니까?", this.saveApprove);
			}
        },
        saveApprove :function() {
			post(TID.APPROVE, "/my/export/approve", this.saveInfo, this.callback);
		},
		approveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 승인되었습니다.", this.approveReload);
            } else {
                alertMsg(results.error.message);
            }
        },
        approveReload :function () {
			let url ="/mypage/export/view/"+this.tkoutReqstNo;
        	get(TID.INFO, url, null,this.callback);
		},
        onclickReject : function (idx, seq) { //거절 클릭
        	
        	var temp = $('#rejectResn' + String(idx));
        	
        	if(!temp.val()){
                alertMsg("거절사유는 필수입니다.", temp );
                return false;
            }
            
            this.saveInfo.tkoutReqstNo = this.tkoutReqstNo;
            this.saveInfo.tkoutConfmSn = seq;
            this.saveInfo.confmResn = temp.val();
            
            this.saveInfo.email = this.exportInfo.email;
			this.saveInfo.chargerNm = this.exportInfo.chargerNm;
			this.saveInfo.dtbxSn = this.exportInfo.dtbxSn;
			this.saveInfo.dtbxNm = this.exportInfo.dtbxNm;
            
            console.log(this.saveInfo);
            confirmMsg("사용 거절 하시겠습니까?", this.saveReject);
        },
        saveReject :function() {
			post(TID.REJECT, "/my/export/reject", this.saveInfo, this.callback);
		},
		rejectCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 거절되었습니다.", this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
    }
});
