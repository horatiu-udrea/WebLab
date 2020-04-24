package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;

@WebServlet("/turn")
public class Turn extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        int player = Integer.parseInt(req.getParameter("player"));
        try
        {
            Database database = Database.get();
            try(PrintWriter writer = resp.getWriter())
            {
                writer.println(database.getPhase(player));
            }
        }
        catch (SQLException e)
        {
            resp.sendError(500);
        }
    }
}
