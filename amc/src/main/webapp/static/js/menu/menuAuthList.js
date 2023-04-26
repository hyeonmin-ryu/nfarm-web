
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
                userRoleSecd: "",
                menuId:""
            },
            codeGroupList: [],
            checkList: [],
            menuAuthList: [],
            idx:"",
            treeData: [],
            defaultProps: {
                children: 'children',
                label: 'label',
                disabled: 'disabled',
                },
        };
    },
    mounted: function () {
        this.onclickSearch();
    },
    methods: {
        onclickSearch: function () {
            get(TID.SEARCH,
            "/my/management/menu/role/list",{},
            this.callback);
        },
        
        getMenuAuthList:function (userRoleSecd, roleNm){
				
			$('#textRole').text(userRoleSecd);
			
			this.cond.userRoleSecd = userRoleSecd;	
				
			this.checkList = [];
            this.treeData = [];
				
            get(TID.SEARCH_MENU,
            "/my/management/menu/auth/list",this.cond,
            this.callback);
        },        
        
        
         checkData: function (id ,id2) {
	
			if(id.id != 'root'){
	
				//체크상태
				if (this.$refs.markupTree.getNode(id).checked ){
					//자식노드 있을경우
					if(this.$refs.markupTree.getNode(id).childNodes.length > 0 ){
						
						for ( let i=0; i< this.$refs.markupTree.getNode(id).childNodes.length; i++){
							this.$refs.markupTree.getNode(id).childNodes[i].checked = true;
						}	
					}
				} else {
					
				     if(this.$refs.markupTree.getNode(id).childNodes.length > 0 ){
						
						for ( let i=0; i< this.$refs.markupTree.getNode(id).childNodes.length; i++){
							this.$refs.markupTree.getNode(id).childNodes[i].checked = false;
						}	
					}
					
				}
			
			}
	
	
	
			let obj = [];
			for ( let i=0; i< this.$refs.markupTree.getCheckedKeys().length; i++){
			    	obj.push(this.$refs.markupTree.getCheckedKeys()[i]);
			}				
			obj.push("root");
			this.$refs.markupTree.setCheckedKeys(obj);
			
			
			
			
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
                case TID.SEARCH_MENU:
                
                    if(results.success){
						
						for ( let i=0; i< results.response[1].chkList.length; i++){
							
							if( results.response[1].chkList[i].menuId != null){
								this.checkList.push(results.response[1].chkList[i].menuId);
								
							}
							
						}
						
						this.$refs.markupTree.setCheckedKeys( this.checkList);
						this.treeData = results.response[0].children;
						

                    } else{
                        alertMsg(results.error.message);
                    }break;
                    
                 case TID.UPDATE:
                    if(results.success){
	
					this.menuAuthList = [];
					alertMsgRtn("정상적으로 수정되었습니다.");
						for ( let i=0; i< results.response[1].chkList.length; i++){
							
							if( results.response[1].chkList[i].menuId != null){
								this.checkList.push(results.response[1].chkList[i].menuId);
								
							}
							
						}
						
						this.$refs.markupTree.setCheckedKeys( this.checkList);
						this.treeData = results.response[0].children;
                        
                    } else{
                        alertMsg(results.error.message);
                    }break;                   
                    
                    
                default :
                    this.codeList = results.response;
                    break;
            }
        },

        onclickmod: function(){
	
            if(this.$refs.markupTree.getCheckedKeys().length < 1){
                alertMsg("메뉴가 선택되지않았습니다.");
            } else{
	
				
				
				for ( let i =0; i < this.$refs.markupTree.getCheckedKeys().length; i++){
					let obj = {};
		        	obj.userRoleSecd = this.cond.userRoleSecd;
          			obj.menuId = this.$refs.markupTree.getCheckedKeys()[i];
			    	this.menuAuthList.push(obj);
				}
	
                post(TID.UPDATE, "/my/management/menu/auth/mod",
                    this.menuAuthList, this.callback);
            }
        },        
        
        

    }
});


