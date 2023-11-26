# Projet: Conception et développement d'un site web pour la gestion des opérations de don de sang

## Authors
- [@Alouache Hafsa](https://wwwgitgithub.com/octokatherine)
- [@Allali Hajar](https://www.github.com/octokatherine)
- [@Bamouh Imane](https://www.github.com/octokatherine)
- [@Belyamani Meryem](https://www.github.com/octokatherine)

![left 100%](https://github.com/bamouhimane/Blood_donation/assets/125123163/ca4bc8cd-f89a-4808-bef9-ae074ae0db75)

_`Début de l'imlementation du code de projet le 08/11/2023`_

_`Fin de projet le 25/11/2023`_
## Table des Matières
- [Introduction](#Introduction)
- [Analyse et conception de projet](#Analyse-et-conception-de-projet)
    - [Besoins fonctionnels du système](#Besoins-fonctionnels-du-système)
      - [Description des utilisateurs](#Description-des-utilisateurs)
      - [Fonctionnalités attendues du système](#Fonctionnalités-attendues-du-système)
    - [Conception de la Base de Données](#Conception-de-la-Base-de-Données)
        - [les tables utilsées](#les-tables-utilsées)
        - [Règles de gestion des tables](#Règles-de-gestion-des-tables)
        - [Modèle conceptuel de données](#Modèle-conceptuel-de-données)
    - [Aspect sécurité du système](#Aspect-sécurité-du-système )
- [Techniques et réalisation](#Techniques-et-réalisation)
    - [Les solutions logicielles](#Les-solutions-logicielles)
        - [Environnement de dévelopement](#Environnement-de-dévelopement)
        - [Gestion des données](#Gestion-des-données)
    - [Design des interfaces](#Design-des-interfaces)
- [Licence](#Licence)

---

## Introduction
Dans un contexte où chaque goutte de sang est cruciale pour sauver des vies, les opérations de don de sang sont souvent confrontées à des obstacles logistiques, des délais d'attente excessifs, et des problèmes d'organisation.

Pour résoudre ces défis, notre équipe s'est engagée dans la création d'un site web novateur dédié à la gestion efficace des opérations de don de sang. 

Notre projet vise à simplifier et optimiser le processus en fournissant une plateforme conviviale qui facilite la coordination entre les hôpitaux, les banques de sang et les donneurs, surmontant ainsi les contraintes temporelles et organisationnelles qui peuvent décourager même les volontaires les plus dévoués.

## Analyse et conception de projet
Dans cette partie, nous présentons une analyse des objectifs, des exigences et des contraintes du système. Cette analyse nous a permis de mieux comprendre les besoins des utilisateurs (Employés, administration) pour proposer une solution adaptée. 

Ensuite, nous présentons l’architecture conçue, les modules et la base de données du système. Ces étapes préliminaires ont posé les fondements d’un système opérationnel et performant

### Besoins fonctionnels du système

  - **Description des utilisateurs** :
    
Les utilisateurs du système de gestion des opéerations de don de snag sont les employés et l’administration.
Chacun de ces acteurs joue un rôle spécifique dans le processus de gestion de notre site. 

- Employés : les employés sont divisés en deux parties :
  
    - responsbales de reception : qui sont conçu pour la gestion des comptes des donneurs de sang, ils ajoutent les donneurs et ils ont la main aussi sur toute modification ou suppression et meme consultation des comptes ajoutés. 
    - infermiers : qui sont conçu pour la gestion des comptes des demandeurs de sang, ils ajoutent les demandeurs et peuvent effectués des opérations comme la modification, la suppression, la consultation des ces comptes, ils peuvent aussi voir l'etat de la demande à chaque moment si elle est assurée ou bien encore en cours . 

- Administration : L’administration gère les comptes des responsables de reception et les infermiers, et donne accès à ces employès au site web en leur offrant un nom d'utilsateur et un mot de passe pour que chacun aura un environnement de travail unique.

  - **Fonctionnalités attendues du système**

Afin d'avoir un site web performant, les fonctionnalités attendues seront soigneusement intégrées pour répondre à l'objectif de conception de se système. Ces fonctionnalités sont: 
- L’administration :
    - Création et suppression des comptes pour les responsables de reception et les infermiers. 
Les responsables de reception :
    - Traitement des opérations de don de sang.
    - Avoir accès à son propre profil où il peut ajouter les demandes de don, les suprrimées, modifiées ainsi que les consultées. 
- Les infermiers :
    - Traitement des opérations de demandes de sang. 
    - Avoir accès à leur propre profil où ils peuvent ajouter les demandes de don, les suprrimées, modifiées ainsi que les consultées.

### Conception de la Base de Données

  - **les tables utilsées**
  
| <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/4739d6c1-2658-42ed-a67b-36d7cb0814bb" width="100%" alt="image-1"> | <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/66057ec5-6b13-4f32-afa8-a911888f3612" width="100%" alt="image-2"> |
| --- | --- |

<p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/26443a53-c533-464f-a322-7d9a84824c98" width="50%" alt="image-3">
</p>

  - **Règles de gestion des tables :**

Voici les règles de gestion de notre système:

TABLE login_users:
- Chaque utilisateur(infirmier ou responsable) doit avoir un CNIE et username unique,se sont la clé primaire de la table.
- Les colonnes :username,password,function,nome et prenom ne doivent pas etre nuls.
- Les colonnes :creation_date et last_modified sont remplis une fois creation de compte respectivement modification.

TABLE login_admin:
- la colonne id represente l'identifiant de l'administrateur censé gérer les comptes des employés(infirmiers et responsables),la colonne username_ad est aussi unique.Ces deux représentent la clé primaire de la table.
- les colonnes username_ad et password_ad ne doivent pas etre nuls.

TABLE centre:
- la colonne idcentre est la clé primaire de la table .
- Les colonnes :nom(nom du centre),adresse et tel ne doivent pas etre nuls.
- La colonne: trois_prem_car ,represente la chaine que chaque centre est censé l'utiliser en début pour construire les mots de passe de ces employés,pour ainsi garder l'appartenance à chaque centre.

TABLE operation_demande :
- la colonne idoperation représente la clé primaire de la table.
- la colonne : iddemandeur est la clé etrangère qui référencie la colonne iddemandeur (clé primaire) dans la table demandeur.
	=> un demandeur peut faire plusieurs opérations de demande dans son centre,alors que une opération de demande appartient à un seul demandeur.
- la colonne date_demande et quantite_sang sont remplis une fois création de demande (remplissage de formulaire de demande).
- la colonne etat_demande représente l'état de la demande effectuée en comparant la quantité disponible du sang avec celle demandée par l'infirmier pour un patient donné,elle peut prendre deux états  : assurée ou en cours.
- la colonne id_centre représente  la clé etrangère de cette table qui référencie la colonne idcentre dans la table centre.
	=> les opérations de demande peuvent etre effectués dans différents centres ,alors qu'une opération selon son id appartient à un seul centre. 

TABLE demandeur:
- la colonne iddemandeur et CNIE représentent la clé primaire de la table.
- Les autres colonnes ne doivent pas etre nuls.
	=> un demandeur peut effectuer plusieurs opérations de demandes mais dans le meme centre.


 
TABLE operation_don :
- la colonne id représente la clé primaire de la table.
- la colonne : id_donneur est la clé etrangère qui référencie la colonne iddonneur (clé primaire) dans la table donneur.
	=> un donneur peut faire plusieurs opérations de don,alors qu'une opération de don appartient à un seul donneur.
- les colonne date_don et quantite_sang donnée sont remplis une fois le don effectué (remplissage de formulaire de don).
- la colonne id_centre représente  la clé etrangère de cette table qui référencie la colonne idcentre dans la table centre.
	=> les opérations de don peuvent etre effectuées dans plusieurs centres,alors qu'une opération de don appartient à un seul centre.

TABLE donneur :
- la colonne idonneur représente la clé primaire de la table.
	=> un donneur peut faire plusieurs opérations de don,alors que une opération de don appartient à un seul doneur.
- la colonne date_demande et quantite_sang sont remplis une fois création de demande (remplissage de formulaire de demande).
- les autres colonnes représentant les informations des donneurs ne doivent pas etre nuls.

  
    

  - **Modèle conceptuel de données :**
  
 Le MCD, qui fait partie de la méthode Merise, a pour objectif de décrire les données
 utilisées par un système d’information ainsi que leurs relations. Il permet de représenter
 de manière logique les informations en utilisant un ensemble de règles et de diagrammes
 standardisés
 Le modèle conceptuel de données de notre système est représenté dans la figure ci-dessous :

![left 50%](https://github.com/bamouhimane/Blood_donation/assets/125123163/6e738150-b05b-4601-b68c-60b141b6720d)

### Aspect sécurité du système 
Le code proposé vise à renforcer la sécurité de l'application de plusieurs manières :



  - Problème :

Le système actuel stocke les mots de passe de manière non sécurisée dans le fichier config.properties, ce qui expose ces informations sensibles. De plus, la comparaison des mots de passe entrés par les utilisateurs avec ceux stockés dans la base de données n'utilise pas de mécanisme de hachage sécurisé, ce qui peut compromettre la sécurité du système en cas de fuite d'informations.

  - Solution :

Pour renforcer la sécurité du système, la première étape consiste à utiliser des algorithmes de hachage sécurisés pour stocker les mots de passe dans la base de données. Cela empêchera la lecture directe des mots de passe même en cas d'accès non autorisé à la base de données.

La deuxième étape consiste à mettre en œuvre une comparaison sécurisée des mots de passe lors de l'authentification des utilisateurs. Plutôt que de comparer les mots de passe en clair, le système doit comparer le hachage du mot de passe entré par l'utilisateur avec le hachage stocké dans la base de données.Cela implique l'utilisation d'un algorithme de hashage robuste, en l'occurrence le BCRYPT. 




  - Problème : 

Actuellement, la clé de connexion à la base de données est stockée en clair dans le fichier config.properties, exposant potentiellement cette information sensible.

  - Solution :

 Afin de renforcer la sécurité du système, nous avons mis en place un mécanisme de chiffrement pour la clé de connexion à la base de données. Cela implique l'utilisation d'un algorithme de chiffrement robuste, en l'occurrence l'AES (Advanced Encryption Standard), pour protéger la confidentialité de cette clé. Le processus de chiffrement est réalisé lors de la configuration initiale et la clé chiffrée est stockée dans le fichier de configuration config.properties. Ainsi, même si un accès non autorisé au fichier se produisait, la clé de connexion demeurerait sécurisée, car elle serait stockée sous forme chiffrée.

- Implémentation :

 L'algorithme AES a été choisi pour son niveau de sécurité élevé et sa large adoption dans les pratiques de chiffrement. La clé de chiffrement utilisée est elle-même stockée de manière sécurisée, en étant obtenue à partir d'un gestionnaire de clés dédié (key.properties). 

Cette solution garantit que même en cas d'accès aux fichiers de configuration, les informations sensibles, telles que la clé de connexion à la base de données, restent confidentielles grâce à leur chiffrement. Elle contribue ainsi à renforcer la sécurité globale du système en adoptant des bonnes pratiques de gestion des données sensibles.

## Techniques et réalisation

Nous présentons la partie réalisation et implémentation de notre projet. Nous commencerons par présenter les outils de développement utilisés. Ensuite, nous élaborerons une présentation des différentes interfaces graphiques.

### Les solutions logicielles

- **Environnement de dévelopement :**
    - Eclipse Java JEE
 <p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/4795d884-333c-4108-880f-0340c4fec914" width="20%" alt="image-7">
</p>
    
- **Gestion des données :**
    - Utilisation de DAO(Data Access Object)pour la manipulation des données.
    - Mise en œuvre de Singletons de Connexion pour assurer une gestion efficace des connexions à la base de données.
    - Intégration de classes CDI pour la gestion de l'injection de dépendances.
      
<p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/3359f7d0-7d9f-46fd-b718-ba1876a1bff7" width="20%" alt="image-8">
</p>
 
**Design des interfaces :**
    - Utilisation de PrimeFaces pour des composants d'interface utilisateur riches.
    - Intégration de Bootstrap pour la conception et la mise en page responsives.
    - Personnalisation des interfaces avec HTML et CSS.
<p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/751612a3-c0f1-4c8b-ac28-434c3ea7f637" width="40%" alt="image-8">
</p>


- **Page d'acceuil :**
  
La page d’accueil (voir la figure 1.1) de notre site est conçue pour répondre aux besoins spécifiques de chaque utilisateur. Selon leur rôle, les employés et les administrateurs ont accès à différentes opérations.
La page d’accueil propose deux choix distincts en fonction de l’utilisateur connecté (voir la figure
1.2), permettant une navigation facile et une expérience personnalisée.
De plus, la page d’accueil comprend également une section ”About” (voir la figure 1.3) qui fournit des
informations sur nous et sur le site, son objectif et son fonctionnement, et une section de Team  (voir la figure 1.4) qui présente notre équipe et leurs comptes sur les réseaux sociaux; ainsi qu'une derniere section de contact qui fournit des informations comme notre mails, numeros de telephone et adresse en cas de vouloir envoyé une reclamation (voir la figure
1.5).

| ![image](https://github.com/bamouhimane/Blood_donation/assets/125123163/6ffefd0b-4c8b-442d-9038-c0c7ae601567) | ![image2](https://github.com/bamouhimane/Blood_donation/assets/125123163/964d65ae-a1ff-430e-b3e3-0c70c2f4067a) |
| --- | --- |
| **(figure 1.1)** | **(figure 1.2)** |

| ![image3](https://github.com/bamouhimane/Blood_donation/assets/125123163/b9448ea5-551c-4e21-bbc0-7b74cc666ba0) | ![image4](https://github.com/bamouhimane/Blood_donation/assets/125123163/47b661b2-910e-4a93-bc05-a7362013aacf) |
| --- | --- |
| **(figure 1.4)** | **(figure 1.3)** |


  <p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/e9685cec-d970-417b-8cca-aeb77b05da39" width="70%" alt="Description de l'image">
</p>

<p align="center"><strong>(Figure 1.5)</strong></p>


- **Page d'Authentification :**

 Pour accéder à la plateforme, les employé devront saisir leur nom d'utilsateur et leur mot
 de passe, puis cliquer sur le bouton ”login” (voir la figure 2.1). Une fois connectés,
les utilisateurs seront redirigés vers l'environnement des responsbales de réception ou des infermiers selon les données saisies lors d'authentification, ainsi avoir un contrôle total sur leur session.
Pour les administrateurs, ils ont une page d'authentification unique qui les redirige vers l'environnement d'adminsitration ainsi avoir de meme un contrôle total sur leur session.

 <p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/0aeb8f06-d153-4497-9bba-d69a6b3b4b5f" width="70%" alt="Description de l'image">
</p>
<p align="center"><strong>(Figure 2.1)</strong></p>

 
- **Connexion en tant que employés :**
  
  Après une authentification réussie, l'employé selon les informations saisies (nom de l'utilisateur, mot de passe)  sera redirigé vers l'interface qui lui correspond :
  
    - **Connexion en tant qu'infirmier :**
      
   Une fois authentifié, l'infirmier aura la main sur la gestion des opérations de demandes de sang, il sera redirigé en premier vers la page de consultation de tous les demandes effectués notant que ces demandes concernent tous les centres (voir la figure 4-1).
- l'infirmier peut ajouter une demande en remplissant tous les champs necessaires sachant qu'il ne faut pas ajouter un demandeur déja existant dans le meme jour (voir la figure 4-2).
- l'infirmier peut aussi modifier les données d'une demande, il est important de noter qu'il n'aura la main que sur les demandes de son centre (voir la figure 4-3).
- l'infirmier peut consulter les demandes qui sont traitées et celles qui sont en cours afin d'avoir une gestion efficace des demandes de sang (voir la figure 4-4).

| ![image](https://github.com/bamouhimane/Blood_donation/assets/125123163/a8775d90-aafe-4465-8b64-763be7aeea9b) | ![image2](https://github.com/bamouhimane/Blood_donation/assets/125123163/fa58f7a0-5d1b-465e-bb48-766248d2c89f) |
| --- | --- |
| **(figure 4.1)** | **(figure 4.2)** |

| ![image3](https://github.com/bamouhimane/Blood_donation/assets/125123163/f738b0a6-89a7-484e-8a20-7e2abc788a4f) | ![image4](https://github.com/bamouhimane/Blood_donation/assets/125123163/85507a7f-eadf-44db-abfa-72e3acf2ed51) |
| --- | --- |
| **(figure 4.3)** | **(figure 4.4)** |



  - **Connexion en tant que responsable de reception :**
    
     Une fois authentifié, le responsable de reception aura la main sur la gestion des opérations de don de sang, il sera redirigé en premier vers la page de consultation de tous les dons effectués notant que ces dons concernent tous les centres (voir la figure 5-1).
    - le responsable de reception peut modifier les données d'une opération de don, il est important de noter qu'il n'aura la main que sur les données de dons de sang de son centre (voir la figure 5-2).
- le responsable de reception peut ajouter une opération de don en remplissant tous les champs necessaires sachant qu'il ne faut pas ajouter un opération de don qui existe déja dans le meme jour (voir la figure 5-3).

| ![image](https://github.com/bamouhimane/Blood_donation/assets/125123163/a993c540-e342-4a89-82f0-5948c2d51d28) | ![image2](https://github.com/bamouhimane/Blood_donation/assets/125123163/063e4ad1-b0c4-44eb-904d-b6fc64354c60) |
| --- | --- |
| **(figure 5.1)** | **(figure 5.2)** |

<p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/fc50c653-eeff-43a2-b80b-d55647492746" width="60%" alt="Description de l'image">
</p>

<p align="center"><strong>(Figure 1.5)</strong></p>

- **Connexion en tant qu'Administrateur :**
  
Après une authentification réussie, l'administrateur peut accéder à l'espace de gestion des employés pour faire la création,la modification,la consultation ou la suppression.
 <p align="center">
  <img src="https://github.com/bamouhimane/Blood_donation/assets/125123163/40ee0dc4-27fa-46cc-864a-17ae2999c875" width="70%" alt="Description de l'image">
</p>
  <p align="center"><strong>(Figure 2.1)</strong></p>

- Création d'un nouveau compte: l'administrateur peut créer un compte si il a rempli tous les champs et si le nom d'utilisateur et le mot de passe sont uniques(n'existent pas déjà dans la base de données),sinon une erreur s'affiche.(voir la figure 3-2)
- Modification d'un compte: l'administrateur peut modifier un compte si il rempli tous les champs et si le nom d'utilisateur et le CNIE existe déjà dans la base de données,sinon une erreur s'affiche.
- Suppression d'un compte: afin de supprimer un compte,l'administrateur doit entre le CNIE sécifique à ce compte qui doit etre déjà existant.(voir la figure 3-3)
- Consultation : l'administrateur peut consulter les  comptes existants dans la base de données (voir la figure 3-4).Il est possible de saisir le CNIE d'un compte pour voir ces détails(voir la figure 3-5).

| ![image](https://github.com/bamouhimane/Blood_donation/assets/125123163/6f55471f-47c7-41d6-a65b-27763639d419) | ![image2](https://github.com/bamouhimane/Blood_donation/assets/125123163/9993d7e7-8e10-47ef-917d-87e323255896) |
| --- | --- |
| **(figure 3.2)** | **(figure 3.3)** |

| ![image3](https://github.com/bamouhimane/Blood_donation/assets/125123163/e8c94909-9a51-459d-9c15-cd8eaf3cb387) | ![image4](https://github.com/bamouhimane/Blood_donation/assets/125123163/d2a053e6-51cc-4dc5-ab2b-5ea71e9bf83b) |
| --- | --- |
| **(figure 3.4)** | **(figure 3.5)** |


## Licence

Distribué sous la licence MIT. Voir `LICENSE` pour plus d'informations.
