using micro_fournisseur.Models;
using micro_fournisseur.Services;

namespace micro_fournisseur.Services
{
    public class micro_fournisseur : IFournisseurService
    {
        public void InscrireFournisseur(string NomFournisseur, string MdpFournisseur){

        }
        public void ConnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }
        public void DeconnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }
        public void AjouterJeu(string NomJeu){

        }
        public List<Jeu> GetJeuxFournisseur(string NomFournisseur){
            return new List<Jeu>();
        }
    }
}