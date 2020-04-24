package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import model.Square;

@WebServlet("/battlefield")
public class Battlefield extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter())
        {
            Database database = Database.get();
            List<Square> squares = database.getSquares();
            String jsonResponse = squares.stream()
                                    .map(Square::toString)
                                    .collect(Collectors.joining(", ", "[", "]"));
            writer.print(jsonResponse);
        }
        catch (SQLException e)
        {
            response.sendError(500);
        }
    }

}