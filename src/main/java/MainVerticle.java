import helpers.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.spi.logging.LogDelegate;
import utils.NotificationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainVerticle extends AbstractVerticle implements Log {

    private HttpServer server;

    @Override
    public void start() {
        server = vertx.createHttpServer().requestHandler(req -> {
            final String productName = req.getParam("productName");
            //req.bodyHandler(this::sendMessage);
        });

        server.listen(8080, res -> {
            if (res.failed()) {
                final LocalDateTime currentTime = LocalDateTime.now();
                final String errMsg = formatErrorMessage(currentTime);
                logger().error("Error processing request", res.cause());
                NotificationUtils.notifyOfFailure(MainVerticle.class.getName(), errMsg);
            }
        });
    }

    private void sendMessage(final Buffer body) {

    }

    private String formatErrorMessage(final LocalDateTime timeStamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final String tsString = formatter.format(timeStamp);
        return String.format("Request on %s returned failure response", tsString);
    }
}
