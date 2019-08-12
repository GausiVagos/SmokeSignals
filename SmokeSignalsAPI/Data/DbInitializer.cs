using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using SmokeSignalsAPI.Data;
using SmokeSignalsAPI.Models;

namespace SmokeSignalsAPI.Data
{
    public class DbInitializer
    {
        public static void Initialize(SmokeSignalsContext context)
        {
            context.Database.EnsureCreated();

            if (context.Users.Any())
            {
                return;   // DB has been seeded
            }

            context.Users.Add(new User {UserName = "Vagos", Gender='M', Password = "Test", BirthDay = new DateTime(1998,12,8) });
            context.SaveChanges();
        }
    }
}
