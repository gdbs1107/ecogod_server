package or.ecogod.ecogod.domain.auth.application.command;

public record AdminLoginCommand(
        String loginId,
        String password
) {
}
