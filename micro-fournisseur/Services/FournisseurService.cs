using micro_fournisseur.Models;
using micro_fournisseur.Services;
using micro_fournisseur.Exceptions;
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

        public int InscrireFournisseur(Fournisseur fournisseur){
            return FournisseurRepository.SaveFournisseur(fournisseur);
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

        public List<Jeu> GetJeux()
        {
            return new List<Jeu>();
        }

        public FournisseurDTO ? GetFournisseurById(string id)
        {
            return FournisseurRepository.GetById(id);
        }

        public FournisseurDTO ? GetFournisseurByNom(string nomF)
        {
            return FournisseurRepository.GetByNom(nomF);
        }

        public Jeu ? GetJeuById(string id)
        {
            return JeuRepository.GetById(id);
        }

        public void DeleteJeu(string IdJeu)
        {
            JeuRepository.DeleteJeu(IdJeu);
        }

        public void DeleteFournisseur(string IdFournisseur)
        {
            FournisseurRepository.DeleteFournisseur(IdFournisseur);
        }
    }
}