
/*
 * @name : noticeView.js
 * @date : 2021-08-12 오후 3:27
 * @author : lsj
 * @version : 1.0.0
 * @modifyed :
 */

let appMain;
const TID = {
    SEARCH : {value: 0, name: "search", code: "S"},
    DELETE : {value: 0, name: "delete", code: "D"}
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
            noticeNo : noticeNo,
            userInfo : [], // 사용자정보
            notice : [], // 공지사항 상세
            messages : "",
        };
    },
    mounted:function(){
        this.getNoticeView();
        console.log("HELLO");
        this.getUserInfo();
    },
    methods:{
        // 공지사항 상세 조회
        getNoticeView:function (){
            get(TID.SEARCH,
                "/board/notice/get/"+this.noticeNo,
                {},
                this.callback);
        },
        getUserInfo: function(){
            get("usrInfo","/user/my/info",{},this.callback);
        },
        // 취소 클릭(목록 이동)
        onclickList: function () {
            location.href = "/board/notice/main";
        },
        // 수정 클릭(ADMIN)
        onclickModify: function () {
            confirmMsg("수정하시겠습니까?", this.mod);
        },
        mod: function() {
            location.href = "/board/notice/reg?noticeNo="+this.noticeNo;
        },
        // 삭제 클릭(ADMIN)
        onclickDelete: function () {
            confirmMsg("삭제하시겠습니까?", this.del);
        },
        del: function() {
            post(TID.DELETE,
                "/board/notice/delete/"+this.noticeNo,
                {},
                this.callback);
        },
        // 제목 클릭(상세보기)
        onclickView: function (noticeNo) {
            location.href = "/board/notice/view?noticeNo="+noticeNo;
        },
        onclickDownloadFile: function(item) {
                console.log(item);
             axios({
	
                url: "/board/downloadFile?fileCours="+item.fileCours+"&"+"fileNm="+item.fileNm,
                method: "get",
                responseType: "blob",
            }).then((response) => {
				var tempDate = new Date();
				const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', item.fileNm); 
                document.body.appendChild(link);
                link.click(); // Start download
                document.body.removeChild(link); // Clean up and remove the link
            })  .catch((error) => {
	
                // 예외 처리
                if (error.response) {
                    alertMsg(error.message);
                }
            })
            ;
        },
        onclickDownloadAllFile: function(file) {
           
           for(let i=0; i<file.length;i++) {
				let fileNm = file[i].fileNm;
	             axios({
		
	                url: "/board/downloadFile?fileCours="+file[i].fileCours+"&"+"fileNm="+file[i].fileNm,
	                method: "get",
	                responseType: "blob",
	            }).then((response) => {
					var tempDate = new Date();
					const url = window.URL.createObjectURL(new Blob([response.data]));
	                const link = document.createElement('a');
	                link.href = url;
	                link.setAttribute('download', fileNm); 
	                document.body.appendChild(link);
	                link.click(); // Start download
	                document.body.removeChild(link); // Clean up and remove the link
	            })  .catch((error) => {
		
	                // 예외 처리
	                if (error.response) {
	                    alertMsg(error.message);
	                }
	            })
            };
                
        },

        callback: function (tid, results) {
            switch (tid) {
                case TID.SEARCH:
                    this.searchCallback(results);
                    console.log(results);
                    break;
                case "usrInfo":
                    if (results.success) {
                        this.userInfo = results.response;
                    } else {
                        console.log(results);
                    }
                    break;
                case TID.DELETE:
                    if (results.success) {
                        alertMsgRtn("정상적으로 삭제되었습니다.",this.onclickList);
                    } else {
                        //console.log(results);
                        alertMsg(results.error.message);
                    }
                    break;
            }
        },
        searchCallback: function (results) {
            if (results.success) {
                //console.log(results);
                this.notice      = results.response;
            } else {
                alertMsg(results.error.message);
            }
        },
        // onclickDownload:function (file){
        //     const bucketAdress = "https://kr.object.ncloudstorage.com/dranswer-upload-files/";
        //     const fileName = file.fileName;
        //
        //     axios({
        //         url: bucketAdress+file.filePath,
        //         method: 'GET',
        //         responseType: 'blob'
        //     })
        //         .then((response) => {
        //             const url = window.URL
        //                 .createObjectURL(new Blob([response.data]));
        //             const link = document.createElement('a');
        //             link.href = url;
        //             link.setAttribute('download', fileName);
        //             document.body.appendChild(link);
        //             link.click();
        //             document.body.removeChild(link);
        //         })
        // }

    }
});

