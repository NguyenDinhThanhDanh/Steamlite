using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Repositories;

namespace micro_fournisseur.Services
{
    public class FournisseurService : IFournisseurService
    {

        private IFournisseurRepository FournisseurRepository { get; set; }
        private IJeuRepository JeuRepository { get; set; }
        private static List<Jeu> _jeuList = new List<Jeu>();

        public FournisseurService(IFournisseurRepository fournisseurRepository, IJeuRepository jeuRepository)
        {
            FournisseurRepository = fournisseurRepository;
            JeuRepository = jeuRepository;
        }

        public Fournisseur InscrireFournisseur(Fournisseur fournisseur){
            FournisseurRepository.SaveFournisseur(fournisseur);
            return fournisseur;
        }

        public void ConnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }

        public void DeconnexionFournisseur(string NomFournisseur, string MdpFournisseur){

        }

        public Jeu AjouterJeu(Jeu jeu){
            JeuRepository.SaveJeu(jeu);
            return jeu;
        }

        public List<Jeu> GetJeuxFournisseur(string NomFournisseur){
            return new List<Jeu>();
        }

        public List<FournisseurDTO> GetFournisseurs()
        {
            return FournisseurRepository.GetAll();
        }

        public FournisseurDTO ? GetFournisseurById(string id)
        {
            return FournisseurRepository.GetById(id);
        }

        public Jeu ? GetJeuById(string id)
        {
            return JeuRepository.GetById(id);
        }

    }
}