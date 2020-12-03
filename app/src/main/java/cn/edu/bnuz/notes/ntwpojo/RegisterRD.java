package cn.edu.bnuz.notes.ntwpojo;

public class RegisterRD extends BaseRD{

    /**
     * code : 200
     * msg : SUCCESS
     * data : {"userId":1320987017011216385,"username":"Capricorn","imgPath":null,"email":"1733670291@qq.com","gmtCreate":"2020-10-27 15:13:42","gmtModified":"2020-10-27 15:13:42","version":0,"roles":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 1320987017011216385
         * username : Capricorn
         * imgPath : null
         * email : 1733670291@qq.com
         * gmtCreate : 2020-10-27 15:13:42
         * gmtModified : 2020-10-27 15:13:42
         * version : 0
         * roles : null
         */

        private long userId;
        private String username;
        private String imgPath;
        private String email;
        private String gmtCreate;
        private String gmtModified;
        private int version;
        private Object roles;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public Object getRoles() {
            return roles;
        }

        public void setRoles(Object roles) {
            this.roles = roles;
        }
    }
}
