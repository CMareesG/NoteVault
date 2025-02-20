package com.marees.SecureNotes.controller;

import com.marees.SecureNotes.models.Note;
import com.marees.SecureNotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return noteService.createNoteForUser(userName, content);
    }

    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable Long noteId, @RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return noteService.updateNoteForUser(noteId, content, userName);
    }

    @GetMapping
    public List<Note> getNotes(@AuthenticationPrincipal UserDetails userDetails) { // Renamed to plural
        String userName = userDetails.getUsername();
        return noteService.getNotesForUser(userName);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, @AuthenticationPrincipal UserDetails userDetails) {
        // Optional: Add a check to ensure that only the owner can delete their notes
        String userName = userDetails.getUsername();
        noteService.deleteNoteForUser(noteId,userName);
    }

}
