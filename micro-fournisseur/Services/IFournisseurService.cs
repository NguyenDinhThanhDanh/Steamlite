using micro_fournisseur.Models;

namespace micro_fournisseur.Services
{
    public interface IFournisseurService
    {
        public void InscrireFournisseur(string NomFournisseur, string MdpFournisseur);
        public void ConnexionFournisseur(string NomFournisseur, string MdpFournisseur);
        public void DeconnexionFournisseur(string NomFournisseur, string MdpFournisseur);
        public Jeu AjouterJeu(Jeu jeu);
        public List<Jeu> GetJeuxFournisseur(string NomFournisseur);
    }
}