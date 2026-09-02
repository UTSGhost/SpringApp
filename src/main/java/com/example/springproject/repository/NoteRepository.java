package com.example.springproject.repository;

import com.example.springproject.entity.Note;
import com.example.springproject.entity.User;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);
    Optional<Note> findByIdAndUser(Long id, User user);
}
