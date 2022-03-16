using micro_fournisseur.Models;
using micro_fournisseur.Repositories;
//using Mysql.Driver;

namespace micro_fournisseur.Repositories
{
    public class FournisseurRepository : IFournisseurRepository
    {
        //private IMongoCollection<Product> Ctx { get; set; }

        public FournisseurRepository(IConfiguration configuration)
        {
            //MongoClient client = new MongoClient(configuration["MongoHost"]);
            //Ctx = client.GetDatabase("Products").GetCollection<Product>("Products");
        }

        public List<Jeu> GetAll(){
            return new List<Jeu>();
        }
        public List<Jeu> ? GetAllJeuxByFournisseur(string NomFournisseur){
            return new List<Jeu>();
        }
        public Jeu ? GetById(string id){
            return null;
        }
        public Jeu ? GetByNom(string NomJeu){
            return null;
        }
        public void SaveJeu(Jeu jeu){
            var entity = GetById(jeu.IdJeu);
            if (entity == null)
            {
                //Ctx.InsertOne(product);
                return;
            }
            //Ctx.FindOneAndReplace(Builders<Product>.Filter.Eq("Id", product.Id), product);
        }

    }
}