# Menufit — Product Design Document

## 1. Philosophie de l'app

**Trois principes non-négociables :**
- **Local-first absolu** — l'app fonctionne intégralement sans connexion. Le réseau n'est jamais un prérequis.
- **Zéro friction cognitive** — un débutant ne doit jamais se sentir perdu ou jugé. Chaque écran a un seul objectif.
- **Respect total** — pas de pub, pas de dark patterns, pas de notifications agressives. L'utilisateur est maître de son expérience.

---

## 2. Carte des écrans

```
MENUFIT
│
├── Accueil
│   ├── Barre de recherche
│   ├── Continuer une recette (si en cours)
│   ├── Collections de l'utilisateur
│   └── Recettes suggérées (basées sur l'historique local)
│
├── Explorer
│   ├── Par catégorie (Entrées, Plats, Desserts, Sauces…)
│   ├── Par technique (Rôtir, Mijoter, Sauter…)
│   ├── Par durée (< 20 min, < 45 min, + d'1h)
│   └── Par difficulté (Facile, Intermédiaire, Avancé)
│
├── Recette
│   ├── Vue résumé (photo, infos, ingrédients scalables)
│   └── Mode Cuisine (plein écran, étapes, timers)
│
├── Ma Cuisine (espace perso)
│   ├── Mes recettes sauvegardées
│   ├── Mes collections
│   ├── Mon historique
│   └── Import de recette
│
└── Paramètres
    ├── Portions par défaut
    ├── Langue
    ├── Thème (clair / sombre)
    └── Backup & restore (Premium)
```

---

## 3. Écrans détaillés

### 3.1 — Accueil

L'accueil est **contextuel et personnel**, pas un catalogue générique.

**Composants :**
- En-tête sobre : logo + icône profil
- Barre de recherche proéminente (recherche fulltext locale, instantanée)
- Carte "Reprendre" si une recette est en cours (persistée localement)
- Section "Vos collections" (horizontale, scrollable)
- Section "Suggestions" basée sur l'historique de consultation local — pas d'algorithme réseau, juste SQLite

**UX rules :**
- Aucun contenu ne charge depuis internet au démarrage
- Pas de splash screen inutile — app prête en < 300ms
- La recherche filtre en temps réel dès le premier caractère

---

### 3.2 — Fiche Recette

C'est l'écran central. Il a deux modes.

**Mode Lecture (avant de cuisiner)**

```
┌─────────────────────────────────┐
│  [Photo plein largeur]          │
│                                 │
│  Bœuf Bourguignon               │
│  ★★☆  Intermédiaire · 2h30     │
│  ──────────────────────────     │
│  [  2 pers  ] [  4 pers  ] [6] │  ← selector portions
│                                 │
│  INGRÉDIENTS                    │
│  • 800g bœuf à braiser          │
│  • 75cl vin rouge               │
│  • 200g lardons                 │
│  …                              │
│                                 │
│  TECHNIQUES UTILISÉES           │
│  [Rissoler] [Mijoter]           │  ← tags cliquables → explications
│                                 │
│  ┌──────────────────────────┐   │
│  │  ▶  Commencer à cuisiner │   │
│  └──────────────────────────┘   │
└─────────────────────────────────┘
```

**Détails importants :**
- Le scaling des portions est **instantané et local** — tous les grammes se recalculent sans réseau
- Les tags de techniques (Rissoler, Mijoter…) ouvrent une mini-fiche explicative — capital pour les débutants
- Le bouton CTA est unique et clair

---

**Mode Cuisine (plein écran)**

C'est là que Menufit se distingue vraiment.

```
┌─────────────────────────────────┐
│  Étape 3 / 8          [Quitter] │
│  ─────────────────────────────  │
│                                 │
│                                 │
│   Faire revenir les lardons     │
│   dans une cocotte à feu vif    │
│   jusqu'à ce qu'ils soient      │
│   bien dorés (environ 5 min).   │
│                                 │
│                                 │
│  ┌──────────────────────────┐   │
│  │  ⏱  5:00   [Démarrer]   │   │  ← timer intégré à l'étape
│  └──────────────────────────┘   │
│                                 │
│  ◀ Précédent       Suivant ▶   │
└─────────────────────────────────┘
```

**UX rules critiques :**
- **Écran toujours allumé** (WakeLock) pendant le mode cuisine
- Navigation au **swipe horizontal** entre les étapes
- Texte en **grand** (min 18sp) — les mains sont sales, l'utilisateur est loin
- Timer par étape — tap pour démarrer, notification locale à la fin
- Indicateur de progression clair en haut
- Fond neutre, zéro distraction

---

### 3.3 — Import de recette (Premium)

Trois sources d'import :

**URL** → scraper local (via WebView headless ou HTTP simple) → parsing structuré → proposition à l'utilisateur pour validation avant sauvegarde

**Photo / scan** → OCR → parsing → validation

**Saisie manuelle** → formulaire guidé étape par étape (pas un grand formulaire monolithique)

La validation avant sauvegarde est obligatoire — l'utilisateur voit exactement ce qui sera stocké.

---

### 3.4 — Collections

L'utilisateur organise ses recettes en collections libres :

- "Recettes du dimanche"
- "À essayer"
- "Recettes de maman"
- Etc.

Une recette peut appartenir à plusieurs collections. Tout est local, ordonnable par drag & drop.

---

## 4. Système de données (SQLite local)

```sql
Recipe
  id, title, description, difficulty, duration_minutes,
  servings_base, photo_path, source_url, created_at, updated_at

Ingredient
  id, recipe_id, name, quantity, unit, sort_order

Step
  id, recipe_id, instruction, duration_seconds, sort_order

Technique
  id, name, description, video_path (offline)

RecipeTechnique
  recipe_id, technique_id

Collection
  id, name, color, created_at

CollectionRecipe
  collection_id, recipe_id, sort_order

History
  id, recipe_id, opened_at, cooked_at (nullable)
```

**Principes :**
- Tout est indexé pour la recherche fulltext (FTS5)
- Les photos sont stockées localement dans le dossier app
- Aucune donnée ne quitte l'appareil sans action explicite de l'utilisateur

---

## 5. Modèle Freemium

### Gratuit — complet et utilisable
- Bibliothèque illimitée de recettes intégrées
- Sauvegarde, collections, historique
- Mode cuisine complet
- Scaling des portions
- Import manuel de recettes (5 max)

### Premium — one-shot (~2–4€) ou abonnement (~0,99€/mois)
- Import URL illimité
- Import photo / OCR
- Planificateur de repas (semaine)
- Liste de courses générée
- Export PDF
- Backup/restore chiffré
- Thèmes visuels additionnels

**Règle d'or :** le gratuit ne doit jamais frustrer. Les features premium sont des **ajouts**, jamais des amputations du core.

---

## 6. Design visuel — direction

**Palette :** tons chauds et naturels — crème, terracotta doux, vert sauge. Évoque la cuisine sans être cliché.

**Typographie :** une serif élégante pour les titres de recettes, une sans-serif lisible pour le corps et les instructions.

**Iconographie :** ligne fine, cohérente, jamais de stock icons reconnaissables.

**Animations :** subtiles et fonctionnelles — transition vers le mode cuisine (zoom doux), timer qui pulse, étapes qui glissent. Jamais décoratif pour le plaisir.

---

## Prochaine étape

Structure de projet initialisée. Prêt pour le développement ! 🚀