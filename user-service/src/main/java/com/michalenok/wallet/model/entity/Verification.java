package com.michalenok.wallet.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification", schema = "app")
@Getter
@Setter
public class Verification {
    @Id
    @Column(name = "mail", nullable = false)
    private String mail;
    private String code;
}
