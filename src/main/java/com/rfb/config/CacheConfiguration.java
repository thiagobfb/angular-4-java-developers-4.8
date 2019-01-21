package com.rfb.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.rfb.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.rfb.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.rfb.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.rfb.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.rfb.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbLocation.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbLocation.class.getName() + ".rfbEvents", jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbEvent.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbEvent.class.getName() + ".rfbEventAttendances", jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbEventAttendance.class.getName(), jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbEventAttendance.class.getName() + ".rvbEvents", jcacheConfiguration);
            cm.createCache(com.rfb.domain.RfbEventAttendance.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.rfb.domain.User.class.getName() + ".rfbEventAttendances", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
