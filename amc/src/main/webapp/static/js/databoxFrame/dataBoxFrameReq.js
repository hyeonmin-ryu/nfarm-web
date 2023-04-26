let appMain;
let menuId;

const TID = {
    SEARCH_USER   : {value: 0, name: "search", code: "S"},
    SAVE          : {value: 0, name: "save",   code: "I"},
    SEARCH_IMAGE  : {value: 0, name: "search", code: "S"},
    SEARCH_SERVER : {value: 0, name: "search", code: "S"}
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
            serverCodeList : [],
            dtbxFrmeSttusCdList : getCodeList('DTBX_FRME_STTUS_CD',this.callback), // 콤보박스 리스트
            saveInfo: {
                dtbxFrmeNm : "",
                dtbxFrmeCn : "",
                myAdmSvrCo : "",
                cmnuseNasCo: "",
                dtbxFrmeSttusCd: "",
                dataBoxFrameAccountList:[],
                dataBoxFrameAdminServerList:[],
                dataBoxFramePublicStorageList:[],
            },
            serverList:[], //서버신청 목록
            serverAccountList:[], //서버신청 목록
			serverData:{
                admSvrTyCd:"",
                admSvrSpecCd:"",
            },
            storageList:[], //서버신청-블록스토리지 목록
            
        };
    },
    mounted:function(){
        setTimeout(function() {
            loadSelect();
        },300);
        
        $('#admSvrTyCd option:eq(0)').remove();
        $('#admSvrSpecCd option:eq(0)').remove();
        
        document.querySelector("#admSvrTyCd").setAttribute("data-value" , document.querySelector('#admSvrTyCd').value);
        document.querySelector("#admSvrSpecCd").setAttribute("data-value" , document.querySelector('#admSvrSpecCd').value);        
        
    },
    methods:{
        // 취소 클릭(목록 이동)
        onclickCancel: function () {
            location.href = "/my/databoxFrame/list";
        },
        // 신청 클릭(신청)
        onclickReqSave: function () {
            this.saveReqStorage();
        },
        // 신청 메소드 호출
        saveReqStorage : function () {
			let reg_name5 = /^[가-힣a-zA-Z0-9_]+$/; // 한글 + 영문만
			var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
			
            if(!this.saveInfo.dtbxFrmeNm){
                alertMsg("데이터박스프레임명은 필수입니다.", this.$refs.dtbxFrmeNm );
                return false;
            }
            
            if (reg_name5.test(this.saveInfo.dtbxFrmeNm) === false) {
				alertMsg('데이터박스명을 정확히 입력해 주세요.', this.$refs.dtbxFrmeNm);
				this.saveInfo.dtbxFrmeNm = '';
				return;
			}

			if(this.serverList.length == 0) {
				alertMsg("서버 신청은 필수입니다.", this.$refs.dtbxFrmeNm );
                return false;
			}
			
			
			if(this.serverAccountList.length == 0) {
				alertMsg("어드민 계정은 필수입니다.", this.$refs.dtbxFrmeNm );
                return false;
			} else {
				
				let serverCnt = 0;
				for (let index in this.serverList) {
					serverCnt += parseInt(this.serverList[index].admSvrQy);
				}
				
				for(var index  = 0; index < serverCnt; index++) {	
					
					
					if(this.$refs.dtbxFrmeAdmAcntPassword[index].value.length == 0){
						alertMsg("비밀번호는 필수입니다", this.$refs.dtbxFrmeAdmAcntPassword[index] );
						return false;
					}
					
//					if(this.$refs.dtbxFrmeAdmAcntPasswordChk[index].value.length == 0){
//						alertMsg("비밀번호확인은 필수입니다", this.$refs.dtbxFrmeAdmAcntPasswordChk[index] );
//						return false;
//					}
					
					
//					if(this.$refs.dtbxFrmeAdmAcntPassword[index].value !== this.$refs.dtbxFrmeAdmAcntPasswordChk[index].value){
//						alertMsg("비밀번호가 다릅니다.", this.$refs.dtbxFrmeAdmAcntPassword[index] );
//						return false;
//					}
					
					if(this.$refs.dtbxFrmeAdmHostNm[index].value.length == 0){
						alertMsg("호스트명은 필수입니다.", this.$refs.dtbxFrmeAdmHostNm[index] );
						return false;
					}
					
					if(this.$refs.dtbxFrmeAdmIp[index].value.length == 0){
						alertMsg("IP는 필수입니다.", this.$refs.dtbxFrmeAdmIp[index] );
						return false;
					}
					
					if(ipformat.test(this.$refs.dtbxFrmeAdmIp[index].value) === false) {
						alertMsg('IP가 형식에 맞지 않습니다.', this.$refs.dtbxFrmeAdmIp[index]);
						this.$refs.dtbxFrmeAdmIp[index].value = "";
						return;
					}					
					
					var arr = {};
	        		arr.dtbxFrmeAdmAcntPassword 	  = this.$refs.dtbxFrmeAdmAcntPassword[index].value;
	        		arr.dtbxFrmeAdmHostNm 			  = this.$refs.dtbxFrmeAdmHostNm[index].value;
	        		arr.dtbxFrmeAdmIp 	  			  = this.$refs.dtbxFrmeAdmIp[index].value;
	        		
	        		this.saveInfo.dataBoxFrameAccountList.push(arr);
					
				}
				
			}
			
			if(this.storageList.length == 0) {
				alertMsg("공용데이터 저장소는 필수입니다.", this.$refs.cmnuseDataCpcty );
                return false;
			}
			
			
			
			let serverCnt = 0;
			for (let index in this.serverList) {
				serverCnt += parseInt(this.serverList[index].admSvrQy);
			}
			
			this.saveInfo.myAdmSvrCo = serverCnt;
			
			
			let nasCnt = 0;
   			for (let index in this.storageList) {
				nasCnt += parseInt(this.storageList[index].cmnuseDataQy);
			}			
			
			this.saveInfo.cmnuseNasCo = nasCnt;
			
			
			this.saveInfo.dataBoxFrameAdminServerList = this.serverList;
			this.saveInfo.dataBoxFramePublicStorageList = this.storageList;
			
			
			console.log(this.saveInfo);
            confirmMsg("신청하시겠습니까?",this.save);
        },
        save: function() {
            post(TID.SAVE, "/my/management/databoxFrame/reg", this.saveInfo, this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SAVE:
                    this.saveCallback(results);
                    break;
                 case "DTBX_FRME_STTUS_CD":
                    this.dtbxFrmeSttusCdList = results.response;
//                    setTimeout(function() {
//                        loadSelect();
//                    },300);
                    break;                   
                    
            }
        },
        serverCbListReload : function(popupId){
            let obj = {
                selectDiv:"serverProductCodeDiv",
                selectId:"serverProductCode",
                optionValue:"productCode",
                optionName:"productDescription",
            };
            // network select box 리로드
            reloadSelect(obj, this.serverCodeList);

            // popupId 가 있을 경우, 팝업을 닫는다.
            if(!isNull(popupId)) {
                fnClosePopup(popupId);
            }
        },
        serverCallback: function (results) {
            if (results.success) {
                this.serverCodeList = results.response;
            } else {
                alertMsg(results.error.message);
            }
        },
        saveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 등록되었습니다",this.onclickCancel);

            } else {
                alertMsg(results.error.message);
            }
        },
        onclickServerAdd: function (){
	
			if(this.$refs.addBtn1.className.match('btn-gray')){
				return;
			}
	
	
			var tempadmSvrTyCdCode = $("#admSvrTyCdDiv .select-selected").text();
			
			var tempadmSvrSpecCdCode = $("#admSvrSpecCdDiv .select-selected").text();
	
            if(tempadmSvrTyCdCode && tempadmSvrSpecCdCode) {
            } else {
                alertMsg("값을 선택해 주세요.");
                return;
            }

  
  			let cnt = this.$refs.admSvrQy.value;
  			let flag = true;
  			let totCnt = 0;
  			
			for (let index in this.serverList) {
				
				if ((tempadmSvrTyCdCode == this.serverList[index].admSvrTyCd) && (tempadmSvrSpecCdCode == this.serverList[index].admSvrSpecCd)) {
					cnt =  parseInt(this.serverList[index].admSvrQy) + parseInt(this.$refs.admSvrQy.value);
					this.serverList[index].admSvrQy = cnt;
					flag = false;
				} 

			}


			let obj = {};
			if(flag) {

	            obj.admSvrTyCd = tempadmSvrTyCdCode;
	            obj.admSvrSpecCd = tempadmSvrSpecCdCode;
	            obj.admSvrQy      = cnt;
				this.serverList.push(obj);
			}
            
   			 for (let index in this.serverList) {
				totCnt += parseInt(this.serverList[index].admSvrQy);
			}

			for(var i  = 0; i < this.$refs.admSvrQy.value; i++) {
				this.serverAccountList.push(obj);
			}



			document.querySelector("#admSvrQy").setAttribute("max" , 4-totCnt );
			this.$refs.admSvrQy.value = 1;
			if(totCnt > 3){
				this.$refs.addBtn1.classList.add('btn-gray');
			}

			
        },
        onclickServerDel : function(idx) {
	
			//삭제전 CNT
		    let delCnt = 0;
			 for (let index in this.serverList) {
				delCnt += parseInt(this.serverList[index].admSvrQy);
			 }
			
			
			//서버리스트 total
		    let totCnt = 0;
		    this.serverList.splice(idx, 1);
			 for (let index in this.serverList) {
				totCnt += parseInt(this.serverList[index].admSvrQy);
			 }
			
			//서버리스트 삭제후 재성성
			this.serverAccountList.splice(idx, delCnt-totCnt);
			
			document.querySelector("#admSvrQy").setAttribute("max" , 4-totCnt );
			
			if(totCnt < 4){
				this.$refs.addBtn1.classList.remove('btn-gray');
			}	
			
        },
        onclickStorageAdd: function (){
			
			
		  if(this.$refs.addBtn2.className.match('btn-gray')){
				return;
			}
			
			var tempcmnuseDataCpctyCode =  this.$refs.cmnuseDataCpcty.value;
			var tempcmnuseDataQyCode  = this.$refs.cmnuseDataQy.value;
	
	
            if(!tempcmnuseDataCpctyCode || !tempcmnuseDataQyCode) {
				alertMsg("용량을 입력해 주세요.");
                return;
            }
            
            
            
            if(tempcmnuseDataCpctyCode !== '') {
				if(tempcmnuseDataCpctyCode < 500) {
					tempcmnuseDataCpctyCode = 500;
				} else if (tempcmnuseDataCpctyCode > 10000) {
					tempcmnuseDataCpctyCode = 10000;
				} else {
					tempcmnuseDataCpctyCode = (tempcmnuseDataCpctyCode - (tempcmnuseDataCpctyCode % 10));
				}
				this.$refs.cmnuseDataCpcty.value = tempcmnuseDataCpctyCode;
			}
			
			
			let cnt = this.$refs.cmnuseDataQy.value;
  			let flag = true;
  			let totCnt = 0;
  			
			for (let index in this.storageList) {
				
				if ((tempcmnuseDataCpctyCode == this.storageList[index].cmnuseDataCpcty)) {
					cnt =  parseInt(this.storageList[index].cmnuseDataQy) + parseInt(this.$refs.cmnuseDataQy.value);
					this.storageList[index].cmnuseDataQy = cnt;
					flag = false;
				} 

			}
			
			
			let obj = {};
			
			if(flag) {
	            obj.cmnuseDataCpcty = tempcmnuseDataCpctyCode;
	            obj.cmnuseDataQy = tempcmnuseDataQyCode;
	
	            this.storageList.push(obj);
			}


   			 for (let index in this.storageList) {
				totCnt += parseInt(this.storageList[index].cmnuseDataQy);
			}

			document.querySelector("#cmnuseDataQy").setAttribute("max" , 20-totCnt );
			this.$refs.cmnuseDataQy.value = 1;
			if(totCnt > 19){
				this.$refs.addBtn2.classList.add('btn-gray');
			}
			
			
        },
        onclickStorageDel : function(idx) {
			 this.storageList.splice(idx, 1);
			 let totCnt = 0;
			for (let index in this.storageList) {
				totCnt += parseInt(this.storageList[index].cmnuseDataQy);
			}
			if(totCnt < 20){
				this.$refs.addBtn2.classList.remove('btn-gray');
			}	
			 
			 
        },
		setNaswNum(e) {
			if(this.saveInfo.naswSize !== '') {
				if(this.saveInfo.naswSize < 500) {
					this.saveInfo.naswSize = 500;
				} else if (this.saveInfo.naswSize > 10000) {
					this.saveInfo.naswSize = 10000;
				} else {
					this.saveInfo.naswSize = (this.saveInfo.naswSize - (this.saveInfo.naswSize % 100));
				}
			}
		},
		setBlockStorageNum(e) {
			if(e.target.value !== '') {
				if(e.target.value < 10) {
					e.target.value = 10;
				} else if (e.target.value > 2000) {
					e.target.value = 2000;
				} else {
					e.target.value = (e.target.value - (e.target.value % 10));
				}
			}
			
		},
    }
});

// selectebox 이벤트
function selectedChange(id) {
	const data = document.querySelector('#' + id).value;
	
};

function selectedProductChange(id) {
	const data = document.querySelector('#' + id).value;
	
	if(!isNull(data)){
		appMain.$refs.maincontents.searchProductChange(id, data);
	}
};

function keydownEvent(evt){
	evt.preventDefault();
}


