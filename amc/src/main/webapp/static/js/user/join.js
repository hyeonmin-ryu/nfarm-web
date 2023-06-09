let appMain;
let codeId;
let agree1;
let agree2;
let next;

window.onload = function(){
    appMain = new Vue({
        el: '#maincontentswrap',
    });

}

Vue.component('maincontents', {
    template : "#main-template",
    data: function() {
        return {
            join: {
                agree1: agree1,
                agree2: agree2,
                next : next,
            },
            info: {
                userRoleSecd:"",
                email: "",
                certCode: "",
                certTxt: "",
                agencyTypeList: [],
                insttTyCd: "",
                insttId:"",
                insttNm:"",
                rprsntvNm: "",
                bsnmNo: "",
                passOk1: "Y",
                password1: "",
                password2: "",
                passOk2: "N",
                inputPw:"",
                dissCd: "",
                diseaseList: [],
                chargerNm: "",
                chargerMoblphonNo: "",
                dissRspnberYn: "",
                ncloudId: "",
                ncloudObjStrgeId:"",
                ncloudAccesKey: "",
                ncloudScrtyKey: "",

            },
            form: {
                action: "/regUserInfo",
                method: "POST",
            },
            message1: "",
            message2: "",
            message3: "",
            message4: "",
            certNumber:"",
            cond: {
                page: 1,
                size: 5,
                agencyName: "",
                agencyTypeCode: "",
                sort: "agencyName"
            },
            agencyList: [],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
        };
    },
    mounted:function(){

        if(this.join.next === "Y"){

          if(isNull(this.join.agree1) && isNull(this.join.agree2)) {

              alertMsg("잘 못된 접근방식 입니다.");
              location.href = "/";
              return;
          }
        }

        codeId = "INSTT_TY_CD";
        getCodeList(codeId, this.callBack);     //

        codeId = "DISS_CD";
        getCodeList(codeId, this.callBack);
        setTimeout(function() {
            loadSelect();
        },1000);
    },
    methods:{

        onclickBack : function(){
            location.href="/";

        },
        onclickNext : function(){
            if(!this.join.agree1 || !this.join.agree2 ){
                alertMsg("모든 약관에 동의 해주세요.");
                return;
            }
            document.frm.submit();
        },
        callBack : function(tid, result){   // 공통코드 조회

            switch (tid){
                case "INSTT_TY_CD":
                    this.info.agencyTypeList = result.response;
                    break;
                case "DISS_CD":
                    this.info.diseaseList = result.response;
                    break;
                case "Cert":
                    if(result.success) {
                        alertMsg("인증 메일이 발송 되었습니다.");
                        this.certNumber = result.response.certNumber;
                    }else{
                        alertMsg(result.error.message);
                        this.$refs.userEmail.focus();
                    }
                    break;
                case "SAVE":
                    if(result.success){
                        alertMsgRtn("정상적으로 등록되었습니다.", this.saveRtn);
                    }else{
                        alertMsg(result.error.message);
                    }
                    break;
                default :
                    break;

            }
        },

        
        // 저장후 다음페이지
        saveRtn : function(){
            location.href="/joinComplete";
        },
        onclickAgency : function(){
            // 변경시 기관정보 초기화
            this.info.insttId="";
            this.info.insttNm ="";
            this.info.rprsntvNm="";
            this.info.bsnmNo="";

            if(this.info.insttTyCd === "01") {
                this.info.dissRspnberYn = "";
            }
        },
        onclickCertSend : function(){ // 인증 email 전송

            let result = regExp("EMAIL", this.info.email);
            if(result === "N" || isNull(this.info.email)){
                this.message1 = "E-Mail 형식으로 입력해주세요.";
                this.$refs.userEmail.focus();
            }else{
                this.message1 ="";
                console.log(this.info);
                post("Cert", "/user/checkAndCert/mail", this.info, this.callBack);
            }
        },
        onclickCertChk : function() { // 인증번호 확인

            if(!isNull(this.certNumber) && this.info.certCode === this.certNumber){
                this.message2 ="확인되었습니다.";
                this.info.passOk1="Y";

            }else{
                this.message2 ="인증번호를 다시 확인해 주세요.";
                this.$refs.certCode.focus();
                this.info.passOk1="N";
            }
        },
        onblurPassChk1 : function(){    //비밀번호 체크
            let result = regExp("PASS2", this.info.password1);
            this.message3 = result;
            if(!isNull(result)){
               this.$refs.password1.focus();
            }

        },
        onblurPassChk2 : function(){    //비밀번호 확인

            if(this.info.password2 !== ""){
                if(this.info.password1 !== this.info.password2){
                    this.message4 ="비밀번호가 일치 하지 않습니다.";
                    this.info.password2="";
                    this.info.passOk2 ="N";
                    this.$refs.password2.focus();
                    return false;
                }else{
                    this.info.passOk2 ="Y";
                    this.message4 ="";
                }
            }
        },
        popupAgency : function(){         // 기관 팝업
            if(isNull(this.info.insttTyCd)){
                alertMsg("회원 구분을 선택해주세요.");
                return;
            }
            console.log(this.info.insttTyCd);
            fnOpenPopup('agencyModal', this.info.insttTyCd);
            //openPopupAgency(this.info.insttTyCd);
        },

        onkeyupCertCode : function(e){  // 인증코드
            const numExp = /[^0-9]/gi;        // 숫자
            this.info.certCode = e.target.value.replace(numExp, "");
        },
        onkeyupHanName : function(e){   // 이름
            const numHan = /[^ㄱ-힣a-zA-Z]/gi;        // 한글,영문
            this.info.userName = e.target.value.replace(numHan, "");
        },
        onkeyupPhoneNumber : function(e){   // 핸드폰
            const numExp = /[^0-9]/gi;        // 숫자
            this.info.userPhoneNumber = e.target.value.replace(numExp, "");
        },
        onkeyupEmail : function(e){
            this.info.passOk1="N";
            //const email = /[^0-9a-zA-Z@\._-]/gi; // 이메일
            const email = /[ㄱ-힣\s]/gi; // 이메일
            this.info.email = e.target.value.replace(email, "");
        },
        onkeyupObjId : function(e){
            //const email = /[^0-9a-zA-Z@\._-]/gi; // 이메일
            const id = /[ㄱ-힣\s]/gi; // 이메일
            this.info.nCloudObjStorageId = e.target.value.replace(id, "");
        },        
        
        
        callbackPopupAgency : function(item){   // 기관 팝업 Callback

            this.info.insttId = item.insttId;
            this.info.insttNm = item.insttNm;
            this.info.rprsntvNm = item.rprsntvNm;
            this.info.bsnmNo = item.bsnmNo;
        },
        onclickManager : function(){    // 데이터업로더시 초기화
              this.info.nCloudId = "";
              this.info.nCloudAccessKey = "";
              this.info.nCloudSecretKey = "";
              this.info.nCloudObjStorageId = "";

        },
        isFormValid : function(){
            
            // 질병명
            let code = document.querySelector("#diseaseCode").getAttribute("data-value");

            this.info.dissCd=code;
            console.log(this.info.diseaseCode + " / " + code);


            let param =[
                {value:this.info.insttTyCd, title:"회원구분", ref:document.getElementById("rdo0"), type:"S", msg:""},
                {value:this.info.email, title:"이메일", ref:this.$refs.userEmail},
                {value:this.info.certCode, title:"이메일인증코드", ref:this.$refs.certCode},
            ];

            if(!isValid(param)) return false;
this.info.passOk1 = "Y";
            if(this.info.passOk1 === "N"){
                alertMsg("이메일 인증확인은 필수입니다.", this.$refs.certCode);
                return false;
            }

            param =[
                {value:this.info.password1, title:"비밀번호", ref:this.$refs.password1},
                {value:this.info.password2, title:"비밀번호확인", ref:this.$refs.password2},
            ];

            if(!isValid(param)) return false;

            if(this.info.passOk2 === "N"){
                alertMsg("비밀번호가 일치 하지 않습니다.", this.$refs.password2);
                return false;
            }

            param =[
                {value:this.info.insttNm, title:"기관명", ref:this.$refs.popupAgency, msg:"기관팝업을 클릭하여 기관명을 선택해주세요."},
                {value:this.info.dissCd, title:"질병명", ref:this.$refs.diseaseCode, type:"S"},
                {value:this.info.chargerNm, title:"담당자이름", ref:this.$refs.userName},
                {value:this.info.chargerMoblphonNo, title:"담당자휴대전화", ref:this.$refs.userPhoneNumber, type:"I"},
            ];

            if(!isValid(param)) return false;


            if(this.info.insttTyCd !=="01" && isNull(this.info.dissRspnberYn)){
                alertMsg("질병책임자여부를 선택해주세요.", this.$refs.diseaseManagerY);
                return false;
            }

            if(this.info.dissRspnberYn === 'Y'){
                param =[
                    {value:this.info.ncloudId, title:"네이버클라우드 아이디", ref:this.$refs.nCloudId},
                    {value:this.info.ncloudAccesKey, title:"네이버클라우드 액세스키", ref:this.$refs.nCloudAccessKey},
                    {value:this.info.ncloudScrtyKey, title:"네이버클라우드 시크릿키", ref:this.$refs.nCloudSecretKey},
                    {value:this.info.ncloudObjStrgeId, title:"오브젝트 스토리지 아이디", ref:this.$refs.nCloudObjStorageId},
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
        btnSave : function(){
            if(!this.isFormValid()){
               return false;
            }
            if(this.info.passOk2 === "Y"){
                this.info.inputPw=this.info.password1;
            }

            if(this.info.insttTyCd ==="01"){
                this.info.userRoleSecd="ROLE_USER";
            }else{
                if(this.info.dissRspnberYn === "Y"){
                    this.info.userRoleSecd ="ROLE_MANAGER";
                }else{
                    this.info.userRoleSecd = "ROLE_UPLOADER";
                }
            }

            confirmMsg("등록 하시겠습니까?", this.save);

        },
        save : function(){
            post("SAVE", "/user/join", this.info, this.callBack);
        }
    },

});

