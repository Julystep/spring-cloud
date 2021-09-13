package com.boomshair.mainentrance.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private URI location = URI.create("/");

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    /**
     * Creates a new instance with location of "/"
     */
    public AuthenticationSuccessHandler() {
    }

    /**
     * Creates a new instance with the specified location
     * @param location the location to redirect if the no request is cached in
     * {@link #setRequestCache(ServerRequestCache)}
     */
    public AuthenticationSuccessHandler(String location) {
        this.location = URI.create(location);
    }

    /**
     * Sets the {@link ServerRequestCache} used to redirect to. Default is
     * {@link WebSessionServerRequestCache}.
     * @param requestCache the cache to use
     */
    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.requestCache = requestCache;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        Map<String, Object> objectMap =  Objects.requireNonNull(exchange.getSession().block()).getAttributes();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        return this.requestCache.getRedirectUri(exchange).defaultIfEmpty(this.location)
                .flatMap((location) -> this.redirectStrategy.sendRedirect(exchange, location));
    }

    /**
     * Where the user is redirected to upon authentication success
     * @param location the location to redirect to. The default is "/"
     */
    public void setLocation(URI location) {
        Assert.notNull(location, "location cannot be null");
        this.location = location;
    }

    /**
     * The RedirectStrategy to use.
     * @param redirectStrategy the strategy to use. Default is DefaultRedirectStrategy.
     */
    public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
        Assert.notNull(redirectStrategy, "redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }

}
