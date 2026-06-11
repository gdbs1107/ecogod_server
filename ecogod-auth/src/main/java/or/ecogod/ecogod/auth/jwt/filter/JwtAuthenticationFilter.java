package or.ecogod.ecogod.auth.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import or.ecogod.ecogod.auth.jwt.JwtTokenProvider;
import or.ecogod.ecogod.auth.jwt.support.BearerTokenExtractor;
import or.ecogod.ecogod.auth.jwt.support.JwtClaims;
import or.ecogod.ecogod.auth.principal.AdminPrincipal;
import or.ecogod.ecogod.auth.principal.AdminPrincipalService;
import or.ecogod.ecogod.common.api.ApiErrorResponseWriter;
import or.ecogod.ecogod.common.exception.CustomException;
import or.ecogod.ecogod.common.exception.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminPrincipalService adminPrincipalService;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            AdminPrincipalService adminPrincipalService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminPrincipalService = adminPrincipalService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtTokenProvider.getHeaderName());

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = BearerTokenExtractor.extract(authorizationHeader);
            JwtClaims claims = jwtTokenProvider.parseAccessToken(accessToken);
            AdminPrincipal principal = adminPrincipalService.loadUserById(claims.adminId());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    principal.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            ApiErrorResponseWriter.write(response, ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException exception) {
            ApiErrorResponseWriter.write(response, ErrorCode.INVALID_TOKEN);
        } catch (CustomException exception) {
            ApiErrorResponseWriter.write(response, exception.getErrorCode());
        }
    }
}
