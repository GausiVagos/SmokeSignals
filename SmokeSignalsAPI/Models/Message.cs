using System;

namespace SmokeSignalsAPI.Models
{
    public class Message
    {
        public int MessageId { get; set; }
        public string MessageContent { get; set; }
        public string Sent { get; set; }
        public User User { get; set; }
    }
}
