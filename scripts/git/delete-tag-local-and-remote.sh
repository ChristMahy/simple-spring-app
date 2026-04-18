#!/bin/bash

set +e # ne pas stopper sur erreur

REGEX=$1
REMOTE=${2:-origin}

if [ -z "$REGEX" ]; then
  echo "Usage: $0 tags-regex [remote]"
  exit 1
fi

echo "Recherche des tags correspondant à la regex : $REGEX"
TAGS=$(git tag -l | grep -E "$REGEX")

if [ -z "$TAGS" ]; then
  echo "Aucun tag trouvé."
  exit 0
fi

echo "Tags trouvés :"
echo "$TAGS"
echo

read -p "Confirmer la suppression de ces tags ? (y/N) " CONFIRM

if [[ ! "$CONFIRM" =~ ^[Yy]$ ]]; then
  echo "Annulé."
  exit 0
fi

for TAG in $TAGS; do
  echo "-----------------------------"
  echo "Traitement du tag : $TAG"

  echo "-> Suppression locale"
  git tag -d "$TAG" 2>/dev/null || echo " (ignoré)"

  echo "-> Suppression distante ($REMOTE)"
  git push "$REMOTE" --delete "$TAG" 2>/dev/null || echo " (ignoré)"
done

echo "-----------------------------"

echo "Done !!!"