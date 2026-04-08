# Proyecto RE PIOLA DE Camisetas - E-commerce de Fútbol ⚽

Este es un backend para una plataforma de e-commerce especializada en la venta de camisetas de fútbol, desarrollado con **Spring Boot 3**. Incluye funcionalidades de catálogo, gestión de carrito de compras, procesamiento de órdenes y seguridad mediante JWT.

## 🚀 Características

- **Autenticación y Autorización**: Sistema de login y registro usando **JWT (JSON Web Tokens)**.
- **Catálogo de Productos**: Gestión de artículos, categorías y clubes.
- **Carrito de Compras**: Agregar, modificar y eliminar artículos de un carrito activo por usuario.
- **Gestión de Órdenes**: Proceso de checkout y creación de órdenes de compra.
- **Base de Datos**: Integración con MySQL y carga de datos iniciales mediante `import.sql`.

## 🛠️ Requisitos Previos

- **Java 17** o superior.
- **MySQL 8.0** o superior.
- **Maven** (incluido mediante el wrapper `./mvnw`).

## ⚙️ Configuración

El proyecto utiliza variables de entorno para la configuración de la base de datos, lo que permite una fácil personalización sin modificar el código fuente.

### Variables de Entorno Disponibles

Puedes configurar estas variables en tu sistema o IDE:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `DB_HOST` | Host de la base de datos MySQL | `localhost` |
| `DB_PORT` | Puerto de la base de datos | `3306` |
| `DB_NAME` | Nombre de la base de datos | `camisetas_db` |
| `DB_USER` | Usuario de MySQL | `tu usuario` |
| `DB_PASSWORD` | Contraseña de MySQL | `tu clave` |

## 🏃 Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/ProyectoCamisetas.git
   cd ProyectoCamisetas
   ```

2. **Configurar la base de datos:**
   Asegúrate de que MySQL esté corriendo y de tener creada la base de datos (o deja que el parámetro `createDatabaseIfNotExist=true` la cree por ti).

3. **Correr la aplicación:**
   ```bash
   ./mvnw spring-boot:run
   ```

La aplicación estará disponible en `http://localhost:8080`.

## 🧪 Probar la API

Puedes usar Postman o `curl` para interactuar con los endpoints.

### Flujo recomendado:
1. **Registro/Login**: Envía un `POST` a `/api/auth/register` o `/api/auth/login` para obtener tu token.
2. **Catálogo**: Explora los productos en `GET /api/catalogo`.
3. **Carrito**: Usa el token en el header `Authorization: Bearer <token>` para agregar ítems en `POST /api/carrito/items`.

## 📂 Estructura del Proyecto

- `src/main/java`: Contiene el código fuente organizado por capas (controller, service, repository, model).
- `src/main/resources`: Archivos de configuración (`application.yml`) y scripts SQL (`import.sql`).
- `.gitignore`: Configurado para evitar subir archivos temporales y sensibles.

---
Desarrollado con ❤️ para los amantes del fútbol.
