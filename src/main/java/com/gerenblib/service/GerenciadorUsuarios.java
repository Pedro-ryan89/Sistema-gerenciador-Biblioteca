/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenblib.service;

import com.gerenblib.model.Usuario;
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
public class GerenciadorUsuarios {
    private List<Usuario> usuarios = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivo = "data/usuarios.txt";

    public GerenciadorUsuarios() {
        carregar();
    }

    public void adicionarUsuario(String nome, String email) {
        Usuario u = new Usuario(proximoId++, nome, email);
        usuarios.add(u);
        salvar();
    }

    public List<Usuario> listar() {
        return usuarios;
    }

    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Usuario u : usuarios) {
                pw.println(u.toString());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
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
                String nome = partes.length > 1 ? partes[1] : "";
                String email = partes.length > 2 ? partes[2] : "";
                Usuario u = new Usuario(id, nome, email);
                usuarios.add(u);
                proximoId = Math.max(proximoId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public Usuario buscarPorId(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public Usuario buscarPorNome(String nome) {
        return usuarios.stream().filter(u -> u.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }
    
}
