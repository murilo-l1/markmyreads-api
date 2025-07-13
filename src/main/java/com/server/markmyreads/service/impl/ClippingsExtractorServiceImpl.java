package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.constant.ClippingsConstants;
import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.ClippingsLocale;
import com.server.markmyreads.handler.exception.ClippingsExtractionErrorException;
import com.server.markmyreads.service.ClippingsExtractorService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ExtractorService")
public class ClippingsExtractorServiceImpl implements ClippingsExtractorService {

    private static final Pattern PT_BR_FLAG = Pattern.compile("Adicionado|posição|segunda|terça");
    private static final Pattern EN_US_FLAG = Pattern.compile("Added|on|position|monday|tuesday"); // for now, if it's not PT-BR it will be EN-US no matter lang.

    @Override
    public ClippingsContext extractClippingsBlocks(@NonNull final MultipartFile file) {
        ClippingsLocale detectedLocale = null;

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
                    if (detectedLocale == null) {
                        detectedLocale = findLocale(line);
                    }
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

            return new ClippingsContext(blocksSet.stream().toList(), detectedLocale);
        }
        catch (Exception e) {
            throw new ClippingsExtractionErrorException();
        }

    }

    private ClippingsLocale findLocale(final String testLine) {
        final Matcher ptBrMatcher = PT_BR_FLAG.matcher(testLine);
        if (ptBrMatcher.find()) {
            return ClippingsLocale.PT_BR;
        }

        final Matcher enUsMatcher = EN_US_FLAG.matcher(testLine);
        if (enUsMatcher.find()) {
            return ClippingsLocale.EN_US;
        }

        return null;
    }

}
