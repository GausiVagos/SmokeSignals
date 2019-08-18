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
            User vagos = new User { UserName = "Vagos", Gender = 'M', Password = "Test", City = "Luttre" };
            Message msg = new Message { User = vagos, MessageContent = "Yo!", Sent = DateTime.Now };
            Chat chat = new Chat { Subject = "Un simple test", Users = new List<User> { vagos }, Messages = new List<Message> { msg } };
            if (!context.Users.Any())
                context.Users.Add(vagos);

            if (!context.Messages.Any())
                context.Messages.Add(msg);

            if (!context.Chats.Any())
                context.Chats.Add(chat);

            context.SaveChanges();
        }
    }
}
