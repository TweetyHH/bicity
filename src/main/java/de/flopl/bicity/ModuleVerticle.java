package de.flopl.bicity;

import org.vertx.java.platform.Verticle;

import de.flopl.bicity.logging.LoggingVerticle;
import de.flopl.bicity.ping.PingVerticle;
import de.flopl.bicity.uuid.GuidVerticle;

public class ModuleVerticle extends Verticle {

	@Override
	public void start() {
		container.deployVerticle(GuidVerticle.class.getName());
		container.deployVerticle(PingVerticle.class.getName());
		container.deployVerticle(LoggingVerticle.class.getName());
	}

}
