
/*
 * @name : farmReg.js
 * @date : 2023-05-14
 * @author : yhm
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
const TID = {
    SEARCH_USER : {value: 0, name: "search", code: "S"},
    SAVE        : {value: 0, name: "save", code: "I"}
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
            messages : "",
            saveInfo: {
                farmNm    : "",
                fmhsNo    : "",
                fmhsAddr  : "",
                frmrNm    : "",
                frmrTelno : ""
            },

        };
    },
    mounted:function(){
        this.getUserInfo();
        if(!isNull(this.questNo)){
            this.faqReg = false;
            this.getFaqView();
        }


    },
    methods:{
        // 취소 클릭(목록 이동)
        onclickCancel: function () {
			location.href = "/farm/list";

        },

        // 저장 메소드 호출
        onclickSave:function () {
            if(!this.saveInfo.farmNm){
                alertMsg("농장명은 필수입니다.", this.$refs.farmNm );
                return false;
            }

            if(!this.saveInfo.fmhsNo){
                alertMsg("농가번호는 필수입니다.", this.$refs.fmhsNo );
                return false;
            }

            if(!this.saveInfo.fmhsAddr){
                alertMsg("농가주소는 필수입니다.", this.$refs.fmhsAddr );
                return false;
            }
            
            if(!this.saveInfo.frmrNm){
                alertMsg("농장주명은 필수입니다.", this.$refs.frmrNm );
                return false;
            }
            
            if(!this.saveInfo.frmrTelno){
                alertMsg("농장주전화번호는 필수입니다.", this.$refs.frmrTelno );
                return false;
            }

            confirmMsg("저장하시겠습니까?",this.save);
        },
        save: function() {
            post(TID.SAVE,
                "/farm/reg",
                this.saveInfo,
                this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    break;
                case "usrInfo":
                    if (results.success) {
                        this.userInfo = results.response;
                    } else {
                        alertMsg(results.error.message);
                    }
                    break;
                case "INQRY_TYCD":
                    this.questionTypeList = results.response;
                    setTimeout(function() {
                        loadSelect();
                    },300);
                    break;
                case TID.SAVE:
                    this.saveCallback(results);
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                /*this.faq      = results.response;
                this.saveInfo.questRspnsSj = this.faq.questRspnsSj;
                this.saveInfo.questRspnsCn = this.faq.questRspnsCn;
                this.saveInfo.questNo = this.faq.questNo;
                this.saveInfo.questRspnsTycd  = this.faq.questionTypeCode;*/
            } else {
                //console.log(results);
                alertMsg(results.error.message);
            }
        },
        saveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 저장되었습니다.",this.onclickView);
                location.href = "/farm/list";

            } else {
                console.log(results);
                
            }
        }
    }
});