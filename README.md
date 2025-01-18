# Sistema de Gestión de Viáticos

> Backend en Spring Boot para registro y consulta de viáticos.

## 📋 Requerimientos Implementados

1. **Dos funcionalidades principales:**
   - Ingreso de información de viáticos
   - Consulta de viáticos por identificación

2. **Registro de viáticos con:**
   - Información general (fecha, empleado, identificación)
   - Motivo del viaje
   - Cliente/proyecto
   - Fechas del viaje
   - Correo del aprobador
   - Carga de documentos en ZIP

3. **Validaciones implementadas:**
   - Fecha de registro no mayor a hoy
   - Fecha no menor a 90 días atrás
   - Validación de documentos PDF en ZIP

4. **Consultas:**
   - Búsqueda por identificación y Id Viatico
   - Visualización de información
   - Detalle de documentos cargados (N°.)

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL

## 💾 Estructura de Base de Datos

### Tabla Viáticos
```sql
CREATE TABLE viaticos (
    id VARCHAR(36) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL,
    nombre_empleado VARCHAR(100) NOT NULL,
    fecha_registro DATE NOT NULL,
    motivo_viaje TEXT NOT NULL,
    cliente_proyecto VARCHAR(100) NOT NULL,
    fecha_inicio_viaje DATE NOT NULL,
    fecha_fin_viaje DATE NOT NULL,
    correo_aprobador VARCHAR(100) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_numero_identificacion (numero_identificacion)
);

CREATE TABLE documentos_viatico (
    id VARCHAR(255) NOT NULL,
    viatico_id VARCHAR(36) NOT NULL,
    nombre_zip VARCHAR(255) NOT NULL,
    ruta_archivo VARCHAR(500) NOT NULL,
    numero_archivos_pdf INT NOT NULL,
    fecha_carga TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (viatico_id) REFERENCES viaticos (id)
);
```

## 📦 Instalación y Despliegue

1. **Clonar el repositorio**
```bash
git clone https://github.com/JMVillota/BackEnd--Gesti-n_de_Vi-ticos.git
```

2. **Configurar base de datos**
- Crear base de datos MySQL
- Ejecutar scripts de creación de tablas
- Actualizar application.properties con las credenciales de base de datos

3. **Compilar y ejecutar**
```bash
./mvnw spring-boot:run
```

## 📚 API Endpoints

### Viáticos
- **POST** `/api/viaticos`: Crear nuevo viático
- **GET** `/api/viaticos`: Listar todos los viáticos
- **GET** `/api/viaticos/{id}`: Obtener viático por ID
- **GET** `/api/viaticos/consulta/{numeroIdentificacion}`: Viáticos por identificación
- **GET** `/api/viaticos/metricas`: Obtener métricas del dashboard
- **GET** `/api/viaticos/grafica-mensual/{año}`: Datos de gráfica por año

### Documentos
- **POST** `/api/viaticos/{id}/documentos`: Subir documentos
- **GET** `/api/documentos-viatico/viatico/{viaticoId}`: Obtener documentos de un viático

## 🔒 Reglas de Negocio Implementadas

1. **Validaciones de fechas:**
   - Fecha de registro no mayor a la fecha actual
   - Fecha de registro no menor a 90 días atrás
   - Fecha fin de viaje posterior a fecha inicio

2. **Validaciones de documentos:**
   - Solo archivos ZIP permitidos
   - Contenido ZIP limitado a PDFs
   - Conteo de archivos PDF en ZIP

3. **Validaciones de datos:**
   - Campos requeridos
   - Formato de correo electrónico
   - Longitud de identificación

## 👤 Autor

Jefferson Villota - [GitHub](https://github.com/JMVillota)