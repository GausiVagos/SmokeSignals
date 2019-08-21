using System.Collections.Generic;

namespace SmokeSignalsAPI.Models
{
    public class User
    {
        public int UserId { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public char Gender { get; set; }
        public string City { get; set; }
        public double LC_Latitude { get; set; }
        public double LC_Longitude { get; set; }
        public List<Participation> Chats { get; set; }
    }
}
