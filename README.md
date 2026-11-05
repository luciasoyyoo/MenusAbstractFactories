# MenusAbstractFactories
Práctica 5 sobre factorías abstractas de la asignatura de Diseño Arquitectónico y Patrones
# 🍽️ Planificador de Menús Familiares

Aplicación de escritorio desarrollada en **Java** para la planificación y consulta de menús familiares. El proyecto utiliza el patrón de diseño **Abstract Factory** para crear familias de menús relacionadas, manteniendo el código desacoplado de las implementaciones concretas.

El sistema permite seleccionar entre diferentes tipos de menú y consultar la información de cada comida del día: **desayuno, almuerzo y cena**.

---

## 📌 Descripción

El proyecto se ha desarrollado como aplicación práctica de la asignatura **Diseño y Análisis de Programas (DAP)**.

La aplicación utiliza el patrón **Abstract Factory** como elemento principal del diseño. La fábrica abstracta define cómo crear los diferentes productos que forman un menú diario, mientras que las fábricas concretas se encargan de crear cada familia de menús.

Actualmente se han implementado dos familias:

- 👧 **Menú infantil**
- 🥗 **Menú saludable**

Los datos de los platos se almacenan en archivos CSV y son recuperados durante la generación del menú.

La aplicación cuenta además con una interfaz gráfica desarrollada utilizando **Java Swing**.

---

## ✨ Funcionalidades

La aplicación permite:

- Seleccionar el tipo de menú que se desea consultar.
- Generar un menú infantil.
- Generar un menú saludable.
- Obtener de forma aleatoria los platos disponibles para cada comida.
- Consultar:
  - Nombre del plato.
  - Número de calorías.
  - Descripción.
  - Ingredientes.
  - Preparación.
- Consultar el total de calorías del menú diario.
- Utilizar una interfaz gráfica de escritorio.
- Incorporar nuevas familias de menús mediante nuevas fábricas concretas.

---

## 🏗️ Patrón de diseño: Abstract Factory

El patrón **Abstract Factory** proporciona una interfaz para crear familias de objetos relacionados sin especificar directamente sus clases concretas.

En este proyecto, la fábrica abstracta es:

```text
FabricaMenuDiario
```

Esta interfaz define los métodos:

```java
Desayuno crearDesayuno();
Almuerzo crearAlmuerzo();
Cena crearCena();
```

Las fábricas concretas son:

```text
FabricaMenuInfantil
FabricaMenuSaludable
```

Cada una crea su correspondiente familia de productos:

```text
FabricaMenuInfantil
├── DesayunoInfantil
├── AlmuerzoInfantil
└── CenaInfantil

FabricaMenuSaludable
├── DesayunoSaludable
├── AlmuerzoSaludable
└── CenaSaludable
```

De esta forma, el código de la interfaz gráfica trabaja principalmente con las abstracciones:

```text
Desayuno
Almuerzo
Cena
FabricaMenuDiario
```

y no necesita depender directamente de una implementación concreta.

### Diagrama simplificado

```text
                         ┌──────────────────────────┐
                         │   FabricaMenuDiario      │
                         │        <<interface>>     │
                         ├──────────────────────────┤
                         │ + crearDesayuno()        │
                         │ + crearAlmuerzo()        │
                         │ + crearCena()            │
                         └────────────┬─────────────┘
                                      │
                    ┌─────────────────┴─────────────────┐
                    │                                   │
                    ▼                                   ▼
        ┌──────────────────────┐           ┌──────────────────────┐
        │ FabricaMenuInfantil  │           │ FabricaMenuSaludable │
        └──────────┬───────────┘           └──────────┬───────────┘
                   │                                  │
          ┌────────┼────────┐                ┌────────┼────────┐
          ▼        ▼        ▼                ▼        ▼        ▼
      Desayuno  Almuerzo   Cena           Desayuno  Almuerzo  Cena
      Infantil  Infantil  Infantil       Saludable Saludable Saludable
```

---

## 🧩 Estructura del proyecto

```text
trabajoPatrones-master/
├── README.md
├── recursos/
│   ├── datos_menus.csv
│   └── MenuStat_Kids.csv
│
└── src/
    └── com/
        └── hogar/
            ├── Main.java
            │
            ├── datos/
            │   └── GestorCSV.java
            │
            ├── gui/
            │   └── VentanaPrincipal.java
            │
            ├── infantil/
            │   ├── AlmuerzoInfantil.java
            │   ├── CenaInfantil.java
            │   ├── DesayunoInfantil.java
            │   └── FabricaMenuInfantil.java
            │
            ├── interfaces/
            │   ├── Almuerzo.java
            │   ├── Cena.java
            │   ├── Desayuno.java
            │   └── FabricaMenuDiario.java
            │
            ├── recursos/
            │   └── datos_menus.csv
            │
            └── saludable/
                ├── AlmuerzoSaludable.java
                ├── CenaSaludable.java
                ├── DesayunoSaludable.java
                └── FabricaMenuSaludable.java
```

### Descripción de los paquetes

| Paquete | Responsabilidad |
|---|---|
| `com.hogar.interfaces` | Define las abstracciones del patrón Abstract Factory. |
| `com.hogar.infantil` | Implementa la familia de productos del menú infantil. |
| `com.hogar.saludable` | Implementa la familia de productos del menú saludable. |
| `com.hogar.datos` | Gestiona la lectura de los datos almacenados en CSV. |
| `com.hogar.gui` | Contiene la interfaz gráfica de la aplicación. |
| `com.hogar` | Contiene el punto de entrada de la aplicación. |
| `recursos` | Contiene los archivos de datos utilizados por el proyecto. |

---

## 📊 Gestión de datos

Los platos se almacenan principalmente en:

```text
recursos/datos_menus.csv
```

El archivo contiene información como:

- Restaurante.
- Nombre del plato.
- Categoría.
- Tipo de comida.
- Calorías.
- Descripción.
- Ingredientes.
- Preparación.

Ejemplo:

```text
Restaurante;Plato;Categoria;Tipo;Calorias;Descripcion;Ingredientes;Preparacion
```

La clase:

```text
GestorCSV
```

se encarga de leer el archivo y localizar platos según:

- Categoría del menú.
- Tipo de comida.

Cuando existen varias opciones que cumplen los criterios, se selecciona una de ellas aleatoriamente.

---

## 🖥️ Interfaz gráfica

La interfaz gráfica se ha desarrollado con **Java Swing**.

La clase principal de la interfaz es:

```text
VentanaPrincipal
```

La aplicación presenta una pantalla inicial desde la que se puede seleccionar:

```text
MENÚ INFANTIL
MENÚ SALUDABLE
```

Una vez seleccionado un tipo de menú, se genera la familia correspondiente utilizando su fábrica concreta.

La interfaz permite consultar posteriormente el detalle de cada plato.

---

## ▶️ Ejecución

### Requisitos

- **JDK 8 o superior**
- Git (opcional, únicamente necesario para clonar y gestionar el repositorio)
- Un IDE compatible con Java, como:
  - IntelliJ IDEA
  - Eclipse
  - Visual Studio Code

No se utilizan librerías externas para ejecutar la aplicación; la interfaz gráfica se basa en **Java Swing** y la lectura de datos utiliza las clases estándar de Java.

### Opción 1: Ejecutar desde un IDE

1. Abrir el proyecto en el IDE.
2. Configurar un JDK.
3. Marcar `src` como directorio de código fuente si el IDE lo requiere.
4. Ejecutar:

```text
com.hogar.Main
```

Es importante ejecutar el programa desde la raíz del proyecto para que la ruta:

```text
recursos/datos_menus.csv
```

pueda localizarse correctamente.

### Opción 2: Compilar desde terminal

Desde la raíz del proyecto:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

Después:

```bash
java -cp out com.hogar.Main
```

---

## 🌿 Organización del trabajo con Git

El proyecto se desarrolla de forma colaborativa utilizando **Git y GitHub**.

Se utiliza una estrategia basada en ramas de funcionalidad (*feature branches*). En lugar de mantener una única rama por persona, cada funcionalidad importante dispone de su propia rama.

La rama:

```text
main
```

contiene la **versión estable e integrada del proyecto**.

Las funcionalidades se desarrollan en ramas independientes, por ejemplo:

```text
main
│
├── feature/interfaces
├── feature/menusInfantiles
├── feature/menusSaludables
├── feature/gestionCSV
└── feature/interfazGrafica
```

### Distribución de tareas

**Lucía**

- `feature/interfaces` — trabajo conjunto con Álvaro.
- `feature/menusSaludables` — familia de menús saludables y fábrica concreta.
- `feature/gestionCSV` — gestión de recursos y datos CSV.

**Álvaro**

- `feature/interfaces` — trabajo conjunto con Lucía.
- `feature/menusInfantiles` — familia de menús infantiles y fábrica concreta.
- `feature/interfazGrafica` — interfaz gráfica.

### Flujo de trabajo

Cada funcionalidad sigue el siguiente proceso:

```text
main
  │
  ▼
Crear rama feature/*
  │
  ▼
Desarrollo local
  │
  ├── Código
  ├── Pruebas
  └── Commits
  │
  ▼
Push a GitHub
  │
  ▼
Revisión y comprobación
  │
  ▼
Merge → main
  │
  ▼
Versión estable
```

Por ejemplo, para desarrollar los menús infantiles:

```bash
git checkout main
git pull origin main
git checkout -b feature/menusInfantiles
```

Durante el desarrollo:

```bash
git add .
git commit -m "Implementación de menús infantiles"
git push -u origin feature/menusInfantiles
```

Una vez terminada y comprobada la funcionalidad, se integra en `main`.

Este procedimiento permite que los dos integrantes trabajen simultáneamente sin modificar directamente la versión estable del proyecto.

---

## 🧱 Principios SOLID

Durante el diseño se han tenido en cuenta los principios **SOLID**.

### Single Responsibility Principle

Las responsabilidades se encuentran separadas entre diferentes componentes:

- `GestorCSV` → lectura de datos.
- `VentanaPrincipal` → interfaz gráfica.
- Fábricas → creación de familias de productos.
- Productos → representación de cada comida.

### Open/Closed Principle

El sistema permite añadir nuevas familias de menús sin modificar las familias existentes.

Por ejemplo, se podría añadir:

```text
FabricaMenuVegetariano
DesayunoVegetariano
AlmuerzoVegetariano
CenaVegetariana
```

### Liskov Substitution Principle

Las implementaciones concretas pueden utilizarse mediante sus respectivas interfaces:

```java
Desayuno desayuno = new DesayunoInfantil(...);
```

### Interface Segregation Principle

Las interfaces de `Desayuno`, `Almuerzo` y `Cena` contienen únicamente las operaciones necesarias para representar cada tipo de comida.

### Dependency Inversion Principle

Las partes principales de la aplicación trabajan con abstracciones como:

```text
FabricaMenuDiario
Desayuno
Almuerzo
Cena
```

en lugar de depender directamente de las clases concretas.

---

## 🔮 Posibles ampliaciones

La arquitectura permite incorporar nuevas funcionalidades sin modificar de forma importante el código existente.

Algunas posibles ampliaciones son:

- Añadir menús vegetarianos.
- Añadir menús deportivos.
- Añadir menús sin gluten.
- Añadir menús adaptados a diferentes edades.
- Incorporar nuevos archivos de datos.
- Añadir filtros por número de calorías.
- Permitir seleccionar restricciones alimentarias.
- Guardar menús favoritos.
- Generar automáticamente un menú semanal.
- Añadir una base de datos en lugar de archivos CSV.

La utilización de Abstract Factory facilita especialmente la incorporación de nuevas familias completas de productos.

---

## 👥 Autores

Proyecto desarrollado por:

- **Lucía Cabrera Garabote**
- **Álvaro Iglesias Álvarez**

Asignatura: **Diseño y Análisis de Programas (DAP)**

---

## 📄 Estado del proyecto

**Estado:** Desarrollo en curso.

El proyecto cuenta actualmente con:

- Patrón Abstract Factory implementado.
- Familia de menús infantiles.
- Familia de menús saludables.
- Gestión de datos mediante CSV.
- Interfaz gráfica mediante Java Swing.
- Organización del desarrollo mediante ramas `feature/*`.
- Integración de las funcionalidades en una rama `main` estable.