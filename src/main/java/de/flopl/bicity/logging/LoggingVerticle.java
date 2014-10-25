package de.flopl.bicity.logging;

import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class LoggingVerticle extends Verticle {

	@Override
	public void start() {
		HttpServer httpServer = vertx.createHttpServer();

		RouteMatcher guidMatcher = new RouteMatcher();
		Handler<HttpServerRequest> handler = new Handler<HttpServerRequest>() {

			@Override
			public void handle(final HttpServerRequest request) {
				final String uuid = request.headers().get("guid");
				final String user = request.headers().get("user");
				request.bodyHandler(new Handler<Buffer>() {
					public void handle(Buffer event) {
						JsonObject jsonObject = new JsonObject(event.toString());
						container.logger().info(jsonObject.toString());
						LoggerFactory
								.getLogger(jsonObject.getString("service"))
								.info(jsonObject.toString());
						request.response().end();
					};

				});
			}
		};
		guidMatcher.put("/service/logging", handler);
		guidMatcher.post("/service/logging", handler);

		httpServer.requestHandler(guidMatcher);
		httpServer.listen(8079, "localhost");

	}
}
