package com.bookstoreapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mybooks")
@Getter
@Setter
public class MyBook {

    //Ebben az adatbázisban már csak a könyvek azonosítóit tárolom,
    //amelyekkel majd hivatkozni tudunk a 'books' tábla elemeire.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long bookId;
}
