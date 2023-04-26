
/**
 * 
 */

let appMain;
const TID = {
    SEARCH: { value: 0, name: "search", code: "S" }, // 상세조회
    SEARCH_MENU: { value: 0, name: "search", code: "S" }, // 상세조회
    SAVE   : {value: 0, name: "save", code: "I"},
    UPDATE   : {value: 0, name: "save", code: "I"},
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
            treeData: [],
            defaultProps: {
                children: 'children',
                label: 'label',
                disabled: 'disabled',
                },
            condMenu: {
                menuId: '',
                menuNm: '',
                menuDc: '',
                menuUrl: '',
                menuIndictOrdr: '',
                menuUseYn: '',
                parntsMenuId: '',
                },
                
              condMenuCopy: {
                menuId: '',
                menuNm: '',
                menuDc: '',
                menuUrl: '',
                menuIndictOrdr: '',
                menuUseYn: '',
                parntsMenuId: '',
                },              
        };
    },
    mounted: function () {
        this.getMenuList();
    },
    methods: {
        getMenuList:function (){
            get(TID.SEARCH,
            "/my/management/menu/list",{},
            this.callback);
        },

        callback:function(tid,results){
            switch(tid){
                case TID.SEARCH:
                    if(results.success){
                        this.treeData = results.response[0].children;
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
                case TID.SEARCH_MENU:
                    if(results.success){
                       this.condMenu =  results.response;
                       this.condMenuCopy =  results.response;
                        
                    } else{
                        alertMsg(results.error.message);
                    }break;
                    
                 case TID.UPDATE:
                    if(results.success){
	
					alertMsgRtn("정상적으로 수정되었습니다.");
                       this.treeData = results.response[0].children;
                        
                    } else{
                        alertMsg(results.error.message);
                    }break;                   
                    
                    
                default :
                    this.codeList = results.response;
                    break;
            }
        },

        nodeClick: function (data) {
            
               get(TID.SEARCH_MENU,
                "/my/management/menu/"+data.id,
                {},
                this.callback);

        },        
        onclickReg: function(){
	
	
	        if(this.condMenu.menuId == ""){
                alertMsg("메뉴가 선택되지않았습니다.");
            } else{
	
				fnOpenPopup('menuModal', this.condMenu);
	
            }
	
        },
        
        onclickMod: function(){
	
	
	console.log(this.condMenuCopy);
	console.log(this.condMenu);
	
            if(this.condMenu.menuId == ""){
                alertMsg("메뉴가 선택되지않았습니다.");
            } else{
                post(TID.UPDATE, "/my/management/menu/mod",
                    this.condMenu, this.callback);
            }
        },        
        
         onclickDel: function(){
            if(this.condMenu.menuId == ""){
                alertMsg("메뉴가 선택되지않았습니다.");
            } else{
	
				confirmMsg("삭제하시겠습니까?", this.delcode);

	
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
            post(TID.DELETE, "/my/management/menu/delete",
                this.condMenu, this.callback);
        },

        delcodeGroup:function(){
            post(TID.DELETE_GROUP, "/code/delete/codegroup/" + this.cond.codeGroup, {}, this.callback);
        }

    }
});
