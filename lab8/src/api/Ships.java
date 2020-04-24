package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;

@WebServlet("/ships")
public class Ships extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        int player = Integer.parseInt(req.getParameter("player"));
        try
        {
            Database database = Database.get();
            resp.setContentType("application/json");
            try(PrintWriter writer = resp.getWriter())
            {
                writer.println(database.getPlayerShips(player));
            }
        }
        catch (SQLException e)
        {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException
    {
        try (BufferedReader reader = req.getReader())
        {
            String[] split = reader.readLine().split(";");

            int player = Integer.parseInt(split[0]);
            int x1 = Integer.parseInt(split[1]);
            int y1 = Integer.parseInt(split[2]);
            int x2 = Integer.parseInt(split[3]);
            int y2 = Integer.parseInt(split[4]);

            Database database = Database.get();
            for (int i = 0; i < 4; i++)
            {
                database.setSquareShip(player, x1 + i, y1);
                database.setSquareShip(player, x2, y2 + i);
            }
            database.setPlayerShips(
                    player,
                    String.format(
                            "{boat1x:%d, boat1y:%d, boat2x:%d, boat2y:%d}",
                            x1,
                            y1,
                            x2,
                            y2
                    )
            );
            if(player == 2 && database.getPhase(player) == 1)
            {
                return;
            }
            database.setPlayerTurn(player, player == 1);


        }
        catch (SQLException e)
        {
            resp.sendError(500);
        }
    }
}
