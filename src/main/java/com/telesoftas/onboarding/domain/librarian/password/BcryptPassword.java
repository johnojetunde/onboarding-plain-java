package com.telesoftas.onboarding.domain.librarian.password;

import com.telesoftas.onboarding.domain.librarian.exception.PasswordMatchException;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptPassword implements PasswordHasher, PasswordMatcher {

    @Override
    public String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public void match(String plainPassword, String hashedPassword) throws PasswordMatchException {
        if (!BCrypt.checkpw(plainPassword, hashedPassword)) {
            throw new PasswordMatchException("Password is incorrect");
        }
    }
}
