package com.telesoftas.onboarding.app;

import com.telesoftas.onboarding.app.database.DatabaseConnection;
import com.telesoftas.onboarding.app.factory.ValidatorFactory;
import com.telesoftas.onboarding.app.filter.AuthenticationFilter;
import com.telesoftas.onboarding.app.model.Version;
import com.telesoftas.onboarding.app.service.*;
import com.telesoftas.onboarding.app.servlet.BookServlet;
import com.telesoftas.onboarding.app.servlet.LibrarianServlet;
import com.telesoftas.onboarding.app.servlet.LoginServlet;
import com.telesoftas.onboarding.app.servlet.StatusServlet;
import com.telesoftas.onboarding.domain.librarian.login.LibrarianFindByEmail;
import com.telesoftas.onboarding.domain.librarian.login.Login;
import com.telesoftas.onboarding.domain.librarian.login.SingleLogin;
import com.telesoftas.onboarding.domain.librarian.password.BcryptPassword;
import com.telesoftas.onboarding.domain.librarian.password.PasswordHasher;
import com.telesoftas.onboarding.domain.librarian.registration.*;
import com.telesoftas.onboarding.domain.librarian.token.JwtClock;
import com.telesoftas.onboarding.domain.librarian.token.JwtToken;
import com.telesoftas.onboarding.domain.librarian.token.TokenGenerator;
import com.telesoftas.onboarding.domain.librarian.validator.CredentialModelValidator;
import com.telesoftas.onboarding.domain.librarian.validator.LibrarianModelValidator;
import com.telesoftas.onboarding.persistence.librarian.repository.LibrarianFindByEmailMySQL;
import com.telesoftas.onboarding.persistence.librarian.repository.MySQLRegistration;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.stream.JsonGenerator;
import javax.validation.Validator;
import java.net.InetSocketAddress;
import java.security.Key;
import java.sql.Connection;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.util.Collections.singletonMap;

@Log
public class Main {

    public static void main(String[] args) throws Exception {
        final Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties"));
        String version = properties.getProperty("version");
        String databaseUrl = properties.getProperty("databaseUrl");
        final String privateKey = properties.getProperty("privateKey");
        final long tokenExpirationLimitMinutes = Long.parseLong(properties.getProperty("tokenExpiration"));


        Server server = new Server(new InetSocketAddress("127.0.0.1", 8080));
        try (Connection connection = DatabaseConnection.initializeDatabaseConnection(databaseUrl)) {

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            context.addServlet(new ServletHolder(new StatusServlet(new Version(version))), "/status");


            /**
             * declaration/initialization of classes and their construction parameters
             * Injection is also done here
             * */
            Map<String, ?> config = singletonMap(JsonGenerator.PRETTY_PRINTING, true);
            JsonBuilderFactory factory = Json.createBuilderFactory(config);

            MySQLRegistration storageMysql = new MySQLRegistration(connection);

            Validator validator = ValidatorFactory.getValidator();
            LibrarianApiModelValidator modelValidator = new LibrarianApiModelValidator(validator);

            LibrarianApiModelMapper librarianApiModelMapper = new LibrarianApiModelMapper(modelValidator);

            BcryptPassword bcryptPassword = new BcryptPassword();
            ResponseBuilder responseBuilder = new ResponseBuilder(factory);
            LoginResponseBuilder loginResponseBuilder = new LoginResponseBuilder(factory);

            Registration registration = buildRegistrationContext(storageMysql, storageMysql, bcryptPassword);


            LoginApiModelValidator loginValidator = new LoginApiModelValidator(validator);
            LoginApiModelMapper loginApiModelMapper = new LoginApiModelMapper(loginValidator);

            LibrarianFindByEmail librarianFindByEmailMySQL = new LibrarianFindByEmailMySQL(connection);

            Key key = Keys.hmacShaKeyFor(privateKey.getBytes());
            JwtClock jwtClock = new JwtClock(Clock.systemDefaultZone());
            JwtToken jwtToken = new JwtToken(key, Duration.ofMinutes(tokenExpirationLimitMinutes), jwtClock);

            TokenExtractor bearerTokenExtractor = new BearerTokenExtractor();

            /**
             * Servlet declaration and instance creation
             * */
            context.addServlet(new ServletHolder(new LibrarianServlet(librarianApiModelMapper, responseBuilder, registration)), "/librarians");
            context.addServlet(new ServletHolder(new LoginServlet(loginApiModelMapper,
                    loginResponseBuilder,
                    buildLibrarianLoginContext(
                        validator, librarianFindByEmailMySQL, jwtToken, bcryptPassword)))
                , "/accessTokens");

            context.addServlet(new ServletHolder(new BookServlet()), "/books");

            context.addFilter(new FilterHolder(new AuthenticationFilter(jwtToken, responseBuilder, bearerTokenExtractor)), "/books/*", null);

            server.setHandler(context);


            server.start();
            server.join();


        }
    }


    private static Registration buildRegistrationContext(LibrarianEmailExistence emailExistence, Storage storage, BcryptPassword bcryptPassword) {
        LibrarianModelValidator modelValidator = new LibrarianModelValidator(ValidatorFactory.getValidatorWithUniqueEmailContext(emailExistence));

        return new SingleLibrarianRegistration(storage,
            buildLibrarianRegistrationProcessor(bcryptPassword), modelValidator);
    }

    private static Login buildLibrarianLoginContext(Validator validator, LibrarianFindByEmail storage,
                                                    TokenGenerator jwtToken, BcryptPassword bcryptPassword) {

        CredentialModelValidator credentialModelValidator = new CredentialModelValidator(validator);

        return new SingleLogin(credentialModelValidator, storage, bcryptPassword, jwtToken);
    }


    private static List<RegistrationProcessor> buildLibrarianRegistrationProcessor(PasswordHasher passwordHasher) {
        List<RegistrationProcessor> processors = new ArrayList<>();
        processors.add(new CreatedDateRegistrationProcessor(Clock.systemDefaultZone()));
        processors.add(new PasswordHashRegistrationProcessor(passwordHasher));

        return processors;
    }


}
