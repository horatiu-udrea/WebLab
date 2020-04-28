using System;
using System.Collections.Generic;
using lab9.Models;
using MySql.Data.MySqlClient;

namespace lab9.Database
{
    public class DatabaseRepository
    {
        private static DatabaseRepository _instance;
        private const string ConnectionString = "server=localhost;uid=root;pwd=;database=recipes;";
        MySqlConnection Connection;

        private DatabaseRepository()
        {
            Connection = new MySqlConnection(ConnectionString);
        }

        public static DatabaseRepository Get()
        {
            return _instance ?? (_instance = new DatabaseRepository());
        }

        private Recipe NewRecipe(MySqlDataReader reader)
        {
            return new Recipe(
                reader.GetInt32("id"),
                reader.GetString("name"),
                reader.GetString("type"),
                reader.GetString("author"),
                reader.GetString("description")
            );
        }

        public List<Recipe> GetAllRecipes()
        {
            var query = "SELECT id, name, type, author, description FROM recipes";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                List<Recipe> list = new List<Recipe>();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(NewRecipe(reader));
                    }
                }

                return list;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public Recipe GetRecipe(int id)
        {
            var query = "SELECT id, name, type, author, description FROM recipes where id='" + id + "'";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                List<Recipe> list = new List<Recipe>();
                using (var reader = command.ExecuteReader())
                {
                    reader.Read();
                    return NewRecipe(reader);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public List<Recipe> FilterRecipes(string type)
        {
            var query = "SELECT id, name, type, author, description FROM recipes WHERE type='" + type + "'";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                List<Recipe> list = new List<Recipe>();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(NewRecipe(reader));
                    }
                }

                return list;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public void Add(Recipe recipe)
        {
            var query = "INSERT into recipes (id, name, type, author, description) values("
                        + recipe.id + ",'" + recipe.name + "','" + recipe.type + "','" + recipe.author + "','" +
                        recipe.description + "');";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public void Delete(int id)
        {
            var query = "DELETE from recipes where id='" + id + "'";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public void Update(Recipe recipe)
        {
            var query = "UPDATE recipes SET name='" + recipe.name + "',type='" + recipe.type
                        + "',author='" + recipe.author + "',description='" + recipe.description +
                        "' where id='"
                        + recipe.id + "'";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }

        public bool AuthenticateUser(UserProfile userProfile)
        {
            var query = "SELECT * FROM users WHERE username='" + userProfile.Username + "' AND password='"+userProfile.Password+"'";
            var command = new MySqlCommand(query, Connection);
            try
            {
                Connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    return reader.Read();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            finally
            {
                Connection.Close();
            }
        }
    }
}