#!/bin/bash

# Função para extrair a última versão das tags
get_latest_version() {
    git tag -l "v*" | sort -V | tail -n 1 | sed 's/v//'
}

# Função para determinar o tipo de incremento baseado nos commits
determine_increment() {
    local commits="$1"
    local increment="patch"

    if echo "$commits" | grep -q "^feat!:\|BREAKING CHANGE:"; then
        increment="major"
    elif echo "$commits" | grep -q "^feat:"; then
        increment="minor"
    fi

    echo "$increment"
}

# Função para incrementar a versão
increment_version() {
    local version="$1"
    local increment="$2"
    
    IFS='.' read -r major minor patch <<< "$version"
    
    case "$increment" in
        "major")
            echo "$((major + 1)).0.0"
            ;;
        "minor")
            echo "$major.$((minor + 1)).0"
            ;;
        "patch")
            echo "$major.$minor.$((patch + 1))"
            ;;
    esac
}

# Obter a última versão
current_version=$(get_latest_version)
if [ -z "$current_version" ]; then
    current_version="0.0.0"
fi

# Obter commits desde a última tag
commits_since_tag=$(git log $(git describe --tags --abbrev=0 2>/dev/null || echo HEAD)..HEAD --pretty=format:%s)

# Determinar o tipo de incremento
increment_type=$(determine_increment "$commits_since_tag")

# Calcular nova versão
new_version=$(increment_version "$current_version" "$increment_type")

# Criar nova tag
echo "Criando tag v$new_version..."
git tag -a "v$new_version" -m "release: versão $new_version"

# Enviar tag para o repositório remoto
echo "Enviando tag para o repositório remoto..."
git push origin "v$new_version" 