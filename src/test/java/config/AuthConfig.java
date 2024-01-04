package config;

import org.aeonbits.owner.Config;
@Config.Sources({
        "classpath:auth.properties"
})

public interface AuthConfig extends Config {
    @Key("auth_key")
    String authKey();
}
