# Les bases du chiffrement RSA

## Introduction

Le chiffrement RSA, nommé d'après ses inventeurs **Rivest, Shamir et Adleman**, est un algorithme de cryptographie **asymétrique**. Il repose sur la difficulté de factoriser de grands nombres composés.

## Table des matières


1. [Génération des clés](#génération-des-clés) \
   1.1 [Choix des nombres premiers](#choix-des-nombres-premiers)\
   1.2 [Calcul du module](#calcul-du-module)\
   1.3 [Calcul de la fonction totiente d'Euler](#calcul-de-la-fonction-totiente-deuler)\
   1.4 [Choix de l'exposant public](#choix-de-lexposant-public)\
   1.5 [Calcul de l'exposant privé](#calcul-de-lexposant-privé)\
   1.6 [Définition des clés](#définition-des-clés)
2. [Signature](#signature)\
   2.1 [Création de la signature](#création-de-la-signature)\
   2.2 [Vérification de la signature](#vérification-de-la-signature)
3. [Certificat](#certificat)\
   3.1 [Génération du certificat](#génération-du-certificat)\
   3.2 [Vérification du certificat](#vérification-du-certificat)
4. [Conclusion](#conclusion)


## Génération des clés

Le chiffrement RSA utilise une paire de clés : une **clé publique** et une **clé privée** : 

**La clé publique** : Permet de chiffrer les données et d'être partagée publiquement 

**La clé privée** : Permet de déchiffrer les données avec la clé publique


### Choix des nombres premiers

Le processus débute avec la sélection de deux grands nombres premiers distincts **p** et **q**



### Calcul du module

Le module nommé **n** est utilisé dans la clé publique ainsi que la clé privée.
n définit également l'espace des opérations de **chiffrement** et de **déchiffrement**. Tous les calculs sont réalisés **modulo n**. Il est important que les messages soient plus petits que le **module n**.

Calcul du module : 

**n = p * q**


### Calcul de la fonction totiente d'Euler

La fonction totiente d'Euler est notée **φ(n)**. Elle représente le nombre d'entiers positifs inférieurs ou égaux à n qui sont premiers avec n (c'est-à-dire dont le **PGCD** avec n est 1).

Calcul de la totiente d'Euler :

φ(n) = φ(p) × φ(q) = **(p - 1) × (q - 1)**

### Choix de l'exposant public

L'exposant public nommé **e** doit être compris entre 1 et φ(n) (suffisamment grand pour assurer la sécurité)


### Calcul de l'exposant privé
L'exposant privé d est l'inverse modulaire de **e** modulo **φ(n)**. Cela s'exprime comme :

**d ≡ e^(-1) mod φ(n)**

Dans notre méthode, on utilise l'algorithme de Bézout.

### Définition des clés

**La clé publique** est le couple (n, e)

**La clé privée** est le couple (n, d)

## Signature
La signature numérique permet d'avoir un moyen sécurisé d'authentifier l'expéditeur d'un message et de garantir l'intégrité du contenu de ce dernier.

### Création de la signature
Afin de créer la signature, il faut utiliser un algorithme de **hachage** comme le SHA-256. Le Hash crée une **empreinte unique** et de la même taille que le message.
Cette empreinte nommée le **condensé** va être **chiffrée avec la clé privée de l'expéditeur**, produisant la fameuse **signature numérique**.

### Vérification de la signature
Pour vérifier la signature, le destinataire déchiffre la signature avec la clé publique de l'expéditeur. Il calcule l'empreinte en appliquant la fonction de hachage. Pour finir, il faut comparer les deux empreintes. Si elles correspondent, alors la signature est validée.


## Certificat

### Génération du certificat
Pour générer le certificat, il y a 3 étapes. La construction de la partie non signée qui combine l'identité, l'exposant public **(e)** et le module **(n)** en une chaîne de caractères. L'étape suivante est la signature et la partie finale est l'assemblage sous la forme : 

**identite:e:n:signature**

### Vérification du certificat
Afin de vérifier le certificat, il faut réaliser un parsing c'est-à-dire découper les composants (identité, e, n, signature). La prochaine étape consiste à recombiner l'identité, e et n pour former la partie non signée. La signature est convertie en BigInteger pour représenter la signature. La dernière étape consiste à vérifier la signature avec la partie non signée et la signature. 

## Conclusion



Le chiffrement RSA est un pilier fondamental de la cryptographie moderne, offrant une solution robuste pour la sécurisation des communications numériques. Les points clés à retenir sont :

1. **Asymétrie** : L'utilisation de clés publiques et privées distinctes permet un chiffrement sécurisé sans échange préalable de clé secrète.

2. **Fondement mathématique** : La sécurité repose sur la difficulté de factoriser de grands nombres, un problème mathématique complexe.

3. **Polyvalence** : RSA permet non seulement le chiffrement, mais aussi la création de signatures numériques, essentielles pour l'authentification et l'intégrité des données.

4. **Certificats** : L'intégration de RSA dans les certificats numériques établit un système de confiance pour l'authentification sur Internet.

5. **Complexité calculatoire** : Bien que sûr, RSA nécessite des calculs intensifs, ce qui peut limiter son utilisation dans certains contextes.


---
