package com.mockmate.auth_service.entities.questions;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qtags_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(length = 500)
    @NotNull
    private String questionTagText;
}