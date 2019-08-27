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
    public class UsersController : ControllerBase
    {
        private readonly SmokeSignalsContext _context;

        public UsersController(SmokeSignalsContext context)
        {
            _context = context;
        }

        // GET: api/Users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return await _context.Users.ToListAsync();
        }

        // GET: api/Users/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ClientUser>> GetUser(int id)
        {
            var user = await _context.Users.Include(u => u.Chats).FirstOrDefaultAsync(u=>u.UserId == id);

            if (user == null)
            {
                return NotFound();
            }

            ClientUser cu = new ClientUser(user, _context, true);

            return cu;
        }

        [HttpGet("cities")]
        public async Task<ActionResult<List<string>>> GetCities(int id)
        {
            List<string> cities = await _context.Users.Select(u => u.City).Distinct().ToListAsync();

            return cities;
        }

        // PUT: api/Users/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(int id, User user)
        {
            if (id != user.UserId)
            {
                return BadRequest();
            }

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
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

        // POST: api/Users
        [HttpPost]
        public async Task<ActionResult<ClientUser>> PostUser(User user)
        {
            User similar = await _context.Users.FirstOrDefaultAsync(u => u.UserName == user.UserName);
            if (similar != null)
                return BadRequest();

            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return new ClientUser(user, _context, true);
        }

        [HttpPost("connect")]
        public async Task<ActionResult<ClientUser>> Connect(User user)
        {
            User connected = await _context.Users.Where(u => u.UserName == user.UserName && u.Password == user.Password).SingleOrDefaultAsync();
            if (connected != null)
            {
                connected.LC_Latitude = user.LC_Latitude;
                connected.LC_Longitude = user.LC_Longitude;
                await _context.SaveChangesAsync();
                ClientUser cu = new ClientUser(connected, _context, true);
                return cu;
            }
            else
                return BadRequest();
        }

        // DELETE: api/Users/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<User>> DeleteUser(int id)
        {
            var user = await _context.Users.Include(u => u.Chats).FirstOrDefaultAsync(u => u.UserId == id);
            if (user == null)
            {
                return NotFound();
            }

            foreach(Participation p in user.Chats)
            {
                _context.Participations.Remove(p);
            }
            List<Message> messages = await _context.Messages.Where(m=>m.User.UserId == user.UserId).ToListAsync();
            foreach(Message m in messages)
            {
                _context.Messages.Remove(m);
            }

            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return user;
        }

        private bool UserExists(int id)
        {
            return _context.Users.Any(e => e.UserId == id);
        }
    }
}
