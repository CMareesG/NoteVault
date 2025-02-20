package com.marees.SecureNotes.repository;

import com.marees.SecureNotes.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwnerUserName(String ownerUserName); // Corrected to match the entity field name

}
