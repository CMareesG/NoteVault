package com.marees.SecureNotes.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Lob //LargeObjectType which is Long String
    private String content;

    private String ownerUserName;

}
