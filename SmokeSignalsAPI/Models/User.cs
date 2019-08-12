using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class User
    {
        public int UserId { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public char Gender { get; set; }
        public string City { get; set; }
        public DateTime BirthDay { get; set; }
        public string LC_Latitude { get; set; }
        public string LC_Longitude { get; set; }
    }
}
