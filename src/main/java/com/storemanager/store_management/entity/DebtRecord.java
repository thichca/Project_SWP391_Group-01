package com.storemanager.store_management.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@Table(name = "debt_records")
public class DebtRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String type; // GHI_NO or TRA_NO

    @Column(nullable = false, columnDefinition = "DECIMAL(15,2)")
    private Double amount;

    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

