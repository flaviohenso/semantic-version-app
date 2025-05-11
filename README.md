# Semantic Version App

Esta aplicação demonstra a implementação de versionamento semântico automatizado usando Spring Boot e Java 21.

## Requisitos do Ambiente

Antes de começar, certifique-se de ter instalado:

1. **Java Development Kit (JDK)**:
   - Versão: JDK 21 ou superior
   - Verifique a instalação: `java -version`

2. **Maven**:
   - Versão: 3.8.0 ou superior
   - Verifique a instalação: `mvn -version`

3. **Git**:
   - Versão: 2.34.0 ou superior
   - Verifique a instalação: `git --version`

4. **Sistema Operacional**:
   - Linux, Windows (com WSL) ou macOS
   - Se estiver usando Windows, recomendamos usar o WSL2 com Ubuntu

## Quick Start para Novos Desenvolvedores

Para começar a trabalhar com este projeto, siga estes passos:

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/flaviohenso/semantic-version-app.git
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
   
   # Teste o hook com um commit
   git commit -m "test: verificando configuração do hook"
   ```

## Gerenciamento de Versão com JGitVer

O projeto utiliza o JGitVer para gerenciamento automático de versões. O JGitVer analisa o histórico do Git e as tags para determinar a versão atual do projeto.

### Estrutura de Arquivos Importantes

1. **Configuração do JGitVer**:
   - Localização: `.mvn/jgitver.config.xml`
   - Propósito: Define a estratégia e regras de versionamento

2. **Script de Versionamento**:
   - Localização: `auto-version.sh`
   - Propósito: Automatiza a criação de novas versões
   - Uso: `./auto-version.sh [major|minor|patch]`

### Como Gerar uma Nova Versão

1. **Preparação**:
   ```bash
   # Certifique-se de estar na branch main
   git checkout main
   
   # Atualize o repositório local
   git pull origin main
   
   # Verifique a versão atual
   mvn help:evaluate -Dexpression=project.version -q -DforceStdout
   ```

2. **Execução do Script**:
   ```bash
   # Torne o script executável (se ainda não estiver)
   chmod +x scripts/auto-version.sh
   
   # Execute o script (ele determinará automaticamente o tipo de versão baseado nos commits)
   ./scripts/auto-version.sh
   ```

   O script analisará automaticamente os commits desde a última tag e determinará o tipo de incremento:
   - Se encontrar commits com `feat!:` ou `BREAKING CHANGE:` -> incremento MAJOR
   - Se encontrar commits com `feat:` -> incremento MINOR
   - Para outros tipos de commits -> incremento PATCH

3. **Verificação**:
   ```bash
   # Confirme a nova versão
   mvn clean install
   
   # Verifique a versão gerada
   mvn help:evaluate -Dexpression=project.version -q -DforceStdout
   ```

### Troubleshooting

1. **Erro de Permissão no Script**:
   ```bash
   # Solução: Ajuste as permissões
   chmod +x auto-version.sh
   ```

2. **Versão Não Atualiza**:
   ```bash
   # Limpe o cache do Maven
   mvn clean
   
   # Remova a pasta target
   rm -rf target/
   
   # Reconstrua o projeto
   mvn clean install
   ```

3. **Conflitos de Branch**:
   ```bash
   # Verifique se está na branch correta
   git branch
   
   # Atualize a branch local
   git pull origin main
   ```

4. **Erro no JGitVer**:
   ```bash
   # Verifique o arquivo de configuração
   cat .mvn/jgitver.config.xml
   
   # Regenere o arquivo se necessário
   # (consulte a documentação para o conteúdo correto)
   ```

### Boas Práticas

1. **Antes de Gerar Nova Versão**:
   - Certifique-se que todos os testes passam
   - Verifique se não há commits pendentes
   - Confirme que está na branch principal
   - Atualize seu repositório local

2. **Mensagens de Commit**:
   - Use os tipos corretos (feat, fix, docs, etc.)
   - Seja claro e conciso
   - Adicione breaking change footer quando necessário

3. **Gestão de Branches**:
   - Mantenha a branch main sempre estável
   - Use feature branches para desenvolvimento
   - Faça merge apenas após code review

4. **Documentação**:
   - Atualize o CHANGELOG.md
   - Documente breaking changes
   - Mantenha exemplos atualizados

### Exemplos Práticos

1. **Lançando uma Nova Feature**:
   ```bash
   # Desenvolvimento
   git checkout -b feature/nova-funcionalidade
   # ... desenvolvimento ...
   git commit -m "feat: adiciona nova funcionalidade"
   
   # Merge na main
   git checkout main
   git merge feature/nova-funcionalidade
   
   # Geração da versão
   ./auto-version.sh minor
   ```

2. **Correção de Bug**:
   ```bash
   # Correção
   git checkout -b fix/bug-critico
   # ... correção ...
   git commit -m "fix: corrige bug crítico"
   
   # Merge e versão
   git checkout main
   git merge fix/bug-critico
   ./auto-version.sh patch
   ```

3. **Breaking Change**:
   ```bash
   git checkout -b feature/mudanca-api
   # ... mudanças ...
   git commit -m "feat!: refatora API completamente
   
   BREAKING CHANGE: Nova estrutura de API incompatível"
   
   git checkout main
   git merge feature/mudanca-api
   ./auto-version.sh major
   ```

## Notas Adicionais

1. **Ambiente Windows**:
   - Use WSL2 para melhor compatibilidade
   - Execute todos os comandos no terminal do WSL
   - Mantenha o Git configurado com LF (não CRLF)

2. **Integração Contínua**:
   - O JGitVer funciona automaticamente no CI
   - Versões de snapshot incluem metadata do commit
   - Tags são criadas apenas em releases oficiais

3. **Manutenção**:
   - Mantenha as dependências atualizadas
   - Revise periodicamente as configurações
   - Monitore o tamanho do histórico Git

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

#comentario de teste