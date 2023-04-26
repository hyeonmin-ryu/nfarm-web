let appMain;
let uploadMain;
const TID = {
    SEARCH: {value: 0, name: "search", code: "S"},
    INFO: {value: 0, name: "info"},
    SAVE: {value: 0, name: "save"},
    CHG: {value: 0, name: "chg"},

};

window.onload = function(){
    appMain = new Vue({
        el: '#maincontentswrap',
    });
    uploadMain = new Vue({
        el: '#uploadcontentswrap',
    });

}
Vue.component('maincontents', {
    template : "#main-template",
    data: function() {
        return {
            joinStatCode: [],
            info : {
                agencyName:"",
                insttTyCd:"",
                agencyTypeName:"",
                blNumber:"",
                ceoName:"",
                diseaseCode:"",
                diseaseName:"",
                diseaseManagerYn:"",
                diseaseManagerName:"",
                joinStatCode:"",
                joinStatName:"",
                nCloudId:"",
                nCloudObjStorageId:"",
                nCloudAccessKey:"",
                nCloudSecretKey:"",
                userEmail:"",
                userName:"",
                userPhoneNumber:"",
                userRoleSecd:"",
                parentUserName:"",
                parentUserYn:false,
                parentYn:false,
            },
            memberText:"",
            managerYn:false,
            messages: "",
        };
    },
    mounted:function(){

        codeId = "SBSCRB_STTUS_CD";
        getCodeList(codeId, this.callback);

        this.getUserInfo();

    },
    methods:{
        callback: function(tid, results){

            switch (tid) {
                case "SBSCRB_STTUS_CD":
                    this.joinStatCode = results.response;
                    break;
                case TID.INFO:
                    this.getUserInfoCallback(results);
                    break;
                case TID.SAVE:
                    this.onclickSaveCallback(results);
                    break;
                case TID.CHG:
                    this.onclickChgManageCallback(results);
                    break;
            }

        },
        getUserInfo : function(){
            get(TID.INFO, "/user/my/info", null,this.callback);
        },
        getUserInfoCallback : function(results){

            let item = results.response;

            console.log(item);
            
            this.info.insttNm = item.insttNm;
            this.info.rprsntvNm = item.rprsntvNm;
            this.info.bsnmNO = item.bsnmNO;
            this.info.dissNm = item.dissNm;
            this.info.sbscrbSttusCd = item.sbscrbSttusCd;
			this.info.insttTyCd = item.insttTyCd;
            this.info.ncloudAccesKey = item.ncloudAccesKey;
            this.info.ncloudId = item.ncloudId;
            this.info.ncloudObjStrgeId = item.ncloudObjStrgeId;
            this.info.ncloudScrtyKey = item.ncloudScrtyKey;
            this.info.email = item.email;
            this.info.chargerNm = item.chargerNm;
            this.info.chargerMoblphonNo = item.chargerMoblphonNo;
            this.info.userRoleSecd = item.userRoleSecd;
            this.info.userId = item.userId;
			this.info.dissRspnberYn = item.dissRspnberYn;
			this.info.dissRspnberUserNm = item.dissRspnberUserNm;
            this.memberText = this.info.insttTyCd + "(" + this.info.userRoleSecd + ")";
            if(!isNull(item.parentUserInfo)){
                this.info.parentUserName =item.parentUserInfo.userName;
                this.info.parentYn = true;
            }else{
                this.info.parentUserName = "지정된 질병책임자가 없습니다.";
                this.info.parentYn = false;
            }

            if(item.dissRspnberYn === "Y"){
                this.info.diseaseManagerName = "질병책임자";
                this.managerYn = true;
            }else if(item.dissRspnberYn === "N"){
                this.info.diseaseManagerName = "데이터업로더";
                this.info.parentUserYn = true;

            }else{
                this.info.diseaseManagerName = "";
            }
        },
        onclickUploderPop : function(){
            uploadMain.$refs.uploadcontents.loadUploader();
            fnOpenPopup('uploaderModal');
        },
        onclickMovePage : function(){
          location.href="/my/userPasswd?menuId="+myMenuId;
        },
        onkeyupHanName : function(e){   // 이름
            const numHan = /[^ㄱ-힣a-zA-Z]/gi;        // 한글,영문
            this.info.userName = e.target.value.replace(numHan, "");
        },
        isFormValid : function(){
            let param =[
                    {value:this.info.chargerNm, title:"담당자이름", ref:this.$refs.userName},
                    {value:this.info.chargerMoblphonNo, title:"담당자휴대전화", ref:this.$refs.userPhoneNumber},
                ];

            if(!isValid(param)) return false;

            if(this.info.userRoleSecd!=="ADMIN" && this.info.insttTyCd === 'COMP' || this.info.diseaseManagerYn === 'Y'){
                param =[
                    {value:this.info.nCloudId, title:"네이버클라우드 아이디", ref:this.$refs.nCloudId},
                    {value:this.info.nCloudObjStorageId, title:"오브젝트 스토리지 아이디", ref:this.$refs.nCloudObjStorageId},
                    {value:this.info.nCloudAccessKey, title:"네이버클라우드 액세스키", ref:this.$refs.nCloudAccessKey},
                    {value:this.info.nCloudSecretKey, title:"네이버클라우드 시크릿키", ref:this.$refs.nCloudSecretKey},
                ];

                if(!isValid(param)) return false;
                
                let result = regExp("OBJID", this.info.nCloudObjStorageId);
	            if(result === "N" || isNull(this.info.nCloudObjStorageId)){
					alertMsg("오브젝트 스토리지 아이디 입력값의 형태가 일치하지 않습니다.　　　　ncp-0000000-0 형태로 입력해주세요.");
	                this.$refs.nCloudObjStorageId.focus();
	                return false;
	            }
                
            }

            return true;
        },

        onclickSave : function(){ // 승인
            if(!this.isFormValid()){
                return false;
            }

            confirmMsg("수정 하시겠습니까?",this.save);
        },
        save: function(){
            post(TID.SAVE,"/user/my/info", this.info, this.callback);
        },
        onclickSaveCallback : function(results){

            if (results.success) {
                alertMsg("정상적으로 수정 되었습니다.");
            } else {
                alertMsg(results.error.message);
            }
        },
        popupUploader : function(){ // 데이터 업로더팝업
            openPopupUploader(this.info.userId);
        },
        onclickChgManage : function(){
            confirmMsg("질병책임자를 변경하시겠습니까?", this.saveManage);
        },
        saveManage: function() {
            post(TID.CHG,"/user/my/manager", null, this.callback);
        },
        onclickChgManageCallback : function(results){
            if (results.success) {
                alertMsgRtn("정상적으로 수정 되었습니다.", this.getUserInfo);
            }else{
                alertMsg(results.error.message);
            }
        }

    },

});

Vue.component('uploadcontents', {
    template: "#upload-template",
    data: function () {
        return {

            insttNm:"",
            dissNm:"",
            chargerNm:"",
            upperUserId:"",
            uploaderList:[],
            messages: "",
            checked:[],

        };
    },
    mounted: function () {

    },
    methods: {
        callback: function(tid, results){

            switch (tid) {
                case TID.INFO:
                    this.loadUploaderCallback(results);
                    break;
                case TID.LIST:
                    this.getListCallback(results);
                    break;
                case TID.SAVE:
                    this.onclickUploaderSaveCallback(results);
                    break;

            }

        },
        loadUploader: function(){
            get(TID.INFO, "/user/my/info", null,this.callback);
        },
        onclickUploaderSave: function(){
            for(let i=0;i < this.uploaderList.length;i++){
                let data = null;
                for (let y=0;y < this.checked.length;y++){

                    if(this.checked[y] ===this.uploaderList[i].userId){
                        data = this.upperUserId;
                    }

                }
                this.uploaderList[i].upperUserId = data;
            }
           confirmMsg("저장 하시겠습니까?",this.save);
        },
        save:function(){
            post(TID.SAVE,"/user/my/uploader",this.uploaderList,this.callback);
        },
        loadUploaderCallback : function(result) {
            let data = result.response;
            this.insttNm= data.insttNm;
            this.dissNm = data.dissNm;
            this.chargerNm = data.chargerNm;
            this.upperUserId = data.userId;

            // 업로더 조회
            get(TID.LIST, "/user/my/uploader",null,this.callback);

        },
        getListCallback : function(result){
            this.uploaderList=result.response;
            for(let i=0;i < this.uploaderList.length;i++){
                if(this.uploaderList[i].upperUserId === this.upperUserId){
                    this.checked.push(this.uploaderList[i].userId);
                }

            }
        },
        onclickUploaderSaveCallback : function(result){
			this.checked=[];
            if (result.success) {
                alertMsgRtn("정상적으로 수정 되었습니다.", this.saveRtn);
            } else {
                alertMsg(result.error.message);
            }
        },
        saveRtn : function(){
            fnClosePopup('uploaderModal');
        },


    },
});