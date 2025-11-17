# DOCUMENTO DE EVALUACIÓN

---

## PORTADA

**Nombre de la materia:**  
_Programacion 2_

**Docente:**  
_Miguel Silva_

**Integrantes del grupo:**  
_Mailen Alassia y Nicolás Guinzio_

**Año, Cátedra y año:**  
_2.603 2025_

**Título del proyecto:**  
Sistema de Gestión Policial Federal

**Link al repositorio de GitHub:**  
_[Completar con el link al repositorio de GitHub]_

---


## 1. DESCRIPCIÓN GENERAL DEL PROYECTO

### 1.1. Tema Elegido

El proyecto consiste en el desarrollo de un **Sistema de Gestión Policial Federal** que permite administrar y gestionar información relacionada con:

- **Vigilantes**: Personal de seguridad contratado para proteger sucursales bancarias
- **Contratos**: Relaciones contractuales entre vigilantes y sucursales bancarias
- **Entidades Bancarias y Sucursales**: Información de instituciones financieras
- **Asaltantes y Bandas Criminales**: Registro de delincuentes y sus organizaciones
- **Asaltos**: Registro de incidentes delictivos en sucursales
- **Jueces y Casos Judiciales**: Gestión de procesos judiciales y condenas
- **Usuarios y Roles**: Sistema de autenticación y autorización basado en roles

### 1.2. Objetivos del Sistema

El sistema tiene como objetivos principales:

1. **Gestionar información policial**: Centralizar la información relacionada con delitos, delincuentes y procesos judiciales
2. **Administrar contratos de seguridad**: Registrar y gestionar los contratos entre vigilantes y sucursales bancarias
3. **Controlar acceso mediante roles**: Implementar un sistema de permisos que restrinja el acceso según el rol del usuario
4. **Facilitar consultas**: Permitir a los usuarios autorizados consultar información relevante según sus permisos
5. **Mantener integridad de datos**: Asegurar la consistencia y validez de la información mediante validaciones y relaciones entre entidades

### 1.3. Descripción de los Actores y sus Roles

El sistema define tres roles principales con diferentes niveles de acceso:

#### **Administrador**
- **Permisos completos** del sistema
- Puede crear, editar y eliminar todas las entidades
- Puede gestionar usuarios y roles
- Puede crear contratos entre vigilantes y sucursales
- Puede registrar asaltos y detenidos
- Puede abrir casos judiciales
- Puede consultar toda la información del sistema

#### **Vigilante**
- **Acceso limitado** a su propia información
- Solo puede consultar sus datos personales y contratos asociados
- No puede modificar información de otras entidades
- Puede cerrar sesión y salir del sistema

#### **Investigador**
- **Acceso de solo lectura** a la información del sistema
- Puede consultar todas las entidades (jueces, vigilantes, asaltantes, casos, etc.)
- No puede crear, editar ni eliminar información
- Puede cerrar sesión y salir del sistema

---

## 2. ARQUITECTURA Y DESARROLLO

### 2.1. Descripción de las Capas del Sistema

El sistema está implementado siguiendo la **arquitectura MVC (Model-View-Controller)** con Spring Boot, organizado en las siguientes capas:

#### **Capa de Modelo (Model)**
- **Ubicación**: `src/main/java/com/PoliciaFederal/Policia_federal/model/`
- **Responsabilidad**: Representa las entidades del dominio y la estructura de datos
- **Tecnología**: JPA/Hibernate con anotaciones `@Entity`, `@Table`, `@Column`
- **Entidades principales**:
    - `Usuario`, `Rol`, `Permiso`
    - `Vigilante`, `Juez`
    - `EntidadBancaria`, `Sucursal`
    - `Asaltante`, `BandaCriminal`
    - `Asalto`, `ContratoSucVig`
    - `Caso`, `Condena`

#### **Capa de Repositorio (Repository)**
- **Ubicación**: `src/main/java/com/PoliciaFederal/Policia_federal/repository/`
- **Responsabilidad**: Abstracción del acceso a datos mediante Spring Data JPA
- **Tecnología**: Interfaces que extienden `JpaRepository<T, ID>`
- **Funcionalidad**: Proporciona métodos CRUD automáticos y consultas personalizadas

#### **Capa de Servicio (Service)**
- **Ubicación**: `src/main/java/com/PoliciaFederal/Policia_federal/service/`
- **Responsabilidad**: Contiene la lógica de negocio y reglas de validación
- **Tecnología**: Clases anotadas con `@Service` y `@Transactional`
- **Funcionalidades**:
    - Validaciones de negocio
    - Generación automática de códigos
    - Manejo de relaciones entre entidades
    - Transformación de datos

#### **Capa de Controlador (Controller)**
- **Ubicación**: `src/main/java/com/PoliciaFederal/Policia_federal/controller/`
- **Responsabilidad**: Maneja las peticiones HTTP y coordina entre Model y View
- **Tecnología**: Clases anotadas con `@Controller` y `@RequestMapping`
- **Funcionalidad**:
    - Recibe peticiones del usuario
    - Valida permisos mediante interceptores
    - Invoca servicios
    - Retorna vistas o redirecciones

#### **Capa de Vista (View)**
- **Ubicación**: `src/main/resources/templates/`
- **Responsabilidad**: Presenta la información al usuario mediante HTML
- **Tecnología**: Thymeleaf como motor de plantillas
- **Características**:
    - Plantillas reutilizables (`layout.html`)
    - Integración con datos del modelo
    - Formularios dinámicos
    - Validación en cliente

### 2.2. Explicación de la Estructura del Proyecto

```
Policia_federal/
├── src/
│   ├── main/
│   │   ├── java/com/PoliciaFederal/Policia_federal/
│   │   │   ├── config/              # Configuración del sistema
│   │   │   │   ├── DataInitializer.java    # Inicialización de datos
│   │   │   │   └── WebConfig.java          # Configuración web
│   │   │   ├── controller/          # Controladores MVC
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── UsuarioController.java
│   │   │   │   ├── ConsultasController.java
│   │   │   │   ├── CrearEntidadController.java
│   │   │   │   ├── EditarController.java
│   │   │   │   ├── ContratoController.java
│   │   │   │   ├── DetenidoController.java
│   │   │   │   ├── CasoController.java
│   │   │   │   ├── EntidadBancariaController.java
│   │   │   │   └── VigilanteController.java
│   │   │   ├── model/               # Entidades JPA
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── Rol.java
│   │   │   │   ├── Permiso.java
│   │   │   │   ├── Vigilante.java
│   │   │   │   ├── Juez.java
│   │   │   │   ├── EntidadBancaria.java
│   │   │   │   ├── Sucursal.java
│   │   │   │   ├── Asaltante.java
│   │   │   │   ├── BandaCriminal.java
│   │   │   │   ├── Asalto.java
│   │   │   │   ├── ContratoSucVig.java
│   │   │   │   ├── Caso.java
│   │   │   │   └── Condena.java
│   │   │   ├── repository/          # Repositorios JPA
│   │   │   │   └── [11 repositorios, uno por entidad]
│   │   │   ├── service/             # Servicios de negocio
│   │   │   │   ├── UsuarioService.java
│   │   │   │   ├── VigilanteService.java
│   │   │   │   ├── CodigoGeneratorService.java
│   │   │   │   └── [10 servicios adicionales]
│   │   │   ├── util/                # Utilidades
│   │   │   │   ├── PermisoUtil.java
│   │   │   │   └── ErrorHandlerUtil.java
│   │   │   ├── interceptor/         # Interceptores
│   │   │   │   └── AuthInterceptor.java
│   │   │   └── PoliciaFederalApplication.java  # Clase principal
│   │   └── resources/
│   │       ├── application.properties    # Configuración de BD y Spring
│   │       └── templates/                # Vistas Thymeleaf
│   │           ├── layout.html           # Plantilla base
│   │           ├── index.html            # Página principal
│   │           ├── login.html            # Login
│   │           ├── usuarios/             # Vistas de usuarios
│   │           ├── consultas/            # Vistas de consultas
│   │           ├── crear-entidad/        # Formularios de creación
│   │           ├── editar/               # Formularios de edición
│   │           ├── contratos/            # Vistas de contratos
│   │           ├── detenidos/            # Vistas de detenidos
│   │           ├── casos/                # Vistas de casos
│   │           └── vigilante/            # Vistas del vigilante
│   └── test/                        # Pruebas unitarias
├── pom.xml                          # Configuración Maven
└── README.md                        # Documentación del proyecto
```

### 2.3. Descripción de las Tecnologías y Frameworks Utilizados

#### **Backend**
- **Spring Boot 3.5.7**: Framework principal para desarrollo de aplicaciones Java
- **Spring MVC**: Framework para arquitectura MVC y manejo de peticiones HTTP
- **Spring Data JPA**: Abstracción para acceso a datos y repositorios
- **Hibernate**: ORM (Object-Relational Mapping) para mapeo objeto-relacional
- **Java 17**: Lenguaje de programación y versión utilizada

#### **Base de Datos**
- **MySQL/MariaDB**: Sistema de gestión de bases de datos relacional
- **XAMPP**: Entorno de desarrollo que incluye MySQL y phpMyAdmin

#### **Frontend**
- **Thymeleaf**: Motor de plantillas para generar HTML dinámicamente
- **HTML5/CSS3**: Lenguajes de marcado y estilos
- **JavaScript**: Para validaciones y funcionalidades dinámicas en el cliente

#### **Herramientas de Desarrollo**
- **Maven**: Gestor de dependencias y construcción del proyecto
- **Git**: Control de versiones

### 2.4. Explicación de las Clases Principales y sus Responsabilidades

#### **Controladores Principales**

**`AuthController`**
- **Responsabilidad**: Maneja la autenticación de usuarios
- **Métodos principales**:
    - `mostrarLogin()`: Muestra el formulario de login
    - `login()`: Valida credenciales y crea sesión
    - `logout()`: Cierra la sesión del usuario

**`HomeController`**
- **Responsabilidad**: Gestiona la página principal y menú según el rol
- **Funcionalidad**: Redirige al usuario según sus permisos

**`ConsultasController`**
- **Responsabilidad**: Maneja todas las consultas de información
- **Endpoints**: `/consultas/jueces`, `/consultas/vigilantes`, `/consultas/asaltantes`, etc.
- **Funcionalidad**: Lista y muestra detalles de todas las entidades

**`CrearEntidadController`**
- **Responsabilidad**: Gestiona la creación de nuevas entidades
- **Endpoints**: `/crear-entidad/vigilante`, `/crear-entidad/juez`, etc.
- **Funcionalidad**: Muestra formularios y procesa la creación de entidades

**`EditarController`**
- **Responsabilidad**: Gestiona la edición de entidades existentes
- **Funcionalidad**: Permite modificar datos de entidades (excepto códigos)

**`ContratoController`**
- **Responsabilidad**: Gestiona la creación de contratos entre vigilantes y sucursales
- **Validaciones**: Fechas de inicio y fin, portación de arma

**`DetenidoController`**
- **Responsabilidad**: Registra asaltos y detenidos
- **Validaciones**: Fecha del asalto no puede ser futura

**`CasoController`**
- **Responsabilidad**: Gestiona la apertura de casos judiciales
- **Funcionalidad**: Asocia asaltos con jueces y crea condenas

#### **Servicios Principales**

**`UsuarioService`**
- **Responsabilidad**: Lógica de negocio para usuarios
- **Funcionalidades**:
    - Crear, actualizar y eliminar usuarios
    - Preservar relaciones con vigilantes
    - Validar nombres de usuario únicos

**`VigilanteService`**
- **Responsabilidad**: Gestión de vigilantes
- **Funcionalidades**:
    - Generación automática de códigos (VIG-001, VIG-002, etc.)
    - Validación de datos
    - Inmutabilidad del código en ediciones

**`CodigoGeneratorService`**
- **Responsabilidad**: Genera códigos únicos autoincrementables
- **Entidades**: Vigilantes, Jueces, Entidades Bancarias, Sucursales, Asaltantes
- **Formato**: Prefijo + número secuencial (ej: VIG-001, JUEZ-001)

**`ContratoSucVigService`**
- **Responsabilidad**: Lógica de contratos
- **Validaciones**:
    - Fecha de inicio no puede ser pasada
    - Fecha de fin debe ser posterior a fecha de inicio

**`AsaltoService`**
- **Responsabilidad**: Gestión de asaltos
- **Validaciones**: Fecha del asalto no puede ser futura

#### **Utilidades**

**`PermisoUtil`**
- **Responsabilidad**: Utilidades para verificación de permisos
- **Métodos**: `tienePermiso()`, `getUsuarioActual()`

**`ErrorHandlerUtil`**
- **Responsabilidad**: Convierte errores técnicos en mensajes amigables
- **Funcionalidad**: Parseo de excepciones de base de datos a mensajes legibles

#### **Configuración**

**`DataInitializer`**
- **Responsabilidad**: Inicializa datos básicos al arrancar la aplicación
- **Funcionalidad**: Crea roles, permisos y usuarios por defecto

**`AuthInterceptor`**
- **Responsabilidad**: Intercepta peticiones para validar autenticación
- **Funcionalidad**: Redirige a login si el usuario no está autenticado

### 2.5. Capturas o Ejemplos de Código Relevantes

#### **Ejemplo 1: Entidad con Relaciones JPA**

```java
@Entity
@Table(name = "vigilantes")
public class Vigilante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombreApellido;
    
    @OneToOne(mappedBy = "vigilante")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "vigilante", cascade = CascadeType.ALL)
    private List<ContratoSucVig> contratos = new ArrayList<>();
    
    // Getters y Setters
}
```

#### **Ejemplo 2: Controlador con Validación de Permisos**

```java
@Controller
@RequestMapping("/contratos")
public class ContratoController {
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        if (!PermisoUtil.tienePermiso(session, Permiso.CONTRATAR_VIGILANTE)) {
            return "redirect:/";
        }
        // Lógica del método
    }
}
```

#### **Ejemplo 3: Servicio con Lógica de Negocio**

```java
@Service
@Transactional
public class VigilanteService {
    
    public Vigilante guardar(Vigilante vigilante) {
        // Generar código automáticamente si no está presente
        if (vigilante.getCodigo() == null || vigilante.getCodigo().trim().isEmpty()) {
            vigilante.setCodigo(codigoGeneratorService.generarCodigoVigilante());
        }
        return vigilanteRepository.save(vigilante);
    }
}
```

#### **Ejemplo 4: Vista Thymeleaf con Formulario**

```html
<form th:action="@{/crear-entidad/vigilante}" th:object="${vigilante}" method="post">
    <div>
        <label for="nombreApellido">Nombre y Apellido:</label>
        <input type="text" id="nombreApellido" th:field="*{nombreApellido}" required>
    </div>
    <div>
        <label for="edad">Edad:</label>
        <input type="number" id="edad" th:field="*{edad}" required>
    </div>
    <button type="submit">Crear Vigilante</button>
</form>
```

---

## 3. CASOS DE USO Y DIAGRAMAS

### 3.1. Diagrama de Casos de Uso con los Actores Involucrados

El sistema cuenta con tres actores principales: **Administrador**, **Vigilante** e **Investigador**. Todos los actores pueden iniciar y cerrar sesión. El Administrador tiene acceso completo a todas las funcionalidades del sistema (gestión de usuarios, creación y edición de entidades, contratos, registro de asaltos, apertura de casos judiciales y consultas). El Vigilante solo puede consultar sus propios datos y contratos. El Investigador tiene acceso de solo lectura a todas las entidades del sistema para realizar consultas.

### 3.2. Breve Descripción de Cada Caso de Uso

#### **Casos de Uso Comunes**

**CU-01: Iniciar Sesión**
- **Actor**: Todos los usuarios
- **Descripción**: El usuario ingresa sus credenciales (nombre de usuario y contraseña) para acceder al sistema
- **Precondiciones**: El usuario debe estar registrado en el sistema
- **Flujo principal**:
    1. Usuario accede a la página de login
    2. Ingresa nombre de usuario y contraseña
    3. Sistema valida credenciales
    4. Sistema crea sesión y redirige según el rol

**CU-02: Cerrar Sesión**
- **Actor**: Todos los usuarios
- **Descripción**: El usuario cierra su sesión activa
- **Flujo principal**: Usuario hace clic en "Cerrar Sesión" y el sistema invalida la sesión

#### **Casos de Uso del Administrador**

**CU-03: Gestionar Usuarios**
- **Actor**: Administrador
- **Descripción**: Crear, editar y eliminar usuarios del sistema
- **Flujo principal**:
    1. Administrador accede al menú de gestión de usuarios
    2. Puede crear nuevos usuarios asignando roles
    3. Puede editar usuarios existentes
    4. Puede eliminar usuarios (excepto si tienen relaciones activas)

**CU-04: Crear Entidades**
- **Actor**: Administrador
- **Descripción**: Crear nuevas entidades (vigilantes, jueces, entidades bancarias, sucursales, asaltantes, bandas criminales)
- **Flujo principal**:
    1. Administrador selecciona el tipo de entidad a crear
    2. Completa el formulario con los datos requeridos
    3. El sistema genera automáticamente el código único
    4. Sistema valida y guarda la entidad

**CU-05: Editar Entidades**
- **Actor**: Administrador
- **Descripción**: Modificar datos de entidades existentes (excepto códigos)
- **Flujo principal**:
    1. Administrador selecciona la entidad a editar
    2. Modifica los campos permitidos
    3. Sistema valida y actualiza la información

**CU-06: Crear Contrato**
- **Actor**: Administrador
- **Descripción**: Crear un contrato entre un vigilante y una sucursal bancaria
- **Flujo principal**:
    1. Administrador selecciona sucursal y vigilante
    2. Define fechas de inicio y fin
    3. Indica si el vigilante portará arma
    4. Sistema valida fechas y crea el contrato

**CU-07: Registrar Asalto/Detenido**
- **Actor**: Administrador
- **Descripción**: Registrar un asalto ocurrido en una sucursal
- **Flujo principal**:
    1. Administrador selecciona asaltante y sucursal
    2. Ingresa la fecha del asalto (no puede ser futura)
    3. Opcionalmente asocia una banda criminal
    4. Sistema registra el asalto

**CU-08: Abrir Caso Judicial**
- **Actor**: Administrador
- **Descripción**: Crear un caso judicial asociado a un asalto
- **Flujo principal**:
    1. Administrador selecciona un asalto
    2. Asigna un juez
    3. Indica si hubo condena y tiempo de condena
    4. Sistema crea el caso y la condena asociada

**CU-09: Consultar Entidades**
- **Actor**: Administrador, Investigador
- **Descripción**: Visualizar información de todas las entidades del sistema
- **Flujo principal**:
    1. Usuario accede al menú de consultas
    2. Selecciona el tipo de entidad a consultar
    3. Sistema muestra lista con opción de ver detalles

#### **Casos de Uso del Vigilante**

**CU-10: Consultar Mis Datos**
- **Actor**: Vigilante
- **Descripción**: El vigilante consulta su información personal y contratos
- **Flujo principal**:
    1. Vigilante accede a "Mis Datos"
    2. Sistema muestra información personal
    3. Sistema muestra lista de contratos asociados

#### **Casos de Uso del Investigador**

**CU-11: Consultar Información del Sistema**
- **Actor**: Investigador
- **Descripción**: El investigador puede consultar toda la información pero no modificarla
- **Flujo principal**: Similar a CU-09 pero sin opciones de edición

---

## 4. BASE DE DATOS

### 4.1. Diagrama Entidad-Relación

La base de datos está compuesta por 13 tablas principales relacionadas entre sí. Las relaciones principales son: Roles se relaciona con Usuarios (1:N) y con Permisos (N:M a través de rol_permisos). Usuarios tiene una relación opcional 1:1 con Vigilantes. Vigilantes se relaciona con Contratos (1:N), y estos últimos se relacionan con Sucursales (N:1). Sucursales pertenece a Entidades Bancarias (N:1). Asaltantes se relaciona con Bandas Criminales (N:1) y con Asaltos y Condenas (1:N cada una). Asaltos se relaciona con Sucursales, Asaltantes y Bandas Criminales (N:1 cada una), y con Condenas (1:1). Condenas se relaciona con Casos (1:1), y estos últimos con Jueces (N:1).

### 4.2. Descripción de las Tablas y Relaciones

#### **Tabla: roles**
- **Descripción**: Almacena los roles del sistema (Administrador, Vigilante, Investigador)
- **Campos principales**: `id`, `nombre`
- **Relaciones**:
    - 1:N con `usuarios`
    - N:M con `permisos` (tabla intermedia `rol_permisos`)

#### **Tabla: usuarios**
- **Descripción**: Almacena los usuarios del sistema con sus credenciales
- **Campos principales**: `id`, `nombre_usuario`, `password`, `rol_id`, `vigilante_id`
- **Relaciones**:
    - N:1 con `roles`
    - 1:1 con `vigilantes` (opcional)

#### **Tabla: vigilantes**
- **Descripción**: Almacena información de los vigilantes
- **Campos principales**: `id`, `codigo`, `nombre_apellido`, `edad`
- **Relaciones**:
    - 1:1 con `usuarios`
    - 1:N con `contratos_suc_vig`

#### **Tabla: entidades_bancarias**
- **Descripción**: Almacena información de entidades bancarias
- **Campos principales**: `id`, `codigo`, `nombre`, `domicilio`
- **Relaciones**: 1:N con `sucursales`

#### **Tabla: sucursales**
- **Descripción**: Almacena información de sucursales bancarias
- **Campos principales**: `id`, `codigo`, `nombre`, `domicilio`, `cantidad_de_empleados`, `entidad_bancaria_id`
- **Relaciones**:
    - N:1 con `entidades_bancarias`
    - 1:N con `contratos_suc_vig`
    - 1:N con `asaltos`

#### **Tabla: contratos_suc_vig**
- **Descripción**: Almacena los contratos entre sucursales y vigilantes
- **Campos principales**: `id`, `fecha_inicio`, `fecha_fin`, `sucursal_id`, `vigilante_id`, `portar_arma`
- **Relaciones**:
    - N:1 con `sucursales`
    - N:1 con `vigilantes`

#### **Tabla: bandas_criminales**
- **Descripción**: Almacena información de bandas criminales
- **Campos principales**: `id`, `numero_identificacion`, `cantidad_de_miembros`
- **Relaciones**: 1:N con `asaltantes` y `asaltos`

#### **Tabla: asaltantes**
- **Descripción**: Almacena información de asaltantes
- **Campos principales**: `id`, `codigo`, `nombre_apellido`, `banda_criminal_id`
- **Relaciones**:
    - N:1 con `bandas_criminales`
    - 1:N con `asaltos`
    - 1:N con `condenas`

#### **Tabla: asaltos**
- **Descripción**: Almacena registro de asaltos ocurridos
- **Campos principales**: `id`, `fecha`, `asaltante_id`, `sucursal_id`, `banda_criminal_id`, `condenado`
- **Relaciones**:
    - N:1 con `asaltantes`
    - N:1 con `sucursales`
    - N:1 con `bandas_criminales`
    - 1:1 con `condenas`

#### **Tabla: jueces**
- **Descripción**: Almacena información de jueces
- **Campos principales**: `id`, `clave_interna_juzgado`, `nombre_apellido`, `anios_de_servicio`
- **Relaciones**: 1:N con `casos`

#### **Tabla: condenas**
- **Descripción**: Almacena información de condenas judiciales
- **Campos principales**: `id`, `asaltante_id`, `asalto_id`, `fue_condenado`, `tiempo_condena`
- **Relaciones**:
    - N:1 con `asaltantes`
    - N:1 con `asaltos`
    - 1:1 con `casos`

#### **Tabla: casos**
- **Descripción**: Almacena casos judiciales
- **Campos principales**: `id`, `juez_id`, `condena_id`
- **Relaciones**:
    - N:1 con `jueces`
    - 1:1 con `condenas`

### 4.3. Ejemplo de Datos Cargados

El sistema incluye datos iniciales que se cargan automáticamente:

**Roles:**
- Administrador (con todos los permisos)
- Vigilante (solo consultar sus datos)
- Investigador (consultar todas las entidades)

**Usuarios por defecto:**
- `admin` / `1234` (Administrador)
- `vigilante` / `1234` (Vigilante, asociado a Juan Pérez VIG-001)
- `investigador` / `1234` (Investigador)

**Ejemplo de datos de prueba:**
- 3 Jueces
- 3 Entidades Bancarias
- 4 Sucursales
- 5 Vigilantes
- 4 Bandas Criminales
- 6 Asaltantes
- 4 Contratos
- 5 Asaltos
- 5 Condenas
- 1 Caso Judicial

---

## 5. MANUAL DE USUARIO

### 5.1. Cómo Acceder al Sistema

1. **Iniciar la aplicación**: Ejecutar la aplicación Spring Boot (puerto 8085)
2. **Acceder al login**: Abrir el navegador en `http://localhost:8085/login`
3. **Ingresar credenciales**:
    - Usuario: `admin`, `vigilante` o `investigador`
    - Contraseña: `1234`
4. **Acceder al sistema**: El sistema redirige automáticamente según el rol del usuario

### 5.2. Funcionalidades por Usuario

#### **5.2.1. Administrador**

**Menú Principal:**
- Gestión de Usuarios
- Crear Entidad
- Editar Datos
- Contratar Vigilante
- Cargar Detenido
- Abrir Caso
- Consultas
- Cerrar Sesión

**Gestión de Usuarios:**
1. Acceder a "Gestión" → "Usuarios"
2. Ver lista de usuarios
3. Crear nuevo usuario: Clic en "Crear Usuario", completar formulario, seleccionar rol
4. Editar usuario: Clic en "Editar", modificar datos
5. Eliminar usuario: Clic en "Eliminar" (solo si no tiene relaciones activas)

**Crear Entidad:**
1. Acceder a "Crear Entidad"
2. Seleccionar tipo: Vigilante, Juez, Entidad Bancaria, Sucursal, Asaltante, Banda Criminal
3. Completar formulario (el código se genera automáticamente)
4. Guardar

**Editar Datos:**
1. Acceder a "Editar Datos"
2. Seleccionar tipo de entidad
3. Seleccionar entidad a editar
4. Modificar campos (código no se puede modificar)
5. Guardar cambios

**Contratar Vigilante:**
1. Acceder a "Contratar Vigilante"
2. Seleccionar Sucursal
3. Seleccionar Vigilante
4. Ingresar fecha de inicio (no puede ser pasada)
5. Ingresar fecha de fin (debe ser posterior a inicio)
6. Marcar si porta arma
7. Crear contrato

**Cargar Detenido:**
1. Acceder a "Cargar Detenido"
2. Seleccionar Asaltante
3. Seleccionar Sucursal
4. Ingresar fecha del asalto (no puede ser futura)
5. Opcionalmente seleccionar Banda Criminal
6. Registrar asalto

**Abrir Caso:**
1. Acceder a "Abrir Caso"
2. Seleccionar Asalto
3. Seleccionar Juez
4. Indicar si fue condenado
5. Si fue condenado, ingresar tiempo de condena (años)
6. Abrir caso

**Consultas:**
- Acceder a "Consultas" para ver todas las entidades
- Ver listas y detalles de: Jueces, Vigilantes, Asaltantes, Casos, Contratos, Sucursales, etc.

#### **5.2.2. Vigilante**

**Menú Principal:**
- Mis Datos
- Cerrar Sesión

**Consultar Mis Datos:**
1. Acceder a "Mis Datos"
2. Ver información personal (código, nombre, edad)
3. Ver lista de contratos asociados
4. Ver detalles de cada contrato (sucursal, fechas, portación de arma)

**Limitaciones:**
- No puede modificar sus datos
- No puede ver información de otros vigilantes
- No puede acceder a otras funcionalidades del sistema

#### **5.2.3. Investigador**

**Menú Principal:**
- Consultas
- Cerrar Sesión

**Consultar Información:**
1. Acceder a "Consultas"
2. Seleccionar tipo de entidad a consultar:
    - Jueces
    - Vigilantes
    - Asaltantes
    - Casos Judiciales
    - Contratos
    - Sucursales
    - Entidades Bancarias
    - Bandas Criminales
3. Ver lista de entidades
4. Ver detalles de cada entidad haciendo clic en ella

**Limitaciones:**
- Solo lectura: no puede crear, editar ni eliminar información
- No puede acceder a gestión de usuarios
- No puede crear contratos, asaltos o casos

### 5.3. Ejemplos o Capturas de Pantalla de las Pantallas Principales

#### **Pantalla de Login**
- Formulario con campos: Nombre de Usuario y Contraseña
- Botón "Iniciar Sesión"
- Mensajes de error si las credenciales son incorrectas

#### **Pantalla Principal (Home)**
- Menú de navegación según el rol
- Mensaje de bienvenida con nombre de usuario
- Opciones disponibles según permisos

#### **Pantalla de Consultas**
- Lista de entidades con información resumida
- Botones para ver detalles
- Filtros y búsqueda (si están implementados)

#### **Pantalla de Crear Entidad**
- Formulario con campos específicos según el tipo de entidad
- Validaciones en tiempo real
- Mensaje informativo sobre generación automática de códigos
- Botón "Crear" o "Guardar"

#### **Pantalla de Editar**
- Formulario pre-cargado con datos existentes
- Campos de código en modo solo lectura
- Botón "Guardar Cambios"

#### **Pantalla Mis Datos (Vigilante)**
- Información personal del vigilante
- Lista de contratos con detalles
- Diseño claro y fácil de leer

---

## CONCLUSIÓN

Este documento presenta una evaluación completa del Sistema de Gestión Policial Federal, incluyendo su arquitectura, funcionalidades, base de datos y manual de usuario. El sistema está diseñado para facilitar la gestión de información policial con un control de acceso basado en roles, asegurando que cada usuario solo acceda a las funcionalidades correspondientes a su nivel de autorización.

El proyecto demuestra el uso adecuado de tecnologías modernas como Spring Boot, JPA/Hibernate y Thymeleaf, siguiendo las mejores prácticas de desarrollo de software y arquitectura MVC.

---

**Fin del Documento**

