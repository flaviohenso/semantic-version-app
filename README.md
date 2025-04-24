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

## Validação de Commits com Git Hooks

Para garantir que todos os commits sigam o padrão de Conventional Commits, configuramos um Git Hook local que valida as mensagens de commit.

### Configuração do Git Hook

1. Crie o arquivo `commit-msg` no diretório `.git/hooks/`:

```bash
#!/bin/bash

# Caminho para o arquivo com a mensagem do commit
commit_msg_file=$1
# Lê a mensagem do commit
commit_msg=$(cat $commit_msg_file)

# Padrões de commit válidos
commit_pattern="^(feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert)(\(.+\))?: .+"
breaking_pattern="^(feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert)!(\(.+\))?: .+"

# Verifica se a mensagem segue o padrão
if [[ ! $commit_msg =~ $commit_pattern ]] && [[ ! $commit_msg =~ $breaking_pattern ]] && [[ ! $commit_msg =~ "^Merge" ]]; then
    echo "Erro: Mensagem de commit inválida"
    echo "A mensagem deve seguir o padrão: tipo(escopo opcional): descrição"
    echo ""
    echo "Tipos permitidos:"
    echo "  feat     : Nova funcionalidade"
    echo "  fix      : Correção de bug"
    echo "  docs     : Documentação"
    echo "  style    : Formatação"
    echo "  refactor : Refatoração"
    echo "  perf     : Performance"
    echo "  test     : Testes"
    echo "  build    : Build"
    echo "  ci       : Integração Contínua"
    echo "  chore    : Tarefas gerais"
    echo "  revert   : Reverte um commit"
    echo ""
    echo "Breaking changes:"
    echo "  feat!    : Nova funcionalidade com breaking change"
    echo "  fix!     : Correção com breaking change"
    echo ""
    echo "Exemplos:"
    echo "  feat: adiciona novo endpoint"
    echo "  fix(auth): corrige validação de token"
    echo "  feat!: nova API de autenticação"
    echo ""
    echo "Sua mensagem: $commit_msg"
    exit 1
fi
```

2. Torne o script executável:

```bash
chmod +x .git/hooks/commit-msg
```

### Instalação do Hook em Novos Clones

Para cada novo clone do repositório, os desenvolvedores precisam:

1. Copiar o script:
```bash
cp .git-hooks/commit-msg .git/hooks/
```

2. Tornar o script executável:
```bash
chmod +x .git/hooks/commit-msg
```

### Compartilhando os Hooks

Para facilitar o compartilhamento dos hooks entre a equipe:

1. Crie um diretório `.git-hooks` na raiz do projeto:
```bash
mkdir .git-hooks
```

2. Copie o hook para este diretório:
```bash
cp .git/hooks/commit-msg .git-hooks/
```

3. Adicione um script de setup:
```bash
#!/bin/bash
# setup-hooks.sh
cp .git-hooks/commit-msg .git/hooks/
chmod +x .git/hooks/commit-msg
```

4. Documente no README que novos desenvolvedores devem executar:
```bash
./setup-hooks.sh
```

### Exemplos de Mensagens de Commit

Válidas:
```bash
git commit -m "feat: adiciona endpoint de usuários"
git commit -m "fix(auth): corrige validação de token"
git commit -m "docs: atualiza README"
git commit -m "feat!: nova API de autenticação"
```

Inválidas:
```bash
git commit -m "adiciona feature"           # Falta o tipo
git commit -m "feat"                       # Falta a descrição
git commit -m "feature: novo endpoint"     # Tipo inválido
```

### Troubleshooting

Se o hook não estiver funcionando:

1. Verifique se o arquivo é executável:
```bash
ls -l .git/hooks/commit-msg
```

2. Verifique se o arquivo tem as permissões corretas:
```bash
chmod +x .git/hooks/commit-msg
```

3. Teste o hook manualmente:
```bash
echo "mensagem inválida" > .git/COMMIT_EDITMSG
.git/hooks/commit-msg .git/COMMIT_EDITMSG
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
- Adicione `!` após o tipo/escopo: `feat!:`
- Ou adicione `BREAKING CHANGE:` no corpo ou rodapé do commit

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