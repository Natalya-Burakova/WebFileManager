package fileManager.app.models;


import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "new_file")
public class UploadFile {

        private Integer id;
        private String urlFile;
        private User user;
        private byte[] file;

        public UploadFile() {}

        public UploadFile(String urlFile, User user, byte[] file) {
                this.urlFile = urlFile;
                this.user = user;
                this.file= file;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = true, updatable = true)
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) { this.id = id; }


        @Basic
        @Column(name = "url_file", nullable = false, length = 50)
        public String getUrlFile() {
            return urlFile;
        }
        public void setUrlFile(String urlFile) {
            this.urlFile = urlFile;
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
        @Column(name = "file", columnDefinition="bytea")
        public byte[] getFile() {
                return file;
        }
        public void setFile(byte[] file) {
                this.file = file;
        }

}

