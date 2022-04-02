package steam.serviceauth.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import steam.serviceauth.databaseKC.MysqlDataBaseKC;
import steam.serviceauth.entities.ClientKC;
//import steam.serviceauth.repository.ClientRepository;

import java.sql.SQLException;
import java.util.*;

import static org.springframework.http.HttpMethod.POST;

@Service
public class AuthentServiceImpl implements AuthentService {
    private String client_id="steamlite-web";
    private String value="steam";
    private MysqlDataBaseKC mysqlDataBaseKC;
//    @Autowired
//    private ClientRepository clientRepository;

    private HashMap<String, ClientKC> clientsConnectes;

    public AuthentServiceImpl(){
        this.mysqlDataBaseKC=new MysqlDataBaseKC();
        this.clientsConnectes= new HashMap<>();
    }

    @Override
    public void createUtilisateur(ClientKC client)  {
        String pseudo=client.getPseudo();
        String mdp=client.getMdp();
        String email=client.getEmail();
            HttpHeaders header= new HttpHeaders();
            String token= this.keycloakToken("admin","admin");
            header.setBearerAuth(token);

            RestTemplate restTemplate = new RestTemplate();
            header.setContentType(MediaType.APPLICATION_JSON);
            JSONObject json= new JSONObject();
            json.put("email",email);
            json.put("username",pseudo);
            json.put("emailVerified",true);
            json.put("enabled",true);
//            json.put("firstname","");
//            json.put("lastname","");

           HttpEntity<String> requesteEntity= new HttpEntity<>(json.toString(),header);
            ResponseEntity<String> res   =restTemplate.exchange("http://localhost:8000/auth/admin/realms/steam/users",POST,requesteEntity,String.class);

//            ResponseEntity<String> res   =restTemplate.exchange("http://localhost:8000/auth/admin/steam",POST,requesteEntity,String.class);
        }




    @Override
    public String keycloakToken(String username, String password) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("client_id",client_id);
        map.add("username",username);
        map.add("password",password);
        map.add("grant_type","password");
        RestTemplate restTemplate= new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> httpEntity= new HttpEntity<>(map,header);
        ResponseEntity<String> response= restTemplate.exchange("http://localhost:8000/auth/realms/steam/protocol/openid-connect/token",POST,
                httpEntity,String.class);
        String token=response.getBody().split(",")[0].split(":")[1].split("\"")[1];
        return token;
    }

    @Override
    public void setUserPassWord(String pseudo) throws SQLException {
        String id=this.mysqlDataBaseKC.getUserIDinKC(pseudo);
        System.out.println(id);
    }


}
