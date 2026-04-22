package or.ecogad.ecogad.domain.notice.application.command;

public record NoticeCreateCommand(
        String title,
        String content,
        boolean published
) {
}
