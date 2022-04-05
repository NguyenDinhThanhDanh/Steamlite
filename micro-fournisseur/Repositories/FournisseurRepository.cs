using micro_fournisseur.Models;
using micro_fournisseur.Exceptions;
using micro_fournisseur.Repositories;
using MySql.Data.MySqlClient;

namespace micro_fournisseur.Repositories
{
    public class FournisseurRepository : IFournisseurRepository
    {

        private MySqlConnection mySql { get; set; }

        public FournisseurRepository(IConfiguration configuration)
        {
            this.mySql = new MySqlConnection(configuration["MySQLHost"]);
            mySql.Open();
        }

        public List<FournisseurDTO> GetAll()
        {
            List<FournisseurDTO> fournisseurs = new List<FournisseurDTO>();
            FournisseurDTO fournisseur;
            string sql = "SELECT * FROM FOURNISSEUR";
            MySqlCommand cmd = new MySqlCommand(sql, mySql);
            MySqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read())
            {
                fournisseur = new FournisseurDTO();  // creates a new instance every iteration
                fournisseur.IdFournisseur = rdr.GetString("idF");
                fournisseur.NomFournisseur = rdr.GetString("nomF");
                //fournisseur.MdpFournisseur = rdr.GetString("mdpF");
                fournisseur.DateInscription = rdr.GetString("dateInscriptionF");
                fournisseurs.Add(fournisseur);
            }
            rdr.Close();
            return fournisseurs;
        }
        public FournisseurDTO ? GetById(string id)
        {
            FournisseurDTO fournisseur = new FournisseurDTO();
            string sql = "SELECT * FROM FOURNISSEUR where idF = @idFour";
            MySqlCommand cmd = new MySqlCommand(sql, mySql);
            cmd.Parameters.AddWithValue("@idFour", id);
            MySqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read()){
                fournisseur.IdFournisseur = rdr.GetString("idF");
                fournisseur.NomFournisseur = rdr.GetString("nomF");
                fournisseur.DateInscription = rdr.GetString("dateInscriptionF");
                rdr.Close();
                return fournisseur;
            }
            return null;
        }
        public FournisseurDTO ? GetByNom(string NomFournisseur)
        {
            FournisseurDTO fournisseur = new FournisseurDTO();
            string sql = "SELECT * FROM FOURNISSEUR where nomF = @nomFour";
            MySqlCommand cmd = new MySqlCommand(sql, mySql);
            cmd.Parameters.AddWithValue("@nomFour", NomFournisseur);
            MySqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read()){
                fournisseur.IdFournisseur = rdr.GetString("idF");
                fournisseur.NomFournisseur = rdr.GetString("nomF");
                fournisseur.DateInscription = rdr.GetString("dateInscriptionF");
                rdr.Close();
                return fournisseur;
            }
            return null;
        }

        public bool CheckNameValidity(string NomFournisseur)
        {
            FournisseurDTO fournisseur = new FournisseurDTO();
            string sql = "SELECT * FROM FOURNISSEUR where nomF = @nomFour";
            MySqlCommand cmd = new MySqlCommand(sql, mySql);
            cmd.Parameters.AddWithValue("@nomFour", NomFournisseur);
            MySqlDataReader rdr = cmd.ExecuteReader();
            while (rdr.Read()){
                if(rdr[1].Equals(NomFournisseur)){
                    rdr.Close();
                    return false;
                }
            }
            rdr.Close();
            return true;
        }

        public int SaveFournisseur(Fournisseur fournisseur)
        {
            try
            {
                if (!this.CheckNameValidity(fournisseur.NomFournisseur))
                {
                    return -1;
                }
                string sql = "INSERT INTO FOURNISSEUR (idF, nomF, mdpF, dateInscriptionF) VALUES (@idFour, @nomFour, @mdpFour, @dateInscriptionFour)";
                MySqlCommand cmd = new MySqlCommand(sql, mySql);
                cmd.Parameters.AddWithValue("@idFour", cmd.LastInsertedId);
                cmd.Parameters.AddWithValue("@nomFour", fournisseur.NomFournisseur);
                cmd.Parameters.AddWithValue("@mdpFour", fournisseur.MdpFournisseur);
                cmd.Parameters.AddWithValue("@dateInscriptionFour", DateTime.Now);
                cmd.ExecuteNonQuery();
                return 1;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            return 0;
        }

        public void DeleteFournisseur(string idF)
        {
            string sql = "DELETE FROM FOURNISSEUR where idF = @idFour";
            MySqlCommand cmd = new MySqlCommand(sql, mySql);
            cmd.Parameters.AddWithValue("@idFour", idF);
            cmd.ExecuteNonQuery();
            
        }
    }
}