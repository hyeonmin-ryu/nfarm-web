
/**
 * 
 */

let appMain;
const TID = {
    SEARCH: { value: 0, name: "search", code: "S" }, // 상세조회
    SAVE   : {value: 0, name: "save", code: "I"},
    SAVE_GROUP : {value:0, name: "saveGroup", code:"G"},
    DELETE : {value: 0, name: "delete", code: "D"} // 저장소삭제 
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
            groupCode:{
                dc: "",
                nm: ""
            },
            cond:{
                codeGroup: "",
                nm:"",
                dc:""
            },
            codeGroupList: [],
            codeList: [],
            idx:"",
        };
    },
    mounted: function () {
        this.getCodeGroupList();
    },
    methods: {
        getCodeGroupList:function (){
            get(TID.SEARCH,
            "/get/codegroup/list",{},
            this.callback);
        },

        callback:function(tid,results){
            switch(tid){
                case TID.SEARCH:
                    if(results.success){
                        this.codeGroupList = results.response;
                    }else{
                        console.log(results);
                    }break;
                case TID.DELETE:
                    if(results.success){
                        alertMsgRtn("정상적으로 삭제되었습니다.", this.onclickSearch);
                    } else{
                        alertMsg(results.error.message);
                    }
                    break;
                case TID.DELETE_GROUP:
                    if(results.success){
                        this.cond.codeGroup = "";
                        alertMsgRtn("정상적으로 삭제되었습니다.", this.getCodeGroupList);
                        console.log(this.cond.codeGroup);
                    }else{
                        alertMsg(results.error.message);
                    }
                case TID.SAVE:
                    if(results.success){
                        alertMsgRtn("정상적으로 저장되었습니다.",this.onclickSearch);
                        this.cond.dc="";
                        this.cond.nm="";
                    }else{
                        alertMsg(results.error.message);
                    }
                    break;
                case TID.SAVE_GROUP:
                    if(results.success){
                        alertMsgRtn("정상적으로 저장되었습니다.", this.getCodeGroupList);
                        
                    this.groupCode.nm="";
                    this.groupCode.dc="";
                    } else{
                        alertMsg(results.error.message);
                    }break;
                default :
                    this.codeList = results.response;
                    break;
            }
        },

        onclickSearch: function(){
            switch(this.cond.codeGroup){
                case "DISEASE":
                    this.cond.codeGroup = "Disease";
                    break;
                case "OPENSTORAGESTAT":
                    this.cond.codeGroup= "OpenStorageStat";
                    break;
                case "REQSTORAGESTAT":
                    this.cond.codeGroup= "ReqStorageStat";
                    break;
                case "AGENCYTYPE":
                    this.cond.codeGroup = "AgencyType";
                    break;
                case "USERTYPE":
                    this.cond.codeGroup= "UserType";
                    break;
                case "JOINSTAT":
                    this.cond.codeGroup= "JoinStat";
                    break;
                case "QUESTIONTYPE":
                    this.cond.codeGroup= "QuestionType";
                    break;
                case "EXPORTREQSTATCODE":
                    this.cond.codeGroup= "ExportReqStatCode";
                    break;
                case "USESTORAGESTAT":
                    this.cond.codeGroup= "UseStorageStat";
                    break;
                default:
                    if(this.cond.codeGroup == ""){
                        alertMsg("코드 그룹이 선택되지 않았습니다.");
                    }
            }
            if(this.cond.codeGroup != "")
                getCodeList(this.cond.codeGroup, this.callback);
        },

        onclickList: function(){
            location.href="/my/code/list";
        },

        onclickGroupReg: function(){
            if(this.groupCode.nm =="" || this.groupCode.dc ==""){
                alertMsg("값을 제대로 입력해주세요.");
            }else{
                post(TID.SAVE_GROUP, "/code/save/codegroup",
                    this.groupCode, this.callback);
            }
        },

        onclickReg: function(){
            if(this.cond.codeGroup != ""){
                if(this.cond.nm =="" || this.cond.dc==""){
                    alertMsg("값을 제대로 입력해주세요.");
                }else{
                    post(TID.SAVE, "/code/save/code",
                        this.cond, this.callback);
                }
            } else{
                alertMsg("코드 그룹이 선택되지 않았습니다.");
            }
        },

        onclickGroupDelete:function(){
            confirmMsg("삭제하시겠습니까?", this.delcodeGroup);
        },

        onclickDelete: function(idx){
            this.idx = idx;
            confirmMsg("삭제하시겠습니까?", this.delcode);
        },

        delcode: function(){
            post(TID.DELETE, "/code/delete/" + this.codeList[this.idx].nm, {}, this.callback);
        },

        delcodeGroup:function(){
            post(TID.DELETE_GROUP, "/code/delete/codegroup/" + this.cond.codeGroup, {}, this.callback);
        }

    }
});
