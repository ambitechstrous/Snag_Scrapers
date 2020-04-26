package snag.helpers;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Simple Helper class to shorten log instantiation
 */
public interface Log {
    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }
}
