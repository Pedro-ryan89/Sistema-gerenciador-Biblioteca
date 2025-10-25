/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenblib.model;

import java.time.LocalDate;

/**
 *
 * @author preya
 */
public class Emprestimo {
    private int id;
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(int id, Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = null;
        if (livro != null) livro.setDisponivel(false);
    }

    // construtor alternativo para casos simples (sem objetos completos)
    public Emprestimo(int id, String nomeUsuario, String tituloLivro, LocalDate dataEmprestimo) {
        this(id, new Usuario(nomeUsuario), new Livro(tituloLivro), dataEmprestimo);
    }

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Livro getLivro() { return livro; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }

    public void devolver() {
        this.dataDevolucao = LocalDate.now();
        if (livro != null) livro.setDisponivel(true);
    }

    public boolean estaAtivo() {
        return dataDevolucao == null;
    }

    @Override
    public String toString() {
        // formato para salvar em arquivo: id;nomeUsuario;tituloLivro;dataEmprestimo;dataDevolucaoOuVazio
        return id + ";" + usuario.getNome() + ";" + livro.getTitulo() + ";" +
               dataEmprestimo + ";" + (dataDevolucao == null ? "" : dataDevolucao.toString());
    }
    
}
