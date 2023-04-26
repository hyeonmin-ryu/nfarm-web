
/*
 * @name : popAgencySearch.js
 * @date : 2021-06-23 오후 1:05
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

let popAgencyTypeCode;
let appPop1;
let appPop2;
let appPop3;
let appPop4;
let appPop5;
let appPop6;


window.addEventListener('load', function() {
    appPop1 = new Vue({
        el: '#popupagencywrap',
    });
    appPop2 = new Vue({
        el: '#popupstoragewrap',
    });
    appPop3 = new Vue({
        el: '#popupbucketwrap',
    });
    appPop4 = new Vue({
        el: '#popupexportwrap',
    });
    appPop5 = new Vue({
        el: '#popupdownloadwrap',
    });
    appPop6 = new Vue({
        el: '#popupmenuloadwrap',
    });    
});
Vue.component('popupagencycontents', {
    template: "#popup-template",
    data: function () {
        return {
            cond: {
                page: 1,
                size: 5,
                agencyName: "",
                insttTyCd: "",
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
            messages: "",
            alertMsg: "",
            focusId:"",
            confirmMsg:"",
        };
    },
    mounted: function () {

    },
    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.insttTyCd=popAgencyTypeCode; // COMP
            this.getAgencyList();
        },
        getAgencyList:function(){
            get("search",
                "join/agency/list",
                this.cond,
                this.searchCallback);
        },
        searchCallback: function (tid, results) {
            console.log(results);
            if (results.success) {
                this.makePageNavi(results.response);
                this.agencyList = results.response.list;
            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable) {
            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;

            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;
            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            //this.total = Math.ceil(total / pageable.size);
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page;
                this.pageInfo.curr = page;
                this.getAgencyList();
            }
        },
        selectAgency : function (agency){
            callbackPopupAgency(agency);
            fnClosePopup('agencyModal');
        },

    }
});

Vue.component('popupstoragecontents', {
    template: "#popup-template-storage",
    data: function () {
        return {
            cond: {
                page: 1,
                size: 5,
                strgeReqSttusCd: "S_ACC", // S_ACC(저장신청승인)
                storgeNm:""
            },
            storageList: [],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            messages: "",
        };
    },
    mounted: function () {
    },

    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.getStorageList();
        },
        getStorageList:function(){
            get("search",
                "/my/management/storage/req/list",
                this.cond,
                this.searchCallback);
        },
        searchCallback: function (tid,results) {
            if (results.success) {
                this.makePageNavi(results.response);
                this.storageList = results.response.list;
            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable) {

            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;
            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;

            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            console.log(pageable.total);
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            //this.total = Math.ceil(total / pageable.size);
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
         },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page;
                this.pageInfo.curr = page;
                this.getStorageList();
            }
        },
        selectStorage : function (storage){
            callbackPopupStorage(storage);
            fnClosePopup('storageModal');
        }

    }
});

Vue.component('popupbucketcontents', {
    template: "#popup-template-bucket",
    data: function () {
        return {
            cond: {
                page: 1,
                size: 5,
                strgeReqSttusCd: "S_ACC", // S_ACC(저장신청승인)
                othbcDataNm:""
            },
            bucketList: [],
            checkList:[],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            messages: "",
        };
    },
    mounted: function () {
		
    },

    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearchOpen: function () {
            this.cond.page = 1;
            this.cond.keyword = "";
            this.getBucketList();
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.getBucketList();
        },
        getBucketList:function(){
            get("search",
                "/data/box/use/list",
                this.cond,
                this.searchCallback);
        },
        searchCallback: function (tid,results) { //27195b84-5c56-440c-934f-a6989ad44c2c
            if (results.success) {
                
				console.log("whot", results.response.list)
                for(var i = 0; i < results.response.list.length; i++) {
	
					if(this.checkList.find(v => v.useStrgeId === results.response.list[i].useStrgeId)  != undefined) {
						results.response.list[i].checked = true;
					} else {
						results.response.list[i].checked = false;
					}
				}
				
				
				this.makePageNavi(results.response);
				this.bucketList = results.response.list;
				
            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable) {

            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;
            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;

            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            console.log(pageable.total);
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            //this.total = Math.ceil(total / pageable.size);
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
         },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page;
                this.pageInfo.curr = page;
                this.getBucketList();
            }
        },
        selectBucket : function (){
			callbackPopupBucket(this.checkList);
            fnClosePopup('bucketModal');
        },
        bucketSelect : function (idx) { //bucket.openStorageInfo.openDataName, bucket.useStorageId, bucket.reqStorageInfo.bucketName, 

			let obj = {};
			obj.othbcDataNm = this.bucketList[idx].othbcDataNm;
			obj.bucketId = this.bucketList[idx].bucketId;
			obj.useStrgeId = this.bucketList[idx].useStrgeId;
			obj.bucketSize =  Math.ceil(this.bucketList[idx].bucketSize/1024/1024/1024 * 100) /100;
			
			if(this.bucketList[idx].checked) {
				this.bucketList[idx].checked = false;
				this.checkList.splice(this.checkList.findIndex(v => v.useStrgeId === this.bucketList[idx].useStrgeId), 1);
			} else {
				this.bucketList[idx].checked = true;
				this.checkList.push(obj);
			}
		}

    }
});

Vue.component('popupexportcontents', {
    template: "#popup-template-export",
    data: function () {
        return {
            cond: {
                page: 1,
                size: 5,
                keyword:""
            },
            dataBoxList: [],
            checkList:[],
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1],
                total: 1
            },
            messages: "",
        };
    },
    mounted: function () {
//        setTimeout(function() {
//            loadSelect();
//        },1000);
		
    },
    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearchOpen: function () {
            this.cond.page = 1;
            this.cond.keyword = "";
            this.getDataBoxList();
        },
        onclickSearch: function () {
            this.cond.page = 1;
            this.getDataBoxList();
        },
        getDataBoxList :function(){
            get("search",
                "/export/use/list",
                this.cond,
                this.searchCallback);
        },
        searchCallback: function (tid,results) { //27195b84-5c56-440c-934f-a6989ad44c2c
            if (results.success) {
            console.log(results.response);
            
                for(var i = 0; i < results.response.list.length; i++) {
	
					if(this.checkList.find(v => v.useStorageId === results.response.list[i].useStorageId)  != undefined) {
						results.response.list[i].checked = true;
					} else {
						results.response.list[i].checked = false;
					}
				}
				
				this.makePageNavi(results.response);
				this.dataBoxList = results.response.list;
				
            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable) {
            let max = Math.ceil(pageable.total / pageable.pageSize);
            max = max == 0 ? 1 : max;
            const curr = pageable.pageNum;
            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;

            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;
            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;
            this.pageInfo.total=pageable.total;


            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page ;
                this.pageInfo.curr = page;
                this.getDataBoxList();
            }
        },
        selectDataBox : function (){
			callbackPopupExport(this.checkList);
            fnClosePopup('exportModal');
        },
        dataBoxSelect : function (idx) { 
			let obj = {};
			this.checkList = [];
			obj.bucketList = this.dataBoxList[idx].exportApproverInfo;
			obj.dataBoxSeq = this.dataBoxList[idx].dtbxReqNo;
			obj.dataBoxName = this.dataBoxList[idx].dtbxNm;
			obj.dissRspnberUserId = this.dataBoxList[idx].dissRspnberUserId;
			this.checkList.push(obj);
			
			callbackPopupExport(this.checkList);
            fnClosePopup('exportModal');
		}

    }
});


Vue.component('popupdownloadcontents', {
    template: "#popup-template-download",
    data: function () {
        return {
            cond: {
                page: 0,
                size: 5,
                dataBoxReqSeq:"",
                useStorageSeq:"",
                errYn:"",
            },
            downloadList: [],
            obj: {
                dataBoxReqSeq : "",
                useStorageSeq : ""
            },
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            messages: "",
        };
    },
    mounted: function () {
	
		
    },
    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickSearchOpen: function () {
	
			this.cond.dataBoxReqSeq = this.obj.dataBoxReqSeq;
			this.cond.useStorageSeq = this.obj.useStorageSeq;
	
            this.cond.page = 0;
            this.cond.keyword = "";
            this.getDownloadList();
        },
        onclickSearch: function () {
	
			this.cond.errYn = document.querySelector("#errYn").getAttribute("data-value");
            this.cond.page = 0;
            this.getDownloadList();
        },
        getDownloadList :function(){
            get("search",
                "/my/admin/databox/download/list",
                this.cond,
                this.searchCallback);
        },
        searchCallback: function (tid,results) { //27195b84-5c56-440c-934f-a6989ad44c2c
            if (results.success) {
				
				this.makePageNavi(results.response.pageable, results.response.total);
				this.downloadList = results.response.content;
				
            } else {
                console.log(results);
            }
        },
        makePageNavi: function (pageable, total) {
            let max = Math.ceil(total / pageable.size);
            max = max == 0 ? 1 : max;
            const curr = pageable.page + 1;

            const first = Math.ceil(curr / 5) * 5 - 4;
            let last = first + 4;
            last = last>max?max:last;
            let prev = first - 1;
            prev = prev<1?1:prev;
            let next = last + 1;
            next = next>max?max:next;

            this.pageInfo.first = first;
            this.pageInfo.max = max;
            this.pageInfo.curr = curr;
            this.pageInfo.last = last;
            this.pageInfo.prev = prev;
            this.pageInfo.next = next;

            this.pageInfo.pages = new Array();
            for (let i=first; i<=last; i++){
                this.pageInfo.pages.push(i);
            }
        },
        onclickPage : function (page){
            if(page === this.pageInfo.curr){
            } else {
                this.cond.page = page - 1;
                this.pageInfo.curr = page;
                this.getDownloadList();
            }
        },
        selectDataBox : function (){
			callbackPopupExport(this.checkList);
            fnClosePopup('downloadModal');
        }

    }
});






Vue.component('popupmenucontents', {
    template: "#popup-template-menu",
    data: function () {
        return {
            cond: {
                page: 0,
                size: 5,
                dataBoxReqSeq:"",
                useStorageSeq:"",
                errYn:"",
            },
            downloadList: [],
            condMenu: {
                menuId: '',
                menuNm: '',
                menuDc: '',
                menuUrl: '',
                menuIndictOrdr: '',
                menuUseYn: '',
                parntsMenuId: '',
                },
            pageInfo: {
                curr : 1,
                max : 1,
                first : 1,
                last : 1,
                prev : 1,
                next : 1,
                pages: [1]
            },
            messages: "",
        };
    },
    mounted: function () {
	
		
    },
    methods: {
        onKeyup:function (e){
            if (e.keyCode == 13){
                this.onclickSearch();
            }
        },
        onclickReg: function(){
                alertMsg("메뉴가 선택되지않았습니다.");
                return;
            if(this.condMenu.menuId == ""){
            } else{
	
	
			this.condMenu.menuId = $('#menuIdPop').val();
			this.condMenu.menuNm = $('#menuNmPop').val();
			this.condMenu.menuDc = $('#menuDcPop').val();
			this.condMenu.menuUrl = $('#menuUrlPop').val();
			this.condMenu.menuIndictOrdr = $('#menuIndictOrdrPop').val();
			this.condMenu.menuUseYn = $('#menuUseYnYPop').val();
			this.condMenu.parntsMenuId = $('#parntsMenuIdPop').val();
	
                post(TID.UPDATE, "/my/management/menu/reg",
                    this.condMenu, this.modCallback);
            }
        },
        searchCallback: function (tid,results) { //27195b84-5c56-440c-934f-a6989ad44c2c
            if (results.success) {
				
            } else {
                console.log(results);
            }
        },
        modCallback: function (tid,results) { //27195b84-5c56-440c-934f-a6989ad44c2c
            if (results.success) {
				fnClosePopup('menuModal');
            } else {
                console.log(results);
            }
        },

    }
});