/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gerenblib.gerenciador_biblioteca;

import com.gerenblib.model.Livro;
import com.gerenblib.model.Usuario;
import com.gerenblib.service.GerenciadorEmprestimos;
import com.gerenblib.service.GerenciadorLivros;
import com.gerenblib.service.GerenciadorUsuarios;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author preya
 */
public class Gerenciador_Biblioteca {

    public static void main(String[] args) {
        GerenciadorLivros gl = new GerenciadorLivros();
        GerenciadorUsuarios gu = new GerenciadorUsuarios();
        GerenciadorEmprestimos ge = new GerenciadorEmprestimos(gu, gl);

        Scanner sc = new Scanner(System.in);
        int opc;

        do {
            System.out.println("\n--- Sistema Biblioteca ---");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Adicionar Usuário");
            System.out.println("4. Listar Usuários");
            System.out.println("5. Adicionar Empréstimo");
            System.out.println("6. Listar Empréstimos");
            System.out.println("7. Devolver Empréstimo (por id)");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1 -> {
                    System.out.print("Título: ");
                    String t = sc.nextLine();
                    System.out.print("Autor: ");
                    String a = sc.nextLine();
                    System.out.print("Ano: ");
                    int ano = Integer.parseInt(sc.nextLine());
                    gl.adicionarLivro(t, a, ano);
                    System.out.println("Livro adicionado.");
                }
                case 2 -> {
                    System.out.println("--- Livros ---");
                    gl.listar().forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    gu.adicionarUsuario(nome, email);
                    System.out.println("Usuário adicionado.");
                }
                case 4 -> {
                    System.out.println("--- Usuários ---");
                    gu.listar().forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Nome do usuário (exato): ");
                    String nome = sc.nextLine();
                    System.out.print("Título do livro (exato): ");
                    String titulo = sc.nextLine();
                    Usuario u = gu.buscarPorNome(nome);
                    Livro l = gl.buscarPorTitulo(titulo);
                    if (u == null) {
                        System.out.println("Usuário não encontrado.");
                        break;
                    }
                    if (l == null) {
                        System.out.println("Livro não encontrado.");
                        break;
                    }
                    ge.adicionarEmprestimo(u, l, LocalDate.now());
                    System.out.println("Empréstimo registrado.");
                }
                case 6 -> {
                    System.out.println("--- Empréstimos ---");
                    ge.listar().forEach(System.out::println);
                }
                case 7 -> {
                    System.out.print("ID do empréstimo: ");
                    int id = Integer.parseInt(sc.nextLine());
                    ge.devolver(id);
                    System.out.println("Operação concluída.");
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opc != 0);

        sc.close();
    }
        
 }

