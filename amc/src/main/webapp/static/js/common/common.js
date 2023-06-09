Vue.config.devtools = true;


/*
 * @name : common.js
 * @date : 2021-06-23 오후 1:12
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */


let instance;
window.addEventListener('load', function() {
    instance  = axios.create({
        baseURL: 'http://localhost:8080',
        headers: { 'X-Custom-Header': 'Custom' },
        timeout: 10000,
    });

});

function get(tid, uri, params, callback){
    axios.get(uri, {
        params:params
    })
        .then(function (response) {
            // 응답 성공
            callback(tid, response.data);
        })
        .catch(function (error) {
            // 응답 실패

            if (error.response) {
                // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
                callback(tid, error.response.data);
            }
            else if (error.request) {
                // 요청이 이루어 졌으나 응답을 받지 못했습니다.
                // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
                // Node.js의 http.ClientRequest 인스턴스입니다.
                // console.log(error.request);
            }
            else {
                // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
                // console.log('Error', error.message);
            }

            // console.log(error.config);
        });
}

function post(tid, uri, params, callback){
    openLoading();
    axios.post(uri, params)
        .then(function (response) {
            // 응답 성공
            callback(tid, response.data);
            closeLoading();
        })
        .catch(function (error) {
            // 응답 실패
            if (error.response) {
                // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
                callback(tid, error.response.data);
            }
            else if (error.request) {
                // 요청이 이루어 졌으나 응답을 받지 못했습니다.
                // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
                // Node.js의 http.ClientRequest 인스턴스입니다.
                // console.log(error.request);
            }
            else {
                // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
                // console.log('Error', error.message);
            }
            closeLoading();
            // console.log(error.config);
        });
}

Number.prototype.format = function(){
    if(this==0) return 0;

    var reg = /(^[+-]?\d+)(\d{3})/;
    var n = (this + '');

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

function getCodeList(codeId, callback){

    get(codeId,
        "/get/code/"+codeId,
        {},
        callback);
}



// 정규식
function regExp(id, str){

    const passExp = /^[a-zA-Z0-9_\!\@\#\$\%\^\&\*\?]{10,15}$/;   //pass
    //const pass2Exp = /(?=.*\d{1,15})(?=.*[~`!@#$%\^&*()-+=]{1,15})(?=.*[a-zA-Z]{1,15}).{10,15}$/;
    const pass2Exp = /(?=.*\d{1,15})(?=.*[a-zA-Z]{1,15}).{10,15}$/;

    const emailExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; // 이메일
    
    const objIdExp = /^ncp-?([0-9]{7})-?([0-9]{1})$/; // 오브젝트스토리지 아이디
    
    let rtnMsg="";
    if(id === "EMAIL"){
        if(!emailExp.test(str)){
            rtnMsg ="N";
        }
    }else if(id === "PASS"){

        if(!passExp.test(str) && str !== ""){
            rtnMsg ="10~15자 이내 영문, 숫자, 특수문자만 입력 가능합니다.";
        }
    }else if(id === "PASS2"){

        if(!pass2Exp.test(str) && str !== ""){
            rtnMsg ="10~15자 이내의 영문,숫자를 조합하여 입력해주세요";
        }
    }else if(id === "OBJID"){

        if(!objIdExp.test(str) && str !== ""){
            rtnMsg ="N";
        }
    }

    return rtnMsg;

}


// 빈값 체크 함수
function isNull(val){
    if (val==="" || val===null || val===undefined)
        return true;
    else
        return false;
}

// Validation check 함수
// value - 항목의 값, title - 항목의 필드명, ref - ref or id  항목으로 focus, msg - 체크 메세지, type - S : radio,selectbox
//ex) let param =[  {value:this.info.agencyTypeCode, title:"회원구분", ref:this.$refs.agencyTypeCode, type:"S", msg:""},]
//    if(!isValid(param)) return false;

function isValid(item){
    for(let i=0;i < item.length;i++){
        let type = item[i].type;
        let afterText = "은(는) 필수입니다.";
        let msg = item[i].msg;

        if(isNull(msg)){
            if(!isNull(type)){
                if("S" === type){
                    afterText = "을(를) 선택해주세요";
                }
            }
            msg = item[i].title + afterText;
        }

        if(isNull(item[i].value)){

            if(!isNull(item[i].ref)){
                alertMsg(msg, item[i].ref);
            }else{
                alertMsg(msg);
            }
            return false;
        }
    }
    return true;
}

// 기관 팝업 open
function openPopupAgency(code){
    window.open("/popup/agency?agencyTypeCode="+code, "pop", "width=500px,height=500px");
}

// 기관명 팝업 callback

function callbackPopupAgency(item){
    appMain.$refs.maincontents.callbackPopupAgency(item);
}

// 저장소불러오기 팝업 callback
function callbackPopupStorage(item){
    appMain.$refs.maincontents.callbackPopupStorage(item);
}

// 저장소불러오기 팝업 callback
function callbackPopupBucket(item){
    appMain.$refs.maincontents.returnPop(item);
}

// 데이터박스불러오기 팝업 callback
function callbackPopupExport(item){
    appMain.$refs.maincontents.returnPop(item);
}

// 데이터 업로더 설정 Popup open
function openPopupUploader(seq){
    window.open("/popup/uploader?userSeq="+seq, "pop", "width=500px,height=500px");
}

// 로그인정보 get
function getUserInfo(tid,callback){
    get(tid,
        "/user/my/info",
        {},
        callback);
};

// 공통 팝업 open (layer )
function fnOpenPopup(id, code){
    if("agencyModal" == id){    // 기관검색
        popAgencyTypeCode=code;
        appPop1.$refs.popupagencycontents.onclickSearch();
    } else if("storageModal" == id){    // 저장소 검색 팝업
        appPop2.$refs.popupstoragecontents.onclickSearch();
    } else if("bucketModal" == id){    // 연구분석데이터 팝업
    	appPop3.$refs.popupbucketcontents.checkList = [];
    	for(var i  = 0; i < code.length; i++) {
			appPop3.$refs.popupbucketcontents.checkList.push(code[i]);
		}
			
        appPop3.$refs.popupbucketcontents.onclickSearchOpen();
    } else if("exportModal" == id){    // 연구분석데이터 팝업
        appPop4.$refs.popupexportcontents.onclickSearchOpen();
    } else if("downloadModal" == id){    // 연구분석데이터 팝업
    	appPop5.$refs.popupdownloadcontents.checkList = [];
		appPop5.$refs.popupdownloadcontents.obj= code;
        appPop5.$refs.popupdownloadcontents.onclickSearchOpen();
    } else if("menuModal" == id){    // 연구분석데이터 팝업
		appPop6.$refs.popupmenucontents.condMenu= code;
    }
    
    if( document.getElementById(id) !== null ) 
    	document.getElementById(id).style.display = "flex";
    document.documentElement.style.overflowX = 'hidden';
    document.documentElement.style.overflowY = 'hidden';

}

// 공통 팝업 close (layer )
function fnClosePopup(id){
    document.getElementById(id).style.display = "none";
    document.documentElement.style.overflowX = 'auto';
    document.documentElement.style.overflowY = 'auto';
}

// 알림 메세지 (layer, id )
let FOCUS_ID;
function alertMsg(msg, targetId){

    appPop1.$refs.popupagencycontents.$data.alertMsg=msg;
    if(!isNull(targetId)){
        FOCUS_ID = targetId;
    }
    fnOpenPopup('alertMsgModal');
}

// 알림 메세지 (layer, callback )
let RTN_FUNC;
function alertMsgRtn(msg, rtn){

    appPop1.$refs.popupagencycontents.$data.alertMsg=msg;
    const btnClose = document.querySelector("#btnClose");
    const btnClosePop = document.querySelector("#btnClosePop");
    
    if(!isNull(rtn)){
        RTN_FUNC=function(){
            rtn();
            alertMsgClose();
        };
        btnClose.addEventListener("click", RTN_FUNC);
        btnClosePop.addEventListener("click", RTN_FUNC);
    }
    fnOpenPopup('alertMsgModal');
}

// 알림 메세지 Close
function alertMsgClose(){

    fnClosePopup('alertMsgModal');
    if(!isNull(FOCUS_ID)){
        FOCUS_ID.focus();
    }
    appPop1.$refs.popupagencycontents.$data.alertMsg="";
    FOCUS_ID="";
}

// 확인 메세지 (msg, function(){})
let FUNC_NAME;
function confirmMsg(msg, func){

    appPop1.$refs.popupagencycontents.$data.confirmMsg=msg;
    const btnOk = document.querySelector("#btnConfirmOk");
    if(!isNull(func) && typeof(func) == 'function' ) {
        FUNC_NAME=function(){
                    func();
                confirmMsgClose();
        };
        btnOk.addEventListener("click", FUNC_NAME);

    }
    fnOpenPopup('confirmModal');
}

// 확인 메세지 Close
function confirmMsgClose(){
    appPop1.$refs.popupagencycontents.$data.confirmMsg="";
    fnClosePopup('confirmModal');
    const btnOk = document.querySelector("#btnConfirmOk");
    btnOk.removeEventListener("click", FUNC_NAME);
}


function loadSelect() {
    var x, i, j, l, ll, selElmnt, a, b, c;
    x = document.getElementsByClassName("custom-select");

    l = x.length;
    for (i = 0; i < l; i++) {
        selElmnt = x[i].getElementsByTagName("select")[0];

        var ev ="";
        if(!isNull(selElmnt.onchange )){
            ev = selElmnt.onchange;
        }
        ll = selElmnt.length;

        a = document.createElement("DIV");
        a.setAttribute("class", "select-selected");
        a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
        x[i].appendChild(a);
        b = document.createElement("DIV");
        b.setAttribute("class", "select-items select-hide");

        for (j = 0; j < ll; j++) {
            c = document.createElement("DIV");
            c.setAttribute("data-value", selElmnt.options[j].value);
            c.innerHTML = selElmnt.options[j].innerHTML;
            c.addEventListener("click", function (e) {
                var y, i, k, s, h, sl, yl, dv;
                s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                sl = s.length;
                h = this.parentNode.previousSibling;
                for (i = 0; i < sl; i++) {
                    if (s.options[i].innerHTML == this.innerHTML) {
                        s.selectedIndex = i;
                        s.setAttribute("data-value", this.getAttribute("data-value"));
                        h.innerHTML = this.innerHTML;
                        y = this.parentNode.getElementsByClassName("same-as-selected");
                        yl = y.length;
                        for (k = 0; k < yl; k++) {
                            y[k].removeAttribute("class");
                        }
                        this.setAttribute("class", "same-as-selected");

                        break;
                    }
                }
                h.click();
            });
            if(!isNull(ev)){
                c.addEventListener("click", ev);
            }
            b.appendChild(c);
        }
        x[i].appendChild(b);
        a.addEventListener("click", function (e) {
            e.stopPropagation();
            closeAllSelect(this);
            this.nextSibling.classList.toggle("select-hide");
            this.classList.toggle("select-arrow-active");
            this.parentNode.classList.toggle('show');
        });
       
    }
    function closeAllSelect(elmnt) {
        var x, y, z, i, xl, yl, zl, arrNo = [];
        x = document.getElementsByClassName("select-items");
        y = document.getElementsByClassName("select-selected");
        z = document.getElementsByClassName("search-select");

        xl = x.length;
        yl = y.length;

        for (i = 0; i < yl; i++) {
            if (elmnt == y[i]) {
                arrNo.push(i)
            } else {
                if( z.length > 0 && typeof z[i] != 'undefined' ) {
                    z[i].classList.remove("show");
                }
                y[i].classList.remove("select-arrow-active");
            }
        }
        for (i = 0; i < xl; i++) {
            if (arrNo.indexOf(i)) {
                x[i].classList.add("select-hide");
            }
        }
        for (i = 0; i < zl; i++ ) {

        }
    }
    document.addEventListener("click", closeAllSelect);
}

// guide tab
function openGuide(evt, tabName) {
    var i, searchcontent, searchlinks;
    guidecontent = document.getElementsByClassName("guide-content");
    for (i = 0; i < guidecontent.length; i++) {
        guidecontent[i].style.display = "none";
    }
    guidelinks = document.getElementsByClassName("guidelinks");
    for (i = 0; i < guidelinks.length; i++) {
        guidelinks[i].className = guidelinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

function openLoading(){
    fnOpenPopup('loadingModal');
}

function closeLoading(){
    fnClosePopup('loadingModal');
}

function onclickDownload(file){
    const bucketAdress = "https://kr.object.ncloudstorage.com/amc-test-upload-files/";
    const fileName = file.fileNm;
    axios({
        url: bucketAdress+file.fileCours,
        method: 'GET',
        responseType: 'blob'
    })
        .then((response) => {
            const url = window.URL
                .createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        })
};
function onclickDownloadAll(file){
    const bucketAddress = "https://kr.object.ncloudstorage.com/amc-test-upload-files/";
    //const fileName = file.fileName;
    for(let i=0; i<file.length;i++) {
        let fileName = file[i].fileName;
        axios({
            url: bucketAddress + file[i].filePath,
            method: 'GET',
            responseType: 'blob'
        })
            .then((response) => {
                const url = window.URL
                    .createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', fileName);
                document.body.appendChild(link);
                // Start download
                link.click();
                // Clean up and remove the link
                document.body.removeChild(link);
            })
    }
};
function fileSize(size){

    let rtn = "";

    if(isNull(size)){
        size = 0;
    }

    if(size < 1024) {
        rtn = size + " byte";
    } else if (size < 1024 * 1024) {
        rtn = (size/1024).toFixed(2)+ " KB";
    } else if (size < 1024 * 1024 * 1024) {
        rtn = (size/(1024 * 1024)).toFixed(2) + " MB";
    } else {
        rtn = (size/(1024 * 1024 * 1024)).toFixed(2) + " GB";
    }
    return rtn;
};

// select box 새로 그린다.
function reloadSelect(targetObj, list) {
    let x, i, j, l, ll, selElmnt, tempElmnt, a, b, c;

    x = document.getElementsByName(targetObj.selectDiv);
    l = x.length;

    for (i = 0; i < l; i++) {
        tempElmnt = x[i].getElementsByTagName("select")[0];

        // 기존 select box 이벤트를 확인한다.
        let onChangeVal = tempElmnt.getAttribute("onchange");
        // 자식노드 삭제
        while(x[i].hasChildNodes()) {
            x[i].removeChild(x[i].firstChild);
        }

        selElmnt = document.createElement("select");
        selElmnt.setAttribute("id",targetObj.selectId);
        // 기존 onchange 이벤트가 있는지 확인한다.
        if(!isNull(onChangeVal)) {
            selElmnt.setAttribute("onchange", onChangeVal);
        }

        let firstOpt = document.createElement("option");
        firstOpt.value = tempElmnt[0].value;
        firstOpt.innerHTML = tempElmnt.options[0].innerHTML;

        selElmnt.appendChild(firstOpt);

        // list만큼 option 생성.
        for(let m=0; m < list.length; m++) {
            let opt = document.createElement("option");
            opt.value = eval('list[m].'+targetObj.optionValue);
            if(targetObj.selectDiv == "subnetDiv") { // Subnet select box
                opt.innerHTML = list[m].subnetName + " | "+ list[m].subnet + " | " + list[m].subnetType.codeName;
            } else if(targetObj.selectDiv == "serverProductDiv") { // 서버스펙
                opt.innerHTML = list[m].productDescription + " | "+ list[m].productType.codeName;
            } else {
                opt.innerHTML = eval('list[m].'+targetObj.optionName);
            }
            selElmnt.appendChild(opt);
        }
        x[i].appendChild(selElmnt);

        let ev ="";
        if(!isNull(selElmnt.onchange )){
            ev = selElmnt.onchange;
        }

        ll = selElmnt.length;
        a = document.createElement("DIV");
        a.setAttribute("class", "select-selected");
        a.innerHTML = selElmnt.options[0].innerHTML;
        x[i].appendChild(a);
        b = document.createElement("DIV");
        b.setAttribute("class", "select-items select-hide");
        for (j = 0; j < ll; j++) {
            c = document.createElement("DIV");
            c.setAttribute("data-value", selElmnt.options[j].value);
            c.innerHTML = selElmnt.options[j].innerHTML;
            c.addEventListener("click", function (e) {
                var y, i, k, s, h, sl, yl, dv;
                s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                sl = s.length;
                h = this.parentNode.previousSibling;
                for (i = 0; i < sl; i++) {
                    if (s.options[i].innerHTML == this.innerHTML) {
                        s.selectedIndex = i;
                        s.setAttribute("data-value", this.getAttribute("data-value"));
                        h.innerHTML = this.innerHTML;
                        y = this.parentNode.getElementsByClassName("same-as-selected");
                        yl = y.length;
                        for (k = 0; k < yl; k++) {
                            y[k].removeAttribute("class");
                        }
                        this.setAttribute("class", "same-as-selected");

                        break;
                    }
                }
                h.click();
            });
            if(!isNull(ev)){
                c.addEventListener("click", ev);
            }
            b.appendChild(c);
        }
        x[i].appendChild(b);
        a.addEventListener("click", function (e) {
            e.stopPropagation();
            closeAllSelect(this);
            this.nextSibling.classList.toggle("select-hide");
            this.classList.toggle("select-arrow-active");
            this.parentNode.classList.toggle('show');
        });
    }
    function closeAllSelect(elmnt) {
        var x, y, z, i, xl, yl, zl, arrNo = [];
        x = document.getElementsByClassName("select-items");
        y = document.getElementsByClassName("select-selected");
        z = document.getElementsByClassName("search-select");

        xl = x.length;
        yl = y.length;

        for (i = 0; i < yl; i++) {
            if (elmnt == y[i]) {
                arrNo.push(i)
            } else {
	
                if( z.length > 0 && typeof z[i] != 'undefined') {
                    z[i].classList.remove("show");
                }
                y[i].classList.remove("select-arrow-active");
            }
        }
        for (i = 0; i < xl; i++) {
            if (arrNo.indexOf(i)) {
                x[i].classList.add("select-hide");
            }
        }
    }
    document.addEventListener("click", closeAllSelect);
};

function textDownload(filename, text) {
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
};

function replaceDate(val){
    if(isNull(val)){
        return "-";
    } else {
        return val.substr(0,10);
    }
};

function replaceComma(val) {
	if(isNull(val)){
        return 0;
    } else {
        return val.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
    }
};

function replaceBlNumber(val) {
	if(isNull(val)){
        return 0;
    } else if (val.length == 10) {
		return val.substr(0,3) + "-" + val.substr(3,2) + "-" + val.substr(5);
	} else {
        return val;
    }
};
