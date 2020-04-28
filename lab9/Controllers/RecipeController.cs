using System.Collections.Generic;
using System.Web.Http;
using lab9.Database;
using lab9.Models;

namespace lab9.Controllers
{
    // [Authorize]
    public class RecipeController : ApiController
    {
        public List<Recipe> Get()
        {
            return DatabaseRepository.Get().GetAllRecipes();
        }

        public Recipe Get(int id)
        {
            return DatabaseRepository.Get().GetRecipe(id);
        }
        
        public void Post([FromBody]Recipe recipe)
        {
            DatabaseRepository.Get().Add(recipe);
        }

        public void Put([FromBody]Recipe recipe)
        {
            DatabaseRepository.Get().Update(recipe);
        }

        public void Delete(int id)
        {
            DatabaseRepository.Get().Delete(id);
        }
    }
}