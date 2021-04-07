package cn.edu.bnuz.notes.ntwpojo;

import java.util.List;

public class UserInfo extends BaseRD{

    /**
     * data : {"userId":888888888,"username":"test","imgPath":"aaa","email":null,"gmtCreate":"2020-05-08 21:57:23","gmtModified":"2020-09-24 20:41:14","version":0,"roles":[{"roleId":1,"roleName":"USER","description":"普通用户","gmtCreate":"2020-05-08 22:02:02"}]}
     */

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 888888888
         * username : test
         * imgPath : aaa
         * email : null
         * gmtCreate : 2020-05-08 21:57:23
         * gmtModified : 2020-09-24 20:41:14
         * version : 0
         * roles : [{"roleId":1,"roleName":"USER","description":"普通用户","gmtCreate":"2020-05-08 22:02:02"}]
         */

        private Long userId;
        private String username;
        private String imgPath;
        private String email;
        private String gmtCreate;
        private String gmtModified;
        private int version;
        private List<RolesBean> roles;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
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

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class RolesBean {
            /**
             * roleId : 1
             * roleName : USER
             * description : 普通用户
             * gmtCreate : 2020-05-08 22:02:02
             */

            private int roleId;
            private String roleName;
            private String description;
            private String gmtCreate;

            public int getRoleId() {
                return roleId;
            }

            public void setRoleId(int roleId) {
                this.roleId = roleId;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }
        }
    }
}
