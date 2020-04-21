<?php

require_once 'Recipe.php';

function newRecipe($row)
{
    return new Recipe($row['id'], $row['name'], $row['type'], $row['author'], $row['description']);
}

class DatabaseRepository
{
    private $host = '127.0.0.1';
    private $db = 'recipes';
    private $user = 'root';
    private $pass = '';
    private $charset = 'utf8';

    private $pdo;
    private $error;

    public function __construct()
    {
        $dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
        $opt = array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES => false);
        try
        {
            $this->pdo = new PDO($dsn, $this->user, $this->pass, $opt);
        } // Catch any errors
        catch (PDOException $e)
        {
            $this->error = $e->getMessage();
            echo "Error connecting to DB: " . $this->error;
        }
    }

    public function getAllRecipes()
    {
        $stmt = $this->pdo->query("SELECT id, name, type, author, description FROM recipes");
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return array_map('newRecipe', $rows);
    }

    public function getRecipe($id)
    {
        $stmt = $this->pdo->query("SELECT id, name, type, author, description FROM recipes where id='" . $id . "'");
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows) != 1)
        {
            http_response_code(404);
            die("No recipe found");
        }
        return newRecipe($rows[0]);
    }

    public function filterRecipes($type)
    {
        $stmt = $this->pdo->query("SELECT id, name, type, author, description FROM recipes WHERE type='" . $type . "'");
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return array_map('newRecipe', $rows);
    }

    public function add(Recipe $recipe)
    {
        $affected_rows = $this->pdo->exec("INSERT into recipes (id, name, type, author, description) values(" . $recipe->id . ",'" . $recipe->name . "','" . $recipe->type . "','" . $recipe->author . "','" . $recipe->description . "');");
        if ($affected_rows != 1)
        {
            http_response_code(404);
            die("Recipe already exists");
        }
    }

    public function delete($id)
    {
        $affected_rows = $this->pdo->exec("DELETE from recipes where id='" . $id . "'");
        if ($affected_rows != 1)
        {
            http_response_code(404);
            die("No recipe found");
        }
    }

    public function update(Recipe $recipe)
    {
        $affected_rows = $this->pdo->exec("UPDATE recipes SET name='" . $recipe->name . "',type='" . $recipe->type . "',author='" . $recipe->author . "',description='" . $recipe->description . "' where id='" . $recipe->id . "'");
        if ($affected_rows != 1)
        {
            http_response_code(404);
            die("No recipe found");
        }
    }
}
