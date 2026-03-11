# 📚 Challenge LiterAlura - Catálogo de Libros

Un catálogo de libros interactivo por consola que consume la API de Gutendex y almacena la información en una base de datos relacional. 

---

## 📝 Descripción del Proyecto

LiterAlura es una aplicación construida en Java con el framework Spring Boot. Su propósito es facilitar la búsqueda de libros literarios a través de una API pública, procesar los resultados en formato JSON y guardar tanto los libros como sus autores en una base de datos PostgreSQL. El sistema incluye validaciones para evitar registros duplicados y mantener la integridad de la base de datos.

## ⚙️ Funcionalidades Principales

El sistema despliega un menú interactivo en la consola con las siguientes opciones:

1. **Buscar libro por título:** Consulta la API de Gutendex, extrae el primer resultado coincidente y guarda el libro y su autor en la base de datos local de forma relacional.
2. **Listar libros registrados:** Consulta la base de datos y muestra todos los libros almacenados históricamente.
3. **Listar autores registrados:** Muestra una lista de todos los autores que han sido guardados en el sistema.
4. **Listar autores vivos en un determinado año:** Permite ingresar un año específico (ej. 1800) y mediante una consulta JPQL personalizada, devuelve los autores que estaban vivos en esa época.
5. **Listar libros por idioma:** Filtra la base de datos y muestra los libros según el código de idioma seleccionado (`es`, `en`, `fr`, `pt`).

## 🛠️ Tecnologías y Herramientas

* **Lenguaje:** Java (JDK 17+)
* **Framework:** Spring Boot 3
* **Base de Datos:** PostgreSQL
* **ORM:** Spring Data JPA / Hibernate
* **Gestor de dependencias:** Maven
* **Procesamiento JSON:** Jackson (ObjectMapper)
* **Peticiones HTTP:** Java `HttpClient` nativo

## 🚀 Instalación y Uso Local

### Prerrequisitos
* Tener instalado Java 17 o superior.
* Tener instalado PostgreSQL y el servidor en ejecución.

### Configuración
1. Clona este repositorio en tu máquina local:
   `git clone https://github.com/tu-usuario/LiterAlura.git`
2. Abre tu gestor de base de datos (ej. pgAdmin) y crea una base de datos vacía llamada `literalura`.
3. Navega al archivo `src/main/resources/application.properties` y configura tus credenciales de PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Listo, sincroniza y ya puedes ejecutar el programa.
