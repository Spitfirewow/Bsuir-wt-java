package entity;

import java.util.Arrays;
import java.util.StringJoiner;

public class User extends Entity{
    private String login;
    private byte[] passwordHash;
    private byte[] passwordSalt;
    private boolean admin;

    public User() {
        super();
    }
    public User(int id, String login, byte[] passwordHash, byte[] passwordSalt, boolean admin) {
        super(id);
        this.login = login;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.admin = admin;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public byte[] getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }
    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return String.format(
                "%s@%h {super:%s, login:'%s', passwordHash:%s, passwordSalt:%s, admin:%b}",
                getClass().getSimpleName(),
                this,
                super.toString(),
                login,
                Arrays.toString(passwordHash),
                Arrays.toString(passwordSalt),
                admin
        );
    }

    @Override
    public int hashCode() {
        final int PRIME_NUMBER = 31;
        int hashCode = super.hashCode();
        hashCode = PRIME_NUMBER * hashCode + (login == null ? 0 : login.hashCode());
        hashCode = PRIME_NUMBER * hashCode + Arrays.hashCode(passwordHash);
        hashCode = PRIME_NUMBER * hashCode + Arrays.hashCode(passwordSalt);
        hashCode = PRIME_NUMBER * hashCode + (admin ? 1 : 0);
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User other = (User) o;
        return login.equals(other.login)
                && Arrays.equals(passwordHash, other.passwordHash)
                && Arrays.equals(passwordSalt, other.passwordSalt)
                && Boolean.compare(admin, other.admin) == 0;
    }

    public int compareTo(User o) {
        return login.compareTo(o.login);
    }
}
