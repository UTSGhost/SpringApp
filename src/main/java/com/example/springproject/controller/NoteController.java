package com.example.springproject.controller;

import com.example.springproject.dto.NoteRequest;
import com.example.springproject.dto.NoteResponse;
import com.example.springproject.entity.Note;
import com.example.springproject.entity.User;
import com.example.springproject.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes(@AuthenticationPrincipal User user){
        List<NoteResponse> userNotesResponse = noteService.getAllNotesByUser(user)
                .stream()
                .map(n -> new NoteResponse(n.getId(), n.getData()))
                .toList();
        return ResponseEntity.ok(userNotesResponse);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> saveNote(@RequestBody NoteRequest noteRequest, @AuthenticationPrincipal User user){
        Note note = new Note(noteRequest.data(), user);
        Note savedNote = noteService.saveNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(new NoteResponse(savedNote.getId(), savedNote.getData()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id, @AuthenticationPrincipal User user){
        noteService.deleteNote(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable long id,
            @RequestBody NoteRequest noteRequest,
            @AuthenticationPrincipal User user
    ) {
        Note updatedNote = noteService.updateNote(id, noteRequest, user);
        return ResponseEntity.ok(new NoteResponse(updatedNote.getId(), updatedNote.getData()));
    }
}
