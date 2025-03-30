package com.server.markmyreads.domain.payload;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteFormatOptionsPayload {

    private NoteSortType sort = NoteSortType.DATE_DESC;

    private NoteStyleEnum style = NoteStyleEnum.CLASSIC;

}
