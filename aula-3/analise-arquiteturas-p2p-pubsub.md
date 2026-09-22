# Atividade 3 - Análise de Estilos Arquiteturais

Nesta atividade eu escolhi analisar dois estilos que vimos em aula: o Ponto a Ponto (P2P) e o Publicador/Assinante (Pub/Sub).

## 1. Ponto a Ponto (P2P)

**Conceito e definição:**
No estilo P2P, não existe um servidor central. Todos os computadores conectados na rede (os chamados "peers" ou nós) funcionam ao mesmo tempo como cliente e como servidor. Eles compartilham recursos direto uns com os outros.

**Casos de uso comuns:**
- **Torrent (ex: uTorrent):** Onde as pessoas baixam e enviam pedaços de arquivos umas para as outras simultaneamente.
- **Blockchain (Criptomoedas):** Onde a rede financeira é distribuída em vários computadores e todo mundo valida as transações, sem precisar de um banco no meio.

**Principais vantagens:**
- Fica muito difícil do sistema cair inteiro (não tem um ponto único de falha que leva tudo junto).
- É mais barato de escalar, já que você usa o processamento e a internet dos próprios usuários da rede e não precisa de um super servidor.

**Principais desvantagens:**
- A segurança é bem mais complicada, já que qualquer computador pode entrar na rede.
- Se muitas pessoas saírem da rede ao mesmo tempo, determinado arquivo ou informação pode ficar indisponível.

---

## 2. Publicador/Assinante (Pub/Sub)

**Conceito e definição:**
Nesse estilo, quem gera a mensagem (o publicador) não sabe quem vai receber (o assinante). No meio deles tem uma ferramenta (o broker/tópico). O publicador simplesmente lança a mensagem nesse tópico, e o broker se encarrega de avisar todos os sistemas que tinham se inscrito para receber aquele tipo de mensagem.

**Casos de uso comuns:**
- **Sistemas de E-commerce moderno:** Quando o cliente finaliza o pagamento, dispara-se um evento. Ao mesmo tempo, o sistema de estoque, o de nota fiscal e o de disparo de e-mail reagem a esse mesmo evento de forma independente.
- **Internet das Coisas (IoT) / Sensores:** Vários sensores mandando dado de temperatura, por exemplo, e quem tiver interessado (um painel, um alerta) assina pra receber.

**Principais vantagens:**
- O código fica super desacoplado. Dá pra colocar mais assinantes pra escutar a mensagem sem ter que mexer nenhuma linha no código de quem publica.
- Comunicação assíncrona! Quem envia a mensagem não precisa ficar travado esperando a resposta.

**Principais desvantagens:**
- O sistema fica mais complexo e difícil de fazer debug. Se der erro num pedido, é mais chato rastrear o fluxo no meio de vários eventos.
- Você tem que cuidar da manutenção desse "broker" no meio (geralmente RabbitMQ ou Kafka), que se cair, trava a comunicação.
