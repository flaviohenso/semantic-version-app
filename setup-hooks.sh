#!/bin/bash

# Copia o hook para o diretório .git/hooks
cp .git-hooks/commit-msg .git/hooks/

# Torna o script executável
chmod +x .git/hooks/commit-msg

echo "Hooks do Git configurados com sucesso!" 