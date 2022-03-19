using System.Net;

namespace micro_fournisseur.Exceptions
{
    public class FournisseurInconnuException : Exception
    {
        public HttpStatusCode StatusCode { get; set; }

        public FournisseurInconnuException(HttpStatusCode statusCode, string? message) : base(message)
        {
            StatusCode = statusCode;
        }
        public FournisseurInconnuException()
        {
            
        }
    }
}