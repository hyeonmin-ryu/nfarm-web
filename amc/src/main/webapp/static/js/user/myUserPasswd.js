
/*
 * @name : popAgencySearch.js
 * @date : 2021-06-23 오후 1:05
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
const TID = {
    SAVE: {value: 0, name: "save", code: "S"}
};

window.onload = function () {
    appMain = new Vue({
        el: '#maincontentswrap',
    });
}

Vue.component('maincontents', {
    template: "#main-template",
    data: function () {
        return {
            info : {
                userSeq:"",
                inputOldPw:"",
                inputNewPw:"",
            },
            password1:"",
            password2:"",
            message1:"",
            message2:"",

        };
    },
    mounted: function () {

    },
    methods: {
        onblurPassChk1 : function(){    //비밀번호 체크
            let result = regExp("PASS2", this.password1);
            this.message1 = result;
            if(!isNull(result)){
                this.$refs.password1.focus();
            }

        },
        onblurPassChk2 : function(){    //비밀번호 확인

            if(this.password2 !== ""){
                if(this.password1 !== this.password2){
                    this.message2 ="비밀번호가 일치 하지 않습니다.";
                    this.$refs.password2.focus();
                    return false;
                }else{
                    this.message2 ="";
                }
            }
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SAVE:
                    this.onclickSaveCallback(results);
                    break;
            }
        },
        onclickBack : function(){
            location.href="/my/userModify?menuId="+myMenuId;
        },
        isFormValid : function() {
            this.message1 ="";
            this.message2 ="";

            let param = [
                {value: this.info.inputOldPw, title: "현재비밀번호", ref: this.$refs.inputOldPw},
                {value: this.password1, title: "새로운비밀번호", ref: this.$refs.password1},
                {value: this.password2, title: "새로운비밀번호확인", ref: this.$refs.password2},
            ];

            if (!isValid(param)) return false;

            if(this.password1 !== this.password2){
                this.message2 ="비밀번호가 일치 하지 않습니다.";
                this.$refs.password2.focus();
                return false;
            }
            return true;
        },
        onclickSave : function(){
            if(!this.isFormValid()){
                return false;
            }

            this.info.inputNewPw=this.password1;
            this.info.userId=userId;
            confirmMsg("비밀번호를 변경 하시겠습니까?", this.save);

        },
        save : function() {
            post(TID.SAVE, "/user/my/pw", this.info, this.callback);
        },
        onclickSaveCallback : function(results){
            if(results.success){
                alertMsgRtn("비밀번호가 변경되었습니다. 자동으로 로그아웃 됩니다.", this.saveRtn);
            }else{
                alertMsg(results.error.message);
            }
        },
        saveRtn : function(){
            location.href="/logout";
        }

    }
});