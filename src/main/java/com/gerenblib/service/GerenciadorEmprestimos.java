/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenblib.service;

import com.gerenblib.model.Emprestimo;
import com.gerenblib.model.Livro;
import com.gerenblib.model.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author preya
 */
public class GerenciadorEmprestimos {
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivo = "data/emprestimos.txt";

    // referências aos outros gerenciadores para reaproveitar objetos existentes
    private GerenciadorUsuarios gu;
    private GerenciadorLivros gl;

    public GerenciadorEmprestimos(GerenciadorUsuarios gu, GerenciadorLivros gl) {
        this.gu = gu;
        this.gl = gl;
        carregar();
    }

    public void adicionarEmprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
        if (livro == null || usuario == null) {
            System.out.println("Usuário ou livro inválido.");
            return;
        }
        if (!livro.isDisponivel()) {
            System.out.println("Livro não está disponível para empréstimo.");
            return;
        }
        Emprestimo e = new Emprestimo(proximoId++, usuario, livro, dataEmprestimo);
        emprestimos.add(e);
        salvar();
    }

    public List<Emprestimo> listar() {
        return emprestimos;
    }

    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Emprestimo e : emprestimos) {
                pw.println(e.toString());
            }
        } catch (IOException ex) {
            System.out.println("Erro ao salvar empréstimos: " + ex.getMessage());
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
                String nomeUsuario = partes.length > 1 ? partes[1] : "";
                String tituloLivro = partes.length > 2 ? partes[2] : "";
                LocalDate data = partes.length > 3 && !partes[3].isEmpty() ? LocalDate.parse(partes[3]) : LocalDate.now();

                // procura objetos existentes nos gerenciadores
                Usuario usuario = gu.buscarPorNome(nomeUsuario);
                Livro livro = gl.buscarPorTitulo(tituloLivro);

                // se não encontrar, cria objetos simples (evita perder o registro)
                if (usuario == null) usuario = new Usuario(nomeUsuario);
                if (livro == null) livro = new Livro(tituloLivro);

                Emprestimo e = new Emprestimo(id, usuario, livro, data);
                emprestimos.add(e);
                proximoId = Math.max(proximoId, id + 1);
            }
        } catch (IOException ex) {
            System.out.println("Erro ao carregar empréstimos: " + ex.getMessage());
        }
    }

    public Emprestimo buscarPorId(int id) {
        return emprestimos.stream().filter(ep -> ep.getId() == id).findFirst().orElse(null);
    }

    public void devolver(int id) {
        Emprestimo e = buscarPorId(id);
        if (e == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }
        if (!e.estaAtivo()) {
            System.out.println("Empréstimo já foi devolvido.");
            return;
        }
        e.devolver();
        salvar();
    }
    
}
