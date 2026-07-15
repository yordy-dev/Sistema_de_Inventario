# "Año de la Esperanza y el Fortalecimiento de la Democracia"

## UNIVERSIDAD TECNOLÓGICA DEL PERÚ

**Facultad de Ingeniería**

Carrera de Ingeniería de Sistemas e informática
Manual de Usuario
**"Sistema de Gestión de Inventario"**

**Curso:**
DISEÑO DE PATRONES

**Integrantes:**
- Flores Mamani Edu E
- Jean Carlos Lopez Suazo
- Gustavo Calderon Molina
- Almerco Solis Yordy

**Docente:**
Franz Mendoza

LIMA - ATE
**2026**

---

## INTRODUCCIÓN

Actualmente, muchas pequeñas y medianas empresas presentan dificultades en el control de sus productos debido al uso de registros manuales o sistemas poco organizados. Esto puede ocasionar errores en el conteo de stock, pérdida de información, duplicidad de registros y retrasos en la gestión de productos, afectando directamente la productividad y las ganancias del negocio.

Frente a esta problemática, el presente proyecto propone el desarrollo de un sistema de gestión de inventario utilizando el lenguaje Java y programación orientada a objetos. El sistema permitirá registrar productos, controlar entradas y salidas, actualizar existencias, realizar búsquedas y generar alertas automáticas cuando el stock de un producto sea bajo.

El proyecto fue elegido debido a que los sistemas de inventario representan una necesidad común dentro de las empresas y permiten aplicar de manera práctica distintos conceptos de ingeniería de software. Además, este tipo de sistema requiere una estructura organizada, escalable y fácil de mantener, por lo que resulta adecuado para implementar patrones de diseño y principios de arquitectura de software.

Durante el desarrollo se aplicarán patrones creacionales, estructurales y de comportamiento, junto con principios SOLID y GRASP, con el objetivo de reducir el acoplamiento entre clases, mejorar la reutilización del código y facilitar futuras ampliaciones del sistema.

Por ejemplo, el patrón Factory Method permitirá crear distintos tipos de productos sin modificar el código principal; el patrón Observer facilitará el envío automático de alertas de stock bajo; y el patrón Facade simplificará el acceso a las funciones principales del sistema. De esta manera, la implementación de patrones de diseño contribuirá a obtener un software más organizado, flexible y mantenible.

---

## 3. OBJETIVOS

### Objetivo General

Desarrollar un sistema de gestión de inventario en Java capaz de registrar productos, controlar existencias y generar alertas automáticas de stock bajo, aplicando patrones de diseño, principios SOLID y GRASP para obtener un software escalable, reutilizable y fácil de mantener.

### Objetivos Específicos

- Aplicar patrones de diseño creacionales, estructurales y de comportamiento dentro de la arquitectura del sistema.
- Implementar el principio SRP separando las responsabilidades de gestión de productos, control de inventario y notificaciones.
- Aplicar el principio OCP permitiendo agregar nuevos tipos de productos sin modificar las clases existentes.
- Utilizar el principio DIP mediante interfaces para reducir la dependencia entre módulos del sistema.
- Aplicar principios GRASP para disminuir el acoplamiento y mejorar la asignación de responsabilidades entre clases.
- Diseñar diagramas UML para representar la estructura y funcionamiento del sistema.
- Desarrollar un sistema orientado a objetos funcional, modular y escalable.
- Implementar alertas automáticas que notifiquen cuando el stock de un producto alcance niveles mínimos.

---

## 4. FUNDAMENTACIÓN TEÓRICA

### 4.1 Principios de Diseño Aplicados

#### Principio de Responsabilidad Única (SRP)

Este principio establece que una clase debe tener una sola responsabilidad dentro del sistema.

En el proyecto de gestión de inventario, este principio se aplicará mediante la separación de funciones específicas en distintas clases:

- La clase `Producto` almacenará únicamente la información relacionada con los productos.
- La clase `Inventario` administrará el stock y el registro de productos.
- La clase `NotificacionService` será responsable del envío de alertas de stock bajo.

La aplicación de este principio permitirá que el sistema sea más ordenado, mantenible y fácil de modificar sin afectar otras partes del software.

#### Principio Abierto/Cerrado (OCP)

Este principio indica que las clases deben estar abiertas para extensión, pero cerradas para modificación.

En el sistema, este principio se aplicará permitiendo agregar nuevos tipos de productos sin alterar las clases ya existentes. Por ejemplo:

- Productos electrónicos
- Productos alimenticios
- Productos de oficina

Estos podrán ser creados mediante herencia y el uso del patrón Factory Method. Gracias a ello, el sistema podrá crecer y adaptarse a nuevos requerimientos sin modificar el funcionamiento principal del software.

#### Principio de Sustitución de Liskov (LSP)

Este principio establece que las clases hijas deben poder reemplazar a sus clases padre sin alterar el comportamiento del sistema.

En el proyecto, los distintos tipos de productos heredarán de una clase base `Producto`, permitiendo que cualquier producto específico pueda utilizarse correctamente dentro del inventario sin generar errores o comportamientos inesperados.

Esto garantizará una estructura más estable y reutilizable.

#### Principio de Segregación de Interfaces (ISP)

Este principio establece que una clase no debe depender de métodos que no utiliza.

En el sistema se crearán interfaces específicas para cada funcionalidad, evitando interfaces demasiado grandes o generales.

Por ejemplo:

- Una interfaz para gestión de productos.
- Una interfaz para notificaciones.
- Una interfaz para generación de reportes.

Esto permitirá mantener un diseño más limpio, flexible y fácil de mantener.

#### Principio de Inversión de Dependencias (DIP)

Este principio establece que las clases deben depender de abstracciones y no de implementaciones concretas.

En el proyecto, módulos como las notificaciones o el almacenamiento de productos trabajarán mediante interfaces en lugar de depender directamente de clases específicas.

Por ejemplo:

- `INotificacion`
- `IProductoRepository`

De esta forma, será posible cambiar implementaciones del sistema sin afectar el resto de la aplicación, reduciendo el acoplamiento y mejorando la flexibilidad del software.

### 4.2 Patrones de Diseño Utilizados

#### Patrones Creacionales

**Patrón Singleton**

El patrón Singleton se utilizará para garantizar que exista una única instancia encargada de gestionar el inventario dentro del sistema.

De esta manera, todas las operaciones relacionadas con productos, entradas y salidas trabajarán sobre una misma fuente de información, evitando inconsistencias y duplicidad de datos.

Se eligió este patrón porque el sistema requiere mantener un control centralizado de las existencias. Si existieran múltiples instancias del inventario, podrían generarse diferencias en la información almacenada y afectar la confiabilidad del sistema.

*Ventajas*
- Centraliza la gestión del inventario.
- Evita inconsistencias en los datos.
- Facilita el acceso a la información.
- Reduce errores por múltiples instancias.

**Patrón Factory Method**

El patrón Factory Method se utilizará para crear diferentes objetos del sistema de manera flexible y desacoplada.

Por ejemplo, podrá emplearse para la creación de distintos tipos de usuarios, como Administrador o Almacenero, permitiendo que la lógica de creación se mantenga separada de la lógica de negocio.

Este patrón facilita la incorporación de nuevos tipos de usuarios o entidades sin necesidad de modificar el código existente, favoreciendo la escalabilidad del sistema.

*Ventajas*
- Facilita la creación de objetos.
- Reduce dependencias con clases concretas.
- Favorece la extensibilidad.
- Cumple con el principio Abierto/Cerrado (OCP).

#### Patrones Estructurales

**Patrón Facade**

El patrón Facade se utilizará para proporcionar una interfaz simplificada que permita acceder a las principales funcionalidades del sistema.

A través de esta fachada, el usuario podrá realizar operaciones como:

- Gestionar productos.
- Gestionar categorías.
- Registrar entradas de inventario.
- Registrar salidas de inventario.
- Generar reportes.

El sistema estará compuesto por varios módulos. La fachada permitirá ocultar la complejidad interna y ofrecer un punto único de acceso para las funcionalidades principales.

*Ventajas*
- Simplifica la interacción con el sistema.
- Reduce la complejidad.
- Mejora la organización del software.
- Disminuye el acoplamiento entre componentes.

#### Patrones de Comportamiento

**Patrón Observer**

El patrón Observer será utilizado para gestionar las alertas automáticas de stock bajo.

Cuando una salida de inventario provoque que la cantidad disponible de un producto alcance el nivel mínimo establecido, el sistema enviará automáticamente una notificación al responsable del inventario.

En esta implementación, la clase `Inventario` actuará como sujeto (Subject) y el servicio de notificaciones actuará como observador (Observer).

Se eligió este patrón porque permite que las notificaciones se generen automáticamente cada vez que ocurra un cambio importante en el stock, sin que el módulo de inventario dependa directamente del módulo de notificaciones.

*Ventajas*
- Automatiza las alertas de inventario.
- Reduce la supervisión manual.
- Facilita la comunicación entre módulos.
- Disminuye el acoplamiento entre clases.

**Patrón Command**

El patrón Command se utilizará para encapsular las acciones principales del sistema como objetos independientes.

Entre las operaciones que podrán implementarse mediante comandos se encuentran:

- Registrar producto.
- Editar producto.
- Eliminar producto.
- Registrar entrada de inventario.
- Registrar salida de inventario.
- Generar reportes.

Cada acción será tratada como un comando independiente, permitiendo una mejor organización de las operaciones del sistema.

Se seleccionó este patrón porque facilita la gestión de las acciones ejecutadas por el usuario y permite incorporar nuevas operaciones sin afectar las ya existentes.

*Ventajas*
- Organiza las operaciones del sistema.
- Facilita la incorporación de nuevas funcionalidades.
- Reduce dependencias entre módulos.
- Mejora la mantenibilidad del software.

### Aplicación de Principios GRASP

**Experto en Información (Information Expert)**

Este principio establece que una responsabilidad debe asignarse a la clase que posee la información necesaria para cumplirla.

En el sistema de gestión de inventario:

- La clase `Producto` administrará la información de los productos.
- La clase `Categoría` administrará la información de las categorías.
- La clase `Inventario` gestionará las existencias, entradas y salidas.
- La clase `Reporte` generará los reportes correspondientes.

**Alta Cohesión (High Cohesion)**

La alta cohesión busca que cada clase o módulo tenga una responsabilidad específica y bien definida.

En el proyecto:

- El módulo Login gestionará la autenticación de usuarios.
- El módulo Productos gestionará las operaciones CRUD de productos.
- El módulo Categorías administrará las categorías.
- El módulo Inventario controlará entradas y salidas.
- El módulo Reportes generará información para la toma de decisiones.

**Bajo Acoplamiento (Low Coupling)**

El bajo acoplamiento busca minimizar las dependencias entre los componentes del sistema.

Para lograrlo, los módulos de Productos, Categorías, Inventario y Reportes interactuarán mediante interfaces y servicios específicos, evitando dependencias innecesarias.

Esto permitirá modificar o ampliar funcionalidades sin afectar significativamente a otros módulos.

**Controlador (Controller)**

El principio Controlador establece que una clase debe encargarse de recibir las solicitudes del usuario y coordinar las operaciones correspondientes.

En el sistema se implementarán controladores responsables de gestionar acciones como:

- Inicio de sesión.
- Registro de productos.
- Actualización de productos.
- Registro de entradas y salidas.
- Generación de reportes.

### 4.3 Antipatrones Evitados

**Clase Dios (God Object)**

Este antipatrón ocurre cuando una sola clase concentra demasiadas responsabilidades y controla gran parte del sistema.

Para evitarlo, las funcionalidades fueron distribuidas entre módulos especializados como Login, Productos, Categorías, Inventario y Reportes.

*Cómo se evitó*
- Separando responsabilidades en diferentes clases.
- Aplicando el principio de Responsabilidad Única (SRP).
- Utilizando principios GRASP de alta cohesión.

**Código Duplicado**

El código duplicado genera dificultades de mantenimiento y aumenta la probabilidad de errores.

*Cómo se evitó*
- Reutilizando métodos comunes.
- Utilizando herencia cuando sea necesario.
- Implementando interfaces para compartir comportamientos.
- Centralizando validaciones y procesos repetitivos.

**Acoplamiento Excesivo**

Este antipatrón se presenta cuando las clases dependen fuertemente unas de otras, dificultando futuras modificaciones.

*Cómo se evitó*
- Utilizando interfaces y abstracciones.
- Aplicando el principio de Inversión de Dependencias (DIP).
- Implementando patrones como Observer y Factory Method.

---

## 5. DESARROLLO DEL PROYECTO

### 5.1 Descripción del problema

En la actualidad, muchas pequeñas y medianas empresas todavía llevan el control de inventarios con registros manuales, hojas de cálculo o sistemas poco integrados. Aunque estos métodos pueden ser adecuados en las etapas iniciales del negocio, cuando aumenta el volumen de productos y transacciones, surgen problemas con la precisión de la información, el control del stock y la toma de decisiones.

Uno de los inconvenientes principales es que no se puede ver en tiempo real la disponibilidad de los productos. Si los registros están desparramados en documentos físicos, hojas de cálculo de Excel o sistemas operativos aislados, no sabrás con precisión cuánto producto tienes en inventario, lo que provocará retrasos en tus operaciones, pedidos erróneos y pérdidas económicas. Diversos estudios señalan que la falta de integración y automatización en los procesos de inventario genera discrepancias entre el inventario físico y el registrado digitalmente, lo que impacta la eficiencia operativa de las organizaciones.

Del mismo modo, los errores cometidos por personas constituyen una de las principales razones que provocan inconsistencias en el ámbito de la gestión del inventario. Los registros de inventarios equivocados, la duplicación de datos, la falta de movimientos de entrada/salida o las actualizaciones tardías transforman la información que recibe la empresa en datos poco fiables. A menudo, estas inexactitudes derivan en decisiones erróneas de compra, en sobre abastecimientos o en escasez de productos.

Un inconveniente recurrente aparece cuando no hay supervisión precisa del ir y venir de la mercancía. Si un sistema no captura cada transacción de manera automática, se torna difícil conocer el inventario exacto, chequear fechas de caducidad, detectar mermas o proyectar compras venideras.

Para una empresa, tener demasiada mercancía a mano significa gastar más en guardarla, tener dinero parado y la posibilidad de que los artículos se queden viejos. Por el contrario, no tener suficiente puede resultar en ventas perdidas, pedidos no cumplidos y clientes menos contentos. Ambas situaciones perjudican las ganancias y la capacidad de la compañía para competir.

Ante esta problemática, se plantea crear un Sistema de Gestión de Inventarios usando lenguaje Java y aplicando patrones de diseño de software. El sistema permitirá gestionar productos y categorías, registrar entradas y salidas de inventario, consultar existencias en tiempo real y generar reportes que faciliten la toma de decisiones. Además, la aplicación de patrones de diseño como Singleton, Factory Method, Facade, Adapter, Observer y Command contribuirá a construir una solución escalable, mantenible y alineada con las buenas prácticas de ingeniería de software.

### 5.2 Análisis y diseño

#### Requerimientos funcionales

| RF | Requerimiento Funcional |
|---|---|
| RF1 | El sistema debe permitir el inicio de sesión de usuarios. |
| RF2 | El sistema debe permitir registrar productos. |
| RF3 | El sistema debe permitir editar productos. |
| RF4 | El sistema debe permitir dar a conocer si el producto está inactivo o activo. |
| RF5 | El sistema debe permitir buscar productos. |
| RF6 | El sistema debe permitir gestionar categorías. |
| RF7 | El sistema debe registrar entradas de inventario. |
| RF8 | El sistema debe registrar salidas de inventario. |
| RF9 | El sistema debe actualizar automáticamente el stock. |
| RF10 | El sistema debe generar reportes de inventario. |
| RF11 | El sistema debe mostrar alertas de stock bajo. |

#### Requerimientos no funcionales

| RNF | Requerimiento No Funcional |
|---|---|
| RNF1 | El sistema será desarrollado en Java. |
| RNF2 | Utilizará Programación Orientada a Objetos. |
| RNF3 | La interfaz debe ser intuitiva y fácil de usar. |
| RNF4 | El sistema debe garantizar la integridad de los datos. |
| RNF5 | Debe permitir futuras ampliaciones sin afectar funcionalidades existentes. |
| RNF6 | La respuesta del sistema no debe superar los 3 segundos en operaciones normales. |
| RNF7 | Debe implementar patrones de diseño y principios SOLID. |

#### Diagrama de clases UML

> *(Diagrama original: "Sistema de Gestión de Inventario - Diagrama de Clases")*
>
> Incluye las clases `Usuario` (con subclases `Administrador` y `Almacenero`), la jerarquía abstracta `Command` (con `RegistrarProductoCommand`, `RegistrarEntradaCommand`, `RegistrarSalidaCommand`), la clase `InventarioFacade` (Patrón Facade), `ProductoFactory` (Patrón Factory Method), `Inventario` (Patrón Singleton), `Categoria`, `Producto`, `Reporte`, las interfaces `INotificacion`, `IProductoRepository`, `ICategoriaRepository`, y `NotificacionService` (Patrón Observer).
>
> *Nota: el diagrama es una imagen en el PDF original; se recomienda insertarlo como imagen si necesitas conservar el gráfico exacto.*

#### Diagrama de secuencia

> *("Registro de Salida de Inventario con Alerta de Stock")*
>
> Flujo: `Usuario` → `InventarioFacade.registrarSalida()` → `RegistrarSalidaCommand.ejecutar()` → `Inventario.registrarSalida()` → `Producto.actualizarStock()` → `Inventario.verificarStock()` → si el stock es bajo, `NotificacionService.enviarAlerta()` → notificación enviada → resultado → salida registrada.

#### Diagrama de componentes

> *("Sistema de Gestión de Inventario - Diagrama de Componentes")*
>
> Capas: **Presentación** (Interfaz de Usuario) → **Lógica de Negocio** (`InventarioFacade`, `Inventario`, `ProductoFactory`, `Reporte`, módulo **Comandos**: `RegistrarSalidaCommand`, `RegistrarEntradaCommand`, `RegistrarProductoCommand`; módulo **Notificaciones**: `NotificacionService`) → **Persistencia** (`IProductoRepository`, `ICategoriaRepository`, `RepositorioProductos`, `RepositorioCategorias`, Base de Datos).
