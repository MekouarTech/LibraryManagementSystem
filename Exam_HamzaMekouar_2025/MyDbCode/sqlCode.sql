CREATE DATABASE libraryManagement;
USE libraryManagement;

-- Table: Editeur (One-to-Many with Livre)
CREATE TABLE Editeur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL
);

-- Table: Livre
CREATE TABLE Livre (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(100) NOT NULL,
    annee_publication INT DEFAULT 1,
    nbr_exemplaires INT DEFAULT 1,
    editeur_id INT,
    FOREIGN KEY (editeur_id) REFERENCES Editeur(id) ON DELETE CASCADE
);

-- Table:  Auteur
CREATE TABLE Auteur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    nationalite VARCHAR(50),
    dateNaissance DATE
);

-- Table: Categorie
CREATE TABLE Categorie (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL
);

-- Many-to-Many: Livre_Auteur
CREATE TABLE Livre_Auteur (
    livre_id INT,
    auteur_id INT,
    PRIMARY KEY (livre_id, auteur_id),
    FOREIGN KEY (livre_id) REFERENCES Livre(id) ON DELETE CASCADE,
    FOREIGN KEY (auteur_id) REFERENCES Auteur(id) ON DELETE CASCADE
);

-- Many-to-Many: Livre_Categorie
CREATE TABLE Livre_Categorie (
    livre_id INT,
    categorie_id INT,
    PRIMARY KEY (livre_id, categorie_id),
    FOREIGN KEY (livre_id) REFERENCES Livre(id) ON DELETE CASCADE,
    FOREIGN KEY (categorie_id) REFERENCES Categorie(id) ON DELETE CASCADE
);


-- Insert into Editeur
INSERT INTO Editeur (nom) VALUES
('Gallimard'),
('Penguin Books'),
('HarperCollins'),
('Seuil');

-- Insert into Livre
INSERT INTO Livre (titre, annee_publication, nbr_exemplaires, editeur_id) VALUES
('Le Petit Prince', 1943, 5, 1),
('1984', 1949, 3, 2),
('To Kill a Mockingbird', 1960, 4, 3),
('L’Étranger', 1942, 6, 1),
('Brave New World', 1932, 2, 2);

-- Insert into Auteur
INSERT INTO Auteur (nom, prenom, nationalite, dateNaissance) VALUES
('Saint-Exupéry', 'Antoine de', 'Française', '1900-06-29'),
('Orwell', 'George', 'Britannique', '1903-06-25'),
('Lee', 'Harper', 'Américaine', '1926-04-28'),
('Camus', 'Albert', 'Française', '1913-11-07'),
('Huxley', 'Aldous', 'Britannique', '1894-07-26');

-- Insert into Categorie
INSERT INTO Categorie (nom) VALUES
('Fiction'),
('Science Fiction'),
('Classique'),
('Roman Philosophique');

------ Many-to-Many
--Livre_Auteur
INSERT INTO Livre_Auteur (livre_id, auteur_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(1, 4);

-- Livre_Categorie
INSERT INTO Livre_Categorie (livre_id, categorie_id) VALUES
(1, 1),
(1, 3),
(2, 2),
(2, 3),
(3, 1),
(3, 3),
(4, 4),
(5, 2),
(5, 4);
