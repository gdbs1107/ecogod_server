package or.ecogod.ecogod.domain.notice.application.service;

import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.domain.notice.application.command.NoticeCreateCommand;
import or.ecogod.ecogod.domain.notice.domain.model.Notice;
import or.ecogod.ecogod.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminNoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private AdminNoticeService adminNoticeService;

    @Test
    @DisplayName("createNotice()는 공지사항을 성공적으로 등록한다")
    void createNotice_success() {
        // given
        NoticeCreateCommand command = new NoticeCreateCommand(
                "시스템 점검 공지",
                "점검이 예정되어 있습니다.",
                true
        );

        when(noticeRepository.existsByTitle("시스템 점검 공지")).thenReturn(false);
        when(noticeRepository.save(any(Notice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        adminNoticeService.createNotice(command);

        // then
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }

    @Test
    @DisplayName("createNotice()는 제목이 중복되면 예외를 발생시킨다")
    void createNotice_duplicate_throwsException() {
        // given
        NoticeCreateCommand command = new NoticeCreateCommand(
                "시스템 점검 공지",
                "점검이 예정되어 있습니다.",
                false
        );

        when(noticeRepository.existsByTitle("시스템 점검 공지")).thenReturn(true);

        // when // then
        assertThrows(CustomException.class, () -> adminNoticeService.createNotice(command));
        verify(noticeRepository, never()).save(any(Notice.class));
    }
}
