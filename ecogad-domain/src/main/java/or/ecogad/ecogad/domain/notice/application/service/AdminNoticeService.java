package or.ecogad.ecogad.domain.notice.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogad.ecogad.common.exception.CustomException;
import or.ecogad.ecogad.common.exception.ErrorCode;
import or.ecogad.ecogad.domain.notice.application.command.NoticeCreateCommand;
import or.ecogad.ecogad.domain.notice.application.result.NoticeCreateResult;
import or.ecogad.ecogad.domain.notice.domain.model.Notice;
import or.ecogad.ecogad.domain.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeCreateResult createNotice(NoticeCreateCommand command) {
        if (noticeRepository.existsByTitle(command.title())) {
            throw new CustomException(ErrorCode.DUPLICATE_NOTICE);
        }

        Notice saved = noticeRepository.save(Notice.create(command.title(), command.content(), command.published()));
        return new NoticeCreateResult(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.isPublished(),
                saved.getPublishedAt(),
                saved.getCreatedAt()
        );
    }
}
