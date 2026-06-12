package or.ecogod.ecogod.external.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3StorageConfig {

    @Bean
    public S3Client s3Client(@Value("${app.storage.s3.region:ap-northeast-2}") String region) {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(@Value("${app.storage.s3.region:ap-northeast-2}") String region) {
        return S3Presigner.builder()
                .region(Region.of(region))
                .build();
    }
}
