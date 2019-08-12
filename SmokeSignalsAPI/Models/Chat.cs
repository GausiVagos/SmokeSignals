using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class Chat
    {
        public int ChatId { get; set; }
        public string Subject { get; set; }
        public List<User> Users { get; set; }
        public List<Message> Messages { get; set; }
    }
}
