package com.bookstoreapi.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    //Ez az osztály reprezentálja magának az adatbázisnak a vázát
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "Hiba a 'Title' mezőben. A mező nem lehet üres.")
    private String title;
    @Column(nullable = false)
    @NotEmpty(message = "Hiba a 'author' mezőben. A mező nem lehet üres.")
    private String author;
    @Column(nullable = false)
    @NotNull(message = "Hiba az 'price' mezőben. A mező nem lehet üres.")
    private Integer price;
    @Column(nullable = false)
    @NotNull(message = "Hiba az 'available' mezőben. A mező nem lehet null.")
    private Boolean available;
}
