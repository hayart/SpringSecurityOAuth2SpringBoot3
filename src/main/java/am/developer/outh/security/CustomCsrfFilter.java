package am.developer.outh.security;

import am.developer.outh.model.OauthClientDetail;
import am.developer.outh.repository.OauthClientDetailsRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class CustomCsrfFilter extends OncePerRequestFilter {



    private static final List<String> PERMITTED_PATHS = List.of(
            "/oauth2/token", "/login", "/index.html", "/index");

    private static final String ALLOW_PATH = "/rest/";
    private static final List<String> ROLES_ALLOWED = List.of("ADMIN");

    private final JwtDecoder jwtDecoder;
    private final OauthClientDetailsRepository clientRepository;

    public CustomCsrfFilter(JwtDecoder jwtDecoder,
                            OauthClientDetailsRepository clientRepository) {
        this.jwtDecoder = jwtDecoder;
        this.clientRepository = clientRepository;
    }

    private boolean validateBearerToken(HttpServletRequest request) {
        try {

            String pathInfo = request.getPathInfo();
            String urlPath = request.getServletPath();
            if (pathInfo == null) {
                pathInfo = urlPath;
            }
            if (PERMITTED_PATHS.contains(pathInfo)) {
                return true;
            }
            String bearerToken = request.getHeader("Authorization").split("Bearer ")[1];

            if (!urlPath.startsWith(ALLOW_PATH)) {
                jwtDecoder.decode(bearerToken);
                return true;
            }
            Jwt jwt = jwtDecoder.decode(bearerToken);
            String clientId = (String) jwt.getClaims().get("sub");
            Optional<OauthClientDetail> apiClientOptional = this.clientRepository.findById(clientId);
            if (apiClientOptional.isEmpty()) {
                return false;
            }
            OauthClientDetail client = apiClientOptional.get();
            String[] roles = client.getRoles().split(",");
            if (urlPath.startsWith("ROLE")) {
                for (String role: roles) {
                    if (ROLES_ALLOWED.contains(role)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (!validateBearerToken(request)) {
            // Handle 401 Unauthorized error
            response.setStatus(UNAUTHORIZED.value());
            response.getWriter().write("\n" +
                    "{     \n" +
                    "    \"errorMessages\": [{\n" +
                    "         \"errorCode\":\"UNAUTHORISED\",\n" +
                    "         \"errorLabel\":\"OAuth2 authentication token is missing or invalid or does not have proper authorities\"\n" +
                    "    }]\n" +
                    "}");
            return;
        }
        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }


}

