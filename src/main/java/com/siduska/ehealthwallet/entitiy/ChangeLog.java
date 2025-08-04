package com.siduska.ehealthwallet.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private StatusEnum oldStatus;
    private StatusEnum newStatus;
    private String description;

    @ManyToMany
    @JoinTable(name = "logs_users",
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
