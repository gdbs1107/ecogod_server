package or.ecogad.ecogad.domain.auth.application.command;

public record AdminLoginCommand(
        String loginId,
        String password
) {
}
