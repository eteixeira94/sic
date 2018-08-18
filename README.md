# SIC - Sistema de Integração do Cliente

Sistema para realizar o cadastro de clientes com alta disponibilidade para o método de consultar cliente.

Ao criar um cliente será necessário identificar a localização da requisição e guardar a informação do clima da requisição.

Caso não o tenha, recuperar o clima mais próximo;


Banco de Dados Utilizado: Mysql 5.0
Servidor utilizado: Apache 9.0
Banco de Dados de Teste: HSQLDB
Conexão JNDI: SIC_DS
Tecnologias utilizadas: 
	Spring boot 2.0.4.RELEASE
	Java 8
	Lombok 1.16.22
	HsqlDB 2.4.1	
	JPA/Hibernate 5
	Jackson 2
	
Foram utilizadas as tecnologias Java, por que é o pré-requisito. O Lombok foi escolhido afim de reduzir código como 
Getter e Setter, Builder. HsqlDB afim de ter um banco em banco em memória para executar os testes.
JPA e Hibernate para persistência dos dados e validação da entidade, jackson como responsável em realizar o 
parser do corpo da requisição [JSON] para o objeto e devolver JSON para o cliente [Serializador e Deserializador].
E o spring boot para prover o framework spring, bem como gerenciamento de dependencias e consumo de rest externo.

SIC - Sistema de Integração de Clientes

API Rest: /api/cliente

salvar: /salvar [POST] 

Caso queira setar o ip remoto, adicione o header: X-FORWARDED-FOR

Será gravado todo o histórico do dia gerado pela API disponilizada pelos senhores.

Json esperado: {nome: "[TEXTO]", idade: [NUMERO]}

Retorno 200.

atualizar: /atualizar [PUT] 

Json esperado: {nome: "[TEXTO]", idade: [NUMERO], id: [NUMERO]}

Retorno 200.

Consultar por id: /[id] [GET]

Retorno 200 com o objeto cliente: 
	{id: [NUMERO], nome: [TEXTO], idade: [NUMERO], climaHistoricos: [{id: [NUMERO], ipOrigem: [TEXTO], longitude: [NUMERO], latitude: [NUMERO], dataRequisicao: [DATA], temperaturaMinima: [NUMERO], temperaturaMaxima: [NUMERO], horario: [DATE]}]}

Deletar: /deletar/[id] [DELETE]

Retorna 200 se o ID existir.

Consultar todos: /consultar-todos [GET]

Retorna 200 e todos os registros do Banco de Dados


Obs.: Foram feito os teste de integração consumindo os serviço em runtime. A máquina utilizada foi um DELL 16Gb Core i7. 


Para empacotar o projeto basta instalar o maven localmente: 
http://luizricardo.org/2014/06/instalando-configurando-e-usando-o-maven-para-gerenciar-suas-dependencias-e-seus-projetos-java/

Após realizar a instalação,

Abra o console na pasta do projeto e execute o comando: mvn clean package.

Será gerado o arquivo .war dentro da pasta target com o nome sic-rest-1.0.0.war

Renome para sic-rest.war.


Criar a conexão JNDI:
http://blog.camilolopes.com.br/tag/jndi/

Instalar um servidor. Recomendo que os senhores utilizem o docker para montar o ambiente e para poder escalonar o serviço.
http://edermag.blogspot.com/2015/06/explorando-o-docker-para-construir.html

Neste link a cima ensina como criar o ambiente e aplicar o war que foi gerado anteriormente.

