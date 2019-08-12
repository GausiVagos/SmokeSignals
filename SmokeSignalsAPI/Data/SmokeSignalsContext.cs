using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using SmokeSignalsAPI.Models;

namespace SmokeSignalsAPI.Data
{
    public class SmokeSignalsContext : DbContext
    {
        public SmokeSignalsContext(DbContextOptions<SmokeSignalsContext> options) : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Chat> Chats { get; set; }
        public DbSet<Message> Messages { get; set; }
    }
}
