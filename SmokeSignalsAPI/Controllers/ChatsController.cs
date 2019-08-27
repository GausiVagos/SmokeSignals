using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SmokeSignalsAPI.Data;
using SmokeSignalsAPI.Models;

namespace SmokeSignalsAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ChatsController : ControllerBase
    {
        private readonly SmokeSignalsContext _context;

        public ChatsController(SmokeSignalsContext context)
        {
            _context = context;
        }

        // GET: api/Chats
        [HttpGet]
        public async Task<ActionResult<List<ClientChat>>> GetChats()
        {
            List<Chat> chats = await _context.Chats.ToListAsync();
            List<ClientChat> list = new List<ClientChat>();
            foreach(Chat c in chats)
            {
                list.Add(new ClientChat(c, _context, true));
            }

            return list;
        }

        // GET: api/Chats/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ClientChat>> GetChat(int id)
        {
            var chat = await _context.Chats.Include(c=>c.Messages).Include(c=>c.Users).SingleOrDefaultAsync(c => c.ChatId == id);
            var clientChat = new ClientChat(chat, _context, true);

            if (chat == null)
            {
                return NotFound();
            }

            return clientChat;
        }

        
        [HttpGet("ofUser/{userId}")]
        public async Task<ActionResult<List<ClientChat>>> GetChatsOfUser(int userId)
        {
            var chats = await _context.Participations.Where(p => p.UserId == userId).Select(p => p.Chat).Include(c=>c.Messages).Include(c=>c.Users).ToListAsync();
            List<ClientChat> list = new List<ClientChat>();
            foreach(Chat c in chats)
            {
                list.Add(new ClientChat(c, _context, true));
            }
            return list;
        }
        
        // PUT: api/Chats/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutChat(int id, ClientChat cc)
        {
            Chat chat = new Chat(cc, true);

            if (id != chat.ChatId)
            {
                return BadRequest();
            }

            _context.Entry(chat).State = EntityState.Modified;
            

            if(chat.Users!=null && chat.Users.Count!=0)
                foreach(Participation p in chat.Users)
                {
                    _context.Entry(p).State = EntityState.Modified;
                    Participation similar = _context.Participations.Where(pa => pa.ChatId == p.ChatId && pa.UserId == p.UserId).FirstOrDefault();
                    if (similar == default)
                        _context.Participations.Add(p);
                    _context.Entry(p.User).State = EntityState.Modified;
                }
            
            if(chat.Messages!=null && chat.Messages.Count!=0)
                foreach (Message m in chat.Messages)
                {
                    _context.Entry(m).State = EntityState.Modified;
                }

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ChatExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Chats
        [HttpPost]
        public async Task<ActionResult<ClientChat>> PostChat(ClientChat clientChat)
        {
            Chat chat = new Chat(clientChat, true);
            List<Participation> parts = chat.Users;
            chat.Users = null;
            _context.Chats.Add(chat);

            foreach(Participation p in parts)
            {
                _context.Participations.Attach(p);
            }

            await _context.SaveChangesAsync();

            return new ClientChat(chat, _context, true);
        }

        [HttpPost("{id}/addMessage")]
        public async Task<ActionResult<List<ClientMessage>>> AddMessage(int id, ClientMessage message)
        {
            Chat chat = await _context.Chats.FindAsync(id);
            if (chat == null)
                return NotFound();

            Message msg = new Message(message);
            if (chat.Messages == null)
                chat.Messages = new List<Message>();

            _context.Messages.Attach(msg);
            chat.Messages.Add(msg);
            await _context.SaveChangesAsync();

            List<ClientMessage> messages = (await GetChat(id)).Value.Messages.OrderBy(m => m.MessageId).ToList();
            return messages;
        }

    // DELETE: api/Chats/5
    [HttpDelete("{id}")]
        public async Task<ActionResult<Chat>> DeleteChat(int id)
        {
            var chat = await _context.Chats.Include(c=>c.Messages).FirstOrDefaultAsync(m=>m.ChatId == id);
            if (chat == null)
            {
                return NotFound();
            }

            foreach (Message m in chat.Messages)
            {
                _context.Remove(m);
            }
            _context.Chats.Remove(chat);

            await _context.SaveChangesAsync();

            return chat;
        }

        private bool ChatExists(int id)
        {
            return _context.Chats.Any(e => e.ChatId == id);
        }
    }
}
