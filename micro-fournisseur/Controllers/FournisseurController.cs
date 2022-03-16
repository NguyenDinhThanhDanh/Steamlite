using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;

namespace micro_fournisseur.Controllers
{
    [Route("fournisseur/")]
    [ApiController]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

        public FournisseurController(IFournisseurService fournisseurService)
        {
            FournisseurService = fournisseurService;
        }

        // POST fournisseur/inscription/
        [HttpPost]
        public IActionResult Post([FromBody] Jeu jeu)
        {
            var result = FournisseurService.AjouterJeu(jeu);
            return Created(Url.RouteUrl("GetJeu", new { id = result.IdJeu }), result);
        }

    }
}