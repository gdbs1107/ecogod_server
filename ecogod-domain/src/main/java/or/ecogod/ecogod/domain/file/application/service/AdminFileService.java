package or.ecogod.ecogod.domain.file.application.service;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import or.ecogod.ecogod.domain.file.domain.model.StoredFile;
import or.ecogod.ecogod.domain.file.port.FileStoragePort;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AdminFileService {

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    private final FileStoragePort fileStoragePort;

    public StoredFile uploadProductImage(String originalFilename, String contentType, long size, InputStream inputStream) {
        if (size <= 0 || size > MAX_IMAGE_SIZE || originalFilename == null || originalFilename.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_FILE_UPLOAD);
        }

        if (contentType == null || !java.util.Set.of("image/jpeg", "image/png", "image/webp").contains(contentType.toLowerCase())) {
            throw new CustomException(ErrorCode.INVALID_FILE_UPLOAD);
        }

        return fileStoragePort.uploadProductImage(originalFilename, contentType, size, inputStream);
    }

    public void deleteProductImage(String key) {
        if (key == null || key.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_FILE_UPLOAD);
        }
        fileStoragePort.deleteProductImage(key);
    }
}
