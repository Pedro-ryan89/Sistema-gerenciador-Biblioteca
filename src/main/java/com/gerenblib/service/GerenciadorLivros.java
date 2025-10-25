/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenblib.service;

import com.gerenblib.model.Livro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author preya
 */
public class GerenciadorLivros {
    private List<Livro> livros = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivo = "data/livros.txt";

    public GerenciadorLivros() {
        carregar();
    }

    public void adicionarLivro(String titulo, String autor, int ano) {
        Livro livro = new Livro(proximoId++, titulo, autor, ano);
        livros.add(livro);
        salvar();
    }

    public List<Livro> listar() {
        return livros;
    }

    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Livro l : livros) {
                pw.println(l.toString());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    public void carregar() {
        File file = new File(arquivo);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String titulo = partes.length > 1 ? partes[1] : "";
                String autor = partes.length > 2 ? partes[2] : "";
                int ano = partes.length > 3 && !partes[3].isEmpty() ? Integer.parseInt(partes[3]) : 0;
                boolean disponivel = partes.length > 4 && !partes[4].isEmpty() ? Boolean.parseBoolean(partes[4]) : true;

                Livro livro = new Livro(id, titulo, autor, ano);
                livro.setDisponivel(disponivel);
                livros.add(livro);
                proximoId = Math.max(proximoId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar livros: " + e.getMessage());
        }
    }

    public Livro buscarPorId(int id) {
        return livros.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }

    public Livro buscarPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst().orElse(null);
    }
    
}
