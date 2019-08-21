using System;
using System.Collections.Generic;
using System.Linq;
using SmokeSignalsAPI.Models;

namespace SmokeSignalsAPI.Data
{
    public class DbInitializer
    {
        public static void Initialize(SmokeSignalsContext context)
        {
            context.Database.EnsureCreated();
            User vagos = new User { UserName = "Vagos", Gender = 'M', Password = "Test", City = "Luttre" };
            //Chat chat = new Chat { Subject = "Un simple test", Users = new List<User> { vagos } };
            if (!context.Users.Any())
                context.Users.Add(vagos);

            context.SaveChanges();
        }
    }
}
