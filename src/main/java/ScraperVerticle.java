import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public class ScraperVerticle extends AbstractVerticle {

    public static String ADDRESS = ScraperVerticle.class.getName();

    @Override
    public void start() {
        vertx.eventBus().consumer(ADDRESS, this::consumeMessage);
    }

    private void consumeMessage(final Message<Object> message) {

    }
}
