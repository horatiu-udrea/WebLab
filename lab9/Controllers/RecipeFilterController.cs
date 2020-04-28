using System.Collections.Generic;
using System.Web.Http;
using lab9.Database;
using lab9.Models;

namespace lab9.Controllers
{
    // [Authorize]
    public class RecipeFilterController : ApiController
    {
        public List<Recipe> Get(string id)//actually the type
        {
            return DatabaseRepository.Get().FilterRecipes(id);
        }
    }
}