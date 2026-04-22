package or.ecogad.ecogad.external.inquiry.notification;

import lombok.RequiredArgsConstructor;
import or.ecogad.ecogad.domain.inquiry.domain.model.QuoteInquiry;
import or.ecogad.ecogad.domain.inquiry.port.InquiryNotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryEmailNotificationSender implements InquiryNotificationSender {

    private final JavaMailSender javaMailSender;

    @Value("${app.mail.inquiry-to}")
    private String inquiryTo;

    @Value("${app.mail.from:no-reply@ecogad.com}")
    private String from;

    @Override
    public void send(QuoteInquiry inquiry) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(inquiryTo);
        message.setFrom(from);
        message.setSubject("[ecogad] 신규 견적 문의 접수");
        message.setText(buildBody(inquiry));

        javaMailSender.send(message);
    }

    private String buildBody(QuoteInquiry inquiry) {
        return "이름: " + inquiry.getName() + "\n"
                + "회사명: " + inquiry.getCompanyName() + "\n"
                + "연락처: " + inquiry.getPhone() + "\n"
                + "이메일: " + inquiry.getEmail() + "\n"
                + "문의내용: " + inquiry.getMessage();
    }
}
