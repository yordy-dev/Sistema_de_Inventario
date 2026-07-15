# Sistema de Gestión de Inventario

Este proyecto consiste en el desarrollo de un **Sistema de Gestión de Inventario** robusto, modular y altamente mantenible en Java. Ha sido diseñado e implementado aplicando rigurosamente **Patrones de Diseño de Software (Creacionales, Estructurales y de Comportamiento)**, principios **SOLID**, y lineamientos **GRASP** con el objetivo de obtener un software desacoplado y escalable.

---

## 📝 Información General

*   **Institución:** Universidad Tecnológica del Perú (UTP)
*   **Facultad:** Facultad de Ingeniería
*   **Carrera:** Ingeniería de Sistemas e Informática
*   **Curso:** Diseño de Patrones
*   **Docente:** Franz Mendoza
*   **Año:** 2026

### 👥 Integrantes
*   Flores Mamani Edu E.
*   Jean Carlos Lopez Suazo
*   Gustavo Calderon Molina
*   Almerco Solis Yordy

---

## 🚀 Características del Sistema

El sistema implementa las siguientes funcionalidades clave (Requerimientos Funcionales):

*   **Autenticación de Usuarios:** Control de accesos seguro con distinción de roles (Administrador y Almacenero).
*   **Gestión de Productos:** CRUD completo de productos (Registrar, Editar, Activar/Desactivar, Buscar).
*   **Gestión de Categorías:** Organización de productos por categorías.
*   **Control de Stock Automatizado:** Registro de entradas y salidas de inventario, actualizando el stock disponible en tiempo real.
*   **Sistema de Alertas:** Notificación automática en consola y en la interfaz gráfica cuando el stock de un producto cae por debajo de su límite mínimo preestablecido.
*   **Reportes de Inventario:** Consultas detalladas de movimientos (entradas/salidas) y estados de stock.

---

## 📐 Patrones de Diseño Aplicados

Para resolver problemas comunes de acoplamiento, extensibilidad y responsabilidades en el desarrollo de software, se han implementado los siguientes patrones de diseño:

### 1. Patrones Creacionales
*   **Singleton:** Garantiza una única instancia global para clases clave como `Inventario`, `InventarioFacade`, `NotificacionService` y los repositorios de datos en memoria (`ProductoRepositoryMemoria`, etc.). Esto previene inconsistencias y duplicidades en los datos de stock.
*   **Factory Method:** Implementado a través de `ProductoFactory` para la creación desacoplada de objetos `Producto`. Permite extender y diversificar la lógica de creación sin alterar el código de negocio principal.

### 2. Patrones Estructurales
*   **Facade (Fachada):** La clase `InventarioFacade` actúa como una interfaz unificada y simplificada para la capa de presentación (GUI en Swing), ocultando la complejidad del subsistema de lógica e inventario.
*   **Proxy:** La clase `SecurityProxy` actúa como intermediario de control de acceso para `InventarioFacade`. Valida los privilegios del usuario activo (por ejemplo, permitiendo operaciones de escritura/modificación únicamente al rol de **Administrador**) antes de delegar la ejecución a la fachada.

### 3. Patrones de Comportamiento
*   **Observer:** Implementado mediante `INotificacion`, `IStockObserver` y `NotificacionService`. El formulario del menú principal (`MenuPrincipalForm`) se registra como observador para ser notificado de inmediato cuando ocurre un movimiento en el inventario o cuando el stock de un producto alcanza niveles críticos de escasez.
*   **Command:** Encapsula las solicitudes y operaciones del sistema como objetos independientes (`RegistrarProductoCommand`, `RegistrarEntradaCommand`, `RegistrarSalidaCommand`), lo que facilita la escalabilidad de las acciones realizables, la cola de comandos y el desacoplamiento de los emisores y receptores.

---

## 🛠️ Principios de Diseño y Buenas Prácticas

### Principios SOLID
*   **Responsabilidad Única (SRP):** Cada clase tiene un único propósito. Por ejemplo, `Producto` solo almacena datos del artículo, `Inventario` administra la lógica y existencias, y `NotificacionService` coordina las alertas.
*   **Abierto/Cerrado (OCP):** Nuevos tipos de productos o flujos se pueden extender sin necesidad de modificar el núcleo de la lógica existente, apoyándose en interfaces y fábricas.
*   **Sustitución de Liskov (LSP):** La herencia en los roles (`Administrador` y `Almacenero` heredando de `Usuario`) asegura que cualquier tipo de usuario pueda operar de forma transparente en los procesos comunes del sistema.
*   **Segregación de Interfaces (ISP):** Interfaces enfocadas y específicas (`IProductoRepository`, `INotificacion`, `IStockObserver`) evitan obligar a las clases a implementar métodos que no utilizan.
*   **Inversión de Dependencias (DIP):** Las clases de negocio de alto nivel interactúan con abstracciones (interfaces de repositorios) en lugar de clases concretas, facilitando el cambio futuro de persistencia (ej. de en-memoria a una base de datos real).

### Principios GRASP
*   **Experto en Información:** Las responsabilidades de cálculo y manejo se asignan a quienes tienen la información (ej. `Producto` conoce su propio stock y verifica si es menor al mínimo).
*   **Alta Cohesión y Bajo Acoplamiento:** Módulos bien definidos e independientes conectados a través de interfaces y proxies.
*   **Controlador:** Uso de clases intermedias y comandos para mediar entre los eventos de interfaz de usuario y las entidades del modelo.

### Antipatrones Evitados
*   *God Object (Clase Dios)*: Evitado distribuyendo de forma equitativa el comportamiento en módulos especializados.
*   *Código Duplicado*: Reutilización de métodos comunes de repositorio y centralización de lógicas de alerta.

---

## 📂 Estructura del Proyecto

A continuación se muestra la estructura de paquetes y clases principales del proyecto:

```text
src/
├── conexion/            # (Reservado para conexiones a Base de Datos)
├── modelos/             # Entidades del dominio de negocio
│   ├── Usuario.java
│   ├── Administrador.java
│   ├── Almacenero.java
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Inventario.java (Singleton & Sujeto de Notificaciones)
│   ├── Entrada.java
│   ├── DetalleEntrada.java
│   ├── Salida.java
│   └── DetalleSalida.java
├── patrones/            # Implementación de Patrones de Diseño
│   ├── command/
│   │   ├── Command.java
│   │   ├── RegistrarProductoCommand.java
│   │   ├── RegistrarEntradaCommand.java
│   │   └── RegistrarSalidaCommand.java
│   ├── facade/
│   │   └── InventarioFacade.java
│   ├── factory/
│   │   └── ProductoFactory.java
│   ├── observer/
│   │   ├── INotificacion.java
│   │   ├── IStockObserver.java
│   │   └── NotificacionService.java
│   └── proxy/
│       └── SecurityProxy.java
├── repositorios/        # Interfaces y Persistencia
│   ├── ICategoriaRepository.java
│   ├── IMovimientoRepository.java
│   ├── IProductoRepository.java
│   ├── IUsuarioRepository.java
│   └── memoria/         # Implementación de repositorios en memoria (Mock)
│       ├── CategoriaRepositoryMemoria.java
│       ├── MovimientoRepositoryMemoria.java
│       ├── ProductoRepositoryMemoria.java
│       └── UsuarioRepositoryMemoria.java
└── vistas/              # Interfaz Gráfica de Usuario (Java Swing)
    ├── LoginForm.java (Punto de entrada de la aplicación)
    └── MenuPrincipalForm.java
```

---

## 🖼️ Diagramas del Sistema

A continuación se presentan los diagramas de arquitectura del sistema.

### 1. Diagrama de Clases UML
Muestra la jerarquía de usuarios, comandos, fachada, fábrica y la relación de observadores.

<img width="778" height="585" alt="image" src="https://github.com/user-attachments/assets/e103f5c6-fa71-4b69-a86d-d98356584d0f" />



### 2. Diagrama de Secuencia
Ilustra el flujo de registro de una salida de stock y la subsiguiente alerta automática de stock bajo.

<img width="862" height="564" alt="image" src="https://github.com/user-attachments/assets/3b9471f7-f7e5-4877-847f-0966e8d0bc5a" />


### 3. Diagrama de Componentes
Muestra las capas físicas del sistema: Presentación, Lógica de Negocio (Commands, Facade, Factory, Observers) y Persistencia (Repositories).

<img width="900" height="455" alt="image" src="https://github.com/user-attachments/assets/75a6dd7b-6b77-41bd-8757-e83bcc6bdd61" />


---

## ⚙️ Instalación y Ejecución

### Requisitos Previos
*   **Java Development Kit (JDK):** Versión 8 o superior.
*   **IDE Recomendado:** NetBeans IDE (8.x o superior / Apache NetBeans), Eclipse, o IntelliJ IDEA con soporte para proyectos Ant.

### Pasos para Ejecutar
1.  Clona este repositorio o descarga el código fuente:
    ```bash
    git clone https://github.com/tu-usuario/Sistema_de_Inventario.git
    ```
2.  Abre el proyecto en tu IDE favorito de Java (el proyecto cuenta con estructura compatible de **NetBeans IDE** a través del archivo `build.xml`).
3.  Limpia y construye el proyecto (*Clean and Build*).
4.  Ejecuta la clase principal ubicada en `vistas.LoginForm` para iniciar la aplicación.

### 🔑 Credenciales de Acceso (Por Defecto)
El repositorio de usuarios en memoria incluye dos cuentas iniciales para pruebas rápidas:

| Usuario | Contraseña | Rol | Acceso / Privilegios |
| :--- | :--- | :--- | :--- |
| **admin** | `123456` | Administrador | Acceso total (CRUD de productos, categorías, entradas y salidas). |
| **juan** | `123` | Almacenero | Acceso limitado (Consulta de existencias, registro de entradas y salidas de stock). |

---

## 📦 Tecnologías Utilizadas
*   **Lenguaje:** Java SE (Standard Edition)
*   **Interfaz Gráfica:** Java Swing (Nimbus Look and Feel)
*   **Gestor de Construcción:** Apache Ant
*   **Persistencia:** In-Memory Cache (diseñado mediante interfaces listo para integrarse a bases de datos relacionales).
