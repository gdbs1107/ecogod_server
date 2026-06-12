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
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getAdminNotices() {
        return noticeRepository.findAll();
    }

    public Notice getAdminNotice(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    }

    @Transactional
    public Notice createNotice(String title, String summary, String content, boolean published) {
        if (noticeRepository.existsByTitle(title)) {
            throw new CustomException(ErrorCode.DUPLICATE_NOTICE);
        }
        return noticeRepository.save(Notice.create(title, summary, content, published));
    }

    @Transactional
    public Notice updateNotice(Long noticeId, String title, String summary, String content, boolean published) {
        Notice current = getAdminNotice(noticeId);
        if (noticeRepository.existsByTitleAndIdNot(title, noticeId)) {
            throw new CustomException(ErrorCode.DUPLICATE_NOTICE);
        }
        return noticeRepository.save(current.update(title, summary, content, published));
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        getAdminNotice(noticeId);
        noticeRepository.deleteById(noticeId);
    }
}
