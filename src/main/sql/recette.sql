create database recette;
\c recette;

create table users(
    idUser SERIAL PRIMARY KEY NOT NULL,
    nomUser VARCHAR,
    email VARCHAR,
    mdp VARCHAR
);

create table recette (
    idRecette SERIAL PRIMARY KEY NOT NULL,
    idUser INT REFERENCES users(idUser),
    nomRecette VARCHAR,
    descriptions TEXT,
    images VARCHAR,
    duree TIME,
    personne INT
);

create table categorie(
    idCategorie  SERIAL PRIMARY KEY NOT NULL,
    nomCategorie VARCHAR
);

create table recetteCategorie(
    idRecette INT REFERENCES recette(idRecette),
    idCategorie INT REFERENCES categorie(idCategorie)
);

create table etapes (
    idEtape SERIAL PRIMARY KEY NOT NULL,
    nomEtape VARCHAR,
    idRecette INT REFERENCES recette(idRecette)
);

create table enregistrement (
    idUser INT REFERENCES users(idUser),
    idRecette int REFERENCES recette(idRecette)
);

create table commentaire (
    idCommentaires SERIAL PRIMARY KEY NOT NULL,
    idRecette INT REFERENCES recette(idRecette),
    idUser INT REFERENCES users(idUser),
    commentaire TEXT
);

create table Ingredient (
    idIngredient SERIAL PRIMARY KEY NOT NULL,
    nomIngredient VARCHAR,
    idRecette int REFERENCES recette(idRecette),
    quantite DECIMAL(10,2),
    unite VARCHAR
);

create table vue (
    idUser INT REFERENCES users(idUser),
    idRecette INT REFERENCES recette(idRecette)
);

INSERT INTO users (nomUser, email, mdp) VALUES
('Alice Dupont', 'alice@example.com', 'password1'),
('Bob Martin', 'bob@example.com', 'password2'),
('Charlie Durand', 'charlie@example.com', 'password3');


INSERT INTO categorie (nomCategorie) VALUES
('Entrée'),
('Plat Principal'),
('Dessert'),
('Accompagnement'),
('Soupe'),
('Salade'),
('Snack'),
('Petit Déjeuner'),
('Boisson');


INSERT INTO recette (idUser, nomRecette, descriptions, images, duree, personne) VALUES
(1, 'Salade César', 'Une salade fraîche avec du poulet grillé et des croûtons.', 'salade_cesar.jpg', '00:20:00', 2),
(2, 'Bœuf Bourguignon', 'Un classique de la cuisine française avec du bœuf mijoté au vin rouge.', 'boeuf_bourguignon.jpg', '03:00:00', 4),
(3, 'Tarte au citron meringuée', 'Une tarte sucrée avec une garniture au citron et une meringue dorée.', 'tarte_citron.jpg', '01:30:00', 6);

INSERT INTO recetteCategorie (idRecette, idCategorie) VALUES
(1, 6),  
(2, 2),  
(3, 3);  

INSERT INTO etapes (nomEtape, idRecette) VALUES
('Préparer les ingrédients', 1),
('Cuire le poulet', 1),
('Assembler la salade', 1),
('Préparer la marinade', 2),
('Cuire le bœuf', 2),
('Préparer la garniture', 3),
('Cuire la tarte', 3);

INSERT INTO enregistrement (idUser, idRecette) VALUES
(1, 2),  
(2, 3),  
(3, 1);  

INSERT INTO commentaire (idRecette, idUser, commentaire) VALUES
(1, 1, 'Super recette de salade ! Très fraîche et savoureuse.'),
(2, 2, 'Un peu long à préparer mais ça vaut le coup.'),
(3, 3, 'Délicieux ! La meringue est parfaite.');

INSERT INTO Ingredient (nomIngredient, idRecette, quantite, unite) VALUES
('Poulet', 1, 200, 'g'),
('Laitue', 1, 1, 'pièce'),
('Croûtons', 1, 50, 'g'),
('Bœuf', 2, 500, 'g'),
('Vin rouge', 2, 750, 'ml'),
('Citron', 3, 3, 'pièces'),
('Sucre', 3, 200, 'g'),
('Blancs d''œufs', 3, 4, 'pièces');

INSERT INTO vue (idUser, idRecette) VALUES
(1, 1),  
(2, 2),  
(3, 3),  
(1, 2), 
(2, 3);  

select r.idRecette,COALESCE(SUM(v.idUser :: INT),0) as nbVue
from recette r
LEFT JOIN vue v on r.idRecette = v.idRecette
GROUP BY r.idRecette ORDER BY COALESCE(SUM(v.idUser),0) DESC;

create view recetteView as 
SELECT r.*,i.nomIngredient,
i.idIngredient,rc.idCategorie,
c.nomCategorie
FROM recette r
JOIN Ingredient i ON 
i.idRecette = r.idRecette
JOIN recettecategorie rc ON
rc.idRecette = r.idRecette
JOIN categorie c ON 
rc.idCategorie = c.idCategorie;