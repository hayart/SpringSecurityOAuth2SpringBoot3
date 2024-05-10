package am.developer.outh.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OauthClientDetail")
public class OauthClientDetail {
    @Id
    @Column(name = "client_id")
    private String id;

    @Column(name = "client_secret")
    private String secret;

    @Column(name = "name")
    private String name;

    @Column(name = "roles")
    private String roles;

    public OauthClientDetail() {
        // No-argument constructor defined explicitly is good practice when working with JPA entities
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
