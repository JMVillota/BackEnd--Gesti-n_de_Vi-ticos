# Sistema de Gestión de Viáticos Empresariales

> Backend en Spring Boot para registro y análisis de viáticos empresariales.

## 🚀 Características
- Registro de viáticos con validaciones
- Carga y visualización de documentos
- Métricas y análisis por dashboard
- Consultas filtradas por identificación
- Gráficas mensuales por año

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL

## 📦 Instalación

1. **Clonar repositorio**
```bash
git clone https://github.com/JMVillota/BackEnd--Gesti-n_de_Vi-ticos.git
```

## 📚 API Endpoints

### Viáticos
- POST `/api/viaticos`: Crear nuevo viático
- GET `/api/viaticos`: Listar todos los viáticos
- GET `/api/viaticos/{id}`: Obtener viático por ID
- GET `/api/viaticos/consulta/{numeroIdentificacion}`: Viáticos por identificación
- GET `/api/viaticos/metricas`: Obtener métricas del dashboard
- GET `/api/viaticos/grafica-mensual/{año}`: Datos de gráfica por año

### Documentos
- POST `/api/viaticos/{id}/documentos`: Subir documentos
- GET `/api/documentos-viatico/viatico/{viaticoId}`: Obtener documentos de un viático

## 👤 Autor
Jefferson Villota - [GitHub](https://github.com/JMVillota)