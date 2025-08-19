# ForoHub

ForoHub es una plataforma de foros en línea que permite a los usuarios crear, compartir y participar en discusiones sobre diversos temas. Nuestro objetivo es fomentar la comunidad, el intercambio de ideas y la colaboración entre usuarios.

---

## 🌟 Funcionalidades

- **Creación de tópicos**: Publica nuevas discusiones.
- **Comentarios y respuestas**: Interactúa con otros usuarios.
- **Edición y eliminación**: Modifica o elimina tus publicaciones.
- **Perfil de usuario**: Gestiona tu información y tus contribuciones.
- **Notificaciones**: Mantente al tanto de respuestas y menciones.

---

## 🛠 Tecnologías utilizadas

- **Backend**: Java, Spring Boot
- **Base de datos**: MySQL 
- **Control de versiones**: Git, GitHub

---

## 🚀 Instalación y ejecución

1. Clona este repositorio:

```bash
git clone https://github.com/KarinaRojasDev/forohub-api.git
```

---
## Autenticación con Spring Security

A partir de ahora, solo los usuarios autenticados pueden interactuar con la API.

---

### Funcionalidades
- Registro e inicio de sesión de usuarios.
- Acceso seguro a los endpoints de la API.
- Validación de credenciales usando `AuthenticationManager`.

---

### Configuración
1. Agrega la dependencia **Spring Security** en tu `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

