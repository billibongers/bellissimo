using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Bellissimo.Models;

namespace Bellissimo.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext() { }

        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {

        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            //connectionString= (@"Server=129.232.181.10;Database=AbujaCallCenter;UID=acc;PWD=acc;");
            //initial migrations on mock empty db
            //string connectionString = @"Data Source=129.232.181.10;Initial Catalog=TanzaniaRUC;User ID=sa;Password=UAisawesome01";
            string connectionString = @"Data Source=BILAL\SQLEXPRESS; Database = Bellissimo; Trusted_Connection = True; MultipleActiveResultSets = true";
            //string connectionString = @"Server=197.189.215.26,1433;Database=SWHCallCenter;uid=swh;pwd=UAisawesome01";
            optionsBuilder.UseSqlServer(connectionString);
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
            // Customize the ASP.NET Identity model and override the defaults if needed.
            // For example, you can rename the ASP.NET Identity table names and more.
            // Add your customizations after calling base.OnModelCreating(builder);
        }
        
        public virtual DbSet<Catalogue> Catalogues { get; set; }
    }
}
