# ActiveMQ Artemis

## Etape 1: Cloner de git

git clone git@github.com:apache/activemq-artemis.git

## Etape 2: Se positionner sur le tag voulu

git checkout tags/2.9.0 -b 290

## Etape 3: Compiler

Se positionner dans le projet artemis-distribution.

cd [repertoire du projet]/artemis-distribution

mvn clean package

## Etape 4: Préparer pour compiler l'image docker

Créer un répertoire "docker" dans ce dernier: [repertoire du projet]/artemis-distribution/target/apache-artemis-2.9.0-bin/apache-artemis-2.9.0
Soit executer le script prepare-docker.sh avec les paramètres qu'il faut, si vous y arrivez.

Soit, du projet artemis-docker, copier les fichiers Dockerfile-centos,Dockerfile-ubuntu,docker-run.sh dans le projet artemis-distribution.

cd [repertoire du projet]/artemis-docker

cp Dockerfile-centos ../artemis-distribution/target/apache-artemis-2.9.0-bin/apache-artemis-2.9.0/docker/
cp Dockerfile-ubuntu ../artemis-distribution/target/apache-artemis-2.9.0-bin/apache-artemis-2.9.0/docker/
cp docker-run.sh ../artemis-distribution/target/apache-artemis-2.9.0-bin/apache-artemis-2.9.0/docker/

## Etape 5: Compiler l'image docker

Aller dans le répertoire [repertoire du projet]/artemis-distribution/target/apache-artemis-2.9.0-bin/apache-artemis-2.9.0

Lancer la commande: docker build -f ./docker/Dockerfile-ubuntu -t artemis-ubuntu .

NB: Si une erreur comportant le texte suivant: libaio1=0.3.110-3 se présente, simplement effacer le contenu "--no-install-recommends \ libaio1=0.3.110-3 " du fichier Dockerfile-ubuntu