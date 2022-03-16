using Microsoft.AspNetCore.Mvc;
using micro_fournisseur.Models;
using micro_fournisseur.Services;

namespace micro_fournisseur.Controllers
{
    [Route("fournisseur/")]
    [ApiController]
    public class FournisseurController : ControllerBase
    {
        private IFournisseurService ? FournisseurService { get; set; }

    }
}