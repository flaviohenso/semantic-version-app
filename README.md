# Semantic Version App

Esta aplicação demonstra a implementação de versionamento semântico automatizado usando Spring Boot e Java 21.

## Quick Start para Novos Desenvolvedores

Para começar a trabalhar com este projeto, siga estes passos:

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/semantic-version-app.git
   cd semantic-version-app
   ```

2. **Configure os Git Hooks**:
   ```bash
   # Torna o script de setup executável
   chmod +x setup-hooks.sh
   
   # Executa o script de configuração dos hooks
   ./setup-hooks.sh
   ```

3. **Verifique a Instalação**:
   ```bash
   # Verifica se o hook está instalado
   ls -la .git/hooks/commit-msg
   ```

## Padrões de Commit e Versionamento

### Tipos de Commit e Impacto na Versão

| Tipo de Commit | Descrição | Impacto na Versão | Exemplo |
|---------------|-----------|------------------|---------|
| `feat:`       | Nova funcionalidade | MINOR (1.0.0 -> 1.1.0) | `feat: adiciona autenticação OAuth` |
| `fix:`        | Correção de bug | PATCH (1.0.0 -> 1.0.1) | `fix: corrige validação de token` |
| `feat!:` ou `fix!:` | Breaking change | MAJOR (1.0.0 -> 2.0.0) | `feat!: nova API incompatível` |
| `docs:`       | Documentação | Nenhum | `docs: atualiza README` |
| `style:`      | Formatação | Nenhum | `style: ajusta indentação` |
| `refactor:`   | Refatoração | Nenhum | `refactor: otimiza função` |
| `perf:`       | Performance | Nenhum | `perf: melhora consulta` |
| `test:`       | Testes | Nenhum | `test: adiciona teste` |
| `build:`      | Build/Dependências | Nenhum | `build: atualiza dependência` |
| `ci:`         | CI/CD | Nenhum | `ci: configura pipeline` |
| `chore:`      | Tarefas gerais | Nenhum | `chore: remove logs` |
| `revert:`     | Reverte mudança | Nenhum | `revert: volta commit abc123` |

### Formato da Mensagem de Commit

```
tipo[(escopo opcional)]: descrição

[corpo opcional]

[rodapé(s) opcional(is)]
```

## Versionamento Semântico Automático

### Configuração do JGitVer

O projeto usa o JGitVer para gerenciar o versionamento semântico automaticamente.

1. **Configuração no pom.xml**:
```xml
<plugin>
    <groupId>fr.brouillard.oss</groupId>
    <artifactId>jgitver-maven-plugin</artifactId>
    <version>1.9.0</version>
    <configuration>
        <strategy>PATTERN</strategy>
        <useGitCommitId>true</useGitCommitId>
        <gitCommitIdLength>8</gitCommitIdLength>
        <nonQualifierBranches>main,master</nonQualifierBranches>
    </configuration>
</plugin>
```

2. **Configuração em .mvn/jgitver.config.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration xmlns="http://jgitver.github.io/maven/configuration/1.1.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://jgitver.github.io/maven/configuration/1.1.0 https://jgitver.github.io/maven/configuration/jgitver-configuration-v1_1_0.xsd">
    <strategy>PATTERN</strategy>
    <useGitCommitId>true</useGitCommitId>
    <gitCommitIdLength>8</gitCommitIdLength>
    <nonQualifierBranches>main,master</nonQualifierBranches>
    <regexVersionTag>v(.*)</regexVersionTag>
</configuration>
```

### Formato das Versões

- **Release**: `MAJOR.MINOR.PATCH` (exemplo: 1.2.3)
- **Snapshot**: `MAJOR.MINOR.PATCH-<distância>-g<commit>` (exemplo: 1.2.3-2-g1234567)

### Comandos Úteis

1. **Verificar Versão Atual**:
   ```bash
   mvn help:evaluate -Dexpression=project.version -q -DforceStdout
   ```

2. **Criar uma Release**:
   ```bash
   # Criar tag
   git tag -a v1.2.0 -m "versão 1.2.0"
   
   # Push das alterações e tags
   git push && git push --tags
   ```

## Resolução de Problemas

### Problemas com Git Hooks

1. **Hook não está funcionando**:
   ```bash
   # Reinstale os hooks
   ./setup-hooks.sh
   
   # Verifique as permissões
   chmod +x .git/hooks/commit-msg
   ```

2. **Commit rejeitado**:
   - Verifique se a mensagem segue o padrão correto
   - Use `git commit --amend` para corrigir a última mensagem

### Problemas com Versionamento

1. **Versão não atualiza**:
   ```bash
   # Limpe o cache do Maven
   mvn clean
   
   # Verifique as tags
   git tag -l
   ```

2. **Conflitos de versão**:
   ```bash
   # Sincronize as tags
   git fetch --tags
   
   # Verifique duplicatas
   git tag -l | sort -V
   ```

## Boas Práticas

1. **Commits**:
   - Faça commits atômicos (uma alteração por commit)
   - Use mensagens claras e descritivas
   - Siga o padrão de commit convencional

2. **Branches**:
   - `main/master`: código em produção
   - `develop`: desenvolvimento
   - `feature/*`: novas funcionalidades
   - `hotfix/*`: correções urgentes

3. **Tags**:
   - Use sempre o prefixo 'v': `v1.0.0`
   - Crie tags apenas para releases estáveis
   - Mantenha as tags sincronizadas com o remoto 

## Processo de Fechamento de Versão

Para criar uma nova versão estável do projeto, siga estes passos:

### Preparação

1. **Verifique o Estado do Código**:
   - Certifique-se que todas as alterações desejadas estão commitadas
   - Confirme que todos os testes estão passando
   - Verifique se você está na branch principal (main/master)
   ```bash
   git status  # Deve mostrar working tree clean
   git branch  # Deve mostrar que você está em main/master
   ```

2. **Determine a Nova Versão**:
   - Verifique a versão atual:
   ```bash
   mvn help:evaluate -Dexpression=project.version -q -DforceStdout
   ```
   - Decida a nova versão baseado nas mudanças:
     - MAJOR (2.0.0): Mudanças incompatíveis com versões anteriores
     - MINOR (1.1.0): Novas funcionalidades compatíveis
     - PATCH (1.0.1): Correções de bugs

### Criação da Release

1. **Crie e Anote a Tag**:
   ```bash
   # Substitua X.Y.Z pela versão escolhida
   git tag -a vX.Y.Z -m "release: versão X.Y.Z

   Principais alterações:
   - Lista de alterações principais
   - Novas funcionalidades
   - Correções importantes"
   ```

2. **Envie as Alterações**:
   ```bash
   # Envie os commits (se houver)
   git push

   # Envie a tag
   git push origin vX.Y.Z
   ```

3. **Verifique a Release**:
   ```bash
   # Confirme que a tag foi criada
   git tag -l

   # Verifique a nova versão
   mvn clean install
   mvn help:evaluate -Dexpression=project.version -q -DforceStdout
   ```

### Pós-Release

1. **Atualize a Documentação**:
   - Documente quaisquer mudanças importantes na descrição da tag
   - Atualize guias de migração se necessário
   - Mantenha o README atualizado com novas funcionalidades

2. **Comunique a Equipe**:
   - Informe sobre a nova versão
   - Destaque mudanças importantes
   - Forneça instruções de atualização se necessário

### Notas Importantes

- Use tags apenas para versões estáveis e testadas
- Sempre inclua o prefixo 'v' nas tags (exemplo: v1.2.0)
- Inclua uma mensagem descritiva na tag com as principais mudanças
- Mantenha as tags sincronizadas entre todos os ambientes
- Commits após uma tag gerarão versões snapshot (exemplo: 1.2.0-1-gXXXXXXX) 