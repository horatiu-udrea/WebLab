<?php
include "header.php"
?>
    <p class="read">ID: <span id="id"></span></p>
    <p class="read">Name: <span id="name"></span></p>
    <p class="read">Type: <span id="type"></span></p>
    <p class="read">Author: <span id="author"></span></p>
    <p class="read">Description: <span id="description"></span></p>
    <script>
        $.urlParam = function (name)
        {
            var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
            return results[1] || 0;
        }

        function displayData(data)
        {
            $('#id').text(data['id'])
            $('#name').text(data['name'])
            $('#type').text(data['type'])
            $('#author').text(data['author'])
            $('#description').text(data['description'])
        }

        $.getJSON("api.php", {action:"getRecipe", id:$.urlParam('id')}, (data) => displayData(data))
    </script>
<?php
include "footer.php"
?>