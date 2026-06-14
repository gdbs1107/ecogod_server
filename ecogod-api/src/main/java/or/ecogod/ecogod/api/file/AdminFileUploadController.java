package or.ecogod.ecogod.api.file;

import lombok.RequiredArgsConstructor;
import or.ecogod.ecogod.api.file.response.FileUploadResponse;
import or.ecogod.ecogod.common.api.ApiResponse;
import or.ecogod.ecogod.domain.file.application.service.AdminFileService;
import or.ecogod.ecogod.domain.file.domain.model.StoredFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/uploads")
public class AdminFileUploadController {

    private final AdminFileService adminFileService;

    @PostMapping("/products/images")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadProductImage(@RequestParam("file") MultipartFile file) throws IOException {
        StoredFile storedFile = adminFileService.uploadProductImage(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                file.getInputStream()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(FileUploadResponse.from(storedFile)));
    }

    @DeleteMapping("/products/images")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(@Valid @RequestBody FileDeleteRequest request) {
        adminFileService.deleteProductImage(request.key());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    public record FileDeleteRequest(@NotBlank String key) {
    }
}
