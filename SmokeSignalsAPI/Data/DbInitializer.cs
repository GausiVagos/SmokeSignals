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

            Participation p = new Participation { ChatId = 1, UserId = 1 };

            if (!context.Participations.Any())
                context.Participations.Add(p);

            context.SaveChanges();
        }
    }
}
