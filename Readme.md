# SmartShop

## Contexte
SmartShop est une application web de gestion commerciale destinée à **MicroTech Maroc**, distributeur B2B de matériel informatique basé à Casablanca.  
Elle permet de gérer un portefeuille de 650 clients actifs avec un système de fidélité à remises progressives et des paiements fractionnés multi-moyens par facture.  
Le système assure une traçabilité complète de tous les événements financiers via un historique immuable et optimise la gestion de la trésorerie.

**Notes importantes :**
- Application purement backend REST (API uniquement)
- Pas d'interface graphique
- Tests et démonstrations via Postman ou Swagger
- Authentification par HTTP Session (login/logout)
- Pas de JWT, Pas de Spring Security
- Gestion des rôles :
    - **ADMIN** : employé MicroTech utilisant SmartShop
    - **CLIENT** : entreprises clientes

---

## Exigences Fonctionnelles

### 1. Gestion des Clients
- CRUD complet (Créer, Consulter, Modifier, Supprimer)
- Suivi automatique :
    - Statistiques : nombre total de commandes et montant cumulé
    - Date de première et dernière commande
    - Historique des commandes par client avec :
        - ID commande
        - Date de création
        - Montant total TTC
        - Statut (PENDING, CONFIRMED, CANCELED, REJECTED)

### 2. Système de Fidélité Automatique
- Niveaux basés sur l’historique du client :
    - BASIC : 0 commande
    - SILVER : ≥3 commandes OU ≥1,000 DH
    - GOLD : ≥10 commandes OU ≥5,000 DH
    - PLATINUM : ≥20 commandes OU ≥15,000 DH
- Remises appliquées automatiquement :
    - SILVER : 5% si sous-total ≥ 500 DH
    - GOLD : 10% si sous-total ≥ 800 DH
    - PLATINUM : 15% si sous-total ≥ 1200 DH

### 3. Gestion des Produits
- Ajouter, modifier, supprimer (soft delete si commandes existantes)
- Consultation avec filtres et pagination

### 4. Gestion des Commandes
- Créer des commandes multi-produits
- Validation du stock
- Application des remises fidélité et codes promo
- Calcul automatique :
    - Sous-total HT
    - Montant remise
    - Montant HT après remise
    - TVA 20% (après remise)
    - Total TTC
- Statuts : PENDING, CONFIRMED, CANCELED, REJECTED
- Mise à jour après validation :
    - Décrément stock
    - Actualisation statistiques client
    - Recalcul du niveau fidélité

### 5. Système de Paiements Multi-Moyens
- Moyens : Espèces, Chèque, Virement
- Limite légale : 20,000 DH par paiement
- Paiements fractionnés possibles
- Validation de la commande uniquement après paiement complet

### 6. Règles Métier Critiques
- Validation stock
- Arrondis à 2 décimales
- Codes promo format strict `PROMO-XXXX`
- Taux de TVA configurable
- Une commande doit avoir au moins un client et un produit

---

## Exigences Techniques
- **Backend** : Spring Boot, REST API, Java 8+
- **Base de données** : MySQL ou PostgreSQL
- **ORM** : Spring Data JPA/Hibernate
- **Tests** : JUnit, Mockito
- **Architecture** : Controller-Service-Repository-Entity-DTO-Mapper
- Validation via annotations (@Valid, etc.)
- Gestion centralisée des exceptions (@ControllerAdvice)
- Lombok et Builder Pattern
- MapStruct pour conversion Entity ↔ DTO
- Concepts Java : Stream API, Java Time API, Lambda expressions
- Authentification : HTTP Session

---

## Modèle de Données (Extrait)
- **User** : id, username, password, role (ADMIN/CLIENT)
- **Client** : id, nom, email, niveau de fidélité
- **Product** : id, nom, prix unitaire, stock disponible
- **Commande** : id, client, liste d’articles, sous-total, remise, TVA, total, code promo, statut, montant_restant
- **OrderItem** : id, produit, quantité, prix unitaire, total ligne
- **Paiement** : id, id_commande, numéro, montant, type, date_paiement, date_encaissement

**Enums :**
- UserRole: ADMIN / CLIENT
- CustomerTier: BASIC / SILVER / GOLD / PLATINUM
- OrderStatus: PENDING / CONFIRMED / CANCELED / REJECTED
- PaymentStatus: EN_ATTENTE / ENCAISSÉ / REJETÉ

---

## Gestion des Erreurs
- Centralisée avec `@ControllerAdvice`
- Codes HTTP cohérents :
    - 400 → validation
    - 401 → non authentifié
    - 403 → accès refusé
    - 404 → ressource inexistante
    - 422 → règle métier violée
    - 500 → erreur interne
- JSON d’erreur inclut : timestamp, code HTTP, type d’erreur, message, chemin de la requête

---