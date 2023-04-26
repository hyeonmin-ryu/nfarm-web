let appMain;
let dtbxReqNo;
let menuId;

const TID = {
    CANCEL : {value: 0, name: "save",   code: "I"},
    COMPLETE : {value: 0, name: "save",   code: "I"},
    DELETE : {value: 0, name: "save",   code: "I"},
    INFO : {value: 0, name: "search", code: "S"},
    
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
                userConList:[],
                serverList:[],
                bucketList:[],
                dataBoxReqHis:[],
            },
            saveInfo : {
				dtbxSn:"",
				dtbxReqNo:"",
                stateCode:"",
				userConList:[],
				serverList:[],
			},
			apprInfo : {
				dtbxSn:"",
				dtbxReqNo:"",
				rejectReason:"",
				dtbxReqstSttusNm:"",
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
        onclickDelete : function () { // 삭제 클릭
	        this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            this.apprInfo.email = this.dataBoxInfo.email;
            this.apprInfo.chargerNm = this.dataBoxInfo.chargerNm;
            this.apprInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
            
            confirmMsg("삭제하시겠습니까?", this.saveDelete);
        },
        saveDelete :function() {
			post(TID.DELETE, "/my/databox/delete", this.apprInfo, this.callback);
		},
        onclickCancel : function () { // 접수 클릭
        	
        	this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            
            confirmMsg("취소하시겠습니까?", this.saveCancel);
        },
        saveCancel : function() {
	
	        this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            this.apprInfo.email = this.dataBoxInfo.email;
            this.apprInfo.chargerNm = this.dataBoxInfo.chargerNm;
            this.apprInfo.dtbxNm = this.dataBoxInfo.dtbxNm;
	
			post(TID.CANCEL, "/my/databox/cancel", this.apprInfo, this.callback);
		},
        
        onclickComple : function () { // 설치완료 클릭
        
        	this.apprInfo.dtbxSn = this.dataBoxInfo.dtbxSn;
            this.apprInfo.dtbxReqNo = this.dataBoxInfo.dtbxReqNo;
            
            confirmMsg("설치 완료하시겠습니까?", this.saveComplete);
        },
        saveComplete : function() {
			post(TID.COMPLETE, "/my/databox/complete", this.apprInfo, this.callback);
		},     
        callback: function (tid, results) {
            switch (tid) {
                case TID.INFO:
                    this.infoCallback(results);
                    break;
                case TID.CANCEL:
                    this.cancelCallback(results);
                    break;
                case TID.DELETE:
                    this.deleteCallback(results);
                    break;
           	    case TID.COMPLETE:
                    this.completeCallback(results);
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

            
            this.dataBoxInfo.userConList = item.userConList;
            
            
            if(!isNull(item.serverList)) {
                this.dataBoxInfo.serverList = item.serverList;
            }
            
            this.dataBoxInfo.naswSize = item.naswSize;
            
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
					
					if(item.dataBoxReqHis[i].rejectReason != null) { //신청거절
						obj.gubun = '사용신청';
						obj.rejectReason = item.dataBoxReqHis[i].rejectReason;
						obj.reqDt = item.dataBoxReqHis[i].reqDt;
						obj.processDt = item.dataBoxReqHis[i].processDt;
					} else if(!item.dataBoxReqHis[i].delRejectReason != null) { //삭제거절
						obj.gubun = '삭제신청';
						obj.rejectReason = item.dataBoxReqHis[i].delRejectReason;
						obj.reqDt = item.dataBoxReqHis[i].deleteReqstDt;
						obj.processDt = item.dataBoxReqHis[i].deleteProcessDt;
					}
					
					this.dataBoxInfo.dataBoxReqHis.push(obj);
				}
			}
            
        },
        cancelCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 취소되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
        completeCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 설치완료신청되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },        
        deleteCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 삭제신청되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        }
    }
});
