package helpers;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * Simple Helper class to shorten log instantiation
 */
public interface Log {
    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }
}
