package or.ecogod.ecogod.domain.auth.application.command;

public record AdminSignupCommand(
        String loginId,
        String password,
        String name
) {
}
