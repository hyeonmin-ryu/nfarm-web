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
				exportReqStatCodeDesc : "",
				dataBoxReq : {},
				reqstDt : "",
				acceptDate : "",
				approveDate : "",
				cancelDate : "",
				exportApproverInfo :[],
			},
			apprInfo : {
				tkoutReqstNo :"",
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
        onclickCancel : function () { // 취소 클릭
        	
        	this.apprInfo.tkoutReqstNo = this.tkoutReqstNo;
            console.log(this.apprInfo);
            confirmMsg("취소하시겠습니까?", this.saveCancel);
        },
        saveCancel : function() {
			post(TID.CANCEL, "/my/export/cancel", this.apprInfo, this.callback);
		},
		onclickDownload : function () { // 취소 클릭
        	
        	this.apprInfo.tkoutReqstNo = this.tkoutReqstNo;
            
            confirmMsg("다운로드 하시겠습니까?", this.download);
        },      
         download : function() {
//			post(TID.DOWNLOAD, "/my/export/download", this.apprInfo, this.callback);
			
			axios({
                url: "/my/export/download?tkoutReqstNo=" + this.apprInfo.tkoutReqstNo ,
                method: "GET",
                responseType: "blob",
            }).then((response) => {
				var tempDate = new Date();
				const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', "반출파일_"+this.apprInfo.tkoutReqstNo+".zip");
                document.body.appendChild(link);
                link.click(); // Start download
                document.body.removeChild(link); // Clean up and remove the link
            });
			
		},   
        
        callback: function (tid, results) {
            switch (tid) {
                case TID.INFO:
                    this.infoCallback(results);
                    break;
                case TID.CANCEL:
                    this.cancelCallback(results);
                    break;
            }
        },
        infoCallback : function(results){
            const item = results.response;
            
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
			this.exportInfo.exportApproverInfo = item.exportApproverInfo;
			
			console.log(this.exportInfo);
			
			//this.exportInfo.exportApproverInfo.agencyInfo = item.agencyInfo;
			//this.exportInfo.exportApproverInfo.userInfo = item.userInfo;
			
        },
        cancelCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 취소되었습니다.",this.onclickList);
            } else {
                alertMsg(results.error.message);
            }
        },
    }
});
