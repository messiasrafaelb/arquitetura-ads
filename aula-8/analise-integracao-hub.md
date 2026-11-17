# Atividade 8 - Integração da Aplicação de Vendas ao Hub de Faturamento

Nesta atividade foi implementada a integração síncrona via HTTP/REST entre a aplicação de vendas (desenvolvida no padrão MVC) e o hub de faturamento (construído sob a arquitetura hexagonal). Ao fechar um pedido na camada de vendas, o sistema envia uma requisição `POST /fatura` ao hub e armazena o `faturaId` devolvido na entidade do pedido.

Abaixo são analisados dois problemas clássicos decorrentes dessa comunicação síncrona distribuída em rede, bem como as soluções arquiteturais estudadas para mitigá-los.

---

## 1. Problema 1: Atraso de Rede e Falha em Cascata (Latência na Chamada REST)

### O que acontece com o pedido
Por se tratar de uma comunicação síncrona (chamada HTTP direta), a aplicação de vendas realiza o envio do pedido e fica aguardando ativamente a resposta do hub para concluir a requisição. Se o hub de faturamento demorar para responder (por exemplo, devido à lentidão no web service SOAP da SEFAZ, sobrecarga na fila de boletos ou congestionamento de rede), a thread de execução da aplicação de vendas fica presa e bloqueada. Se múltiplos clientes tentarem fechar pedidos simultaneamente durante essa lentidão, o pool de threads do servidor de vendas se esgota rapidamente, impedindo o processamento tanto daquele pedido quanto de quaisquer outras operações no sistema.

### O que o negócio percebe
- **Travamento da interface:** A tela de checkout do cliente ou o terminal do vendedor congela com o indicador de carregamento girando infinitamente, até expirar com erro de timeout de navegador (ex: HTTP 504 Gateway Timeout).
- **Abandono de compras:** Clientes frustrados com a demora desistem da compra e abandonam o carrinho, resultando em perda direta de receita.
- **Indisponibilidade generalizada:** Uma falha ou lentidão em um serviço interno de faturamento derruba o sistema de vendas da empresa inteira, caracterizando uma falha em cascata.

### Solução arquitetural: Timeout e Circuit Breaker
Para sanar esse problema, a solução estudada consiste na combinação de **Timeout** com o padrão **Circuit Breaker (Disjuntor)**.

- **Onde ela atuaria:** Atuaria na **aplicação de vendas (no cliente HTTP)**.
- **Justificativa de atuação:** 
  1. O **Timeout** estabelece um limite rígido e curto de espera (por exemplo, 2 a 3 segundos). Passado esse tempo sem resposta do hub, a requisição é cancelada imediatamente, liberando a thread de execução e evitando que a venda fique travada.
  2. O **Circuit Breaker** monitora a taxa de falhas consecutivas ou timeouts nas chamadas ao hub. Se o hub estiver instável, o disjuntor "abre o circuito" (estado Aberto), fazendo com que as próximas tentativas de fechar pedido falhem ou sigam um caminho alternativo na hora (fallback instantâneo), sem nem tentar gastar tempo enviando pacotes pela rede.
  3. No fluxo alternativo (fallback), a venda pode salvar o pedido com o status `FATURAMENTO_PENDENTE` e liberar o vendedor/cliente imediatamente, enquanto um processo em segundo plano tenta faturar o pedido assim que o disjuntor voltar ao estado Meio-Aberto/Fechado.

---

## 2. Problema 2: Repetição de Mensagens e Duplicidade de Faturamento

### O que acontece com o pedido
Em redes não confiáveis, uma falha comum ocorre no caminho de volta da comunicação: a venda envia o pedido, o hub de faturamento recebe, gera o boleto, emite a nota fiscal, persiste a fatura no banco, mas, no momento de devolver a resposta HTTP 201 com o `faturaId`, a conexão de rede cai ou sofre timeout de leitura na aplicação de vendas. Como a aplicação de vendas (ou o próprio usuário clicando várias vezes no botão "Fechar Pedido") não recebeu a confirmação de sucesso, ela assume que a operação falhou e reenvia a mesma requisição de fechamento de pedido para o hub. Sem uma proteção adequada, o hub trata a segunda chamada como um novo faturamento, criando uma segunda fatura, gerando um segundo boleto e emitindo uma segunda nota fiscal para uma única venda.

### O que o negócio percebe
- **Cobrança duplicada:** O cliente recebe dois boletos bancários por e-mail para pagar a mesma compra.
- **Inconsistência fiscal e contábil:** A empresa emite duas notas fiscais para a mesma mercadoria, gerando impostos indevidos e obrigando o setor contábil a efetuar cancelamento de NF-e e estornos bancários.
- **Perda de confiança do cliente:** O comprador se assusta com a duplicidade de cobrança e aciona o suporte/reclamações, gerando custos operacionais de atendimento.

### Solução arquitetural: Idempotência (Idempotency Key / Verificação por pedidoId)
Para sanar esse problema, a solução estudada é a implementação de **Idempotência**.

- **Onde ela atuaria:** Atuaria no **Hub de Faturamento (no lado servidor / camada de aplicação do hub)**.
- **Justificativa de atuação:**
  1. A operação de faturamento deve ser idempotente, isto é, executar a operação duas ou mais vezes com os mesmos parâmetros deve produzir exatamente o mesmo resultado de executá-la uma única vez.
  2. Ao receber a requisição de faturamento, o hub utiliza o identificador do pedido (`pedidoId`) ou uma chave de idempotência (`Idempotency-Key` enviada no cabeçalho HTTP) como identificador único.
  3. Antes de criar uma nova fatura, o hub consulta sua base de dados. Se já existir uma fatura associada àquele `pedidoId`, o hub não gera novo boleto nem emite nova nota fiscal: ele simplesmente recupera o registro existente e devolve o mesmo `faturaId` original com status de sucesso.
  4. Dessa forma, mesmo que a aplicação de vendas envie a requisição duas ou dez vezes devido a instabilidades de rede, existirá sempre uma única fatura, um único boleto e uma única nota fiscal no sistema.

---

## 3. Conclusão

A integração direta e síncrona via HTTP/REST entre Vendas e Faturamento é simples de implementar, porém introduz alto acoplamento temporal e suscetibilidade às falhas de rede. A aplicação de padrões como Timeout, Circuit Breaker e Idempotência protege a estabilidade dos sistemas e a consistência das regras de negócio, servindo como base técnica para a posterior evolução em direção a estilos orientados a eventos e mensageria assíncrona.
