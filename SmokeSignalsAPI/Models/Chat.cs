using System.Collections.Generic;

namespace SmokeSignalsAPI.Models
{
    public class Chat
    {
        public int ChatId { get; set; }
        public string Subject { get; set; }
        public List<Participation> Users { get; set; }
        public List<Message> Messages { get; set; }

        public Chat() { }
        public Chat(ClientChat cc, bool needRef)
        {
            ChatId = cc.ChatId;
            Subject = cc.Subject;

            if(cc.Messages!=null && cc.Messages.Count!=0)
            {
                Messages = new List<Message>();
                foreach(ClientMessage cm in cc.Messages)
                {
                    Message m = new Message(cm);
                    Messages.Add(m);
                }
            }

            if(needRef && cc.Users != null && cc.Users.Count!=0)
            {
                Users = new List<Participation>();
                foreach (ClientUser cu in cc.Users)
                {
                    Participation pa = new Participation
                    {
                        ChatId = cc.ChatId, Chat = this, UserId = cu.UserId, User = new User(cu, false)
                    };
                    Users.Add(pa);
                }
            }
        }
    }
}
