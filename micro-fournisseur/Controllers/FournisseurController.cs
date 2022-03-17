using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;

namespace micro_fournisseur.Controllers
{
    [Route("api/fournisseur/")]
    [ApiController]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

        public FournisseurController(IFournisseurService fournisseurService)
        {
            FournisseurService = fournisseurService;
        }

        [HttpGet("~/jeu/{id}", Name = "GetJeu")]
        public IActionResult GetJeu(string id)
        {
            //var product = FournisseurService.G(id);
            if (id == null)
            {
                return NotFound();
            }

            return Ok();
        }
        [HttpGet("~/fournisseur/{id}", Name = "GetFournisseur")]
        public IActionResult GetFournisseur(string id)
        {
            FournisseurDTO fournisseur = FournisseurService.GetFournisseurById(id);
            if (fournisseur == null)
            {
                return NotFound();
            }

            return Ok(fournisseur);
        }

        // POST fournisseur/jeu/
        [HttpPost("~/jeu")]
        public IActionResult AjouterJeu([FromBody] Jeu jeu)
        {
            var result = FournisseurService.AjouterJeu(jeu);
            return Created(Url.RouteUrl("GetJeu", new { id = result.IdJeu }), result);
        }

        // POST fournisseur/inscription/
        [HttpPost("~/inscription")]
        public IActionResult Inscription([FromBody] Fournisseur fournisseur)
        {
            var result = FournisseurService.InscrireFournisseur(fournisseur);
            //Console.WriteLine(result.ToString());
            return Created(Url.RouteUrl("GetFournisseur", new { id = result.IdFournisseur }), result);
        }

        // POST fournisseur/
        [HttpPost("~/fournisseurs")]
        public List<FournisseurDTO> GetAllFournisseurs()
        {
            return FournisseurService.GetFournisseurs();
        }
    }
}