package io.yamyamiya.telegram.bot.service.password;

public class Password {
    private final String rawPassword;
    private final String encryptedPassword;

    public Password(String rawPassword, String encryptedPassword) {
        this.rawPassword = rawPassword;
        this.encryptedPassword = encryptedPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    @Override
    public String toString() {
        return "Password{" +
                "rawPassword='" + rawPassword + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
