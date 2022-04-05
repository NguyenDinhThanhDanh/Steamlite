using micro_fournisseur.Models;

namespace micro_fournisseur.Services
{
    public interface IFournisseurService
    {
        public int InscrireFournisseur(Fournisseur fournisseur);
        public void ConnexionFournisseur(string NomFournisseur, string MdpFournisseur);
        public void DeconnexionFournisseur(string NomFournisseur, string MdpFournisseur);
        public Jeu AjouterJeu(Jeu jeu);
        public Jeu ? GetJeuById(string id);
        public List<Jeu> GetJeuxFournisseur(string NomFournisseur);
        public List<Jeu> GetJeux();
        public FournisseurDTO ? GetFournisseurById(string id);
        public FournisseurDTO ? GetFournisseurByNom(string nomF);
        public List<FournisseurDTO> GetFournisseurs();
        public void DeleteJeu(string IdJeu);
        public void DeleteFournisseur(string IdFournisseur);
    }
}