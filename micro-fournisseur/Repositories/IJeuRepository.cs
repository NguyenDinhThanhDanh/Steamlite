using micro_fournisseur.Models;

namespace micro_fournisseur.Repositories
{
    public interface IJeuRepository
    {
        List<Jeu> GetAll();
        List<Jeu> ? GetAllJeuxByFournisseur(string NomFournisseur);
        Jeu ? GetById(string id);
        Jeu ? GetByNom(string NomJeu);
        void SaveJeu(Jeu jeu);

        //void DeleteJeu(string id);
    }
}