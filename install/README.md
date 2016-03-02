
### Dependência do MongoDB
Faça a instalação padrão do MongoDB em um servidor da sua escolha. O restaut usa o MongoDB para gravar os scripts e execuções dos testes, além de outras configurações.

http://docs.mongodb.org/master/tutorial/install-mongodb-on-ubuntu/?_ga=1.219090522.294414601.1430255282

### Instalando no Linux
Dentro do diretório install encontran-se os arquivos para configurar o serviço, siga os passos abaixo:

1. Copie o diretório 'restaut' com seus arquivos para o local onde desejar instalar o serviço. 

a. No arquivo startup.sh as duas primeiras linhas são usadas para definir a HOME do Java 8 que precisa estar instalado no seu servidor. 
Modifique o caminho se necessário ou remova as linhas se preferir.

b. Os dois parâmetros de execução do restaut são a url da instância do MongoDB e a porta onde o serviço irá receber as requisições.
Troque o que for necessário.

2. Copie o arquivo ./init.d/restaut para o diretório /etc/init.d do servidor onde desejar instalar o serviço.

3. Edite o arquivo 'restaut' que você copio para o diretório /etc/init.d

a. Troque o conteúdo da variável RESTAUT_HOME pelo diretório onde você instalou o restaut.

b. Na função start procure alinha abaixo e troque a palavra 'tray' pelo usuário que você usa para logar no seu servidor
De   -> /bin/su -p -s /bin/sh tray $RESTAUT_HOME/startup.sh > /dev/null 2>&1
Para -> /bin/su -p -s /bin/sh seu_usuario $RESTAUT_HOME/startup.sh > /dev/null 2>&1

c. Dê permissão de execução no arquivo 'restaut'
```
sudo chmod a+x restaut 
```

### Testando a instalação
Se tudo estiver correto você deve poder iniciar o serviço com o comando abaixo:

```
sudo service restaut start
```
Teste o serviço pelo seu browser http://localhost:8082/ . A URL e a porta podem variar de acordo com a sua instalação.

Você pode parar o serviço com o comando abaixo:

```
sudo service restaut start
```

Mantendo o serviço ligado no CentOs Linux

```
sudo chkconfig restaut on
```

Verificar os serviços do boot

```
chkconfig --list
```

