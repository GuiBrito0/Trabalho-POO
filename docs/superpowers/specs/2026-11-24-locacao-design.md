# Locadora: design do módulo de locação

## Intenção
O projeto hoje é só um cadastro de veículos (CRUD). Falta o núcleo de uma locadora: alugar e devolver, com regras de negócio. O professor criticou exatamente isso. O objetivo é um código funcional e de boa qualidade (sem prazo de reapresentação), mantendo a estrutura de POO que já existe.

## Objetivos
- Adicionar `Cliente` e `Locacao` com regras de negócio reais.
- Manter as regras dentro das classes de domínio e de um serviço, não espalhadas no menu.
- Entrada de dados robusta: nada de `InputMismatchException` derrubando o programa.
- Poder demonstrar desconto e multa na hora, sem esperar dias reais.

## Não objetivos
- Persistência em arquivo ou banco (os dados continuam em memória).
- Bibliotecas externas (Java puro, sem Maven/JUnit).
- Interface gráfica.
- Validação dos dígitos verificadores do CPF (só formato, 11 dígitos).

## Anti-objetivo
Regra de negócio dentro de `Locadora.java` (o menu). Se o menu só lê dados e chama o serviço, está certo.

## Abordagens consideradas
- A) Núcleo enxuto (Cliente, Locacao, disponibilidade). Rápido, mas ainda parece trabalho simples.
- B) **Escolhida:** locadora completa em memória (CNH, desconto, multa, validações, relatórios, serviço separado do menu, exceções próprias).
- C) B + persistência em arquivo. Pode ser acrescentada depois sem refazer nada.

## Regras de negócio (decididas com o usuário)
| Regra | Definição |
|---|---|
| Valor do aluguel | `calcularDiaria()` do veículo × dias |
| Desconto | 10% sobre o total se dias >= 7 |
| Multa | 50% da diária (a calculada) por dia de atraso, somada ao total, sem desconto sobre a multa |
| Mínimo | 1 dia (retirar e devolver no mesmo dia = 1 diária) |
| CNH | Carro exige B ou AB. Moto exige A ou AB |
| Idade mínima | 21 anos na data da retirada, a partir da data de nascimento |
| Disponibilidade | Veículo com locação aberta não pode ser alugado de novo nem excluído |
| Cliente | Não pode ser excluído se tiver locação aberta |
| Unicidade | Placa única (comparada em maiúsculas) e CPF único |
| Datas | `dd/MM/yyyy`. Enter = hoje. Data inválida é recusada e pedida de novo |

## Estrutura
```
Entidades/
  Veiculo   (+ abstract boolean aceitaCnh(Cnh))
  Carro     (aceitaCnh: B, AB; categoria restrita a Suv/Sedan/Hatch/Picape)
  Moto      (aceitaCnh: A, AB)
  Cliente   (nome, cpf, dataNascimento, cnh; calcula idade numa data)
  Cnh       (enum A, B, AB)
  Locacao   (cliente, veiculo, diariaAplicada, dataRetirada, dataPrevista, dataDevolucao;
             calcula dias, desconto, multa e total)
Excecoes/
  RegraDeNegocioException (base, unchecked) e subclasses:
  VeiculoIndisponivelException, CnhIncompativelException, ClienteMenorDeIdadeException,
  RegistroDuplicadoException, NaoEncontradoException, DadoInvalidoException
Servicos/
  LocacaoService       (alugar, devolver, excluir com checagem de locação aberta)
  Repositorio*         (mesmo padrão interface + implementação em ArrayList, para veículos, clientes e locações)
  Locadora             (só menu e tela; lê dados, chama serviço, captura RegraDeNegocioException e imprime a mensagem)
  Entrada              (leitura segura de int, double, texto e data, com repetição em caso de erro)
Aplicacao/
  Programa             (dados de exemplo: veículos, clientes e uma locação)
```

## Decisões do builder, marcadas como minhas (não foram discutidas)
- A diária calculada é guardada na locação no momento do aluguel (`diariaAplicada`), para que alterar o veículo depois não mude o valor de aluguéis já feitos.
- Testes sem JUnit: um runner simples em Java puro (`Testes/`) que cobre cálculo, CNH, idade e disponibilidade.
- Menu em submenus (Veículos, Clientes, Locações, Relatórios) em vez de uma lista única longa.
- Categoria do carro passa a aceitar só as quatro conhecidas, em vez de texto livre.
- Ano do veículo entre 1980 e ano atual + 1. Diária e potência devem ser maiores que zero.
- Relatórios: veículos disponíveis, veículos alugados (com cliente e data prevista), histórico por cliente.
- O README é atualizado ao final com as regras novas.

## Fora do escopo agora (possíveis próximos passos)
Persistência em arquivo, reserva antecipada, manutenção de veículo.
