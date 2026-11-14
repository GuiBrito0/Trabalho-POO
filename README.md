# Locadora de Veículos

Trabalho da disciplina de Programação Orientada a Objetos (POO). É um sistema de locadora de veículos em Java que roda no terminal: dá para cadastrar carros e motos, alterar, excluir, listar e buscar pela placa.

## Funcionalidades

- Incluir veículo (carro ou moto)
- Alterar modelo, ano e valor da diária
- Excluir veículo pela placa
- Listar os veículos ordenados por ano, já com a diária calculada
- Buscar veículo pela placa

O programa já começa com 4 veículos de exemplo cadastrados (Onix, Corolla, Hilux e uma CG 160) para facilitar os testes.

## Cálculo da diária

Cada tipo de veículo calcula a própria diária a partir do valor base:

| Veículo | Regra |
|---|---|
| Carro SUV | diária + 40 |
| Carro Picape | diária + 50 |
| Carro Sedan | diária + 20 |
| Carro Hatch (ou outra categoria) | diária base |
| Moto acima de 500 cilindradas | diária × 1,3 |
| Moto até 500 cilindradas | diária base |

## Conceitos de POO usados

- **Classe abstrata e herança:** `Veiculo` é abstrata, e `Carro` e `Moto` herdam dela.
- **Polimorfismo:** `calcularDiaria()` e `getTipo()` são sobrescritos em cada subclasse, e a listagem trata todos como `Veiculo`.
- **Encapsulamento:** atributos privados com getters e setters.
- **Composição:** todo veículo tem um `Motor` (potência e combustível).
- **Interface:** `Repositorio` define as operações (adicionar, remover, buscar e listar), e `RepositorioVeiculos` implementa usando um `ArrayList`.

## Estrutura

```
Aplicacao/
  Programa.java            -> método main, cria os dados de exemplo e abre o menu
Entidades/
  Veiculo.java             -> classe abstrata
  Carro.java               -> herda de Veiculo, tem categoria
  Moto.java                -> herda de Veiculo, tem cilindradas
  Motor.java               -> potência e combustível
Servicos/
  Locadora.java            -> menu e regras do sistema
  Repositorio.java         -> interface
  RepositorioVeiculos.java -> guarda os veículos em memória
```

## Como executar

Precisa do Java (JDK) instalado. Na pasta raiz do projeto:

```bash
javac -d bin Aplicacao/*.java Entidades/*.java Servicos/*.java
java -cp bin Aplicacao.Programa
```

Ou abra a pasta no VS Code / IntelliJ e rode o `Programa.java`.

> Os dados ficam só na memória: ao fechar o programa, os veículos cadastrados são perdidos.

## Autor

Guilherme Brito — [GitHub](https://github.com/GuiBrito0)
