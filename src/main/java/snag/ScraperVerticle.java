package snag;

import snag.helpers.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import snag.utils.NotificationUtils;

public class ScraperVerticle extends AbstractVerticle implements Log {

    private static final String s_emailSubject = "ERROR: Scraping Failed";
    public static final String ADDRESS = ScraperVerticle.class.getName();

    @Override
    public void start() {
        vertx.eventBus().consumer(ADDRESS, this::consumeMessage);
    }

    private void consumeMessage(final Message<String> message) {
        try {
            final String query = message.body();
            runScrapers(query);
        } catch (final Exception e) {
            logger().error("Scraping failed. Verticle will continue running.", e);
            NotificationUtils.notifyOfFailure(s_emailSubject, e.getMessage());
        }
    }

    private void runScrapers(final String query) {
        // TODO Run actual Scrapers
    }
}
