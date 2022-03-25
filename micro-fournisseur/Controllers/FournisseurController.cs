using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;

namespace micro_fournisseur.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

        public FournisseurController(IFournisseurService fournisseurService)
        {
            FournisseurService = fournisseurService;
        }

        [HttpGet("~/jeu/{id}")]
        public IActionResult GetJeu(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet("~/jeuById/{id}", Name = "GetJeuById")]
        public IActionResult GetJeuById(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }

        [HttpGet("~/jeuByName/{nameJ}", Name = "GetJeuByName")]
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
        [HttpGet("~/jeux")]
        public List<Jeu> GetAllJeux()
        {
            return FournisseurService.GetJeux();
        }

        // POST /jeu/
        [HttpPost("~/jeu")]
        public IActionResult AjouterJeu([FromBody] Jeu jeu)
        {
            var result = FournisseurService.AjouterJeu(jeu);
            return Created(Url.RouteUrl("GetJeuById", new { id = result.IdJeu }), result);
        }

        [HttpDelete("~/jeu/{id}")]
        public IActionResult DeleteJeu(string id)
        {
            FournisseurService.DeleteJeu(id);
            return Ok();
        }


        [HttpGet("~/fournisseur/{id}")]
        public IActionResult GetFournisseur(string id)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurById(id);
            if (fournisseur == null)
            {
                return NotFound();
            }
            return Ok(fournisseur);
        }

        [HttpGet("~/fournisseurById/{id}", Name = "GetFournisseurById")]
        public IActionResult GetFournisseurById(string id)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurById(id);
            if (fournisseur == null)
            {
                return NotFound();
            }
            return Ok(fournisseur);
        }

        [HttpGet("~/fournisseurByName/{nomF}", Name = "GetFournisseurByNom")]
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
        [HttpGet("~/fournisseurs")]
        public List<FournisseurDTO> GetAllFournisseurs()
        {
            return FournisseurService.GetFournisseurs();
        }

        // POST fournisseur/inscription/
        [HttpPost("~/inscription")]
        public IActionResult Inscription([FromBody] Fournisseur fournisseur)
        {
            var result = FournisseurService.InscrireFournisseur(fournisseur);
            if(result == -1)
            {
                return StatusCode(409, "Already exists");
            }
            return Ok();
        }

        [HttpDelete("~/fournisseur/{id}")]
        public IActionResult DeleteFournisseur(string id)
        {
            FournisseurService.DeleteFournisseur(id);
            return Ok();
        }
    }
}