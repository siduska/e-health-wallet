package com.siduska.ehealthwallet.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "change_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime changeDateTime = OffsetDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusEnum oldStatus;

    @Enumerated(EnumType.STRING)
    private StatusEnum newStatus;

    private String description;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reimbursement_id", nullable = false)
    private Reimbursement reimbursement;

}
