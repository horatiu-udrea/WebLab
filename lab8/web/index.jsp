<%@ page import="database.Database" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int playerNumber = 0;
    String playing = "true";
    int phase = -2;
    String boats = "\"\"";
    try
    {
        Database database = Database.get();
        String session1 = database.getPlayer1SessionId();
        String session2 = database.getPlayer2SessionId();
        String currentSessionId = request.getSession().getId();

        if (session1.isEmpty())
        {
            database.setPlayer1SessionId(currentSessionId);
            playerNumber = 1;
        }
        else if (currentSessionId.equals(session1))
        {
            playerNumber = 1;
            phase = database.getPhase(1);
        }
        else if (session2.isEmpty())
        {
            database.setPlayer2SessionId(currentSessionId);
            playerNumber = 2;
        }
        else if (currentSessionId.equals(session2))
        {
            playerNumber = 2;
            phase = database.getPhase(2);
        }
        else
        {
            playing = "false";
        }

        if (phase == 0 || phase == 1)
        {
            boats = database.getPlayerShips(playerNumber);
        }
    }
    catch (SQLException e)
    {
        response.sendError(500);
        return;
    }

%>
<html>
<head>
    <title>Battleships</title>
    <link rel="stylesheet" href="style.css"/>
    <script src="https://code.jquery.com/jquery-3.5.0.min.js"
            integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
            crossorigin="anonymous"></script>
    <script src="script.js"></script>
</head>
<body>
    <div class="container">
        <h1 id="announcer">Here comes the important message</h1>

        <p class="label">My board</p>
        <table class="board" id="myboard"></table>
        <br/>
        <p class="label">Attack board</p>
        <table class="board" id="attackboard"></table>
        <br />
        <button onclick="resetGame()">Reset game</button>
    </div>

    <script>
        let playing = <% out.print(playing);%>;
        let player = <% out.print(playerNumber);%>;
        let phase = <% out.print(phase);%>;
        <%--let boats = <% out.print(boats);%>;--%>

        if (playing)
        {
            announce("You are player" + player + "!<br />Place your ships!")
            createBoards(myboard, "#myboard")
            createBoards(attackboard, "#attackboard")
            if (phase === 0)
            {
                $(document).ready( () => phase0())
            } else if (phase === 1)
            {
                $(document).ready( () => phase1())
            }
        } else
        {
            announce('The game is currently ongoing. <br />You must wait for it to finish in order to join.');
            $('.label').css('display', 'none')
        }

    </script>
</body>
</html>
