using SmokeSignalsAPI.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class ClientMessage
    {
        public int MessageId { get; set; }
        public string MessageContent { get; set; }
        public string Sent { get; set; }
        public ClientUser User { get; set; }

        public ClientMessage(Message model, SmokeSignalsContext context)
        {
            if (model == null)
                return;

            MessageId = model.MessageId;
            MessageContent = model.MessageContent;
            Sent = model.Sent;
            User u = context.Messages.Where(m => m.MessageId == MessageId).Select(m => m.User).FirstOrDefault();
            User = new ClientUser(u, null, false);
        }
    }
}
