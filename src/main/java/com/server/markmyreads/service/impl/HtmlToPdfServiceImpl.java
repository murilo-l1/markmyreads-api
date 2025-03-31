package com.server.markmyreads.service.impl;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.server.markmyreads.service.HtmlToPdfService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service("Html2PdfService")
public class HtmlToPdfServiceImpl implements HtmlToPdfService {

    @Override
    public byte[] toPdfBytes(@NonNull final String htmlContent) {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(htmlContent, byteOut);

        return byteOut.toByteArray();
    }

}
