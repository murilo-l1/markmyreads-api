package com.server.markmyreads.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class KindleNote {

    private final String title;
    private final String author;
    private final List<String> notes;
    private LocalDate lastReadAt;

    public KindleNote(final String title, final String author, final String note, final LocalDate lastReadAt) {
        this.title = title;
        this.author = author;
        this.notes = new ArrayList<>(List.of(note));
        this.lastReadAt = lastReadAt;
    }

    public void addNote(@NonNull @NotBlank final String note) {
        this.notes.add(note);
    }

    public void updateLastReadAt(final LocalDate date) {
        if (date != null && (lastReadAt == null || date.isAfter(this.lastReadAt))) {
            this.lastReadAt = date;
        }
    }

    public int notesCount() {
        return this.notes.size();
    }

    public void removeDuplicateNotes() {

        if (this.notes == null || this.notes.size() <= 1) {
            return;
        }

        final List<String> filteredNotes = new ArrayList<>();
        int index = 0;

        while (index < this.notes.size()) {
            final String currentNote = this.notes.get(index);

            if (index == this.notes.size() - 1) { // if we are on last note, just add and quit
                filteredNotes.add(currentNote);
                break;
            }

            final String nextNote = this.notes.get(index + 1);

            if (currentNote.contains(nextNote)) { // if the current is the bigger version, skip next
                filteredNotes.add(currentNote);
                index += 2;
            }
            else if (nextNote.contains(currentNote)) { // but if next is the bigger one, skip current
                index++;
            }
            else { // notes arent duplicate versions
                filteredNotes.add(currentNote);
                index++;
            }

        }

        this.notes.clear();
        this.notes.addAll(filteredNotes);
    }

    @Override
    public String toString() {
        return "KindleNote{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                ", lastReadAt=" + lastReadAt +
                ", notesCount=" + this.notesCount() +
                '}';
    }

}