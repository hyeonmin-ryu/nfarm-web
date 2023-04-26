let appMain;
let dtbxReqNo;
let menuId;

const TID = {
    APPROVE : {value: 0, name: "save",   code: "I"},
    REJECT : {value: 0, name: "save",   code: "I"},
    ACCEPT : {value: 0, name: "save",   code: "I"},
    INFO : {value: 0, name: "search", code: "S"},
    DOWNLOAD : {value: 0, name: "save", code: "I"}
    
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
	        dtbxReqNo : dtbxReqNo,
	        radioList: [{"name":"HDD","desc":"HDD"},{"name":"SSD","desc":"SSD"}],
	        sumBucketSize: 0,
	        dataBoxInfo : {
				dtbxSn:"",
				dtbxReqNo:"",
                chargerNm:"",
                chargerMoblphonNo:"",
                email:"",
                insttNm:"",
                dtbxReqTyCd:"",
                dtbxReqTyNm:"",
                dtbxReqstSttusCd:"", //desc
                dtbxReqstSttusNm:"",
                dtbxOperAcntId:"", //운영계정
                reqDt:"",
                rceptDt:"",
                processDt:"",
                canclDt:"",
                dtbxNm:"",
                deleteReqstDt:"",
                deleteRceptDt:"",
                deleteProcessDt:"",
                deleteCanclDt:"",
                naswSize:"",
                manageSvrIpAdres:"",
                manageSvrUserId:"", //
                manageSvrUserPassword:"",
                naswSize:"",
                userConList:[],
                serverList:[],
                bucketList:[],
                dataBoxReqHis:[],
            },
            saveInfo : {
				dtbxSn:"",
				dtbxReqNo:"",
                dtbxOperAcntId:"", //운영계정
                manageSvrIpAdres:"", //
                manageSvrUserId:"", //
                manageSvrUserPassword:"", //
                stateCode:"",
				userConList:[],
				serverList:[],
			},
			apprInfo : {
				dtbxSn:"",
				dtbxReqNo:"",
				rejectResn:"",
				dtbxReqstSttusCd:"",
			},
        };
    },
    mounted:function(){
        let url ="/mypage/data/req/"+this.dtbxReqNo;
        get(TID.INFO, url, null,this.callback);
        
        setTimeout(function() {
            loadSelect();
        },1000);
        
    },
    methods:{
        onclickList: function () { // 목록 클릭
            location.href = "/my/databox/list?menuId="+myMenuId;
        },
        onclickReject : function () { // 거절 클릭
        
        	if(!this.apprInfo.rejectResn){
                alertMsg("거절사유를 입력해주세요.", this.$refs.rejectResn );
                return false;
            }
            
            this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            this.apprInfo.dtbxReqstSttusCd = this.dataBoxInfo.dtbxReqstSttusCd;;
            this.apprInfo.email = this.dataBoxInfo.email;
            this.apprInfo.chargerNm = this.dataBoxInfo.chargerNm;
            this.apprInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
            
            confirmMsg("승인 거절하시겠습니까?", this.saveReject);
        },
        saveReject :function() {
			post(TID.REJECT, "/my/admin/databox/reject", this.apprInfo, this.callback);
		},
        onclickAccept : function () { // 접수 클릭
        	
        	this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            
            this.apprInfo.email = this.dataBoxInfo.email;
            this.apprInfo.chargerNm = this.dataBoxInfo.chargerNm;
            this.apprInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
            
            confirmMsg("접수하시겠습니까?", this.saveAccept);
        },
        saveAccept :function() {
			post(TID.ACCEPT, "/my/admin/databox/accept", this.apprInfo, this.callback);
		},
		
        onclickDownload : function () { // 파일다운로드 클릭
        	if(this.dataBoxInfo.dtbxReqstSttusCd === 'COMPLETE' ||  this.dataBoxInfo.dtbxReqstSttusCd === 'APPROVE' ){
			
				this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
	            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
	            
	            this.apprInfo.email = this.dataBoxInfo.email;
	            this.apprInfo.chargerNm = this.dataBoxInfo.chargerNm;
	            this.apprInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
	            
	            confirmMsg("다운로드 하시겠습니까?", this.dataDownload);
	
			} else {
				
				alertMsg("신청자의 프로그램 설치 완료 이후 데이터 다운로드가 가능합니다.");
                	return false;
			}

        },
        dataDownload :function() {
	
	
			post(TID.DOWNLOAD, "/my/admin/databox/download", this.apprInfo, this.callback);
		},		
		
		onclickDown : function(dtbxReqNo , useStorageSeq) {
			
	        var obj = {};
            obj.dtbxReqNo = dtbxReqNo;
            obj.useStorageSeq = useStorageSeq
			
            fnOpenPopup('downloadModal', obj);
        },
		
        onclickSave : function (btn) { // 승인 클릭
       		var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    		var reg_email =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        
        	for(var i = 0; this.dataBoxInfo.userConList.length > i; i++) { //데이터박스 동시 접속자
        	
        		var user01 = $('#user01' + String(i)); //SSL VPN ID
        		var user02 = $('#user02' + String(i)); //SSL VPN 암호
        		var user03 = $('#user03' + String(i)); //IP
        		var user04 = $('#user04' + String(i));
        		var user05 = $('#user05' + String(i));
        		
        		if(!user01.val()) {
					alertMsg("데이터박스 동시접속자 SSL VPN ID를 입력해 주세요.", user01 );
                	return false;
				}
				if(!user02.val()) {
					alertMsg("데이터박스 동시접속자 SSL VPN 암호를 입력해 주세요.", user02 );
                	return false;
				}
				if(!user03.val()) {
					alertMsg("데이터박스 동시접속자 접속 서버 IP를 입력해 주세요.", user03 );
                	return false;
				}
				if(ipformat.test(user03.val()) === false) {
					alertMsg('접속 서버 IP가 형식에 맞지 않습니다.', user03);
					user03.val('');
					return;
				}
				if(!user04.val()) {
					alertMsg("데이터박스 동시접속자 접속 서버 ID를 입력해 주세요.", user04 );
                	return false;
				}
				if(!user05.val()) {
					alertMsg("데이터박스 동시접속자 접속 서버 암호를 입력해 주세요.", user05 );
                	return false;
				}
				
        		var arr = {};
        		arr.userSn = this.dataBoxInfo.userConList[i].userSn;
        		arr.sslVpnUserId = user01.val();
        		arr.sslVpnUserPassword = user02.val();
        		arr.cnncSvrIpAdres = user03.val();
        		arr.cnncSvrUserId = user04.val();
        		arr.cnncSvrUserPassword = user05.val();
        		
        		this.saveInfo.userConList.push(arr);
			}
			
			for(var i = 0; this.dataBoxInfo.serverList.length > i; i++) { //서버신청
        	
        		var server01 = $('#server01' + String(i)); //IP
        		var server02 = $('#server02' + String(i));
        		var server03 = $('#server03' + String(i));
        		
        		if(!server01.val()) {
					alertMsg("서버 접속정보 IP를 입력해 주세요.", server01 );
                	return false;
				}
				if(ipformat.test(server01.val()) === false) {
					alertMsg('서버 접속정보 IP가 형식에 맞지 않습니다.', server01);
					server01.val('');
					return;
				}
				if(!server02.val()) {
					alertMsg("서버 접속정보 아이디를 입력해 주세요.", server02 );
                	return false;
				}
				if(!server03.val()) {
					alertMsg("서버 접속정보 암호를 입력해 주세요.", server03 );
                	return false;
				}
        		
        		var arr = {};
        		arr.svrSn = this.dataBoxInfo.serverList[i].svrSn;
        		arr.svrIpAdres = server01.val();
        		arr.svrUserId = server02.val();
        		arr.svrUserPassword = server03.val();
        		
        		this.saveInfo.serverList.push(arr);
			}
			
			
			if(!this.$refs.manageSvrIpAdres.value){
                alertMsg("어드민서버IP 정보는 필수입니다.", this.$refs.manageSvrIpAdres );
                return false;
            }
			
			if(ipformat.test(this.$refs.manageSvrIpAdres.value) === false) {
				alertMsg('어드민서버 IP가 형식에 맞지 않습니다.', this.$refs.manageSvrIpAdres);
				this.$refs.manageSvrIpAdres.value = "";
				return;
			}
			
			
			if(!this.$refs.manageSvrUserId.value){
                alertMsg("어드민서버ID 정보는 필수입니다.", this.$refs.manageSvrUserId );
                return false;
            }
        
			if(!this.$refs.manageSvrUserPassword.value){
                alertMsg("어드민서버암호 정보는 필수입니다.", this.$refs.manageSvrUserPassword );
                return false;
            }
        
			if(!this.$refs.dtbxOperAcntId.value){
                alertMsg("운영계정은 필수입니다.", this.$refs.dtbxOperAcntId );
                return false;
            }
            if(reg_email.test(this.$refs.dtbxOperAcntId.value) === false) {
				alertMsg('운영계정을 이메일 형식에 맞게 입력해 주세요.', this.$refs.dtbxOperAcntId);
				return;
			}
            
            this.saveInfo.manageSvrIpAdres = this.$refs.manageSvrIpAdres.value;
            this.saveInfo.manageSvrUserId = this.$refs.manageSvrUserId.value;
            this.saveInfo.manageSvrUserPassword = this.$refs.manageSvrUserPassword.value;


            this.saveInfo.dtbxOperAcntId = this.$refs.dtbxOperAcntId.value;
            this.saveInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.saveInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            
            this.saveInfo.chargerNm = this.dataBoxInfo.chargerNm;
            this.saveInfo.email = this.dataBoxInfo.email;
            this.saveInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
            
            if(btn === 'approve') {
				confirmMsg("승인하시겠습니까?",this.save);
				this.saveInfo.stateCode = "approve";
			} else if (btn === 'create') {
				this.saveInfo.stateCode = "create";
				confirmMsg("생성하시겠습니까?",this.save);
			} else if (btn === 'update') {
				this.saveInfo.stateCode = "update";
				confirmMsg("수정하시겠습니까?",this.save);
			}
            
		},
		save :function() {
			post(TID.APPROVE, "/my/admin/databox/reg", this.saveInfo, this.callback);
		},
        callback: function (tid, results) {
            switch (tid) {
                case TID.INFO:
                    this.infoCallback(results);
                    break;
                case TID.APPROVE:
                    this.approveCallback(results);
                    break;
                case TID.REJECT:
                    this.rejectCallback(results);
                    break;
                case TID.ACCEPT:
                    this.acceptCallback(results);
                    break;
           	    case TID.DOWNLOAD:
           	    
           	        if (results.success) {
		                alertMsgRtn("정상적으로 접수되었습니다.",this.onclickList);
		                let url ="/mypage/data/req/"+this.dtbxReqNo;
			            get(TID.INFO, url, null,this.callback);
		                
		            } else {
		                alertMsg(results.error.message);
		            }
           	    

                    break;                      
                    
                    
            }
        },
        infoCallback : function(results){
            const item = results.response;
            
            this.dataBoxInfo.dtbxSn = item.dtbxSn;
            this.dataBoxInfo.dtbxReqNo = item.dtbxReqNo;
            
            this.dataBoxInfo.chargerNm = item.chargerNm;
            this.dataBoxInfo.chargerMoblphonNo = item.chargerMoblphonNo;
            this.dataBoxInfo.email = item.email;
        
            this.dataBoxInfo.insttNm = item.insttNm;
            
            this.dataBoxInfo.dtbxReqTyCd = item.dtbxReqTyCd;
            this.dataBoxInfo.dtbxReqTyNm = item.dtbxReqTyNm;
            this.dataBoxInfo.dtbxReqstSttusCd = item.dtbxReqstSttusCd;
            this.dataBoxInfo.dtbxReqstSttusNm = item.dtbxReqstSttusNm; //code
            this.dataBoxInfo.reqDt = item.reqDt;
            this.dataBoxInfo.rceptDt = item.rceptDt;
            this.dataBoxInfo.processDt = item.processDt;
            this.dataBoxInfo.canclDt = item.canclDt;
            this.dataBoxInfo.dtbxNm = item.dtbxNm;
            
            this.dataBoxInfo.deleteReqstDt = item.deleteReqstDt;
            this.dataBoxInfo.deleteRceptDt = item.deleteRceptDt;
            this.dataBoxInfo.deleteProcessDt = item.deleteProcessDt;
            this.dataBoxInfo.deleteCanclDt = item.deleteCanclDt;

            this.dataBoxInfo.manageSvrIpAdres = item.manageSvrIpAdres;
            this.dataBoxInfo.manageSvrUserId = item.manageSvrUserId;
            this.dataBoxInfo.manageSvrUserPassword = item.manageSvrUserPassword;
            
            
            console.log(!isNull(item.userConList));
            
            if(!isNull(item.userConList)) {
                this.dataBoxInfo.userConList = item.userConList;
            }
            
            
            if(!isNull(item.serverList)) {
                this.dataBoxInfo.serverList = item.serverList;
            }
            
            this.dataBoxInfo.naswSize = item.naswSize;
            this.dataBoxInfo.dtbxOperAcntId = item.dtbxOperAcntId;
            
            
            if(!isNull(item.bucketList)) {
                this.dataBoxInfo.bucketList = item.bucketList;
                
                for(var i = 0; this.dataBoxInfo.bucketList.length > i; i++) {
					this.sumBucketSize += (Math.ceil( ( ( this.dataBoxInfo.bucketList[i].bucketSize * 1) /1024/1024/1024) * 100) /100 * 1);
				}
				
				this.sumBucketSize = this.sumBucketSize.toFixed(2);
            }
            
            if(!isNull(item.dataBoxReqHis)) { //히스토리
            	for(var i = 0; item.dataBoxReqHis.length > i; i++) {
					var obj = {};
					
					if(item.dataBoxReqHis[i].rejectResn != null) { //신청거절
						obj.gubun = '사용신청';
						obj.rejectResn = item.dataBoxReqHis[i].rejectResn;
						obj.reqDt = item.dataBoxReqHis[i].reqDt;
						obj.processDt = item.dataBoxReqHis[i].processDt;
					} else if(!item.dataBoxReqHis[i].delrejectResn != null) { //삭제거절
						obj.gubun = '삭제신청';
						obj.rejectResn = item.dataBoxReqHis[i].delrejectResn;
						obj.reqDt = item.dataBoxReqHis[i].deleteReqstDt;
						obj.processDt = item.dataBoxReqHis[i].deleteProcessDt;
					}
					
					this.dataBoxInfo.dataBoxReqHis.push(obj);
				}
			}
            
            
        },
        approveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 처리되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
        rejectCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 처리되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
        acceptCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 접수되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        }
    }
});
