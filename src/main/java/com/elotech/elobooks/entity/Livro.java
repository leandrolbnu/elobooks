package com.elotech.elobooks.entity;

import java.time.LocalDateTime;

import com.elotech.elobooks.enums.Categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;
    
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDateTime dataPublicacao;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Livro() {}
    
    public Livro(String titulo, String autor, String isbn, LocalDateTime dataPublicacao, Categoria categoria) {
    	this.titulo = titulo;
    	this.autor = autor;
    	this.isbn = isbn;
    	this.dataPublicacao = dataPublicacao;
    	this.categoria = categoria;
    }
    
    public Livro(Long id, String titulo, String autor, String isbn, LocalDateTime dataPublicacao, Categoria categoria) {
    	this.id = id;
    	this.titulo = titulo;
    	this.autor = autor;
    	this.isbn = isbn;
    	this.dataPublicacao = dataPublicacao;
    	this.categoria = categoria;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDateTime dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
