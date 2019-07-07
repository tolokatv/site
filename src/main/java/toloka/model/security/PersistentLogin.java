package toloka.model.security;

import javax.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "persistent_logins")
    public class PersistentLogin {

        @Id
        @Column(name = "series", nullable = false)
        private String series;
        private String username;
        private String token;
        private LocalDateTime last_used;

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LocalDateTime getLast_used() {
            return last_used;
        }

        public void setLast_used(LocalDateTime last_used) {
            this.last_used = last_used;
        }
    }
