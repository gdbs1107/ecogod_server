package or.ecogod.ecogod.external.file.storage;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.file.domain.model.StoredFile;
import or.ecogod.ecogod.domain.file.port.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileStorageAdapter implements FileStoragePort {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${app.storage.s3.bucket:}")
    private String bucket;

    @Value("${app.storage.s3.region:ap-northeast-2}")
    private String region;

    @Value("${app.storage.s3.public-base-url:}")
    private String publicBaseUrl;

    @Value("${app.storage.s3.product-image-prefix:products}")
    private String productImagePrefix;

    @Override
    public StoredFile uploadProductImage(String originalFilename, String contentType, long size, InputStream inputStream) {
        if (bucket == null || bucket.isBlank()) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        String extension = extractExtension(originalFilename);
        String key = normalizePrefix(productImagePrefix) + UUID.randomUUID() + extension;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, size));
            return new StoredFile(key, resolveUrl(key), originalFilename, contentType, size);
        } catch (Exception exception) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private String normalizePrefix(String prefix) {
        String normalized = prefix == null ? "products" : prefix.trim();
        if (normalized.isBlank()) {
            normalized = "products";
        }
        if (!normalized.endsWith("/")) {
            normalized += "/";
        }
        return normalized;
    }

    private String resolveUrl(String key) {
        if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
            return publicBaseUrl.replaceAll("/+$", "") + "/" + key;
        }
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    private String extractExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index < 0) {
            return "";
        }
        return filename.substring(index).toLowerCase(Locale.ROOT);
    }
}
