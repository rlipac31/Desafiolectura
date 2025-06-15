# 📚 Desafío Literario - Un Explorador de Libros y Autores con Spring Boot y JPA

---

¡Bienvenido al proyecto **Desafío Literario**! Este es un proyecto backend desarrollado con **Spring Boot** enfocado en el aprendizaje y la práctica de **JPA (Java Persistence API)** e **Hibernate**. Aquí exploro cómo consumir datos de una API externa, persistirlos en una base de datos **PostgreSQL**, y luego consultarlos eficientemente usando **JPQL (Java Persistence Query Language)**.

Mi objetivo es consolidar mis conocimientos en persistencia de datos y manipulación de bases de datos relacionales, sentando las bases para una futura integración con una aplicación móvil desarrollada en **Kotlin**.

---

## ✨ Características Principales

* **Consumo de API Externa**: Conexión y obtención de datos bibliográficos (libros, autores, idiomas, etc.) de la API pública de [Gutendex](https://gutendex.com/).
* **Persistencia de Datos**: Almacenamiento de la información relevante de libros y autores en una base de datos relacional.
* **Gestión de Base de Datos**: Utilización de **PostgreSQL** como sistema de gestión de base de datos.
* **Mapeo ORM**: Implementación de **JPA** e **Hibernate** para el mapeo objeto-relacional, facilitando la interacción con la base de datos a través de entidades Java.
* **Consultas JPQL**: Realización de consultas avanzadas para filtrar, ordenar y obtener información específica de la base de datos (por ejemplo, autores vivos en un año determinado, libros por idioma o por autor).
* **Organización del Código**: Estructura de proyecto modular con capas de servicio y repositorio para una mayor claridad y mantenibilidad.

---

## 🚀 Tecnologías Utilizadas

Este proyecto hace uso de las siguientes tecnologías y herramientas:

* **Java 17**: Lenguaje de programación principal.
* **Spring Boot 3.x**: Framework para el desarrollo rápido de aplicaciones Java robustas y escalables.
* **Spring Data JPA**: Abstracción que simplifica enormemente el acceso a datos con JPA.
* **Hibernate**: Implementación de referencia de JPA, utilizada para el mapeo ORM.
* **PostgreSQL**: Base de datos relacional robusta y de código abierto para la persistencia de datos.
* **API Gutendex**: Fuente de datos bibliográficos (libros y sus metadatos).
* **Maven**: Herramienta para la gestión de dependencias y construcción del proyecto.
* **DTOs (Data Transfer Objects)**: Patrón de diseño para la transferencia segura y eficiente de datos entre capas.

---

## 🏗️ Estructura del Proyecto

El proyecto sigue una arquitectura por capas típica de Spring Boot:

```


src/main/java/com/richardlipa/desafioLectura
├── DesafioLecturaApplication.java  # Clase principal de la aplicación
├── model/                            # Entidades JPA (Libro, Autor, Lenguaje Enum)
├── repository/                       # Interfaces de repositorios (JpaRepository)
├── service/                          # Lógica de negocio y orquestación
├── dto/                              # Objetos de Transferencia de Datos para API y proyecciones JPQL
└── Principal.java                    # Clase principal para la interacción por consola (si aplica)

---
```

## 🛠️ Cómo Configurar y Ejecutar

Para poner en marcha este proyecto en tu entorno local:

1.  **Clona el Repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/nombre-de-tu-repo.git](https://github.com/tu-usuario/nombre-de-tu-repo.git)
    cd nombre-de-tu-repo
    ```
2.  **Configura PostgreSQL:**
    * Asegúrate de tener una instancia de PostgreSQL corriendo.
    * Crea una base de datos para el proyecto (ej: `desafio_literario_db`).
    * Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de base de datos:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:<TU_PUERTO>/<TU_BD>
        spring.datasource.username=tu_usuario_postgres
        spring.datasource.password=tu_contraseña_postgres
        spring.jpa.hibernate.ddl-auto=update # o create para la primera vez
        spring.jpa.show-sql=true
        ```
3.  **Construye el Proyecto:**
    ```bash
    mvn clean install
    ```
4.  **Ejecuta la Aplicación:**
    Puedes ejecutar la aplicación directamente desde tu IDE (ej. IntelliJ IDEA, Eclipse) o desde la línea de comandos:
    ```bash
    mvn spring-boot:run
    ```
    La aplicación se iniciará y podrás interactuar con ella a través de la consola (si `Principal.java` es tu punto de entrada principal para interacciones).

---

## 💡 Próximos Pasos

Este proyecto es una base sólida. Mis planes a futuro incluyen:

* **Desarrollo de Aplicación Móvil en Kotlin**: Crear una interfaz de usuario móvil que consuma los servicios REST expuestos por este backend.
* **API RESTful**: Exponer las funcionalidades de búsqueda y gestión de datos a través de una API REST para una mejor integración con la aplicación móvil.
* **Mejoras en Consultas**: Explorar consultas más complejas y optimizadas, incluyendo el uso de especificaciones JPA y Criteria API.
* **Manejo de Errores**: Implementar un manejo de errores más robusto y personalizado.

---