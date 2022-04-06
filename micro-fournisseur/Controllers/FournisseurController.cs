using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;

using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;

using Newtonsoft.Json;

namespace micro_fournisseur.Controllers
{
    [ApiController]
    [Route("/")]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

        public FournisseurController(IFournisseurService fournisseurService)
        {
            FournisseurService = fournisseurService;
        }

        [HttpGet("fournisseur/jeu/{id}")]
        public IActionResult GetJeu(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet("fournisseur/jeuById/{id}", Name = "GetJeuById")]
        public IActionResult GetJeuById(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet("fournisseur/jeuByName/{nameJ}", Name = "GetJeuByName")]
        public IActionResult GetJeuByName(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }

        // Get jeu/
        [HttpGet("fournisseur/jeux")]
        public List<Jeu> GetAllJeux()
        {
            return FournisseurService.GetJeux();
        }

        // POST /jeu/
        [HttpPost("fournisseur/jeu")]
        public IActionResult AjouterJeu([FromBody] Jeu jeu)
        {
            var result = FournisseurService.AjouterJeu(jeu);
            return Created(Url.RouteUrl("GetJeuById", new { id = result.IdJeu }), result);
        }

        [HttpDelete("fournisseur/jeu/{id}")]
        public IActionResult DeleteJeu(string id)
        {
            FournisseurService.DeleteJeu(id);
            return Ok();
        }


        [HttpGet("fournisseur/fournisseur/{id}")]
        public IActionResult GetFournisseur(string id)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurById(id);
            if (fournisseur == null)
            {
                return NotFound();
            }
            return Ok(fournisseur);
        }

        [HttpGet("fournisseur/fournisseurById/{id}", Name = "GetFournisseurById")]
        public IActionResult GetFournisseurById(string id)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurById(id);
            if (fournisseur == null)
            {
                return NotFound();
            }
            return Ok(fournisseur);
        }

        [HttpGet("fournisseur/fournisseurByName/{nomF}", Name = "GetFournisseurByNom")]
        public IActionResult GetFournisseurByNom(string nomF)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurByNom(nomF);
            if (fournisseur == null)
            {
                return NotFound();
            }
            return Ok(fournisseur);
        }

        // Get fournisseurs/
        [HttpGet("fournisseur/fournisseurs")]
        public List<FournisseurDTO> GetAllFournisseurs()
        {
            return FournisseurService.GetFournisseurs();
        }

        // POST fournisseur/inscription/
        [HttpPost("fournisseur/inscription")]
        public IActionResult Inscription([FromBody] Fournisseur fournisseur)
        {
            var result = FournisseurService.InscrireFournisseur(fournisseur);
            if(result == -1)
            {
                return StatusCode(409, "Already exists");
            }
            return Ok();
        }

        [HttpDelete("fournisseur/fournisseur/{id}")]
        public IActionResult DeleteFournisseur(string id)
        {
            FournisseurService.DeleteFournisseur(id);
            return Ok();
        }

        //[HttpGet("fournisseur/checkToken")]
        [HttpGet("checkToken")]
        public bool checkToken(string pseudo, string mdp){
            HttpClient _httpClient = new HttpClient();

            var values = new Dictionary<string, string>
            {
                { "pseudo", pseudo },
                { "mdp", mdp }
            };

            var dico = JsonConvert.SerializeObject( values );

            string finalStringContent = "{\"pseudo\":" + pseudo + ",\"mdp\":" + mdp +"}";

            StringContent httpContent = new StringContent(dico, System.Text.Encoding.UTF8, "application/json");

            var response = _httpClient.PostAsync("http://localhost:8080/authent/token", httpContent);
            if (response.Result.IsSuccessStatusCode)
            {
                //string trest = new string[] { response.Result.Headers.GetValues("token").FirstOrDefault() };
                string token = (string)response.Result.Headers.GetValues("token").FirstOrDefault();
                Console.WriteLine(token);

                Response.Headers.Add("token", new string[] { token });
            }
            return response.Result.IsSuccessStatusCode;
        }
    }
}