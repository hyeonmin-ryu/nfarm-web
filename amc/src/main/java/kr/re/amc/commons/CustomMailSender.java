package kr.re.amc.commons;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Component
public class CustomMailSender {

	@Value("${spring.mail.username}")
	private String adminMail;

	private final JavaMailSender mailSender;

    private final SpringTemplateEngine emailTemplateEngine;
    
    
    @Autowired
    public CustomMailSender(JavaMailSender mailSender, SpringTemplateEngine emailTemplateEngine){
        this.mailSender = mailSender;
        this.emailTemplateEngine = emailTemplateEngine;
    }

    public void sendMail(String template, String subject, String[] to, Context context) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setTo(to);
        helper.setFrom(adminMail);
        String html = emailTemplateEngine.process(template, context);
        helper.setText(html, true);

        mailSender.send(message);
    }

    public void sendAcceptMail(String email, String mailsubject, String title, String userName, String subject) {
        String template = "mail/accept";
//        String mailsubject = "[국토연구원]이메일 인증을 위한 인증번호가 발급되었습니다.";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("title", title);
        ctx.setVariable("userName", userName);
        ctx.setVariable("subject", subject);

        try {
            this.sendMail(template, mailsubject, to, ctx);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendRejectMail(String email, String mailsubject, String title, String userName, String subject, String reject) {
        String template = "mail/reject";
//        String mailsubject = "[국토연구원]이메일 인증을 위한 인증번호가 발급되었습니다.";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("title", title);
        ctx.setVariable("userName", userName);
        ctx.setVariable("subject", subject);
        ctx.setVariable("reject", reject);

        try {
            this.sendMail(template, mailsubject, to, ctx);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void sendReqMail(String email, String mailSubject, String title, String agencyName, String userName) {
        String template = "mail/request";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("title", title);
        ctx.setVariable("agencyName", agencyName);
        ctx.setVariable("userName", userName);

        try {
            this.sendMail(template, mailSubject, to, ctx);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendExpiredAlarmMail(String email, String mailSubject, String title, String msg) {
        String template = "mail/expiredAlarm";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("title", title);
        ctx.setVariable("msg", msg);

        try {
            this.sendMail(template, mailSubject, to, ctx);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendJoinReqMail(String email, String mailSubject, String userName) {
        String template = "mail/joinRequest";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("userName", userName);

        try {
            this.sendMail(template, mailSubject, to, ctx);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public void sendBillsMail(String email, String mailsubject, String title, String userName, String subject,File file, String fileName, String mailtext) throws MessagingException, IOException {
        String template = "mail/bills";
        String[] to = {email};
        Context ctx = new Context();
        ctx.setVariable("title", title);
        ctx.setVariable("userName", userName);
        ctx.setVariable("subject", subject);
        ctx.setVariable("mailtext", mailtext);

        this.sendMailWithFile(template, mailsubject, to, ctx, file, fileName);
    }
    
    public void sendMailWithFile(String template, String subject, String[] to, Context context, File file, String fileName) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setTo(to);
        helper.setFrom(adminMail);
        String html = emailTemplateEngine.process(template, context);
        helper.setText(html, true);
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }
}
