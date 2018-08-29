package fileManager.app.models;

import java.util.Date;

public class UploadFile {

        private Integer id;
        private String nameFile;
        private String type;
        private String info;
        private String status;
        private Date data;
        private Integer user;
        private byte[] file;
        private Integer count;

        public UploadFile() {}

        public UploadFile(String nameFile) {
        this.nameFile = nameFile;
    }

        public UploadFile(String nameFile, Integer user_id, byte[] file, String type, String status, Date data, String info, Integer count) {
                this.nameFile = nameFile;
                this.user = user_id;
                this.file= file;
                this.type = type;
                this.status = status;
                this.data = data;
                this.info = info;
                this.count = count;
        }

        public UploadFile(Integer id, String nameFile, Integer user_id, byte[] file, String type, String status, Date data, String info, Integer count) {
                this.id = id;
                this.nameFile = nameFile;
                this.user = user_id;
                this.file= file;
                this.type = type;
                this.status = status;
                this.data = data;
                this.info = info;
                this.count = count;
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) { this.id = id; }


        public String getNameFile() { return nameFile; }
        public void setNameFile(String nameFile) {
            this.nameFile = nameFile;
        }


        public Integer getUser() {
            return user;
        }
        public void setUser(Integer user) {
            this.user = user;
        }


        public String getType() { return type; }
        public void setType(String type) { this.type = type; }


        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }


        public byte[] getFile() { return file; }
        public void setFile(byte[] file) {
                this.file = file;
        }


        public String getInfo() { return info; }
        public void setInfo(String info) {
                this.info = info;
        }


        public Integer getCount() { return count; }
        public void setCount(Integer count) {
                this.count = count;
        }


        public Date getData() { return data; }
        public void setData(Date data) { this.data = data; }

}

