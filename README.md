# Locadora de Veículos

Trabalho da disciplina de Programação Orientada a Objetos (POO). É um sistema de locadora de veículos em Java que roda no terminal: cadastra veículos e clientes, aluga, devolve, calcula desconto e multa e gera relatórios.

## Funcionalidades

- **Veículos:** incluir (carro ou moto), alterar, excluir, listar por ano e buscar pela placa
- **Clientes:** cadastrar (nome, CPF, data de nascimento e CNH), listar e excluir
- **Locações:** alugar um veículo por N dias e devolver, com cálculo do total
- **Relatórios:** veículos disponíveis, veículos alugados (com cliente e data prevista) e histórico de um cliente

O programa já começa com 4 veículos, 3 clientes e uma locação em aberto, para facilitar os testes.

## Regras de negócio

| Regra | Definição |
|---|---|
| Valor do aluguel | diária calculada do veículo × número de dias |
| Desconto | 10% sobre o aluguel se forem 7 dias ou mais |
| Multa por atraso | 50% da diária por dia de atraso, somada ao total (sem desconto sobre a multa) |
| Aluguel mínimo | 1 dia (retirar e devolver no mesmo dia cobra 1 diária) |
| Devolução antecipada | não há reembolso: cobra os dias contratados |
| CNH | carro exige categoria B ou AB; moto exige A ou AB |
| Idade mínima | 21 anos na data da retirada |
| Disponibilidade | veículo alugado não pode ser alugado de novo nem excluído |
| Cliente | não pode ser excluído se tiver locação em aberto |
| Unicidade | placa única e CPF único (a placa ignora maiúsculas e o CPF aceita pontos e traço) |
| Validações | ano entre 1980 e o ano que vem, diária/potência/cilindradas maiores que zero, categoria do carro entre Suv, Sedan, Hatch e Picape |

A diária de aluguel é "congelada" no momento da locação: mudar o preço do veículo depois não altera aluguéis já feitos.

### Cálculo da diária por tipo de veículo

| Veículo | Regra |
|---|---|
| Carro SUV | diária + 40 |
| Carro Picape | diária + 50 |
| Carro Sedan | diária + 20 |
| Carro Hatch | diária base |
| Moto acima de 500 cilindradas | diária × 1,3 |
| Moto até 500 cilindradas | diária base |

### Exemplo

Corolla (Sedan, diária 180 → 200 com o acréscimo) alugado por 7 dias e devolvido com 2 dias de atraso:

```
Aluguel: 200 × 7 = 1400, menos 10% de desconto = 1260
Multa:   2 dias × 50% × 200 = 200
Total:   1460
```

As datas são digitadas no formato `dd/MM/yyyy`, e Enter usa a data de hoje. Isso permite demonstrar a multa na hora, informando uma data de devolução posterior à prevista.

## Conceitos de POO usados

- **Classe abstrata e herança:** `Veiculo` é abstrata, e `Carro` e `Moto` herdam dela.
- **Polimorfismo:** `calcularDiaria()`, `getTipo()` e `aceitaCnh()` são sobrescritos em cada subclasse. A locação e a listagem tratam todos como `Veiculo`.
- **Encapsulamento:** atributos privados com getters e setters que validam os dados.
- **Composição:** todo veículo tem um `Motor`, e toda `Locacao` tem um `Cliente` e um `Veiculo`.
- **Interface:** `Repositorio`, `RepositorioClientes` e `RepositorioLocacoes` definem as operações, e as classes `...EmMemoria` implementam com `ArrayList`.
- **Exceções próprias:** `RegraDeNegocioException` e suas filhas (`VeiculoIndisponivelException`, `CnhIncompativelException`, `ClienteMenorDeIdadeException`, `RegistroDuplicadoException`, `NaoEncontradoException`, `DadoInvalidoException`). As regras lançam, e o menu só captura e mostra a mensagem.
- **Separação de responsabilidades:** as regras ficam nas entidades e em `LocacaoService`. A `Locadora` cuida só do menu, e a `Entrada` cuida da leitura segura do teclado.

## Estrutura

```
Aplicacao/
  Programa.java                    -> método main, cria os dados de exemplo e abre o menu
Entidades/
  Veiculo.java                     -> classe abstrata
  Carro.java                       -> herda de Veiculo, tem categoria
  Moto.java                        -> herda de Veiculo, tem cilindradas
  Motor.java                       -> potência e combustível
  Cliente.java                     -> nome, CPF, data de nascimento e CNH
  Cnh.java                         -> enum A, B, AB
  Locacao.java                     -> aluguel: datas, desconto, multa e total
Excecoes/
  RegraDeNegocioException.java     -> base das exceções de regra (e as filhas)
  EntradaEncerradaException.java   -> entrada de dados terminou (Ctrl+D)
Servicos/
  LocacaoService.java              -> regras de alugar, devolver e excluir
  Locadora.java                    -> menus e telas
  Entrada.java                     -> leitura segura de número, data e texto
  Repositorio.java                 -> interface de veículos
  RepositorioVeiculos.java         -> veículos em memória
  RepositorioClientes.java         -> interface de clientes
  RepositorioClientesEmMemoria.java
  RepositorioLocacoes.java         -> interface de locações
  RepositorioLocacoesEmMemoria.java
Testes/
  Testes.java                      -> testes em Java puro (sem bibliotecas)
```

## Como executar

Precisa do Java (JDK) instalado. Na pasta raiz do projeto:

```bash
javac -encoding UTF-8 -d bin Aplicacao/*.java Entidades/*.java Excecoes/*.java Servicos/*.java Testes/*.java
java -cp bin Aplicacao.Programa
```

Ou abra a pasta no VS Code / IntelliJ e rode o `Programa.java`.

### Testes

```bash
java -cp bin Testes.Testes
```

Cada teste imprime `OK` ou `FALHOU`, e o programa termina com código 1 se algum falhar.

> Os dados ficam só na memória: ao fechar o programa, tudo que foi cadastrado é perdido.

## Autor

Guilherme Brito — [GitHub](https://github.com/GuiBrito0)
