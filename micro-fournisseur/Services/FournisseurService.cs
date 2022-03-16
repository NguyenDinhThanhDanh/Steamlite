using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Repositories;

namespace micro_fournisseur.Services
{
    public class FournisseurService : IFournisseurService
    {

        private IFournisseurRepository FournisseurRepository { get; set; }
        private static List<Jeu> _jeuList = new List<Jeu>();

        public FournisseurService(IFournisseurRepository fournisseurRepository)
        {
            FournisseurRepository = fournisseurRepository;
        }

        public void InscrireFournisseur(string NomFournisseur, string MdpFournisseur){
            
        }

        public void ConnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }

        public void DeconnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }

        public Jeu AjouterJeu(Jeu jeu){
            jeu.IdJeu = Guid.NewGuid().ToString();
            FournisseurRepository.SaveJeu(jeu);
            return jeu;
        }

        public List<Jeu> GetJeuxFournisseur(string NomFournisseur){
            return new List<Jeu>();
        }
    }
}