# Sistema de Simulação de Credito - Creditas

Este repositório contém a implementação do microserviço: **simulador-credito**, parte de um sistema para simular transações de empréstimos de crédito.

## Visão Geral

### **Microserviço**
Este serviço gerencia a criação de cartões, consulta de saldo e armazenamento de dados no banco MongoDB.
https://github.com/JuanShelton17/simulador_credito

---

## Ferramentas Utilizadas

### **Backend**:

1. **Spring Boot**:
    - Framework para construir microserviços de forma simples e eficaz.

2. **MongoDB**:
    - Banco de dados NoSQL utilizado para armazenar informações das simulações de cada cliente.

3. **OpenAPI**:
    - Utilizado para documentar as APIs RESTFULL do microserviço e gerar contratos de APIs.

4. **Spring Security**:
    - Usado para implementar autenticação básica via HTTP (Básica: `username`/`password`).


## Desenho Arquitetural Para Resolver o Desafio

![img_2.png](img_2.png)

## Vantagens da  Arquitetura

1. **Escalabilidade**:
   - Kafka desacopla os serviços, permitindo o processamento de transações em grande escala.
   - Redis reduz a carga no banco de dados.
   - Configurar partições no Kafka para permitir múltiplos consumidores/instancias de serviços.
   - Adicionar DLQ no Kafka para lidar com eventos que falhem no processamento.

2. **Baixa Latência**:
   - Redis acelera consultas de simulações dos clientes.
   - Kafka melhora o desempenho em cenários de alto volume de transações.

3. **Resiliência**:
   - Mesmo que um serviço fique temporariamente fora do ar, Kafka garante que os eventos sejam processados posteriormente.
   - Com a utilização de um serviço de consumer, podemos garantir que as mensagens enviadas para o Kafka sejam idempotentes, ajudando a reduzir os problemas de concorrência. A lógica do consumer deve ser capaz de garantir que as mensagens que cairem pra DLQ sejam reprocessadas.

4. **Flexibilidade para Expansão**:
   - Novos serviços podem ser adicionados facilmente, como um serviço de notificação para avisar usuários sobre simulações de crédito.




