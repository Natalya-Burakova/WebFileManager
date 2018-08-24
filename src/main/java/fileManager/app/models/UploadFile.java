package fileManager.app.models;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "new_file")
public class UploadFile {

        private Integer id;
        private String nameFile;
        private String urlFile;
        private String type;
        private Long size;
        private String info;
        private Boolean status;
        private Date data;
        private User user;
        private byte[] file;
        private Integer count;

        public UploadFile() {}

        public UploadFile(String nameFile) { this.nameFile = nameFile; }

        public UploadFile(String nameFile, String urlFile, User user, byte[] file, String type, Boolean status, Long size, Date data, String info, Integer count) {
                this.nameFile = nameFile;
                this.urlFile = urlFile;
                this.user = user;
                this.file= file;
                this.type = type;
                this.status = status;
                this.size =size;
                this.data = data;
                this.info = info;
                this.count = count;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = true, updatable = true)
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) { this.id = id; }


        @Basic
        @Column(name = "name_file", nullable = false)
        public String getNameFile() { return nameFile; }
        public void setNameFile(String nameFile) {
            this.nameFile = nameFile;
        }



        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }


        @Basic
        @Column(name = "url_file", nullable = false)
        public String getUrlFile() {
                return urlFile;
        }
        public void setUrlFile(String urlFile) {
                this.urlFile = urlFile;
        }


        @Basic
        @Column(name = "type", nullable = false)
        public String getType() { return type; }
        public void setType(String type) {
                this.type = type;
        }


        @Basic
        @Column(name = "status", nullable = false)
        public Boolean getStatus() { return status; }
        public void setStatus(Boolean status) { this.status = status; }

        @Basic
        @Column(name = "size", nullable = false)
        public Long getSize() { return size; }
        public void setSize(Long size) { this.size = size; }


        @Basic
        @Column(name = "file", columnDefinition="bytea")
        public byte[] getFile() { return file; }
        public void setFile(byte[] file) {
                this.file = file;
        }


        @Basic
        @Column(name = "info", nullable = false)
        public String getInfo() { return info; }
        public void setInfo(String info) {
                this.info = info;
        }


        @Basic
        @Column(name = "count", nullable = false)
        public Integer getCount() { return count; }
        public void setCount(Integer count) {
                this.count = count;
        }

        @Basic
        @Column(name = "data", nullable = false)
        public Date getData() { return data; }
        public void setData(Date data) {
                this.data = data;
        }

}

