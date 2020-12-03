package cn.edu.bnuz.notes.ntwpojo;


public class TokenRD {
    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTkyOTE2NzEsInVzZXJfbmFtZSI6IntcImltZ1BhdGhcIjpcImFhYVwiLFwidXNlcklkXCI6ODg4ODg4ODg4LFwidXNlcm5hbWVcIjpcInRlc3RcIn0iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYTM0MGQzODctNjhlZi00YzMyLWJkYmEtYTYwYzg3NTJjODY5IiwiY2xpZW50X2lkIjoiYW5kcm9pZCIsInNjb3BlIjpbIlJPTEVfQURNSU4iXX0.tFH3gNS-W2omidEd3_njSPfrVOHfdt-OXZBfYai4iE8
     * token_type : bearer
     * refresh_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ7XCJpbWdQYXRoXCI6XCJhYWFcIixcInVzZXJJZFwiOjg4ODg4ODg4OCxcInVzZXJuYW1lXCI6XCJ0ZXN0XCJ9Iiwic2NvcGUiOlsiUk9MRV9BRE1JTiJdLCJhdGkiOiJhMzQwZDM4Ny02OGVmLTRjMzItYmRiYS1hNjBjODc1MmM4NjkiLCJleHAiOjE1OTk1NDM2NzEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIxMWZkNjg4Yy0xMTUwLTQ3ZjMtOWE1Ni01YzY0ODBlMjA3MzYiLCJjbGllbnRfaWQiOiJhbmRyb2lkIn0.UCFYDRvT5fPZwS5ZPMABHWrxlWKlTTDfpTTjGFHXOLY
     * expires_in : 7199
     * scope : ROLE_ADMIN
     * jti : a340d387-68ef-4c32-bdba-a60c8752c869
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}





