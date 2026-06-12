package or.ecogod.ecogod.domain.file.domain.model;

public record StoredFile(
        String key,
        String url,
        String originalFilename,
        String contentType,
        long size
) {
}
