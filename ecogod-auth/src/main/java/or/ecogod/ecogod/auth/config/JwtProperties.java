package or.ecogod.ecogod.auth.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.auth.jwt")
public class JwtProperties {

    @NotBlank
    private String header;

    @NotBlank
    @Size(min = 32)
    private String secret;

    @Min(60)
    private long accessTokenValidityInSeconds;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getAccessTokenValidityInSeconds() {
        return accessTokenValidityInSeconds;
    }

    public void setAccessTokenValidityInSeconds(long accessTokenValidityInSeconds) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }
}
