package com.server.markmyreads.controller.privat;

import com.server.markmyreads.controller.CommonController;
import com.server.markmyreads.domain.dto.SessionDto;
import com.server.markmyreads.usecase.upload.ClippingsUpload;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("UploadController")
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class UploadController extends CommonController {

    private final ClippingsUpload upload;

    @PostMapping(value = "/upload",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SessionDto> upload(@NonNull @RequestParam(name = "file") final MultipartFile file) {
        return upload.upload(file);
    }

}
