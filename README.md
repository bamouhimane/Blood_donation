# Projet: Conception et développement d'un site web pour la gestion des opérations de don de sang

## Authors
- [@Alouache Hafsa](https://www.github.com/octokatherine)
- [@Allali Hajar](https://www.github.com/octokatherine)
- [@Bamouh Imane](https://www.github.com/octokatherine)
- [@Belyamani Meryem](https://www.github.com/octokatherine)

![left 100%](https://github.com/bamouhimane/Blood_donation/assets/125123163/ca4bc8cd-f89a-4808-bef9-ae074ae0db75)

_`Début du projet le 08/11/2023`_

## Table des Matières
- [Introduction](#Introduction)
- [Analyse et conception de projet](#Analyse-et-conception-de-projet)
- [Techniques et réalisation](#Techniques-et-réalisation) 
- [Licence](#licence)

---

## Introduction
Dans un contexte où chaque goutte de sang est cruciale pour sauver des vies, les opérations de don de sang sont souvent confrontées à des obstacles logistiques, des délais d'attente excessifs, et des problèmes d'organisation.

Pour résoudre ces défis, notre équipe s'est engagée dans la création d'un site web novateur dédié à la gestion efficace des opérations de don de sang. 

Notre projet vise à simplifier et optimiser le processus en fournissant une plateforme conviviale qui facilite la coordination entre les hôpitaux, les banques de sang et les donneurs, surmontant ainsi les contraintes temporelles et organisationnelles qui peuvent décourager même les volontaires les plus dévoués.

## Analyse et conception de projet
Dans cette partie, nous présentons une analyse des objectifs, des exigences et des contraintes du système. Cette analyse nous a permis de mieux comprendre les besoins des utilisateurs (Employés, administration) pour proposer une solution adaptée. 

Ensuite, nous présentons l’architecture conçue, les modules et la base de données du système. Ces étapes préliminaires ont posé les fondements d’un système opérationnel et performant

**Besoins fonctionnels du système**
  - **Description des utilisateurs** :
Les utilisateurs du système de gestion des opéerations de don de snag sont les employés et l’administration.
Chacun de ces acteurs joue un rôle spécifique dans le processus de gestion de notre site. 

Employés : les employés sont divisés en deux parties : 
-  responsbales de reception : qui sont conçu pour la gestion des comptes des donneurs de sang, ils ajoutent les donneurs et ils ont la main aussi sur toute modification ou suppression et meme consultation des comptes ajoutés. 
- infermiers : qui sont conçu pour la gestion des comptes des demandeurs de sang, ils ajoutent les demandeurs et peuvent effectués des opérations comme la modification, la suppression, la consultation des ces comptes, ils peuvent aussi voir l'etat de la demande à chaque moment si elle est assurée ou bien encore en cours . 

Administration : L’administration gère les comptes des responsables de reception et les infermiers, et donne accès à ces employès au site web en leur offrant un nom d'utilsateur et un mot de passe pour que chacun aura un environnement de travail unique.

- **Fonctionnalités attendues du système**

Afin d'avoir un site web performant, les fonctionnalités attendues seront soigneusement intégrées pour répondre à l'objectif de conception de se système. Ces fonctionnalités sont: 
L’administration :
- Création et suppression des comptes pour les responsables de reception et les infermiers. 
Les responsables de reception :
- Traitement des opérations de don de sang.
- Avoir accès à son propre profil où il peut ajouter les demandes de don, les suprrimées, modifiées ainsi que les consultées. 
Les infermiers :
- Traitement des opérations de demandes de sang. 
- Avoir accès à leur propre profil où ils peuvent ajouter les demandes de don, les suprrimées, modifiées ainsi que les consultées.

**Conception de la Base de Données**
- **les tables utilsées**
hna ndir screenshots l tables dyali 


- **Règles de gestion des tables**
hna regles dyal kula columns f chaque table 

- **Modèle conceptuel de données**
hna ndir diagramme UML

## Techniques et réalisation

Nous présentons la partie réalisation et implémentation de notre projet. Nous commencerons par présenter les outils de développement utilisés. Ensuite, nous élaborerons une présentation des différentes interfaces graphiques.

**Les solutions logicielles :**

- **Environnement de dévelopement :**
    - Eclipse Java JEE
- **Gestion des données :**
    - Utilisation de DAO(Data Access Object)pour la manipulation des données.
    - Mise en œuvre de Singletons de Connexion pour assurer une gestion efficace des connexions à la base de données.
    - Intégration de classes CDI pour la gestion de l'injection de dépendances.
- **Design des interfaces :**
    - Utilisation de PrimeFaces pour des composants d'interface utilisateur riches.
    - Intégration de Bootstrap pour la conception et la mise en page responsives.
    - Personnalisation des interfaces avec HTML et CSS.

**Les interfaces de notre site web :**

- **Page d'acceuil :**
- **Page d'Authentification :**
- **Connexion en tant que employés :**

    - **Connexion en tant que responsable de reception :**

    - **Connexion en tant qu'infemier :**

- **Connexion en tant qu'Administrateur :**


## Licence

Distribué sous la licence MIT. Voir `LICENSE` pour plus d'informations.
