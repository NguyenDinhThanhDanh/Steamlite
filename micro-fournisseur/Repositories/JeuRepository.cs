using micro_fournisseur.Models;
using micro_fournisseur.Repositories;
using MySql.Data.MySqlClient;

namespace micro_fournisseur.Repositories
{
    public class JeuRepository : IJeuRepository
    {

        /*private MySqlConnection ? mySql { get; set; }

        public JeuRepository(IConfiguration configuration)
        {
            MySqlConnection mySql = new MySqlConnection(configuration["MySQLHost"]);
        }

        public List<Jeu> GetAll(){
            return new List<Jeu>();
        }
        public List<Jeu> ? GetAllJeuxByFournisseur(string NomFournisseur){
            return new List<Jeu>();
        }
        public Jeu ? GetById(string id)
        {
        /*{
            try
            {
                mySql.Open();
                string sql = "SELECT * FROM FOURNISSEUR WHERE id = @id";
                MySqlCommand cmd = new MySqlCommand(sql, mySql);
                cmd.Prepare();
                cmd.Parameters.AddWithValue("@id", id);
                cmd.ExecuteNonQuery();

                using (var reader = cmd.ExecuteReader())  
                {  
                    while (reader.Read())  
                    {  
                        return new Jeu()  
                        {  
                            id = reader["idF"].ToString(),
                            NomJeu= reader["nomF"].ToString(),
                            nomF = reader["mdpF"].ToString(),
                            DateJeu = reader["dateInscriptionF"].ToString()
                            prixJeu = reader["dateInscriptionF"].ToString()
                        };
                    }  
                }
                
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
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

            return null;
        }
        public void DeleteJeu(string idJ)
        {
        }
    */
    }
}