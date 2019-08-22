using SmokeSignalsAPI.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class ClientChat
    {
        public int ChatId { get; set; }
        public string Subject { get; set; }
        public List<ClientUser> Users { get; set; }
        public List<ClientMessage> Messages { get; set; }

        public ClientChat(Chat model, SmokeSignalsContext context, bool needRef)
        {
            if(model == null)
                return;

            ChatId = model.ChatId;
            Subject = model.Subject;

            if(model.Messages != null)
            {
                Messages = new List<ClientMessage>();
                foreach(Message m in model.Messages)
                {
                    Messages.Add(new ClientMessage(m, context));
                }
            }

            if(needRef && model.Users != null)
            {
                Users = new List<ClientUser>();
                foreach (Participation p in model.Users)
                {
                    User user = context.Users.SingleOrDefault(u => u.UserId == p.UserId);
                    Users.Add(new ClientUser(user, null, false));
                }
            }
        }

    }
}
