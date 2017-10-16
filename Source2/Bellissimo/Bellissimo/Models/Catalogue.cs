using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Bellissimo.Models
{
    public class Catalogue
    {
        [Key]
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public double Price { get; set; }
        public double PromoPrice { get; set; }
        public string Path { get; set; }
        public bool OnSpecial { get; set; }


    }
}
