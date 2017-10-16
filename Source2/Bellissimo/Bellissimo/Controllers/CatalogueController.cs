using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Bellissimo.Models;
using Bellissimo.Data;

namespace Bellissimo.Controllers
{
    public class CatalogueController : Controller
    {
        [HttpPost]
        public ActionResult AddNewCatalogue(Catalogue c)
        {
            using(var cn = new ApplicationDbContext())
            {
                try
                {
                    cn.Catalogues.Add(c);
                    cn.SaveChanges();
                    return Json(true);
                }
                catch(Exception e)
                {
                    return Json(e);
                }
            }
        }

        [HttpGet]
        public ActionResult GetAllCatalogue()
        {
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    List<Catalogue> AllCatalogues;
                    AllCatalogues = cn.Catalogues.ToList();
                    return Json(AllCatalogues);
                }
                catch (Exception e)
                {
                    return Json(e);
                }
            }
        }

        [HttpPost]
        public ActionResult UpdateCatalogue(Catalogue c)
        {
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    Catalogue CurrentCatalogue = cn.Catalogues.FirstOrDefault(x => x.Id == c.Id);
                    CurrentCatalogue.OnSpecial = c.OnSpecial;
                    CurrentCatalogue.Path = c.Path;
                    CurrentCatalogue.Price = c.Price;
                    CurrentCatalogue.PromoPrice = c.PromoPrice;
                    CurrentCatalogue.Title = c.Title;
                    cn.SaveChanges();
                    return Json(true);
                }
                catch (Exception e)
                {
                    return Json(e);
                }
            }
        }


        /************************** USERS BALANCE *******************************/
        [HttpGet]
        public ActionResult GetUserBalance()
        {
            var user = User.Identity.Name;
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    return Json(cn.Users.Single(x => x.UserName == user).BankBalance);
                }
                catch (Exception e)
                {
                    return Json(e);
                }
            }
        }

        [HttpPost]
        public ActionResult UpdateUserBalance(double updatedBankBalance)
        {
            var user = User.Identity.Name;
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    cn.Users.Single(x => x.UserName == user).BankBalance = updatedBankBalance;
                    cn.SaveChanges();
                    return Json(true);
                }
                catch (Exception e)
                {
                    return Json(e);
                }
            }
        }
    }
}