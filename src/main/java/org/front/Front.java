package org.front;

import org.front.http.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Front {

    private static final Logger log = LoggerFactory.getLogger(Front.class);

    private Front() {
    }

    public static void main(String[] args) {

        log.info("Starting Front");

        Configuration configuration = parseConfiguration(args);

        if (configuration != null) {

            log.info(String.format("Binding Address : %s port : %s", configuration.getBindingAddress(), configuration.getBindingPort()));

            Server server = new Server();
            server.listen(configuration.getBindingAddress(), configuration.getBindingPort());

            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        }
    }

    private static Configuration parseConfiguration(String[] args) {

        Configuration configuration = null;

        try {
            configuration = ConfigurationParser.getInstance().parse(args);
        } catch (IllegalArgumentException e) {
            ConfigurationParser.getInstance().printUsage();
        }

        return configuration;
    }
}
