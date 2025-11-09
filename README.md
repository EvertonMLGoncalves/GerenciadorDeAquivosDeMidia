# Gerenciador de Mídias

Sistema para gerenciar arquivos de mídia (filmes, músicas e livros) com interface gráfica em Java Swing.

## 📋 Diagrama de Classes UML

![Diagrama de Classes](docs/diagrama-aplicacao.png)

> O diagrama completo mostra a arquitetura MVC do sistema com as camadas de modelo, controle, persistência e visualização.

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)** em três camadas:

- **Model (modelo)**: Classes de mídia (Midia, Filme, Musica, Livro)
- **Controller (controle)**: GerenciadorMidia
- **View (visao)**: TelaPrincipal, DialogoMidia
- **Persistência**: PersistenciaArquivo (arquivos .tpoo)

## 📦 Estrutura de Pacotes

```
projeto-gerenciador-midia/
├── src/
│   └── br/edu/furb/gerenciadormidia/
│       ├── modelo/
│       │   ├── Midia.java
│       │   ├── Filme.java
│       │   ├── Musica.java
│       │   └── Livro.java
│       ├── controle/
│       │   └── GerenciadorMidia.java
│       |
│       ├── visao/
│       │   ├── TelaPrincipal.java
│       │   └── DialogoMidia.java
│       └── Main.java
│
├── test/
│   └── br/edu/furb/gerenciadormidia/
│       ├── modelo/
│       ├── controle/
│       └── persistencia/
│
└── doc/
    └── (Javadoc gerado)
```

## ✨ Funcionalidades

- ✅ Incluir, editar e remover mídias
- ✅ Mover arquivos para novas pastas
- ✅ Renomear arquivos de mídia
- ✅ Listar por formato (filme, música, livro)
- ✅ Listar por categoria (ação, aventura, rock, etc.)
- ✅ Ordenar por título ou duração
- ✅ Combinar filtros (ex: livros de aventura)
- ✅ Persistência em arquivos .tpoo
- ✅ Carregamento automático ao iniciar

## 📊 Formatos Suportados

| Mídia   | Extensões Suportadas | Duração em      |
|---------|---------------------|-----------------|
| Filme   | MP4, MKV            | Minutos         |
| Música  | MP3                 | Segundos        |
| Livro   | PDF, EPUB           | Páginas         |

## 🎯 Conceitos de POO Aplicados

- **Herança**: Classe abstrata `Midia` com subclasses `Filme`, `Musica` e `Livro`
- **Polimorfismo**: Método `exibirDetalhes()` implementado de forma específica em cada subclasse
- **Encapsulamento**: Atributos privados com métodos públicos de acesso

## 🛠️ Tecnologias

- Java 17+
- Swing (Interface Gráfica)
- JUnit 5 (Testes Unitários)

## 🚀 Como Executar

1. Clone o repositório
2. Abra o projeto em sua IDE (Eclipse, IntelliJ, NetBeans)
3. Execute a classe `Main.java`

## 🧪 Testes

Execute os testes JUnit na pasta `test/`:

```bash
# Todos os testes
mvn test

# Ou através da IDE
```

## 👥 Equipe

- Everton
- Eloiza
- Leslie

---

© 2025 - Trabalho Final de POO