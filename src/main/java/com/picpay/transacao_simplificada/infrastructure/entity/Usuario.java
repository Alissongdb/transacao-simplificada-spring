package com.picpay.transacao_simplificada.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "usuario")
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpfCnpj;

    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Carteira carteira;

    private TipoUsuario tipoUsuario;
}
