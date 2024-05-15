package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "FILE_UPLOAD_TBL")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String username;
    private String password;

    private String fromEmail;
    private String subjectBody;
    private String textBody;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] fileData;
}
