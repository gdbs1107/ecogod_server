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

    private final FileStoragePort fileStoragePort;

    public StoredFile uploadProductImage(String originalFilename, String contentType, long size, InputStream inputStream) {
        if (size <= 0 || originalFilename == null || originalFilename.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_FILE_UPLOAD);
        }

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new CustomException(ErrorCode.INVALID_FILE_UPLOAD);
        }

        return fileStoragePort.uploadProductImage(originalFilename, contentType, size, inputStream);
    }
}
