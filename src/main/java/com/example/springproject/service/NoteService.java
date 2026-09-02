package com.example.springproject.service;

import com.example.springproject.dto.NoteRequest;
import com.example.springproject.entity.Note;
import com.example.springproject.entity.User;
import com.example.springproject.exception.ResourceNotFoundException;
import com.example.springproject.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotesByUser(User user){
        return noteRepository.findAllByUser(user);
    }

    public Note saveNote(Note note){
        return noteRepository.save(note);
    }

    public void deleteNote(Long id, User user){
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        noteRepository.delete(note);
    }

    public Note updateNote(Long id, NoteRequest noteRequest, User user){
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note with ID " + id + " not found"));
        note.setData(noteRequest.data());
        return noteRepository.save(note);
    }
}
