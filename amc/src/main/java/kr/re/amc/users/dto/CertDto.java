package kr.re.amc.users.dto;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import kr.re.amc.utils.StringUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CertDto {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 작성해 주세요.")
    private String email;

    private String certNumber;

    public CertDto(){
        this.certNumber = StringUtil.random(6);
    }

    public CertDto(String email, String certNumber){
        this.email = email;
        this.certNumber = StringUtil.random(6);
    }

}
