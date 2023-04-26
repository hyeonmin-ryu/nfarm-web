let appMain;
let exportReqSeq;
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
				mailTo : []
			},
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
            }
        },
        infoCallback : function(results){
            const item = results.response;
            console.log(results.response);
            this.exportInfo.tkoutReqstNo = item.tkoutReqstNo;
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
        	
        	var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        	
        	if(!this.apprInfo.tkoutSvrIpAdres){
                alertMsg("반출서버 접속 IP는 필수입니다.", this.$refs.exportServerIp );
                return false;
            }
            
            if(ipformat.test(this.apprInfo.tkoutSvrIpAdres) === false) {
				alertMsg('반출서버 접속 IP가 형식에 맞지 않습니다.', this.$refs.exportServerIp);
				this.apprInfo.exportServerIp = '';
				return;
			}
            
            if(!this.apprInfo.tkoutSvrUserId){
                alertMsg("반출서버 접속 ID는 필수입니다.", this.$refs.exportServerId );
                return false;
            }
            
            if(!this.apprInfo.tkoutSvrUserPassword){
                alertMsg("반출서버 접속 암호는 필수입니다.", this.$refs.exportServerPw );
                return false;
            }
        
        	this.apprInfo.tkoutReqstNo = this.tkoutReqstNo;
        	this.apprInfo.email = this.exportInfo.email;
			this.apprInfo.chargerNm = this.exportInfo.chargerNm;
			this.apprInfo.dtbxSn = this.exportInfo.dtbxSn;
			this.apprInfo.dtbxNm = this.exportInfo.dtbxNm;
        	
        	/*for(var i = 0; this.exportInfo.exportApproverInfo.length > i; i++) {
				
				if(i === 0 || this.apprInfo.mailTo.find(x => x === this.exportInfo.exportApproverInfo[i].userInfo.userEmail) > -1) {
					this.apprInfo.mailTo.push(this.exportInfo.exportApproverInfo[i].userInfo.userEmail);
				}
			}*/
        	
        	console.log(this.apprInfo);
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
    }
});
