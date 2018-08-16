package fileManager.app.models;


import javax.persistence.*;

@Entity
@Table(name = "new_file")
public class UploadFile {

        private Integer id;
        private String fileName;
        private User user;

        public UploadFile() {}

        public UploadFile(String fileName) { this.fileName = fileName; }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = true, updatable = true)
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) { this.id = id; }


        @Basic
        @Column(name = "fileName", nullable = false, length = 50)
        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }



        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }


}

