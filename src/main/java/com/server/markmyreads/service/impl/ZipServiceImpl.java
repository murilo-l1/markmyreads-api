package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.service.ZipService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ZipService")
public class ZipServiceImpl implements ZipService {


    @Override
    public byte[] zipMarkdowns(@NonNull final List<MarkMyReadsFile> files) {
        final String fileExtension = ".md";


    }

    @Override
    public byte[] zipPdfs(@NonNull final List<MarkMyReadsFile> files) {
        final String fileExtension = ".pdf";





    }


}
