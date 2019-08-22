using SmokeSignalsAPI.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace SmokeSignalsAPI.Models
{
    public class ClientUser
    {
        public int UserId { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public char Gender { get; set; }
        public string City { get; set; }
        public double LC_Latitude { get; set; }
        public double LC_Longitude { get; set; }
        public List<ClientChat> Chats { get; set; }

        public ClientUser(User model, SmokeSignalsContext context, bool needRef)
        {
            if (model == null)
                return;

            UserId = model.UserId;
            UserName = model.UserName;
            Password = model.Password;
            Gender = model.Gender;
            City = model.City;
            LC_Latitude = model.LC_Latitude;
            LC_Longitude = model.LC_Longitude;

            if(needRef && model.Chats != null)
            {
                Chats = new List<ClientChat>();
                foreach (Participation p in model.Chats)
                {
                    Chat chat = context.Chats.SingleOrDefault(c => c.ChatId == p.ChatId);
                    Chats.Add(new ClientChat(chat, null , false));
                }
            }
        }
    }
}
