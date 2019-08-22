using System;

namespace SmokeSignalsAPI.Models
{
    public class Message
    {
        public int MessageId { get; set; }
        public string MessageContent { get; set; }
        public string Sent { get; set; }
        public User User { get; set; }

        public Message() { }
        public Message(ClientMessage cm)
        {
            MessageId = cm.MessageId;
            MessageContent = cm.MessageContent;
            Sent = cm.Sent;
            User = new User(cm.User, false);
        }
    }
}
