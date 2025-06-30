package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.constant.ClippingsConstants;
import com.server.markmyreads.domain.dto.BookcoverDto;
import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.handler.exception.ClippingsExtractionErrorException;
import com.server.markmyreads.service.ClippingsExtractorService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service("ExtractorService")
public class ClippingsExtractorServiceImpl implements ClippingsExtractorService {

    @Override
    public ClippingsContext extractClippingsBlocks(@NonNull final MultipartFile file) {

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            final Set<String> blocksSet = new LinkedHashSet<>();
            StringBuilder currentBlock = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals(ClippingsConstants.BLOCK_DELIMITER)) {
                    if (!currentBlock.isEmpty()) {
                        blocksSet.add(currentBlock.toString());
                        currentBlock = new StringBuilder();
                    }
                }
                else {
                    currentBlock.append(line).append("\n");
                }
            }

            if (!currentBlock.isEmpty()) {
                blocksSet.add(currentBlock.toString());
            }

            if (blocksSet.isEmpty()) {
                throw new ClippingsExtractionErrorException();
            }

            br.close();

            return new ClippingsContext(blocksSet.stream().toList());
        }
        catch (Exception e) {
            throw new ClippingsExtractionErrorException();
        }

    }

    @Override
    public List<BookcoverDto> extractAuthorAndTitle(@NotNull final @NonNull MultipartFile file) {
        return List.of();
    }


}
