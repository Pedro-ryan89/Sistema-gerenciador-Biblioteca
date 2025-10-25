# Sistema-gerenciador-Biblioteca
O Sistema Gerenciador de Biblioteca é um projeto desenvolvido em Java puro, com o objetivo de simular o funcionamento básico de uma biblioteca, permitindo o cadastro, listagem e gerenciamento de livros e usuários, além do empréstimo e devolução de obras.
O foco do projeto é o aprendizado de Programação Orientada a Objetos (POO), manipulação de arquivos, e organização de código em camadas.

# Funcionalidades principais:

### usuarios
- Cadastrar novos usuários com ID, nome e e-mail.
- Listar todos os usuários cadastrados.
- Buscar usuários pelo nome.
- Persistência automática dos dados em arquivo.

### Livros:
- Cadastrar livros com título, autor e ID único.
- Listar e buscar livros cadastrados.
- Controle de disponibilidade (livros emprestados ou disponíveis).

### Emprestimo:
- Registrar o empréstimo de um livro para um usuário.
- Registrar devolução de livros.
- Impedir que o mesmo livro seja emprestado a mais de uma pessoa.

# Estrutura do Projeto
``` bash

📦 Sistema-gerenciador-Biblioteca
 ┣ 📂 src
 ┃ ┣ 📂 com.gerenblib.biblioteca.model       # Classes modelo (Usuario, Livro)
 ┃ ┣ 📂 com.gerenblib.biblioteca.gerenciador # Gerenciadores de dados e lógica
 ┃ ┗ 📂 com.gerenblib.biblioteca.main        # Classe principal com o menu
 ┣ 📜 usuarios.txt                           # Armazena os dados dos usuários
 ┣ 📜 livros.txt                             # Armazena os dados dos livros
 ┗ 📜 README.md                              # Este arquivo

```
  
