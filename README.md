# IBO Plus

Aplicativo Android (TV e Mobile) integrado ao **painel IBO Plus**  
➡️ Painel/API: [https://iboplus.motanetplay.top](https://iboplus.motanetplay.top)

## Estrutura do Projeto
- `settings.gradle.kts` → nome e módulos do projeto
- `build.gradle.kts` → plugins globais
- `app/` → código-fonte do aplicativo

## Tecnologias
- **Kotlin** + AndroidX
- **ExoPlayer** para reprodução
- **Retrofit/OkHttp** para consumo das APIs
- **Hilt** para injeção de dependência
- **Compose/Views** para UI

## Funcionalidades
- Login automático via **MAC + Chave**
- Teste automático de **7 dias** (quando habilitado no painel)
- Mensagens e **notificações internas** configuradas pelo painel
- Consumo das APIs:
  - `backdrop.php`, `backdrop2.php` → temas/menus (inferior ou lateral)
  - `playlist.php` → listas e servidores
  - `getappuser.php` → status do usuário
  - `logo.php`, `bg.php`, `setting.php/json` → identidade visual
  - `note.json`, `language.json` → avisos e traduções

## Build
```bash
./gradlew clean build
