package or.ecogod.ecogod.api.file.response;

import or.ecogod.ecogod.domain.file.domain.model.StoredFile;

public record FileUploadResponse(
        String key,
        String url,
        String originalFilename,
        String contentType,
        long size
) {
    public static FileUploadResponse from(StoredFile storedFile) {
        return new FileUploadResponse(
                storedFile.key(),
                storedFile.url(),
                storedFile.originalFilename(),
                storedFile.contentType(),
                storedFile.size()
        );
    }
}
