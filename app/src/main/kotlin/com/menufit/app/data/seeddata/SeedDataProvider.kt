package com.menufit.app.data.seeddata

import com.menufit.app.data.database.entity.IngredientEntity
import com.menufit.app.data.database.entity.RecipeEntity
import com.menufit.app.data.database.entity.StepEntity
import com.menufit.app.data.database.entity.TechniqueEntity
import com.menufit.app.data.database.entity.RecipeTechniqueEntity

object SeedDataProvider {
    fun getRecipes(): List<RecipeEntity> = listOf(
        RecipeEntity(
            id = 1,
            title = "Bœuf Bourguignon",
            description = "Un classique de la cuisine française : bœuf braisé au vin rouge avec oignons grelots, champignons et lardons.",
            difficulty = "Intermédiaire",
            durationMinutes = 150,
            servingsBase = 4,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 2,
            title = "Coq au Vin",
            description = "Poulet cuit lentement au vin rouge de Bourgogne avec champignons, lardons et oignons grelots.",
            difficulty = "Intermédiaire",
            durationMinutes = 120,
            servingsBase = 4,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 3,
            title = "Blanquette de Veau",
            description = "Ragoût classique de veau à la sauce blanche crémeuse avec champignons et oignons.",
            difficulty = "Intermédiaire",
            durationMinutes = 90,
            servingsBase = 4,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 4,
            title = "Sole Meunière",
            description = "Filet de sole délicatement poêlé au beurre noisette. Élégante et rapide.",
            difficulty = "Facile",
            durationMinutes = 20,
            servingsBase = 2,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 5,
            title = "Escalope de Veau Milanaise",
            description = "Escalope panée et dorée, servie avec un trait de citron frais.",
            difficulty = "Facile",
            durationMinutes = 15,
            servingsBase = 2,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 6,
            title = "Pot-au-Feu",
            description = "Le réconfort par excellence : viande de bœuf avec légumes braisés dans un bouillon savoureux.",
            difficulty = "Facile",
            durationMinutes = 180,
            servingsBase = 6,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 7,
            title = "Œufs Bénédict",
            description = "Muffin anglais, œuf poché et sauce hollandaise maison. Parfait pour le brunch.",
            difficulty = "Avancé",
            durationMinutes = 30,
            servingsBase = 2,
            photoPath = null,
            sourceUrl = null
        ),
        RecipeEntity(
            id = 8,
            title = "Sauce Béchamel",
            description = "La base incontournable : mélange de beurre, farine et lait pour une sauce lisse et onctueuse.",
            difficulty = "Facile",
            durationMinutes = 10,
            servingsBase = 4,
            photoPath = null,
            sourceUrl = null
        )
    )

    fun getIngredients(): List<IngredientEntity> = listOf(
        // Bœuf Bourguignon (recipeId = 1)
        IngredientEntity(id = 1, recipeId = 1, name = "Bœuf à braiser", quantity = 800.0, unit = "g", sortOrder = 1),
        IngredientEntity(id = 2, recipeId = 1, name = "Vin rouge de Bourgogne", quantity = 75.0, unit = "cl", sortOrder = 2),
        IngredientEntity(id = 3, recipeId = 1, name = "Lardons", quantity = 200.0, unit = "g", sortOrder = 3),
        IngredientEntity(id = 4, recipeId = 1, name = "Oignons grelots", quantity = 300.0, unit = "g", sortOrder = 4),
        IngredientEntity(id = 5, recipeId = 1, name = "Champignons de Paris", quantity = 250.0, unit = "g", sortOrder = 5),
        IngredientEntity(id = 6, recipeId = 1, name = "Tomate concentrée", quantity = 2.0, unit = "c. à soupe", sortOrder = 6),
        IngredientEntity(id = 7, recipeId = 1, name = "Bouillon de bœuf", quantity = 50.0, unit = "cl", sortOrder = 7),
        IngredientEntity(id = 8, recipeId = 1, name = "Carotte", quantity = 2.0, unit = "pièce", sortOrder = 8),
        IngredientEntity(id = 9, recipeId = 1, name = "Ail", quantity = 2.0, unit = "gousse", sortOrder = 9),
        IngredientEntity(id = 10, recipeId = 1, name = "Laurier", quantity = 1.0, unit = "feuille", sortOrder = 10),
        IngredientEntity(id = 11, recipeId = 1, name = "Thym", quantity = 1.0, unit = "brindille", sortOrder = 11),
        IngredientEntity(id = 12, recipeId = 1, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 12),

        // Coq au Vin (recipeId = 2)
        IngredientEntity(id = 13, recipeId = 2, name = "Poulet fermier", quantity = 1.2, unit = "kg", sortOrder = 1),
        IngredientEntity(id = 14, recipeId = 2, name = "Vin rouge de Bourgogne", quantity = 75.0, unit = "cl", sortOrder = 2),
        IngredientEntity(id = 15, recipeId = 2, name = "Lardons", quantity = 150.0, unit = "g", sortOrder = 3),
        IngredientEntity(id = 16, recipeId = 2, name = "Oignons grelots", quantity = 250.0, unit = "g", sortOrder = 4),
        IngredientEntity(id = 17, recipeId = 2, name = "Champignons", quantity = 200.0, unit = "g", sortOrder = 5),
        IngredientEntity(id = 18, recipeId = 2, name = "Bouillon de volaille", quantity = 25.0, unit = "cl", sortOrder = 6),
        IngredientEntity(id = 19, recipeId = 2, name = "Cognac", quantity = 1.0, unit = "verre", sortOrder = 7),
        IngredientEntity(id = 20, recipeId = 2, name = "Ail", quantity = 2.0, unit = "gousse", sortOrder = 8),
        IngredientEntity(id = 21, recipeId = 2, name = "Laurier", quantity = 1.0, unit = "feuille", sortOrder = 9),
        IngredientEntity(id = 22, recipeId = 2, name = "Thym", quantity = 1.0, unit = "brindille", sortOrder = 10),
        IngredientEntity(id = 23, recipeId = 2, name = "Beurre", quantity = 30.0, unit = "g", sortOrder = 11),
        IngredientEntity(id = 24, recipeId = 2, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 12),

        // Sole Meunière (recipeId = 4)
        IngredientEntity(id = 25, recipeId = 4, name = "Filet de sole", quantity = 2.0, unit = "pièce", sortOrder = 1),
        IngredientEntity(id = 26, recipeId = 4, name = "Beurre", quantity = 50.0, unit = "g", sortOrder = 2),
        IngredientEntity(id = 27, recipeId = 4, name = "Farine", quantity = 2.0, unit = "c. à soupe", sortOrder = 3),
        IngredientEntity(id = 28, recipeId = 4, name = "Citron", quantity = 0.5, unit = "pièce", sortOrder = 4),
        IngredientEntity(id = 29, recipeId = 4, name = "Persil frais", quantity = 1.0, unit = "poignée", sortOrder = 5),
        IngredientEntity(id = 30, recipeId = 4, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 6),

        // Escalope de Veau Milanaise (recipeId = 5)
        IngredientEntity(id = 31, recipeId = 5, name = "Escalope de veau", quantity = 2.0, unit = "pièce", sortOrder = 1),
        IngredientEntity(id = 32, recipeId = 5, name = "Œuf", quantity = 1.0, unit = "pièce", sortOrder = 2),
        IngredientEntity(id = 33, recipeId = 5, name = "Farine", quantity = 3.0, unit = "c. à soupe", sortOrder = 3),
        IngredientEntity(id = 34, recipeId = 5, name = "Chapelure", quantity = 5.0, unit = "c. à soupe", sortOrder = 4),
        IngredientEntity(id = 35, recipeId = 5, name = "Beurre", quantity = 30.0, unit = "g", sortOrder = 5),
        IngredientEntity(id = 36, recipeId = 5, name = "Citron", quantity = 0.5, unit = "pièce", sortOrder = 6),
        IngredientEntity(id = 37, recipeId = 5, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 7),

        // Pot-au-Feu (recipeId = 6)
        IngredientEntity(id = 38, recipeId = 6, name = "Viande de bœuf (gîte, poitrine)", quantity = 1.5, unit = "kg", sortOrder = 1),
        IngredientEntity(id = 39, recipeId = 6, name = "Os à moelle", quantity = 500.0, unit = "g", sortOrder = 2),
        IngredientEntity(id = 40, recipeId = 6, name = "Carottes", quantity = 600.0, unit = "g", sortOrder = 3),
        IngredientEntity(id = 41, recipeId = 6, name = "Navets", quantity = 300.0, unit = "g", sortOrder = 4),
        IngredientEntity(id = 42, recipeId = 6, name = "Poireaux", quantity = 300.0, unit = "g", sortOrder = 5),
        IngredientEntity(id = 43, recipeId = 6, name = "Oignons", quantity = 2.0, unit = "pièce", sortOrder = 6),
        IngredientEntity(id = 44, recipeId = 6, name = "Clou de girofle", quantity = 2.0, unit = "pièce", sortOrder = 7),
        IngredientEntity(id = 45, recipeId = 6, name = "Laurier", quantity = 1.0, unit = "feuille", sortOrder = 8),
        IngredientEntity(id = 46, recipeId = 6, name = "Thym", quantity = 1.0, unit = "brindille", sortOrder = 9),
        IngredientEntity(id = 47, recipeId = 6, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 10),

        // Sauce Béchamel (recipeId = 8)
        IngredientEntity(id = 48, recipeId = 8, name = "Beurre", quantity = 30.0, unit = "g", sortOrder = 1),
        IngredientEntity(id = 49, recipeId = 8, name = "Farine", quantity = 30.0, unit = "g", sortOrder = 2),
        IngredientEntity(id = 50, recipeId = 8, name = "Lait entier", quantity = 50.0, unit = "cl", sortOrder = 3),
        IngredientEntity(id = 51, recipeId = 8, name = "Sel et poivre", quantity = 1.0, unit = "à goût", sortOrder = 4),
        IngredientEntity(id = 52, recipeId = 8, name = "Muscade", quantity = 1.0, unit = "pincée", sortOrder = 5)
    )

    fun getSteps(): List<StepEntity> = listOf(
        // Bœuf Bourguignon
        StepEntity(id = 1, recipeId = 1, instruction = "Couper le bœuf en morceaux réguliers d'environ 4-5 cm. Faire dorer les lardons à feu moyen dans une cocotte en fonte pendant 5 minutes jusqu'à ce qu'ils soient bien croustillants. Les retirer avec une écumoire.", durationSeconds = 300, sortOrder = 1),
        StepEntity(id = 2, recipeId = 1, instruction = "Dans la graisse des lardons, faire revenir le bœuf par portions. Ne pas surcharger la cocotte. Bien laisser dorer chaque côté (3-4 minutes par face). Mettre de côté.", durationSeconds = 600, sortOrder = 2),
        StepEntity(id = 3, recipeId = 1, instruction = "Dans la même cocotte, faire revenir les carottes coupées en tronçons, puis les oignons grelots et l'ail. Laisser colorer 5 minutes.", durationSeconds = 300, sortOrder = 3),
        StepEntity(id = 4, recipeId = 1, instruction = "Ajouter la tomate concentrée et bien mélanger. Laisser cuire 2 minutes pour éliminer l'acidité.", durationSeconds = 120, sortOrder = 4),
        StepEntity(id = 5, recipeId = 1, instruction = "Verser le vin rouge et le bouillon. Gratter le fond de la cocotte pour bien déglacer. Porter à ébullition.", durationSeconds = 300, sortOrder = 5),
        StepEntity(id = 6, recipeId = 1, instruction = "Remettre le bœuf et les lardons dans la cocotte. Ajouter le laurier et le thym. Couvrir et enfourner à 160°C (thermostat 5-6) pendant 1h30 à 2 heures.", durationSeconds = 7200, sortOrder = 6),
        StepEntity(id = 7, recipeId = 1, instruction = "30 minutes avant la fin, ajouter les champignons nettoyés et coupés en deux. Bien mélanger.", durationSeconds = 1800, sortOrder = 7),
        StepEntity(id = 8, recipeId = 1, instruction = "Vérifier la cuisson : la viande doit être très tendre. Saler et poivrer à goût. Servir chaud avec du riz, des pâtes ou de la purée.", durationSeconds = 300, sortOrder = 8),

        // Coq au Vin
        StepEntity(id = 9, recipeId = 2, instruction = "Découper le poulet en 8 morceaux (cuisses, cuisses, ailes, poitrine). Sécher avec du papier absorbant.", durationSeconds = 300, sortOrder = 1),
        StepEntity(id = 10, recipeId = 2, instruction = "Faire dorer les lardons à feu moyen dans une cocotte en fonte pendant 5 minutes. Les retirer avec une écumoire.", durationSeconds = 300, sortOrder = 2),
        StepEntity(id = 11, recipeId = 2, instruction = "Dans la graisse des lardons, faire dorer les morceaux de poulet par portions. Bien laisser colorer chaque côté. Mettre de côté.", durationSeconds = 600, sortOrder = 3),
        StepEntity(id = 12, recipeId = 2, instruction = "Verser le cognac dans la cocotte et flamber légèrement. Déglacer avec le vin rouge.", durationSeconds = 120, sortOrder = 4),
        StepEntity(id = 13, recipeId = 2, instruction = "Ajouter le bouillon, les oignons grelots, l'ail écrasé, le laurier et le thym. Remettre le poulet et les lardons. Couvrir et enfourner à 160°C pendant 1 heure.", durationSeconds = 3600, sortOrder = 5),
        StepEntity(id = 14, recipeId = 2, instruction = "30 minutes avant la fin, ajouter les champignons. Bien mélanger.", durationSeconds = 1800, sortOrder = 6),
        StepEntity(id = 15, recipeId = 2, instruction = "Saler et poivrer à goût. Servir chaud avec du riz ou des pâtes.", durationSeconds = 300, sortOrder = 7),

        // Sole Meunière
        StepEntity(id = 16, recipeId = 4, instruction = "Rincer les filets de sole à l'eau froide et les sécher complètement avec du papier absorbant. C'est important pour éviter que le beurre éclaboussé.", durationSeconds = 120, sortOrder = 1),
        StepEntity(id = 17, recipeId = 4, instruction = "Mettre un peu de farine dans une assiette. Saler et poivrer légèrement les filets, puis les passer dans la farine en secouant l'excédent.", durationSeconds = 120, sortOrder = 2),
        StepEntity(id = 18, recipeId = 4, instruction = "Faire fondre le beurre à feu moyen-vif dans une poêle antiadhésive. Lorsqu'il est bien chaud et mousseux (mais pas trop brun), y placer les filets de sole.", durationSeconds = 180, sortOrder = 3),
        StepEntity(id = 19, recipeId = 4, instruction = "Cuire 3-4 minutes par côté. La chair doit être opaque et s'émietter facilement à la fourchette. Ne pas trop cuire sinon la sole devient sèche.", durationSeconds = 420, sortOrder = 4),
        StepEntity(id = 20, recipeId = 4, instruction = "Dresser les filets dans l'assiette. Verser le beurre noisette sur la sole. Presser un trait de citron frais et parsemer de persil ciselé.", durationSeconds = 120, sortOrder = 5),

        // Sauce Béchamel
        StepEntity(id = 21, recipeId = 8, instruction = "Faire fondre le beurre à feu moyen dans une casserole. Ajouter la farine en pluie en remuant constamment avec un fouet pendant 1-2 minutes. On obtient un roux blanc léger.", durationSeconds = 120, sortOrder = 1),
        StepEntity(id = 22, recipeId = 8, instruction = "Verser le lait froid en trois fois en fouettant énergiquement après chaque ajout pour éviter les grumeaux. Continuer à fouetter jusqu'à l'obtention d'une sauce lisse.", durationSeconds = 300, sortOrder = 2),
        StepEntity(id = 23, recipeId = 8, instruction = "Laisser cuire à feu moyen en remuant souvent pendant 5-10 minutes. La sauce doit épaissir et devenir coulante mais encore crémeuse.", durationSeconds = 600, sortOrder = 3),
        StepEntity(id = 24, recipeId = 8, instruction = "Saler, poivrer et ajouter une pincée de muscade râpée. Mélanger. La béchamel est prête à être utilisée.", durationSeconds = 60, sortOrder = 4)
    )

    fun getTechniques(): List<TechniqueEntity> = listOf(
        TechniqueEntity(
            id = 1,
            name = "Rissoler",
            description = "Faire cuire et brunir rapidement à feu vif dans un corps gras chaud (beurre, huile). C'est l'étape qui donne une croûte dorée à la viande ou aux légumes et renforce les saveurs par la réaction de Maillard.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 2,
            name = "Mijoter",
            description = "Cuire lentement à feu doux avec le couvercle, dans un liquide (bouillon, vin, sauce). La température reste juste en-dessous de l'ébullition. Idéal pour les viandes longues fibres et les plats braisés.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 3,
            name = "Déglacer",
            description = "Verser un liquide (vin, bouillon) dans une cocotte ou une poêle chaude pour dissoudre les sucs collés au fond. Cela enrichit la sauce en saveurs.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 4,
            name = "Roux",
            description = "Mélange de beurre et de farine cuit à feu moyen. Sert de base pour épaissir les sauces. Plus blanc, plus savoureux.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 5,
            name = "Flambéer",
            description = "Enflammer un alcool versé sur des aliments pour éliminer l'alcool brut et intensifier les arômes. À faire avec précaution.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 6,
            name = "Pocher",
            description = "Cuire dans de l'eau frémissante (juste avant l'ébullition). Idéal pour les œufs ou les poissons délicats. Le liquide doit frémir sans gros bouillons.",
            videoPath = null
        ),
        TechniqueEntity(
            id = 7,
            name = "Sauter",
            description = "Cuire rapidement à feu moyen-vif dans un peu de beurre ou d'huile. Les aliments doivent sautiller dans la poêle. Parfait pour les légumes et petites pièces de viande.",
            videoPath = null
        )
    )

    fun getRecipeTechniques(): List<RecipeTechniqueEntity> = listOf(
        RecipeTechniqueEntity(recipeId = 1, techniqueId = 1), // Bœuf Bourguignon -> Rissoler
        RecipeTechniqueEntity(recipeId = 1, techniqueId = 2), // Bœuf Bourguignon -> Mijoter
        RecipeTechniqueEntity(recipeId = 1, techniqueId = 3), // Bœuf Bourguignon -> Déglacer
        RecipeTechniqueEntity(recipeId = 2, techniqueId = 1), // Coq au Vin -> Rissoler
        RecipeTechniqueEntity(recipeId = 2, techniqueId = 2), // Coq au Vin -> Mijoter
        RecipeTechniqueEntity(recipeId = 2, techniqueId = 5), // Coq au Vin -> Flambéer
        RecipeTechniqueEntity(recipeId = 4, techniqueId = 7), // Sole Meunière -> Sauter
        RecipeTechniqueEntity(recipeId = 8, techniqueId = 4), // Sauce Béchamel -> Roux
    )
}
