package kr.re.amc.users.page;


/*
 * @package : com.itsm.dranswer.users
 * @name : UserPage.java
 * @date : 2021-06-07 오전 11:02
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPage {


    @GetMapping(value = "/login")
    public String login() {

        return "pages/user/login";
    }

    // ID or 비밀번호찾기
    @GetMapping(value = "/login/findIdPw")
    public String findIdPw() {

        return "pages/user/findIdPw";
    }

    // 마이페이지(관리자)  > 회원목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/admin/memberList")
    public String myAdminMemberList() {

        return "pages/user/myMemberList";
    }
    // 마이페이지(관리자)  > 회원상세
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/admin/memberView")
    public String myAdminMemberView() {
        return "pages/user/myMemberView";
    }


    // 마이페이지  > 회원정보변경
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/userModify")
    public String myUserModify() {
        return "pages/user/myUserModify";
    }

    // 마이페이지  > 회원정보변경 > 비밀번호변경
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/userPasswd")
    public String myUserPasswd() {
        return "pages/user/myUserPasswd";
    }

    // 마이페이지  > 회원정보변경 > 데이터업로더 팝업
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/popup/uploader")
    public String popupUploader() {
        return "pages/user/popUploader";
    }

}
