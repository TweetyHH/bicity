package de.flopl.bicity.uuid;

import java.util.UUID;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class GuidVerticle extends Verticle {

	@Override
	public void start() {
		HttpServer httpServer = vertx.createHttpServer();

		RouteMatcher guidMatcher = new RouteMatcher();
		guidMatcher.all("/service/guid", new Handler<HttpServerRequest>() {

			@Override
			public void handle(HttpServerRequest request) {
				String uuid = UUID.randomUUID().toString();
				JsonObject json = new JsonObject();
				String location = request.absoluteURI().toString();
				json.putString("hsfguid", uuid);
				json.putString("location", location);
				request.response().end(json.toString());
			}
		});

		guidMatcher.noMatch(new Handler<HttpServerRequest>() {

			@Override
			public void handle(HttpServerRequest request) {
				request.response().end("Failure");
			}
		});

		httpServer.requestHandler(guidMatcher);
		httpServer.listen(8080, "localhost");

	}
}
