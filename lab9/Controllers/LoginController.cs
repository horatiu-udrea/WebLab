using System.Web.Mvc;
using lab9.Database;
using lab9.Models;

namespace lab9.Controllers
{
    public class LoginController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Index(UserProfile userProfile)
        {
            if (ModelState.IsValid && DatabaseRepository.Get().AuthenticateUser(userProfile))
            {
                Session["UserName"] = userProfile.Username;
                return RedirectToRoute("Default", new { controller = "Home", action = "Index"});
            }

            ViewBag.Message = "Login failed!";
            return View(userProfile);
        }

        public ActionResult Logout()
        {
            Session["UserName"] = null;
            return RedirectToAction("Index");
        }
    }
}