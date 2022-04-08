using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;

using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;


using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace micro_fournisseur.Controllers
{
    [ApiController]
    [Route("/")]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

        private HttpClient _CLIENT;
        private JsonSerializerOptions _options;
        private string URI;
        public FournisseurController(IFournisseurService fournisseurService)
        {
            FournisseurService = fournisseurService;
            _CLIENT = new HttpClient();
            URI =  "http://localhost:8080/catalogue/jeu/";
            _options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };
        }

        [HttpGet("fournisseur/jeu/{id}")]
        public Jeu GetJeu([FromHeader] string token, string id)
        {
            if (id == null)
            {
                return null;
            }
            SetHeader(token);
            var response = _CLIENT.GetAsync(URI + id);
            var content = response.Result.Content.ReadAsStringAsync();
            Jeu jeu = JsonConvert.DeserializeObject<Jeu>(content.Result);
            return jeu;
        }

        // Get jeu/
        [HttpGet("fournisseur/jeux")]
        public List<Jeu> GetAllJeux([FromHeader] string token)
        {
            SetHeader(token);
            var response = _CLIENT.GetAsync(URI);
            var content = response.Result.Content.ReadAsStringAsync();
            Console.WriteLine(content.Result);
            return JsonConvert.DeserializeObject<List<Jeu>>(content.Result);
        }

        // POST /jeu/
        [HttpPost("fournisseur/jeu")]
        public IActionResult AjouterJeu([FromBody] Jeu jeu, [FromHeader] string token)
        {
            SetHeader(token);
            var values = new Dictionary<string, string>
            {
                { "nomJeu", jeu.NomJeu },
                { "dateJeu", jeu.DateJeu },
                { "nomF", jeu.nomF },
                { "prixJeu", jeu.prixJeu }
            };

            var dico = JsonConvert.SerializeObject( values );

            StringContent httpContent = new StringContent(dico, System.Text.Encoding.UTF8, "application/json");

            var response = _CLIENT.PostAsync("http://localhost:8080/catalogue/jeu", httpContent);
            //Console.WriteLine(response.Result.StatusCode.ToString());
            //Console.WriteLine(response.Result.Content.ReadAsStringAsync().Result);
        
            if (response.Result.IsSuccessStatusCode)
            {
                var content = response.Result.Content.ReadAsStringAsync();
                string location = response.Result.Headers.Location.ToString().Split("/")[3];
                
                return Ok(response.Result.Content.ReadAsStringAsync().Result);
            }
            if(response.Result.StatusCode.ToString().Equals("Conflict")){
                return Conflict(response.Result.Content.ReadAsStringAsync().Result);
            }
            return StatusCode(500, "Internal error");
        }

        [HttpDelete("fournisseur/jeu/{id}")]
        public IActionResult DeleteJeu([FromHeader] string token, string id)
        {
            SetHeader(token);
            var response = _CLIENT.DeleteAsync("http://localhost:8080/catalogue/jeu/" + id);
            Console.WriteLine(response.Result.Content);
            if (response.Result.IsSuccessStatusCode)
            {
                var content = response.Result.Content.ReadAsStringAsync();
                
                return Ok(response.Result.Content.ReadAsStringAsync().Result);
            }
            if(response.Result.StatusCode.ToString().Equals("NotFound")){
                return NotFound(response.Result.Content.ReadAsStringAsync().Result);
            }
            return StatusCode(500, "Internal error");
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

        private void SetHeader(string token)
        {
            _CLIENT.DefaultRequestHeaders.Add("token",token);
            Response.Headers.Add("token", new string[] { token });
        }

    }
}