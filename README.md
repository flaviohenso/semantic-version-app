# Semantic Version App

Esta aplicação demonstra a implementação de versionamento semântico automatizado usando Spring Boot e Java 21.

## Configuração do Versionamento Semântico

O projeto utiliza três plugins principais para gerenciar o versionamento semântico de forma automática e confiável:

### 1. JGitVer Plugin

O JGitVer é responsável pelo cálculo automático das versões baseado no histórico do Git.

```xml
<plugin>
    <groupId>fr.brouillard.oss</groupId>
    <artifactId>jgitver-maven-plugin</artifactId>
    <version>1.9.0</version>
</plugin>
```

Configurações principais:
- `strategy`: PATTERN - Usa padrão de versionamento semântico
- `policy`: LATEST_TAG - Baseia-se na última tag para cálculo da versão
- `autoIncrementPatch`: true - Incrementa automaticamente versão patch
- `useCommitDistance`: true - Considera distância entre commits
- `useGitCommitId`: true - Inclui ID do commit em versões snapshot
- `nonQualifierBranches`: main,master - Branches principais sem qualificadores

### 2. Maven Release Plugin

Gerencia o processo de release do projeto.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-release-plugin</artifactId>
    <version>3.0.1</version>
</plugin>
```

Configurações principais:
- `tagNameFormat`: v@{project.version} - Formato das tags
- `autoVersionSubmodules`: true - Versiona submódulos automaticamente
- `generateReleasePoms`: false - Não gera POMs de release
- `scmCommentPrefix`: [release] - Prefixo para commits de release

### 3. Commitlint Maven Plugin

Valida as mensagens de commit seguindo a convenção de Conventional Commits.

```xml
<plugin>
    <groupId>com.github.commitlint</groupId>
    <artifactId>commitlint-maven-plugin</artifactId>
    <version>1.0.0</version>
</plugin>
```

## Convenção de Commits

O projeto segue o padrão Conventional Commits. As mensagens de commit devem seguir o formato:

```
tipo(escopo opcional): descrição

[corpo opcional]

[rodapé(s) opcional(is)]
```

Tipos permitidos:
- `feat`: Nova funcionalidade (MINOR)
- `fix`: Correção de bug (PATCH)
- `docs`: Documentação
- `style`: Formatação
- `refactor`: Refatoração de código
- `perf`: Melhorias de performance
- `test`: Adição/modificação de testes
- `build`: Sistema de build ou dependências
- `ci`: Configuração de CI
- `chore`: Outras alterações
- `revert`: Reverte um commit anterior

Breaking Changes:
- Adicione `BREAKING CHANGE:` no corpo ou rodapé do commit
- Ou adicione `!` após o tipo/escopo: `feat!:`

## Processo de Release

1. Desenvolvimento normal:
```bash
git commit -m "feat: adiciona novo endpoint"
git commit -m "fix: corrige validação"
```

2. Preparar release:
```bash
mvn release:prepare
```

3. Executar release:
```bash
mvn release:perform
```

## Exemplos de Versionamento

1. Versão atual: 1.0.0
   - Commit: `fix: corrige bug no login`
   - Nova versão: 1.0.1

2. Versão atual: 1.0.1
   - Commit: `feat: adiciona suporte a OAuth`
   - Nova versão: 1.1.0

3. Versão atual: 1.1.0
   - Commit: `feat!: nova API de autenticação`
   - Nova versão: 2.0.0

## Boas Práticas

1. **Commits Atômicos**: Cada commit deve conter uma única alteração lógica

2. **Mensagens Claras**: Use mensagens descritivas e significativas

3. **Breaking Changes**: Sempre documente claramente mudanças incompatíveis

4. **Branches**: 
   - `main/master`: Código em produção
   - `develop`: Desenvolvimento
   - `feature/*`: Novas funcionalidades
   - `hotfix/*`: Correções urgentes

5. **Tags**: São criadas automaticamente pelo processo de release

## Troubleshooting

1. **Erro no Release Prepare**:
   ```bash
   mvn release:rollback
   git reset --hard HEAD^
   ```

2. **Conflito de Versões**:
   ```bash
   git tag -d v1.2.3
   git push origin :v1.2.3
   ```

3. **Reverter Release**:
   ```bash
   git reset --hard HEAD~2
   git push -f origin master
   ``` 