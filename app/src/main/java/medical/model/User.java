package medical.model;

public class User {

    //{"email":"mix7reload@gmail.com ","id":"0EaBsE2IitYHgz52mNOXmWp4Jey2","nombre":"Edgar Andres Angrino","telefono":31555555555}

    private String email = "";
    private String id = "";
    private String nombre = "";
    private int telefono = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }
}