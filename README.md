# delivery-app

Recursos necesarios antes de empezar:
* Maven
* Docker
* Docker Compose

### Instalar Maven
#### Linux
```
sudo apt-get update
sudo apt-get install maven
```
Para comprobar que la instalación finalizó con éxito ejecute el siguiente comando:
```
mvn -v
```
Debería poder ver la versión actual de Maven

#### Windows
1. Descargue Maven desde la página oficial de Maven:
[Descargar Maven](https://maven.apache.org/download.cgi)

2. Descargue el Binary zip archive
3. Extraiga el zip
4. Cree una nueva variable de entorno llamada MAVEN_HOME que apunte al directorio donde extrajo el contenido del zip. Por ejemplo, si lo extrajo en C:\maven, la variable de entorno deberia de ser:

```
MAVEN_HOME=C:\maven\apache-maven-<version>
```
6. Agregue el directorio "bin" de Maven a la variable de entorno "Path"

```
C:\maven\apache-maven-<version>\bin
```
7. Para verificar que Maven se ha instalado correctamente abra una nueva ventana de línea de comandos y escriba el siguiente comando:

```
mvn -v
```
Debería poder ver la versión actual de Maven.

```
## Ejecutar la aplicación
1. Dirijase a la ruta raíz del proyecto delivery-app al mismo nivel donde se encuentra el archivo docker-compose.yml y ejecute el comando:
```
docker-compose up -d
```
O si usa Windwos con Docker Desktop
```
docker compose up
```
Para comprobar que esté funcionando corretamente dirijase a su navegador e ingrese la siguiente ruta:
http://localhost:8080/DELIVERY/hello

Debería ver el mensaje 'Hola' en el navegador
 

