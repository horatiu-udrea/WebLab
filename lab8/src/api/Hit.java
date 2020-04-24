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

import static java.lang.Math.abs;

@WebServlet("/hit")
public class Hit extends HttpServlet
{

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException
    {
        try (BufferedReader reader = req.getReader())
        {
            String[] split = reader.readLine().split(";");

            int player = Integer.parseInt(split[0]);
            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);

            Database database = Database.get();
            database.hitSquare(player, x, y);
            database.setPlayerTurn(player, false);
            database.setPlayerTurn(player == 1 ? 2 : 1, true);
        }
        catch (SQLException e)
        {
            resp.sendError(500);
        }
    }
}
