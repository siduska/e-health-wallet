package com.siduska.ehealthwallet.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "change_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime changeDateTime;

    @Enumerated(EnumType.STRING)
    private StatusEnum oldStatus;

    @Enumerated(EnumType.STRING)
    private StatusEnum newStatus;

    private String description;
    private String userName;

}
