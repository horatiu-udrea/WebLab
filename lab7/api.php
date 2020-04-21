<?php
require_once "Recipe.php";
require_once "DatabaseRepository.php";

/**
 * @return bool
 */
function check($parameter)
{
    return isset($_GET[$parameter]) && !empty($_GET[$parameter]);
}

if (isset($_GET['action']) && !empty($_GET['action']))
{
    $action = $_GET['action'];

    if($action == 'getAllRecipes')
    {
        $repository = new DatabaseRepository();
        header('Content-Type: application/json');
        echo json_encode($repository->getAllRecipes());
        return;
    }
    if($action == 'getRecipe')
    {
        if (check('id'))
        {
            $id = $_GET['id'];
            $repository = new DatabaseRepository();
            header('Content-Type: application/json');
            echo json_encode($repository->getRecipe($id));
            return;
        }
    }
    if($action == 'filterRecipes')
    {
        if (check('type'))
        {
            $type = $_GET['type'];
            $repository = new DatabaseRepository();
            header('Content-Type: application/json');
            echo json_encode($repository->filterRecipes($type));
            return;
        }
    }
    if($action == 'deleteRecipe')
    {
        if (check('id'))
        {
            $id = $_GET['id'];
            $repository = new DatabaseRepository();
            header('Content-Type: application/json');
            $repository->delete($id);
            return;
        }
    }
    if($action == 'updateRecipe')
    {
        if (check('id') && check('name') && check('type') && check('author') && check('description'))
        {
            $id = $_GET['id'];
            $name = $_GET['name'];
            $type = $_GET['type'];
            $author = $_GET['author'];
            $description = $_GET['description'];
            $recipe = new Recipe($id, $name, $type, $author, $description);

            $repository = new DatabaseRepository();
            header('Content-Type: application/json');
            $repository->update($recipe);
            return;
        }
    }
    if($action == 'addRecipe')
    {
        if (check('id') && check('name') && check('type') && check('author') && check('description'))
        {
            $id = $_GET['id'];
            $name = $_GET['name'];
            $type = $_GET['type'];
            $author = $_GET['author'];
            $description = $_GET['description'];
            $recipe = new Recipe($id, $name, $type, $author, $description);

            $repository = new DatabaseRepository();
            header('Content-Type: application/json');
            $repository->add($recipe);
            return;
        }
    }
    http_response_code(404);
}
http_response_code(404);
