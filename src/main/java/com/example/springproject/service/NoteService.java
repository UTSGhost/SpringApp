package com.example.springproject.service;

import com.example.springproject.entity.Note;
import com.example.springproject.entity.User;
import com.example.springproject.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotesByUser(User user){
        return noteRepository.findAllByUser(user);
    }
}
