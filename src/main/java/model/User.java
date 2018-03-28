package model;

import java.util.List;

public class User {

    private Long id;
    private String username;
    private String password;
    private List<Role> roles;
    private List<Right> rights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    public void addRights(List<Right> rights) {
        if (this.rights == null)
            this.rights = rights;
        else
            this.rights.addAll(rights);
    }

    public boolean hasRoleOf(String roleName) {
        for (Role role : roles) {
            if (role.getRole().equals(roleName))
                return true;
        }
        return false;
    }


}
