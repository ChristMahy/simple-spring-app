#!/usr/bin/env bash

set -euo pipefail

OWNER="ChristMahy"
REPO="simple-spring-app"
# TODO: Go github and generate a token
TOKEN=""
DAYS=5

# Date limite (ISO 8601)
CUTOFF=$(date -u -v-"$DAYS"d +"%Y-%m-%dT%H:%M:%SZ")

echo "Suppression des runs avant : $CUTOFF"

# Récupérer les runs
RUNS=$(curl -s -H "Authorization: Bearer $TOKEN" \
  "https://api.github.com/repos/$OWNER/$REPO/actions/runs?per_page=100")

# Filtrer et supprimer
echo "$RUNS" | jq -c '.workflow_runs[]' | while read -r run; do
  CREATED_AT=$(echo "$run" | jq -r '.created_at')
  ID=$(echo "$run" | jq -r '.id')

  if [[ "$CREATED_AT" < "$CUTOFF" ]]; then
    echo "🗑 Suppression run $ID ($CREATED_AT)"
    curl -s -X DELETE \
      -H "Authorization: Bearer $TOKEN" \
      "https://api.github.com/repos/$OWNER/$REPO/actions/runs/$ID" \
      > /dev/null
  fi
done

echo "✅ Nettoyage terminé"