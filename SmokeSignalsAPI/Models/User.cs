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

        public User() { }
        public User(ClientUser cu, bool needRef)
        {
            UserId = cu.UserId;
            UserName = cu.UserName;
            Password = cu.Password;
            Gender = cu.Gender;
            City = cu.City;
            LC_Latitude = cu.LC_Latitude;
            LC_Longitude = cu.LC_Longitude;

            if (needRef && cu.Chats!=null && cu.Chats.Count!=0)
            {
                Chats = new List<Participation>();
                foreach (ClientChat cc in cu.Chats)
                {
                    Participation pa = new Participation
                    {
                        ChatId = cc.ChatId,
                        Chat = new Chat(cc, false),
                        UserId = UserId,
                        User = this
                    };
                }
            }
        }
    }
}
