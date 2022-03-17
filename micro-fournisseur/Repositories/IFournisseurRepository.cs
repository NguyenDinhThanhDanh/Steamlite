using micro_fournisseur.Models;

namespace micro_fournisseur.Repositories
{
    public interface IFournisseurRepository
    {
        List<FournisseurDTO> GetAll();
        FournisseurDTO ? GetById(string id);
        FournisseurDTO ? GetByNom(string NomFournisseur);
        void SaveFournisseur(Fournisseur fournisseur);

        //void DeleteFournisseur(string id);
    }
}