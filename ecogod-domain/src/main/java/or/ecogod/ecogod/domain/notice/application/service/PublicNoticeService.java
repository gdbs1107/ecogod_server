package or.ecogod.ecogod.domain.notice.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.notice.domain.model.Notice;
import or.ecogod.ecogod.domain.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicNoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getNotices() {
        return noticeRepository.findAllPublished();
    }

    public Notice getNotice(Long noticeId) {
        return noticeRepository.findPublishedById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    }
}
