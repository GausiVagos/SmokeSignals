using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class Message
    {
        public int MessageId { get; set; }
        public string MessageContent { get; set; }
        public DateTime Sent { get; set; }
        public User User { get; set; }
    }
}
