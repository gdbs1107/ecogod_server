package or.ecogad.ecogad.domain.auth.application.command;

public record AdminSignupCommand(
        String loginId,
        String password,
        String name
) {
}
