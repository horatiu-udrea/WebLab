<?php
include "header.php"
?>
    <table class="paleBlueRows">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Type</th>
            <th>Author</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>

    <p><label for="id">Id: </label><input type="text" id="id" disabled/></p>
    <p><label for="name">Name: </label><input type="text" id="name"/></p>
    <p><label for="type">Type: </label><input type="text" id="type"/></p>
    <p><label for="author">Author: </label><input type="text" id="author"/></p>
    <p><label for="description">Description: </label><input type="text" id="description"/></p>
    <button onclick="updateRecipe()">Update recipe</button>
    <br/>
    <span id="added"></span>
    <br/>


    <script>

        function updateRecipe()
        {
            $.ajax({
                type: "GET",
                url: "api.php",
                data: {
                    action: "updateRecipe",
                    id: $('#id').val(),
                    name: $('#name').val(),
                    type: $('#type').val(),
                    author: $('#author').val(),
                    description: $('#description').val(),
                },
                complete: () =>
                {
                    $('#added').text("Updated!");
                    fetchData()
                }
            })
        }

        function populateFields(data)
        {
            $('#id').val(data['id'])
            $('#name').val(data['name'])
            $('#type').val(data['type'])
            $('#author').val(data['author'])
            $('#description').val(data['description'])
        }

        function populateTable(data)
        {
            let table = $('table tbody')
            table.empty()
            for (let row of data)
            {
                let tr = $('<tr>')
                tr.append($('<td>').text(row['id']))
                tr.append($('<td>').text(row['name']))
                tr.append($('<td>').text(row['type']))
                tr.append($('<td>').text(row['author']))
                tr.click(() => populateFields(row))
                table.append(tr)
            }
        }

        function fetchData()
        {
            $.getJSON("api.php", {action: "getAllRecipes"}, (data) => populateTable(data))
        }

        fetchData();
    </script>
<?php

include "footer.php"
?>