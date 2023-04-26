let appMain;
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
            userInfo : [],
            imageCodeList : [],
            imageCodeListTmp : [],
            serverCodeList : [],
            radioList: [{"name":"HDD","desc":"HDD"},{"name":"SSD","desc":"SSD"}],
            saveInfo: {
                dtbxNm : "",
                naswSize : "",
                chargerNm: ""
            },
			userConList:[], //데이터박스 동시접속자
			userData:{
                dtbxUserName:"",
                dtbxUserMoblphonNo:"",
            },
            serverList:[], //서버신청 목록
			serverData:{
                imageGoodsCd:"",
                imageGoodsNm:"",
                svrGoodsCd:"",
                svrGoodsNm:""
            },
            storageList:[], //서버신청-블록스토리지 목록
			storageData:{
                strgeTyCd:"",
                strgeSize:"",
            },
            bucketListMain: [] //연구분석데이터신청 목록
        };
    },
    mounted:function(){
//        this.getReqUser();
        this.getImageCode();
    },
    methods:{
        // 신청화면에 출력될 데이타 조회
        getReqUser:function (){
            get(TID.SEARCH_USER, "/data/box/user", {}, this.callback);
        },
        // 신청화면에 출력될 데이타 조회
        getImageCode : function (){
            get(TID.SEARCH_IMAGE, "/data/box/image", {}, this.callback);
        },
        // 취소 클릭(목록 이동)
        onclickCancel: function () {
            location.href = "/data/box/main";
        },
        // 신청 클릭(신청)
        onclickReqSave: function () {
            this.saveReqStorage();
        },
        // 신청 메소드 호출
        saveReqStorage : function () {
			let reg_name5 = /^[가-힣a-zA-Z0-9_]+$/; // 한글 + 영문만
			
            if(!this.saveInfo.dtbxNm){
                alertMsg("데이터박스명은 필수입니다.", this.$refs.dtbxNm );
                return false;
            }
            
//            if (reg_name5.test(this.saveInfo.dtbxNm) === false) {
//				alertMsg('데이터박스명을 정확히 입력해 주세요.', this.$refs.dtbxNm);
//				this.saveInfo.dtbxNm = '';
//				return;
//			}

            if(this.userConList.length == 0) {
				alertMsg("데이터박스 동시 접속자는 필수입니다.", this.$refs.dtbxUserName );
                return false;
			}
			
			if(this.serverList.length == 0) {
				alertMsg("서버 신청은 필수입니다.", this.$refs.imageGoodsCd );
                return false;
			}
			
			if(!this.saveInfo.naswSize) {
				alertMsg("NAS-W 신청은 필수입니다.", this.$refs.naswSize );
                return false;
			}
			
			if(this.saveInfo.naswSize > 20000 || this.saveInfo.naswSize < 500) {
				alertMsg("NAS-W 용량은 최소 500GB ~ 최대 20000GB 입니다.", this.$refs.naswSize );
                return false;
			}
			
			if(this.bucketListMain.length == 0) {
				alertMsg("연구분석데이터 신청은 필수입니다.", this.$refs.bucketBtn );
                return false;
			}
			this.saveInfo.userConList = this.userConList;
			this.saveInfo.serverList = this.serverList;
			this.saveInfo.bucketList = this.bucketListMain;
			this.saveInfo.chargerNm = this.userInfo.chargerNm;
			
            confirmMsg("신청하시겠습니까?",this.save);
        },
        save: function() {
            post(TID.SAVE, "/data/box/reg", this.saveInfo, this.callback);
        },
        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH_USER:
                    this.searchCallback(results);
                    break;
                case TID.SEARCH_IMAGE:
                    this.imageCallback(results);
                    break;
                case TID.SEARCH_SERVER:
                    this.serverCallback(results);
                    this.serverCbListReload();
                    break;
                case TID.SAVE:
                    this.saveCallback(results);
                    break;
            }
        },
        serverCbListReload : function(popupId){
            let obj = {
                selectDiv:"svrGoodsCdDiv",
                selectId:"svrGoodsCd",
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
        searchCallback: function (results) {
	
            if (results.success) {
                this.userInfo      = results.response;
                
                let obj = {};
	            obj.dtbxUserName = this.userInfo.chargerNm;
	            obj.dtbxUserMoblphonNo = this.userInfo.chargerMoblphonNo;
	            this.userConList.push(obj);
            
            } else {
                alertMsg(results.error.message);
            }
        },
        imageCallback: function (results) {
            if (results.success) {
				this.getReqUser();
                this.imageCodeList = results.response.getServerImageProductListResponse.productList;
                setTimeout(function() {
                    loadSelect();
                },300);
            } else {
                alertMsg(results.error.message);
            }
        },
        serverCallback: function (results) {
            if (results.success) {
                this.serverCodeList = results.response.getServerProductListResponse.productList;
            } else {
                alertMsg(results.error.message);
            }
        },
        saveCallback: function (results) {
            if (results.success) {
                alertMsgRtn("정상적으로 신청되었습니다. 관리자 확인 후 연락할 예정입니다.",this.onclickCancel);

            } else {
                alertMsg(results.error.message);
            }
        },
        onclickUserAdd: function (){
			
			let reg_name1 = /^[가-힣]+$/; // 한글만
			let reg_name2 = /^[a-zA-z]+$/; // 영문만
			let reg_name3 = /^[a-z]+$/; // 영문 소문자만
			let reg_name4 = /^[A-Z]+$/; // 영문 대문자만
			let reg_name5 = /^[가-힣a-zA-Z]+$/; // 한글 + 영문만
			let text = this.userData.dtbxUserMoblphonNo;
			let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	
            if(!this.userData.dtbxUserName) {
				alertMsg("동시 접속자 이름을 입력해주세요.", this.$refs.dtbxUserName);
                return;
			}
			
			if (reg_name5.test(this.userData.dtbxUserName) === false) {
				alertMsg('동시 접속자 이름을 정확히 입력해 주세요.', this.$refs.dtbxUserName);
				this.userData.dtbxUserName = '';
				return;
			}
			
			if(!this.userData.dtbxUserMoblphonNo) {
				alertMsg("동시 접속자 휴대전화 번호를 입력해주세요.", this.$refs.dtbxUserMoblphonNo);
                return;
			}
            
			if (regPhone.test(text) === false) {
				alertMsg('휴대전화 번호를 확인해 주세요.', this.$refs.dtbxUserMoblphonNo);
				this.userData.dtbxUserMoblphonNo = '';
				return;
			}
			
			let obj = {};
            obj.dtbxUserName = this.userData.dtbxUserName;
            obj.dtbxUserMoblphonNo = this.userData.dtbxUserMoblphonNo;

            this.userConList.push(obj);
            this.userData = {};
			
        },
        onclickUserDel : function(idx) {
			 this.userConList.splice(idx, 1);
        },
        onclickServerAdd: function (){
	
			var tempimageGoodsCd = document.querySelector("#imageGoodsCd").getAttribute("data-value");
			var tempimageGoodsNm = $("#imageGoodsCdDiv .select-selected").text();
			
			var tempsvrGoodsCd = document.querySelector("#svrGoodsCd").getAttribute("data-value");
			var tempsvrGoodsNm = $("#svrGoodsCdDiv .select-selected").text();
	
            if(tempimageGoodsCd && tempsvrGoodsCd) {
            } else {
                alertMsg("값을 선택해 주세요.");
                return;
            }

            if(tempimageGoodsCd.indexOf('WND64') > -1) {
				this.imageCodeList = this.imageCodeList.filter(x => x.productCode.indexOf('WND64') > -1);
			} else {
				this.imageCodeList = this.imageCodeList.filter(x => x.productCode.indexOf('LNX64') > -1);
			}
			
			let tempObj = {
                selectDiv:"imageGoodsCdDiv",
                selectId:"imageGoodsCd",
                optionValue:"productCode",
                optionName:"productDescription",
            };
            // network select box 리로드
            reloadSelect(tempObj, this.imageCodeList);
			
			let obj = {};
            obj.imageGoodsCd = tempimageGoodsCd;
            obj.imageGoodsNm = tempimageGoodsNm;
            obj.svrGoodsCd = tempsvrGoodsCd;
            obj.svrGoodsNm = tempsvrGoodsNm;
            obj.storageList = this.storageList;
            
            this.serverList.push(obj);
            
            //아래는 초기화
			this.storageList = [];
			
			$("#imageGoodsCdDiv .select-selected").text("서버 이미지를 선택하세요");
			document.querySelector("#imageGoodsCd").setAttribute("data-value", "");
			$("#svrGoodsCdDiv .select-selected").text("서버 타입을 선택하세요");
			document.querySelector("#svrGoodsCd").setAttribute("data-value", "");
			
			this.storageData.strgeTyCd = "";
			this.storageData.strgeSize = "";
			
			let tempObj2 = {
                selectDiv:"svrGoodsCdDiv",
                selectId:"svrGoodsCd",
                optionValue:"productCode",
                optionName:"productDescription",
            };
            // network select box 리로드
            reloadSelect(tempObj2, this.serverCodeList);
			
        },
        onclickServerDel : function(idx) {
			 this.serverList.splice(idx, 1);
			 
			 if(this.serverList.length === 0) {
				this.imageCodeList = this.imageCodeListTmp;
				
				let tempObj = {
	                selectDiv:"imageGoodsCdDiv",
	                selectId:"imageGoodsCd",
	                optionValue:"productCode",
	                optionName:"productDescription",
	            };
	            // network select box 리로드
	            reloadSelect(tempObj, this.imageCodeList);
			}
        },
        onclickStorageAdd: function (){
			
			var tempimageGoodsCd = document.querySelector("#imageGoodsCd").getAttribute("data-value");
			var tempsvrGoodsCd = document.querySelector("#svrGoodsCd").getAttribute("data-value");
	
            if(!tempimageGoodsCd || !tempsvrGoodsCd) {
				alertMsg("서버이미지와 서버타입을 먼저 선택해 주세요.");
                return;
            }
            
            if(tempsvrGoodsCd.indexOf('HDD') > -1 && this.storageData.strgeTyCd === 'SSD') {
				alertMsg("서버타입과 동일한 블록스토리지 타입을 선택해 주세요. [서버타입:HDD]");
                return;
			} else if(tempsvrGoodsCd.indexOf('SSD') > -1 && this.storageData.strgeTyCd === 'HDD') {
				alertMsg("서버타입과 동일한 블록스토리지 타입을 선택해 주세요. [서버타입:SSD]");
                return;
			}
	
            if(!this.storageData.strgeTyCd || !this.storageData.strgeSize || this.storageData.strgeSize === '') {
				alertMsg("블록스토리지 정보를 입력해주세요.");
                return;
            }
            
            if(this.storageData.strgeSize !== '') {
				if(this.storageData.strgeSize < 10) {
					this.storageData.strgeSize = 10;
				} else if (this.storageData.strgeSize > 2000) {
					this.storageData.strgeSize = 2000;
				} else {
					this.storageData.strgeSize = (this.storageData.strgeSize - (this.storageData.strgeSize % 10));
				}
			}
			
			let obj = {};
            obj.strgeTyCd = this.storageData.strgeTyCd;
            obj.strgeSize = this.storageData.strgeSize;

            this.storageList.push(obj);
            this.storageData.strgeSize = '';
			
        },
        onclickStorageDel : function(idx) {
			 this.storageList.splice(idx, 1);
        },
        onclickBucketAdd : function() {
            fnOpenPopup('bucketModal', this.bucketListMain);
        },
        returnPop : function(item) {
			this.bucketListMain = [];
			
			for(var i  = 0; i < item.length; i++) {
				this.bucketListMain.push(item[i]);
			}
		},
		onclickBucketDel : function(idx) {
			this.bucketListMain.splice(idx, 1);
		},
		searchChange(id, data) {
			get(TID.SEARCH_SERVER, "/data/box/server", {data:data}, this.callback);
		},
		setNaswNum(e) {
			if(this.saveInfo.naswSize !== '') {
				if(this.saveInfo.naswSize < 500) {
					this.saveInfo.naswSize = 500;
				} else if (this.saveInfo.naswSize > 20000) {
					this.saveInfo.naswSize = 20000;
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
		searchProductChange(id, data) {
			if(data.indexOf('HDD') > -1) {
				this.storageData.strgeTyCd = 'HDD'
			} else if(data.indexOf('SSD') > -1) {
				this.storageData.strgeTyCd = 'SSD'
			}
		}
    }
});

// selectebox 이벤트
function selectedChange(id) {
	const data = document.querySelector('#' + id).value;
	
	if(!isNull(data)){
		appMain.$refs.maincontents.searchChange(id, data);
	}
};

function selectedProductChange(id) {
	const data = document.querySelector('#' + id).value;
	
	if(!isNull(data)){
		appMain.$refs.maincontents.searchProductChange(id, data);
	}
};
