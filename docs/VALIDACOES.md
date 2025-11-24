# Documentação de Validações - Gerenciador de Mídias

## 📋 Validações Implementadas

### 1. Validações da Classe `Midia` (Abstrata)

#### 1.1 Local do Arquivo
- **Regra**: Não pode ser nulo ou vazio
- **Validação**: `setLocal(String local)`
- **Exceção**: `IllegalArgumentException("O local não pode ser nulo ou vazio.")`
- **Comportamento**: Remove aspas duplas e faz trim automático

#### 1.2 Título
- **Regra**: Não pode ser nulo ou vazio
- **Validação**: `setTitulo(String titulo)`
- **Exceção**: `IllegalArgumentException("O título não pode ser nulo ou vazio.")`
- **Comportamento**: Faz trim automático

#### 1.3 Duração
- **Regra**: Não pode ser negativa (>= 0)
- **Validação**: `setDuracao(int duracao)`
- **Exceção**: `IllegalArgumentException("A duração não pode ser negativa.")`

#### 1.4 Categoria
- **Regra**: Não pode ser nula ou vazia
- **Validação**: `setCategoria(String categoria)`
- **Exceção**: `IllegalArgumentException("A categoria não pode ser nula ou vazia.")`
- **Comportamento**: Faz trim automático

#### 1.5 Formato
- **Regra**: Deve ser um formato suportado pelo tipo de mídia
- **Validação**: `setFormato(String formato)` + `validarFormato(String)`
- **Exceção**: `IllegalArgumentException("Formato não suportado: {formato}")`
- **Comportamento**: Converte para maiúsculas automaticamente

### 2. Validações Específicas por Tipo de Mídia

#### 2.1 Filme
- **Campo**: Idioma
- **Regra**: Não pode ser nulo ou vazio
- **Validação**: `setIdioma(String idioma)`
- **Exceção**: `IllegalArgumentException("O idioma não pode ser nulo ou vazio.")`
- **Formatos aceitos**: MP4, MKV

#### 2.2 Música
- **Campo**: Artista
- **Regra**: Não pode ser nulo ou vazio
- **Validação**: `setArtista(String artista)`
- **Exceção**: `IllegalArgumentException("O artista não pode ser nulo ou vazio.")`
- **Formatos aceitos**: MP3

#### 2.3 Livro
- **Campo**: Autores
- **Regra**: Não pode ser nulo ou vazio
- **Validação**: `setAutores(String autores)`
- **Exceção**: `IllegalArgumentException("Os autores não podem ser nulos ou vazios.")`
- **Formatos aceitos**: PDF, EPUB

### 3. Validações do `GerenciadorMidia`

#### 3.1 Incluir Mídia
```java
public void incluirMidia(Midia midia)
```
- **Validação 1**: Mídia não pode ser nula
- **Validação 2**: Não pode existir outra mídia com o mesmo local
- **Exceções**:
  - `IllegalArgumentException("A mídia não pode ser nula.")`
  - `IllegalArgumentException("Já existe uma mídia cadastrada neste local: {local}")`

#### 3.2 Remover Mídia
```java
public void removerMidia(Midia midia)
```
- **Validação 1**: Mídia não pode ser nula
- **Validação 2**: Mídia deve existir na lista
- **Exceções**:
  - `IllegalArgumentException("A mídia não pode ser nula.")`
  - `IllegalArgumentException("Mídia não encontrada: {local}")`

#### 3.3 Editar Mídia
```java
public void editarMidia(Midia midiaAntiga, Midia midiaNova)
```
- **Validação 1**: Nenhuma das mídias pode ser nula
- **Validação 2**: Mídia antiga deve existir na lista
- **Exceções**:
  - `IllegalArgumentException("As mídias não podem ser nulas.")`
  - `IllegalArgumentException("Mídia não encontrada: {local}")`

#### 3.4 Renomear Mídia
```java
public void renomearMidia(String local, String novoTitulo)
```
- **Validação 1**: Local não pode ser nulo ou vazio
- **Validação 2**: Novo título não pode ser nulo ou vazio
- **Validação 3**: Mídia deve existir no local especificado
- **Exceções**:
  - `IllegalArgumentException("O local não pode ser nulo ou vazio.")`
  - `IllegalArgumentException("O novo título não pode ser nulo ou vazio.")`
  - `IllegalArgumentException("Mídia não encontrada no local: {local}")`

#### 3.5 Mover Mídia
```java
public void moverMidia(String localAntigo, String localNovo)
```
- **Validação 1**: Local antigo não pode ser nulo ou vazio
- **Validação 2**: Local novo não pode ser nulo ou vazio
- **Validação 3**: Não pode existir mídia no local de destino
- **Validação 4**: Mídia deve existir no local antigo
- **Exceções**:
  - `IllegalArgumentException("O local antigo não pode ser nulo ou vazio.")`
  - `IllegalArgumentException("O local novo não pode ser nulo ou vazio.")`
  - `IllegalArgumentException("Já existe uma mídia no local de destino: {localNovo}")`
  - `IllegalArgumentException("Mídia não encontrada no local: {localAntigo}")`
- **Comportamento adicional**: Atualiza o tamanho em disco após mover

### 4. Validações da Interface Gráfica

#### 4.1 DialogoMidia - Validações ao Salvar

**Campos Obrigatórios**:
1. Local do Arquivo
2. Título
3. Categoria
4. Campo Específico (Idioma/Artista/Autores)
5. Duração (número inteiro não negativo)

**Validações de Formato**:
- Verifica se o arquivo tem extensão
- Valida extensão conforme tipo selecionado
- Exibe mensagem com formatos aceitos em caso de erro

**Validação de Existência do Arquivo**:
- Se o arquivo não existir, pergunta se deseja continuar
- Permite cadastrar mesmo sem arquivo físico (para casos de mídias offline)

**Mensagens de Erro**:
- Todas focam automaticamente no campo com erro
- Exibem mensagem clara e específica sobre o problema

#### 4.2 TelaPrincipal - Tratamento de Exceções

Todas as operações CRUD possuem tratamento de exceções:
- **Adicionar**: Captura exceções de validação e duplicação
- **Editar**: Captura exceções de validação e mídia não encontrada
- **Remover**: Captura exceções de mídia não encontrada
- **Renomear**: Captura exceções de validação
- **Mover**: Captura exceções de validação e conflito de local

### 5. Validações de Carregamento

#### 5.1 Carregamento do Arquivo `midias.tpoo`
```java
public void carregarMidias()
```
- Ignora linhas vazias
- Ignora linhas com formato inválido (menos de 6 campos)
- Ignora mídias sem local válido
- Captura exceções de dados inválidos e continua carregamento
- Exibe erro no console para mídias problemáticas

**Formato esperado do arquivo**:
```
tipo;local;titulo;duracao;categoria;campoEspecifico
```

### 6. Boas Práticas Implementadas

#### 6.1 Limpeza de Dados
- Todos os campos de texto passam por `trim()` antes de validação
- Remove aspas duplas do local do arquivo automaticamente

#### 6.2 Mensagens de Erro
- Sempre específicas e claras
- Indicam exatamente qual campo/operação falhou
- Sugerem formatos aceitos quando aplicável

#### 6.3 Experiência do Usuário
- Foco automático no campo com erro
- Diálogos de confirmação para operações destrutivas
- Feedback visual com barra de status
- Permite cadastrar mídias sem arquivo físico (com aviso)

#### 6.4 Integridade de Dados
- Não permite duplicação de locais
- Garante que todas as mídias tenham dados mínimos válidos
- Valida formato antes de criar objeto
- Atualiza tamanho em disco ao mover arquivo

## 🧪 Testando as Validações

### Teste 1: Campos Obrigatórios
1. Tente adicionar uma mídia sem preencher todos os campos
2. Resultado esperado: Mensagem de erro específica para cada campo

### Teste 2: Formato Inválido
1. Tente adicionar um arquivo .avi como filme
2. Resultado esperado: "Formato de arquivo inválido! Formatos aceitos para Filme: MP4, MKV"

### Teste 3: Duração Negativa
1. Digite um número negativo no campo duração
2. Resultado esperado: "A duração deve ser um número inteiro não negativo!"

### Teste 4: Local Duplicado
1. Adicione uma mídia
2. Tente adicionar outra com o mesmo local
3. Resultado esperado: "Já existe uma mídia cadastrada neste local: {caminho}"

### Teste 5: Arquivo Inexistente
1. Digite um caminho que não existe
2. Sistema pergunta se deseja continuar
3. Se sim, cadastra com tamanho = 0 MB

### Teste 6: Mover para Local Ocupado
1. Adicione duas mídias
2. Tente mover uma para o local exato da outra
3. Resultado esperado: "Já existe uma mídia no local de destino"

## 📊 Resumo de Validações

| Componente | Total de Validações | Campos Validados |
|------------|---------------------|------------------|
| Midia (abstrata) | 5 | local, título, duração, categoria, formato |
| Filme | 2 | idioma, formato (MP4/MKV) |
| Música | 2 | artista, formato (MP3) |
| Livro | 2 | autores, formato (PDF/EPUB) |
| GerenciadorMidia | 15 | inclusão, remoção, edição, renomear, mover |
| DialogoMidia | 7 | campos obrigatórios, formato, existência |
| **TOTAL** | **33** | **Todas as operações protegidas** |

---

✅ **Todas as validações estão implementadas e funcionando!**
