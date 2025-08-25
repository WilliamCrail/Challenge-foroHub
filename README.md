#**Challenge ForoHub**

Este proyecto fue desarrollado como parte de la evaluación de la especialidad **Back-End** del curso Alura.

Consiste en una **API** que simula el funcionamiento de un foro, el cual incluye el manejo de diferentes endpoints para crear, modificar, eliminar datos tales como:
Post y respuestas (ambos siendo guardados en la fecha y hora de su cración), usuarios y ademas de contar con la implementación de tokens para la autenticación de los usuarios autorizados en el foro.

---

## 🛠️ Tecnologías utilizadas
- **Java 24**
- **Spring Boot**
- **Spring Security**
- **Flyway (MySQL)**
- **Insomnia REST (para prueba de endpoints)**

## 🚀 Instalación y ejecución

1. Descargar el repositorio .zip.
2. Configurar las variables de entorno mostradas en la siguiente sección.
3. Compilar y ejecutar la aplicación con Maven.

## ⚙️ Configuración

Antes de ejecutar la aplicación, asegúrate de definir las siguientes variables de entorno:
| Variable      | Descripción |
|---------------|-------------|
| `DB_URL`      | URL de conexión a la base de datos |
| `DB_USERM`    | Usuario de la base de datos |
| `DB_PASS`     | Contraseña del usuario de la base de datos |
| `JWT_SECRET`  | Clave secreta para firmar los tokens JWT |

## 📖 Endpoints principales

Todas son requesiciones con body tipo Json.

**Login**
{
	"login": "",
	"contrasena": ""
}

**Posts nuevos**
{
  "titulo": "",
  "mensaje": "",
  "fechaDeCreacion": "",
  "status": true,
  "autor": "",
  "curso": "EN MAYUSCULAS"
}

**Respuesta nueva**
{
  "mensaje": "",
  "usuarioId": ,
  "topicoId": 
}

**Usuario nuevo**
{
  "login": "",
  "contraseña": ""
}

La api encripta las contraseñas.

Los demas endpoints dependeran del cambio a realizar, por ejemplo: actualizar Put y colocar el id con /{id} y el mismo caso para eliminar (delete).

##🔑 Seguridad con JWT

El acceso a los endpoints (excepto login) requiere enviar el token en el header de autorización.

##📌 Estado del proyecto

✅ Proyecto concluido.
Fue desarrollado como un challenge de evaluación para Aluracursos.

##👤 Autor

**Brandon william Crail Castro**

-[GitHub](github.com/WilliamCrail)
