#!/usr/bin/env bash

set -euo pipefail

# TODO: Go github and generate a token
TOKEN=""
USER="ChristMahy"
PACKAGE_TYPE="maven"   # container, maven, npm...
NAME_PREFIX="cmahy.simple.spring"
DAYS=90

CUTOFF=$(date -u -v-"$DAYS"d +"%Y-%m-%dT%H:%M:%SZ")

echo "Suppression des versions avant $CUTOFF"
echo "Recherche packages commençant par: $NAME_PREFIX"

PACKAGES=$(curl -s \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/vnd.github+json" \
  "https://api.github.com/users/$USER/packages?package_type=$PACKAGE_TYPE&per_page=1000")

echo "$PACKAGES" | jq -c \
  --arg prefix "$NAME_PREFIX" \
  '.[] | select(.name | startswith($prefix))' | while read -r pkg; do

    PACKAGE_NAME=$(echo "$pkg" | jq -r '.name')

    echo ""
    echo "Package trouvé: $PACKAGE_NAME"

    VERSIONS=$(curl -s \
      -H "Authorization: Bearer $TOKEN" \
      -H "Accept: application/vnd.github+json" \
      "https://api.github.com/users/$USER/packages/$PACKAGE_TYPE/$PACKAGE_NAME/versions?per_page=100")

    echo "$VERSIONS" | jq -c '.[]' | while read -r version; do
        VERSION_ID=$(echo "$version" | jq -r '.id')
        CREATED=$(echo "$version" | jq -r '.created_at')

        if [[ "$CREATED" < "$CUTOFF" ]]; then
            echo "  -> suppression version $VERSION_ID ($CREATED)"

            curl -s -X DELETE \
              -H "Authorization: Bearer $TOKEN" \
              -H "Accept: application/vnd.github+json" \
              "https://api.github.com/users/$USER/packages/$PACKAGE_TYPE/$PACKAGE_NAME/versions/$VERSION_ID"
        fi
    done
done

echo ""
echo "Nettoyage terminé"