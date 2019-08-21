using Microsoft.EntityFrameworkCore;
using SmokeSignalsAPI.Models;

namespace SmokeSignalsAPI.Data
{
    public class SmokeSignalsContext : DbContext
    {
        public SmokeSignalsContext(DbContextOptions<SmokeSignalsContext> options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Participation>().HasKey(p => new { p.UserId, p.ChatId });
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Chat> Chats { get; set; }
        public DbSet<Message> Messages { get; set; }
        public DbSet<Participation> Participations { get; set; }
    }
}
