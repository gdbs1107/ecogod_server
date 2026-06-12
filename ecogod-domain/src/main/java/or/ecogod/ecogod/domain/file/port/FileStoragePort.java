package or.ecogod.ecogod.domain.file.port;

import or.ecogod.ecogod.domain.file.domain.model.StoredFile;

import java.io.InputStream;

public interface FileStoragePort {
    StoredFile uploadProductImage(String originalFilename, String contentType, long size, InputStream inputStream);
}
