# UCE Dating App

Una aplicación de citas exclusiva para estudiantes de la Universidad Central del Ecuador (UCE).

## 📁 Estructura del Proyecto

```
UCEDateAPP/
├── 📱 frontend/          # Aplicación Android (Kotlin + Jetpack Compose)
│   ├── app/             # Código fuente de la app Android
│   ├── gradle/          # Configuración de Gradle
│   └── *.gradle.kts     # Archivos de build
│
├── 🖥️ backend/           # API Backend (NestJS + TypeScript)
│   ├── src/             # Código fuente del backend
│   ├── node_modules/    # Dependencias de Node.js
│   └── package.json     # Configuración de npm
│
└── README.md           # Este archivo
```

## 🚀 Características Principales

### Frontend (Android)

- **Framework**: Kotlin + Jetpack Compose
- **Autenticación**: Firebase Auth
- **UI**: Material Design 3
- **Navegación**: Navigation Compose

### Backend (NestJS)

- **Framework**: NestJS + TypeScript
- **Base de Datos**: PostgreSQL + TypeORM
- **Autenticación**: JWT + Passport
- **IA**: OpenAI GPT-4 para chatbot
- **WebSockets**: Socket.io para chat en tiempo real
- **Validación**: Class Validator para correos UCE

## 🎯 Funcionalidades Implementadas

### ✅ Autenticación UCE

- Registro con correos @uce.edu.ec
- Validación de estructura de correo
- Verificación de email obligatoria

### 🔄 Sistema de Matching

- Algoritmos de compatibilidad inteligente
- Filtros por facultad, carrera, intereses
- Machine Learning para mejorar recomendaciones

### 💬 Chat Privado

- Mensajes en tiempo real
- Conversaciones temporales (24h visibles)
- Análisis de compatibilidad basado en historial

### 🤖 Chatbot Inteligente

- Sugerencias de lugares en Quito
- Recomendaciones contextuales
- Integración con Google Places API

## 🛠️ Instalación y Desarrollo

### Frontend (Android)

```bash
cd frontend/
./gradlew build
```

### Backend (NestJS)

```bash
cd backend/
npm install
npm run start:dev
```

## 👥 Equipo de Desarrollo

- **Backend**: Alexis Tenegusñay
- **Frontend**: Pablo Mendez, Franchesco Pullupaxi, Xavier Quishpe

## 📄 Licencia

Proyecto académico - Universidad Central del Ecuador
