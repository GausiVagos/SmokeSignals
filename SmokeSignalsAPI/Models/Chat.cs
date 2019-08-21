using System.Collections.Generic;

namespace SmokeSignalsAPI.Models
{
    public class Chat
    {
        public int ChatId { get; set; }
        public string Subject { get; set; }
        public List<Participation> Users { get; set; }
        public List<Message> Messages { get; set; }
    }
}
