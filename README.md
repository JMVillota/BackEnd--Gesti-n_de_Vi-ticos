# Sistema de Gestión de Viáticos Empresariales

## 📋 Descripción
Sistema backend robusto desarrollado en Spring Boot para la gestión integral de viáticos empresariales. Esta solución permite administrar el ciclo completo de solicitudes de viáticos, desde su registro inicial hasta la aprobación final, incluyendo gestión documental y análisis estadístico.

## ✨ Características Principales
- **Gestión de Solicitudes**: CRUD completo para administrar solicitudes de viáticos
- **Gestión Documental**: 
  - Carga y validación de documentos en formato ZIP
  - Manejo de documentación PDF
  - Validación automática de formatos
- **Análisis y Reportes**:
  - Dashboard interactivo con métricas clave
  - Gráficas de análisis mensual
  - Exportación de reportes
- **Sistema de Estados**:
  - Seguimiento en tiempo real de solicitudes
  - Flujo de aprobación configurable
  - Notificaciones automáticas

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Jakarta Persistence
- Lombok
- Maven
- MySQL

## 📦 Requisitos Previos
- Java JDK 17+
- Maven 3.8.x+
- MySQL 8.0+
- IDE compatible con Spring Boot (recomendado: IntelliJ IDEA o Spring Tool Suite)

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/gestion-viaticos.git
cd gestion-viaticos
```

### 2. Configuración de Base de Datos
Crear una base de datos MySQL y configurar las variables de entorno o el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. Compilar y Ejecutar
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Verificar Instalación
Acceder a: `http://localhost:8080/swagger-ui.html` para ver la documentación de la API

## 📚 Documentación de la API

### Endpoints Principales
- `POST /api/viaticos`: Crear nueva solicitud
- `GET /api/viaticos`: Listar todas las solicitudes
- `GET /api/viaticos/{id}`: Obtener solicitud específica
- `PUT /api/viaticos/{id}`: Actualizar solicitud
- `DELETE /api/viaticos/{id}`: Eliminar solicitud

Consulta la documentación completa en Swagger UI después de iniciar la aplicación.

## 💻 Uso del Sistema

### Ejemplo de Creación de Solicitud
```bash
curl -X POST http://localhost:8080/api/viaticos \
  -H "Content-Type: application/json" \
  -d '{
    "empleadoId": "1",
    "fechaInicio": "2024-01-20",
    "fechaFin": "2024-01-25",
    "motivo": "Viaje de negocios",
    "montoSolicitado": 1500.00
  }'
```

## 🔧 Configuración para Producción
Para desplegar en producción, asegúrate de configurar las siguientes variables de entorno:
- `MYSQL_HOST`
- `MYSQL_PORT`
- `MYSQL_DATABASE`
- `MYSQL_USER`
- `MYSQL_PASSWORD`

## 🤝 Contribución
1. Fork el proyecto
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia
Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles

## 👥 Autores
- Nombre del Autor - [GitHub](https://github.com/tu-usuario)

## 🙏 Agradecimientos
- Spring Boot Team
- Todos los contribuidores
- Tu empresa/organización