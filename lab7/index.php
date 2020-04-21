<?php
include "header.php"
?>
    <br/>
    <label for="filter">Filter: </label><input type="text" id="filter"/>
    <button onclick="filter()">Filter</button>
    <p>Last filter: <span id="lastFilter"></span></p>
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
    <script>
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
                tr.click(() => window.location.href = "read.php?id=" + row['id'])
                table.append(tr)
            }
        }

        function filter()
        {
            let type = $('#filter').val();
            let lastFilterSpan = $('#lastFilter');
            if (type)
            {
                setCookie("lastFilter", type, 10)
                lastFilterSpan.text(type)
                $.getJSON("api.php", {
                    action: "filterRecipes",
                    type: type
                }, (data) => populateTable(data))
                return
            }
            lastFilterSpan.text(getCookie('lastFilter'))
            $.getJSON("api.php", {action: "getAllRecipes"}, (data) => populateTable(data))
        }

        function getCookie(cname)
        {
            let name = cname + "=";
            let decodedCookie = decodeURIComponent(document.cookie);
            let cookieStrings = decodedCookie.split(';');
            for (let i = 0; i < cookieStrings.length; i++)
            {
                let cookieString = cookieStrings[i];
                while (cookieString.charAt(0) === ' ')
                {
                    cookieString = cookieString.substring(1);
                }
                if (cookieString.indexOf(name) === 0)
                {
                    return cookieString.substring(name.length, cookieString.length);
                }
            }
            return "";
        }

        function setCookie(cname, cvalue, exdays)
        {
            let date = new Date();
            date.setTime(date.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + date.toUTCString();
            document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
        }

        filter()
    </script>
<?php

include "footer.php"
?>