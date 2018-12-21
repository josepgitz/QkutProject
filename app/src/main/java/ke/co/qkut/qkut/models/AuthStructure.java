package ke.co.qkut.qkut.models;

public class AuthStructure {

    public String accessTocken;
    public String tokenType;
    public String expiresAt;


    public AuthStructure(){
        this.accessTocken = "";
        this.tokenType = "";
        this.expiresAt = "";
    }

    //get access token
    //----------------------------------------------------------------------------------------------
    public String getAuthorizationToken(){

        return this.tokenType + " " + this.accessTocken;
    }

}
