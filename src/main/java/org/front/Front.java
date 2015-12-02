package org.front;

import org.front.http.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Front {

    private static final Logger log = LoggerFactory.getLogger(Front.class);

    public static void main(String[] args) {
        log.info("Starting Front");

        Server server = new Server();
        server.listen("127.0.0.1", 8888);

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
