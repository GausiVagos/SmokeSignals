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
            context.SaveChanges();
        }
    }
}
