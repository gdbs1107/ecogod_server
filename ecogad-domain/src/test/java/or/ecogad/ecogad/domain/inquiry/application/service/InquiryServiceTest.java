package or.ecogad.ecogad.domain.inquiry.application.service;

import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.domain.inquiry.application.command.InquiryCreateCommand;
import or.ecogad.ecogad.domain.inquiry.domain.model.QuoteInquiry;
import or.ecogad.ecogad.domain.inquiry.port.InquiryNotificationSender;
import or.ecogad.ecogad.domain.inquiry.repository.QuoteInquiryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

    @Mock
    private QuoteInquiryRepository quoteInquiryRepository;

    @Mock
    private InquiryNotificationSender inquiryNotificationSender;

    @InjectMocks
    private InquiryService inquiryService;

    @Test
    @DisplayName("createInquiry()는 문의를 저장하고 이메일 알림을 전송한다")
    void createInquiry_success() {
        // given
        InquiryCreateCommand command = new InquiryCreateCommand(
                "홍길동",
                "에코가드",
                "010-1234-5678",
                "test@ecogad.com",
                "견적 문의드립니다.",
                true
        );

        when(quoteInquiryRepository.save(any(QuoteInquiry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        inquiryService.createInquiry(command);

        // then
        verify(quoteInquiryRepository, times(1)).save(any(QuoteInquiry.class));
        verify(inquiryNotificationSender, times(1)).send(any(QuoteInquiry.class));
    }

    @Test
    @DisplayName("createInquiry()는 이메일 전송 실패 시 예외를 발생시킨다")
    void createInquiry_mailFailed_throwsException() {
        // given
        InquiryCreateCommand command = new InquiryCreateCommand(
                "홍길동",
                "에코가드",
                "010-1234-5678",
                "test@ecogad.com",
                "견적 문의드립니다.",
                true
        );

        when(quoteInquiryRepository.save(any(QuoteInquiry.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("mail error")).when(inquiryNotificationSender).send(any(QuoteInquiry.class));

        // when // then
        assertThrows(CustomException.class, () -> inquiryService.createInquiry(command));
    }
}
