package kr.re.amc.databoxFrame.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataBoxFramePage {

    // 관리자 마이페이지 데이터박스 사용신청
    @GetMapping(value = "/my/databoxFrame/list")
    public String myDataBoxList() {

        return "pages/databoxFrame/dataBoxFrameList";
    }
    
    
    // 데이터박스 사용신청
    @GetMapping(value = "/my/databoxFrame/req")
    public String dataBoxFrameReq() throws Exception {
        return "pages/databoxFrame/dataBoxFrameReq";
    }  
    
    // 데이터박스 사용신청
    @GetMapping(value = "/my/databoxFrame/view")
    public String dataBoxFrameView() throws Exception {
        return "pages/databoxFrame/dataBoxFrameView";
    }   
    
   
}
