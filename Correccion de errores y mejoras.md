# Revision del proyecto vs. material de clase (hasta aprox. clase 4)

## Alcance usado para la comparacion

Como el alcance fue pedido como "aprox. clase 4", tome como referencia principal los ejemplos practicos disponibles en `Material de Clase`:

- `Material de Clase/Clase Springboot`
- `Material de Clase/Clase Hibernate/demo`
- `Material de Clase/demo JWT`
- `Material de Clase/JWT + Transactional + Roles/demo`

Priorice lo que efectivamente se ve aplicado en el proyecto actual: arquitectura en capas, Spring Boot, JPA/Hibernate, relaciones entre entidades, repositorios, servicios, controladores y, en segundo plano, JWT/roles.

## Lo que esta bien aplicado

- La separacion `controller -> service -> repository` esta bien encaminada y coincide con la arquitectura mostrada en clase.
- El proyecto usa entidades JPA, repositorios Spring Data y DTOs, lo cual esta alineado con el material de Spring Boot + Hibernate.
- Hay un esfuerzo real por modelar relaciones de negocio: usuarios, carrito, ordenes, productos, talles, clubes y categorias.
- JWT y seguridad estan implementados como una extension razonable del material, aunque todavia hay varios detalles por corregir.

## Errores detectados

### 1. El modelo de carrito entra en conflicto con el flujo de checkout

- En `src/main/java/com/ecommerce/camisetas/model/entity/Carrito.java:24-26` el carrito esta modelado como `@OneToOne` con `Usuario`.
- En `src/main/java/com/ecommerce/camisetas/service/OrderService.java:90-99` el checkout cierra el carrito actual y crea uno nuevo para el mismo usuario.
- En `basededatosuade.sql:33-41` la tabla `carritos` deja `id_usuario` con restriccion `UNIQUE`.

Consecuencia:

- Un usuario no puede tener mas de un carrito historico.
- El checkout puede fallar al intentar insertar el nuevo carrito para el mismo `id_usuario`.

Comparado con lo visto en Hibernate, el problema no es de sintaxis sino de modelo relacional: la relacion deberia reflejar el comportamiento real del dominio. Si quieren conservar historico de carritos, esa relacion no puede ser `OneToOne` global.

### 2. El build no es reproducible en otra maquina

- `./mvnw test` falla porque falta `.mvn/wrapper/maven-wrapper.properties`.
- El `README.md:21-25` dice que Maven viene incluido mediante `./mvnw`, pero hoy el wrapper esta incompleto.
- Ademas, `pom.xml:78-93` fija un `javac` local y `pom.xml:85` apunta a `/opt/homebrew/Cellar/openjdk@17/17.0.18/...`, que es un path especifico de una instalacion local.

Consecuencia:

- El proyecto no se puede validar de forma estandar en otra maquina aunque tenga Java 17.
- La compilacion queda acoplada al entorno del desarrollador que lo configuro.

Esto se aparta del enfoque de los ejemplos de clase, donde el proyecto deberia poder levantarse con configuracion base de Spring y Maven wrapper funcional.

### 3. La validacion de requests esta casi vacia

- Los controladores usan `@Validated`, por ejemplo:
  - `src/main/java/com/ecommerce/camisetas/controller/AuthController.java:21-27`
  - `src/main/java/com/ecommerce/camisetas/controller/CatalogController.java:34-35`
  - `src/main/java/com/ecommerce/camisetas/controller/CatalogController.java:60-70`
  - `src/main/java/com/ecommerce/camisetas/controller/CartController.java:23-27`
- Pero DTOs criticos no tienen restricciones:
  - `src/main/java/com/ecommerce/camisetas/model/dto/RegistroRequestDto.java:1-11`
  - `src/main/java/com/ecommerce/camisetas/model/dto/LoginRequestDto.java:1-8`
  - `src/main/java/com/ecommerce/camisetas/model/dto/ProductoRequestDto.java:1-19`
  - `src/main/java/com/ecommerce/camisetas/model/dto/AgregarItemRequestDto.java:1-9`
  - `src/main/java/com/ecommerce/camisetas/model/dto/CheckoutRequestDto.java:1-7`
  - `src/main/java/com/ecommerce/camisetas/model/dto/DescuentoRequestDto.java:1-16`
- La unica excepcion clara es `src/main/java/com/ecommerce/camisetas/model/dto/ClubRequestDto.java:8-17`.

Consecuencia:

- El backend acepta requests vacios, nulos o inconsistentes hasta llegar a capa de servicio o base de datos.
- Eso genera errores 500 evitables y reglas de negocio poco defendidas desde la API.

### 4. Los descuentos no impactan ni en carrito ni en checkout

- `src/main/java/com/ecommerce/camisetas/service/CatalogService.java:256-269` calcula `precioConDescuento` solamente al armar el DTO del catalogo.
- `src/main/java/com/ecommerce/camisetas/service/CartService.java:90-100` guarda `producto.getPrecio()` como `precioUnitario`.
- `src/main/java/com/ecommerce/camisetas/service/OrderService.java:71-80` usa ese `precioUnitario` para calcular subtotales y total de la orden.

Consecuencia:

- El catalogo puede mostrar un descuento activo.
- Pero cuando el usuario agrega al carrito o hace checkout, termina pagando el precio base.

Eso es un error funcional, no solo una mejora pendiente.

### 5. El stock general y el stock por talle pueden desincronizarse

- `src/main/java/com/ecommerce/camisetas/model/entity/Producto.java:29-33` define `stock` general.
- `src/main/java/com/ecommerce/camisetas/model/entity/Producto.java:57-58` define `productoTalles` con stock por variante.
- `src/main/java/com/ecommerce/camisetas/service/CartService.java:51-66` permite agregar un producto sin `idProdTalle`; si viene nulo, valida contra `producto.stock`.
- `src/main/java/com/ecommerce/camisetas/service/OrderService.java:51-69` descuenta stock general o stock por talle segun como quedo guardado el item.

Consecuencia:

- Un producto con talles puede comprarse sin elegir talle.
- Se puede consumir `stock` general sin tocar `stockTalle`, o al reves.
- El inventario queda inconsistente.

## Mejoras recomendadas

### 1. Completar el manejo de errores de API

- `src/main/java/com/ecommerce/camisetas/exception/GlobalExceptionHandler.java:15-28` solo contempla `ResourceNotFoundException`, `BusinessValidationException` y un `Exception` generico.
- Faltaria manejar al menos:
  - `MethodArgumentNotValidException`
  - `DataIntegrityViolationException`
  - errores de autenticacion/autorizacion cuando quieran respuestas mas limpias

Hoy muchas entradas invalidas van a terminar en 500 generico en lugar de 400 bien explicado.

### 2. Evitar secretos y credenciales hardcodeadas

- `src/main/java/com/ecommerce/camisetas/security/JwtService.java:20-21` deja la `SECRET_KEY` dentro del codigo.
- `src/main/java/com/ecommerce/camisetas/CamisetasApplication.java:27-44` crea un vendedor por defecto con password fija y la imprime en consola.

Como practica academica puede servir para probar rapido, pero como implementacion del proyecto conviene moverlo a propiedades/variables de entorno y no exponer credenciales en logs.

### 3. Mejorar la respuesta ante JWT invalido

- `src/main/java/com/ecommerce/camisetas/security/JwtAuthenticationFilter.java:61-66` captura cualquier excepcion y solo hace `System.out.println`.

Mejorable porque:

- no deja trazabilidad real
- no devuelve una respuesta de seguridad consistente
- dificulta diagnosticar errores de autenticacion

### 4. Completar la actualizacion de productos

- `src/main/java/com/ecommerce/camisetas/service/CatalogService.java:96-137` actualiza campos simples e imagenes, pero no actualiza `talles`.

Si el proyecto ya maneja stock por talle, la actualizacion deberia contemplar altas, bajas y cambios de esas variantes.

### 5. Revisar el arranque de base de datos

- `src/main/resources/application.yml:9-21` combina `ddl-auto: update` con `sql.init.mode: always` e `import.sql`.

No necesariamente esta mal para desarrollo, pero vuelve el arranque menos predecible. Para una entrega suele convenir elegir una estrategia mas controlada:

- o esquema manejado por Hibernate para desarrollo
- o scripts SQL/versionados
- pero no dejar ambos mezclados sin una convencion clara

### 6. Agregar pruebas

- El proyecto actual no tiene `src/test`.
- Los proyectos de ejemplo de clase si traen al menos el esqueleto de pruebas de Spring Boot.

Minimo recomendable:

- test de contexto
- tests de servicio para `AuthService`, `CartService` y `OrderService`
- algun test de controlador para flujos principales

## Observacion general respecto de lo visto en clase

La base conceptual esta bien orientada: hay capas, entidades, repositorios, DTOs y seguridad. El problema principal no es que "falte usar Spring" o "falte usar Hibernate", sino que algunas decisiones de modelado y negocio todavia no estan cerradas con consistencia.

En otras palabras:

- lo visto en clase esta mayormente presente
- pero no todo esta bien aplicado
- los desajustes mas grandes aparecen en relaciones, validaciones y reglas de negocio

## Validacion realizada

- Revision estatica del codigo actual.
- Comparacion con los ejemplos de `Clase Springboot`, `Clase Hibernate`, `demo JWT` y `JWT + Transactional + Roles`.
- Intento de validacion con `./mvnw test`.

Resultado del intento de build:

- fallo porque falta `.mvn/wrapper/maven-wrapper.properties`, por lo que no pude ejecutar compilacion ni tests reales del proyecto desde el wrapper.

## Nota final

No hice cambios de codigo sobre la aplicacion. Este archivo solo documenta errores y mejoras detectadas para una siguiente etapa de correccion.
