<?php
include "header.php"
?>
    <p><label for="id">Id: </label><input type="text" id="id"/></p>
    <p><label for="name">Name: </label><input type="text" id="name"/></p>
    <p><label for="type">Type: </label><input type="text" id="type"/></p>
    <p><label for="author">Author: </label><input type="text" id="author"/></p>
    <p><label for="description">Description: </label><input type="text" id="description"/></p>
    <button onclick="addRecipe()">Add recipe</button>
    <br/>
    <span id="added"></span>

    <script>
        function addRecipe()
        {
            $.ajax({
                type: "GET",
                url: "api.php",
                data: {
                    action: "addRecipe",
                    id: $('#id').val(),
                    name: $('#name').val(),
                    type: $('#type').val(),
                    author: $('#author').val(),
                    description: $('#description').val(),
                },
                complete: () => $('#added').text("Added!")
            })
        }
    </script>
<?php
include "footer.php"
?>