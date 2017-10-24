using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Bellissimo.Models;
using Bellissimo.Data;
using Newtonsoft.Json;

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
                    return Json(e + "It reached here...");
                }
            }
        }

        [HttpPost]
        public ActionResult UpdateCatalogue(string title, double price)
        {
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    Catalogue CurrentCatalogue = cn.Catalogues.FirstOrDefault(x => x.Title == title);
                    CurrentCatalogue.OnSpecial = true;
                    CurrentCatalogue.Price = price;
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

        public class Sales
        {
            public string name { get; set; }
            public string price { get; set; }
        }

        [HttpPost]
        public ActionResult MakePayment(double total, List<Sales> s)
        {
            var user = User.Identity.Name;
            double currentBalance = 0;
            using (var cn = new ApplicationDbContext())
            {
                try
                {
                    currentBalance = cn.Users.Single(x => x.UserName == user).BankBalance;

                    if(currentBalance < total)
                    {
                        return Json("Your items total exceeds your bank balance");
                    }
                    else
                    {
                        Random r = new Random();
                        double probability = 95;
                        int range = 100;
                        int luckyNumber = r.Next(0, range);

                        if(luckyNumber < probability) // Successful Payment
                        {
                            UpdateUserBalance(currentBalance - total);
                            var temp = cn.Users.Single(x => x.UserName == user).Sales;

                            if (temp == null)
                            {
                                List<Sales> monster = new List<Sales>();
                                s.ForEach(i =>
                                {
                                    monster.Add(i);
                                });
                                cn.Users.Single(x => x.UserName == user).Sales = JsonConvert.SerializeObject(monster);
                            }
                            else
                            {
                                var result = JsonConvert.DeserializeObject<List<Sales>>(temp);
                                s.ForEach(i =>
                                {
                                    result.Add(i);
                                });

                                cn.Users.Single(x => x.UserName == user).Sales = JsonConvert.SerializeObject(result);
                                
                            }
                            cn.SaveChanges();
                            return Json("Success: Your Payment has been recieved.");
                        }
                    }
                    return Json("Error: There was a problem with your payment, please try again."); //fail-safe Something has went wrong...
                }
                catch (Exception e)
                {
                    return Json(e);
                }
            }
        }

        [HttpGet]
        public ActionResult SalesHistory()
        {
            var user = User.Identity.Name;
            using (var cn = new ApplicationDbContext())
            {
                var temp = cn.Users.Single(x => x.UserName == user).Sales;

                if(temp == null)
                {
                    return Json(false);
                }

                var result = JsonConvert.DeserializeObject<List<Sales>>(temp);
                return Json(result.ToList()); 
            }
        }
    }

}