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
    public class MessagesController : ControllerBase
    {
        private readonly SmokeSignalsContext _context;

        public MessagesController(SmokeSignalsContext context)
        {
            _context = context;
        }

        // GET: api/Messages
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ClientMessage>>> GetMessages()
        {
            List<Message> list = await _context.Messages.Include(m=>m.User).ToListAsync();
            List<ClientMessage> list2 = new List<ClientMessage>();
            foreach(Message m in list)
            {
                list2.Add(new ClientMessage(m, _context));
            }

            return list2;
        }

        // GET: api/Messages/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ClientMessage>> GetMessage(int id)
        {
            var message = await _context.Messages.Where(m=>m.MessageId == id).Include(m=>m.User).SingleOrDefaultAsync();

            if (message == null)
            {
                return NotFound();
            }

            ClientMessage clm = new ClientMessage(message, _context);

            return clm;
        }

        [HttpGet("ofChat/{chatId}")]
        public async Task<ActionResult<List<ClientMessage>>> FromChat(int chatId)
        {
            Chat chat = _context.Chats.Where(c => c.ChatId == chatId).Include(c=>c.Messages).SingleOrDefault();
            if (chat == null)
                return null;

            List<Message> messages = chat.Messages.OrderBy(m=>m.MessageId).ToList();
            if (chat.Messages == null)
                return null;

            List<ClientMessage> clientMessages = new List<ClientMessage>();
            foreach(Message m in messages)
            {
                clientMessages.Add(new ClientMessage(m, _context));
            }

            return clientMessages;
        }

        // PUT: api/Messages/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMessage(int id, Message message)
        {
            if (id != message.MessageId)
            {
                return BadRequest();
            }

            _context.Entry(message).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MessageExists(id))
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

        // POST: api/Messages
        [HttpPost]
        public async Task<ActionResult<Message>> PostMessage(Message message)
        {
            _context.Messages.Add(message);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetMessage", new { id = message.MessageId }, message);
        }

        // DELETE: api/Messages/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<Message>> DeleteMessage(int id)
        {
            var message = await _context.Messages.FindAsync(id);
            if (message == null)
            {
                return NotFound();
            }

            _context.Messages.Remove(message);
            await _context.SaveChangesAsync();

            return message;
        }

        private bool MessageExists(int id)
        {
            return _context.Messages.Any(e => e.MessageId == id);
        }
    }
}
