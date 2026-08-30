package com.example.springproject.controller;

import com.example.springproject.entity.Note;
import com.example.springproject.entity.User;
import com.example.springproject.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(@AuthenticationPrincipal User user){
        List<Note> userNotes = noteService.getAllNotesByUser(user);
        return ResponseEntity.ok(userNotes);
    }
}
