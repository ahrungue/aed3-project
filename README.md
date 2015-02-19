# show-milhao
Trabalho de AED3 - Aplicativo que simula o jogo Show do Milhão

######  Estrutura do projeto  #####
```
[ROOT]
|\
| +---[doc]
| |\
| | \
| |  +---[BD]  - Scripts SQL para Iniciar o Banco de Dados
| |  |
| |  +---[UML] - Diagramas UML, SaveFile do Programa para monta-los
| |
| |
| +---[src] - Arquivos Fonte Java
| |
| |
| +---[yo]
| |\
| | \
| |  +--- [app]
| |  |
| |  |---bower.json   - Dependencias de Bibliotecas importadas pelo bower
| |  |
| |  |---Gruntfile.js - Configurações das tarefas do Grunt 
| |  |
| |  |---package.json - Dependencia de Modulos.
| |
| |
| |---pom.xml - Configurações do maven, dependencias do Java
```
######  Bibliotecas e Softwares necessários  ######
* Maven
* NodeJS
* Npm
* Yeoman
* Bower
* Grunt
* generator-angular
* RVM - Stable Ruby
* compass
* Git


######  Instalando NodeJS e NPM  ######
```bash
sudo apt-get -y install curl
curl -sL https://deb.nodesource.com/setup | sudo bash -
sudo apt-get -y install nodejs
```
######  Checar as versões  ######
```bash
node --version && npm --version
```
######  Instalando Yeoman, Bower, Grunt, Generator-Angular  ######
```bash
sudo npm install -g yo bower grunt-cli
sudo npm install -g generator-angular
```
######  Checar as versões  ######
```bash
yo --version && bower --version && grunt --version
```
######  RVM e Ruby  ######
Acessar o site:
http://rvm.io/rvm/install

```bash
gpg --keyserver hkp://keys.gnupg.net --recv-keys D39DC0E3
\curl -sSL https://get.rvm.io | bash -s stable --ruby
```
######  Subir a instancia, pode por .bashrc   ######
```bash
source /home/alvaro/.rvm/scripts/rvm
```
######  Instalar suporte no grunt serve ao compass sass  ######
```bash
gem install compass
```
######  Verificar permissão das pastas com os modulos do node  ######

######  Normalmente em um dos diretorios abaixo:  ######
* sudo chown  -R  {user|root}:{user|grupo}  /usr/lib/node_modules
* sudo chown  -R  {user|root}:{user|grupo}  /usr/local/lib/node_modules

######  Normalmente em um dos diretorios abaixo:  ######
* sudo chmod -R 775	/usr/lib/node_modules
* sudo chmod -R 775	/usr/local/lib/node_modules

###### Exemplo - Adicionar dependencia bower #####
```bash
##### Adicionar ao projeto ######
bower install font-awesome --save

##### Adicionar apenas para dev ######
bower install font-awesome --save-dev
```

##### Para rodar o projeto depois de clonar o repositorio, deve executar os comandos #####
```bash
cd ~/IdeaProjects/show-milhao/yo

###### Modulos do projecto yo,grunt,npm,bower,etc ######
npm update

###### Depencias do bibliotecas ######
bower install

###### Compilar o projeto e gerar o front-end para a aplicação ######
grunt
```