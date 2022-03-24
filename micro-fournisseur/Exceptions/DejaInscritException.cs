using System.Net;

namespace micro_fournisseur.Exceptions
{
    public class DejaInscritException : Exception
    {
        public HttpStatusCode StatusCode { get; set; }

        public DejaInscritException(HttpStatusCode statusCode, string? message) : base(message)
        {
            StatusCode = statusCode;
        }
        public DejaInscritException()
        {
            
        }
    }
}