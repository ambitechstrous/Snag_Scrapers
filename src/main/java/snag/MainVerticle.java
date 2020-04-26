package snag;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import snag.helpers.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import snag.utils.NotificationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainVerticle extends AbstractVerticle implements Log {

    private static final String s_query = "query";

    public static void main(final String... args) {
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }

    @Override
    public void start() {
        final ScraperVerticle scraperVerticle = new ScraperVerticle();
        vertx.deployVerticle(
                scraperVerticle,
                new DeploymentOptions().setWorker(true));

        final HttpServer server = vertx.createHttpServer()
                .requestHandler(this::handleRequest);

        server.listen(8080, res -> {
            if (res.failed()) {
                final LocalDateTime currentTime = LocalDateTime.now();
                final String errMsg = formatErrorMessage(currentTime);
                logger().error("Error processing request", res.cause());
                NotificationUtils.notifyOfFailure(MainVerticle.class.getName(), errMsg);
            }
        });
    }

    private void handleRequest(final HttpServerRequest request) {
        final String query = request.getParam(s_query);
        vertx.eventBus().send(ScraperVerticle.ADDRESS, query);
    }

    private String formatErrorMessage(final LocalDateTime timeStamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final String tsString = formatter.format(timeStamp);
        return String.format("Request on %s returned failure response", tsString);
    }
}
