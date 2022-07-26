
# Greencode: Arquiteturas Sustentáveis na Nuvem

Arquiteturas para baixo consumo de energia e melhor custo X performance com AWS

![](https://cdn-images-1.medium.com/max/2000/1*jggoyM6wGUwJ0CSOsa55Sw.png)

Architecturing for low power consumption and better cost/performance with AWS

Sustentabilidade é uma prioridade mundial e ao longo dos últimos 50 anos aprendemos e evoluímos exponencialmente a melhorar o meio-ambiente através de tratamentos adequados de água e esgoto, coletas de lixo seletivas com reciclagem, multas por jogar lixo na rua, carros elétricos, energia solar residencial e muito mais; porém como falamos na Amazon: still day one!

Empresas tem se preocupado cada vez mais em quantificar os impactos gerados no meio-ambiente por suas operações, incluindo seu consumo computacional, por diversas razões:

* **Demandas de clientes**: as preferências por contratar empresas mais sustentáveis é um fator crescente para competitividade

* **Regulamentações Governamentais**: diferentes leis e regras de diferentes países podem gerar impactos severos em negócios menos sustentáveis.

* **Impacto em investimentos**: investidores cada vez mais evitam investimentos em projetos com alto impacto ambiental; sustentabilidade é um posicionamento competitivo.

### Sustainability Transformation

Enquanto a transformação digital acontece em meses ou anos, a Sustainability Transformation pode levar décadas e precisamos definir as metas certas para resultados de curto e longo prazo. Como podemos fazer isso na prática?

Em termos de cargas computacionais, o foco deste artigo, sem dúvidas que o uso de computação em nuvem é mais sustentável que on-primesis pois similar ao modelo de segurança compartilhada onde nós somos responsáveis pela segurança da nuvem e nossos clientes são responsáveis pela segurança na nuvem, o mesmo acontece: nós somos responsáveis por sustentabilidade **da nuvem** e nossos clientes são responsáveis por sustentabilidade das suas cargas **na nuvem**. 

![](https://cdn-images-1.medium.com/max/3360/1*k-cB4hE_GMmm1PGZCBb43A.png)

Nós construímos prédios com concretos low-carbon, salas, servidores e tomamos conta da reciclagem, fazemos uso eficiente de energia comprada e outros recursos como a água para refrigeração e nossos times de serviços AWS otimizam constantemente os serviços para sustentabilidade. 

Por outro lado nossos clientes são responsáveis por tomar decisões arquiteturais, escolher, usar e executar código de forma sustentável. Na prática vamos buscar gradativamente:

* Executar menos carga, armazenar menos informações

* Executar carga de forma mais eficiente possível

* Aumentar a utilização de recursos alocado

* Utilizar estratégias sustentáveis para agendamento de processos

### Cloud Sustainability

Definimos há alguns anos 5 pilares de arquitetura fundamentais no [Well Architected](https://aws.amazon.com/architecture/well-architected/?wa-lens-whitepapers.sort-by=item.additionalFields.sortDate&wa-lens-whitepapers.sort-order=desc):

1. Excelencia operacional

1. Segurança

1. Confiabilidade

1. Performance e Eficiência

1. Otimização de custos

Essas melhores práticas ajudam nossos clientes a lançarem seus produtos com arquiteturas eficientes e já vem sendo utilizas há alguns anos mas o comprometimento com o meio-ambiente nos levou a adicionar o sexto pilar: **sustentabilidade**. 

![](https://cdn-images-1.medium.com/max/3968/1*oJ0Gzdh-5QV32ss1q15z6g.png)

Nós fornecemos neste pilar as melhores práticas para sustentabilidade na nuvem desde a seleção de região, comportamento do usuário, padrões de arquitetura, padrões de dados e hardware e também o processo de desenvolvimento e implantação.

Podemos entender que sustentabilidade é um requisito não-funcional para nossas arquiteturas onde devemos pensar por exemplo, se movermos uma carga de uma região para outra, vamos acabar consumindo mais carbono devido ao aumento de data transfer? Isso vai afetar performance? Disponibilidade? Enfim, assim como os diversos outros populares requisitos não-funcionais de software como escalibilidade, auditabilidade, disponibilidade, etc.. a sustentabilidade passa a integrar essa grande família.

Resumidamente queremos:

1. Entender o impacto ambiental causado pelos serviços utilizados

1. Quantificar o impacto causado através do ciclo de vida completo do workload

1. Aplicar melhores práticas para minimizar tais impactos

### Arquiteturas Sustentáveis

As primeiras coisas que devemos considerar para arquiteturas sustentáveis  são:

1. Focar em mudanças de alto impacto positivo na sustentabilidade: planejar, recodificar e implementar soluções podem ter um maior consumo de carbono no desenho e execução do que os recursos economizados após implantação.

1. Assim como Digital Transformation, falhar rápido e falhar barato é parte da busca por arquiteturas sustentáveis poder fazer tentivas de mudanças que eventualmente falham e temos que regressar para a arquitetura anterior.

1. Automação de processos é essencial: infraestrutura como código é mandatório! Isso vai ajudar o processo de implementação ou regressão de mudanças aumentando seu ritmo de inovação não só em sustentabilidade mas também para novas funcionalidades

### Redução de Picos

O impacto causado depende da capacidade provisionada:

![](https://cdn-images-1.medium.com/max/3324/1*qDTtdW0sfTzas-2LiuOFPA.png)

E não da média da sua utilização:

![](https://cdn-images-1.medium.com/max/3316/1*5-hg5MJ-JJR15_sNuwC3VA.png)

Um dos exemplos comuns de capacidade provisionada é o uso de agendamento, especialmente crontabs que misticamente são há décadas agendadas a meia-noite. Muitos dos picos de uso de nossos recursos na AWS são ocasionados por crontabs a meia-noite, pensar em mover o horário diariamente pode ajudar e muito o seu provedor evitar picos e manter a região mais sustentável.

### Logs: enemies and friends

Logging  é um mal necessário e através delas sabemos se tudo está indo bem ou não. Um exemplo simples de mudança de alto impacto é a própria amazon.com que trocou o algoritmo de compactação de gzip para ZSTD e ganhou uma economia de armazenamento de 1 exabyte:

![](https://cdn-images-1.medium.com/max/3372/1*FosAO7fGmGjMTmDhQUVUtw.png)

Um tipo de mudança relativamente simples em um algoritmo de compactação com alto impacto em storage e consequentemente na sustentabilidade e cloud economics.

### Regiões Sustentáveis

Mover suas cargas para regiões mais sustentáveis também é uma mudança sem impacto em código e pode ser muito simples. Na AWS temos atualmente cinco regiões onde somos carbon free em razão da arquitetura sustentável dos data-centers onde também compramos de créditos de carbono para compensar o uso de energia adquirida.

![](https://cdn-images-1.medium.com/max/3100/1*u5PHdQta7-Vvb_2MUSz_Bg.png)

### Otimizando Workloads

Além de mudar para regiões mais sustentáveis, você também pode otimizar seus workloads para as mais de 150 tipos de instâncias EC2 e também mover para serviços que mantém seus workloads mais próximos de seus clientes para obter um melhor custo Vs. benefício Vs. latência Vs.data transfer; e muitas vezes realmente necessita de processamento on-premises eficiente ou projetos de inovação como IoT:

* **Regions / Availability Zone**: podemos entender como a "grande nuvem" e representam o uso padrão da nuvem AWS. Temos mais de 25 regiões no globo contabilizando mais de 80 availability zones que são os data-centers com redundância porém ao longo dos anos muitos de nossos clientes apresentaram necessidades em menor latência, resultando como resposta o desenvolvimento de serviços de computação em nuvem na borda.

* **AWS Local Zones**: podemos entender por Local Zones "pequenas nuvens" mais próximas de cidades super habitadas, indústrias e demandas de clientes para computação mais próxima e com menor latência em instâncias de servidores, armazenamento, banco de dados e outros.

* **AWS Wavelength**: e temos ainda uma solução para latências de um dígito de milisegundos embarcando nossa infraestrutura em provedores de 5G.

* **AWS Outposts**: neste caso levamos a nuvem até o cliente: você pode utilizar o mesmo hardware da AWS para executar cargas on-premises com ambientes totalmente gerenciado via AWS.

* **AWS IoT Greengrass**: permite que você leve um pedaço da nuvem para seu dispositivo IoT que executa Linux ou Windows. 

A lista completa de serviços edge da AWS [estão relacionadas aqui](https://aws.amazon.com/edge/services/) e incluem além dos citados acima, serviços para robótica, AI/ML, armazenamento e outros.

Eu pessoalmente executo um pedaço da nuvem na minha Kombi Alexa usando AWS IoT Greengrass para executar funções AWS Lambda localmente escritas em Python e Java para ler dados do motor (RPM, velocidade, temperatura e outros) através de OBD2:

![](https://cdn-images-1.medium.com/max/3316/1*4VieLqyzPvE3-U3SwQAYdg.png)

### AWS Graviton

Os processadores AWS Graviton são projetados pela AWS para oferecer a melhor relação preço/performance para suas workloads em nuvem em execução no Amazon EC2.

Os processadores AWS Graviton2 oferecem um grande avanço em performance e recursos em relação aos processadores AWS Graviton de primeira geração. Instâncias baseadas no Graviton2 fornecem a melhor relação preço/performance para workloads no Amazon EC2 e não só para EC2: com apenas um click você pode mudar sua função lambda para utilizar arquitetura ARM / Graviton e reduzir muito o custo e também energia consumida:

![](https://cdn-images-1.medium.com/max/3992/1*K2UULqGhEsy3wc6UuLpDfA.png)

### Storage

Surfing the pace of innovation in your Cloud provider is part of the game to be more sustainable. 

Surfar o ritmo de inovação do seu provedor de nuvem é parte do jogo para tornar seus workloads mais sustentáveis seja em armazenamento, processamento ou até mesmo movendo a computação mais perto de seus clientes. Em armazenameno S3 procure automatizar a camada com melhor custo Vs benefício Vs tempo de recuperação de dados:

![](https://cdn-images-1.medium.com/max/3376/1*MpWyehY4gBW3CdI6G0k2lg.png)

### Reciclagem na Nuvem

Quem nunca esqueceu algo ligado / armazenado na nuvem sem querer??? Essa desconfortável situação em larga escala pode levar um consumo inadequado de recursos e energia; quanto maior sua carga melhor será recorrer a soluções para analisar e avaliar recursos obsoletos ou não utilizados que possam ser eliminados. Um projeto de comunidade open-source super interessante é o Cloud Janitor do Caravana Cloud:

![](https://cdn-images-1.medium.com/max/3280/1*tjevKR5o-4eISZ25B8mCVw.png)

### Modern Apps

Modern Apps é a resposta para sustentabilidade e podemos abstrair em 3 pilares:

* **Microservices**: a natureza dos micro serviços permitem trabalhar com unidades pequenas de software baseadas em produtos e não em projetos monolíticos que aumentam o ritmo de inovação uma vez que mudanças e melhorias são mais fáceis de serem desenvolvidas e implementadas em produção

* **Serverless**: sem dúvida é o campeão da sustentabilidade pois você não precisa provisionar e dimensionar workloads trabalhando com o conceito que podemos chamar: "consume energy as you go" no lugar do "pay as you go"

* **DevOps**: automação de processos de desenvolvimento e CI / CD permitem experimentos rápidos e eficientes para levar para produção sua inovação sustentável ou mesmo fazer o rollback em experimentos que não atingiram a expectativa.

![](https://cdn-images-1.medium.com/max/3312/1*iEzxEk9ZPqgB4zrBGnQzLA.png)

### Carbon Footprint Console

E para ajudar nossos clientes anunciamos a nova funcionalidade em 2021 que permite a monitoração do Carbon Footprint através do billing console:

![](https://cdn-images-1.medium.com/max/3324/1*Gx4uHjb6H5i77s2rXiIY2w.png)














