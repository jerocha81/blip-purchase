package pt.blip.blippurchaseservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.dropwizard.Configuration;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import pt.blip.blippurchaseapi.client.BlipPurchaseApi;
import pt.blip.blippurchaseapi.client.BlipPurchaseApiFactory;
import pt.blip.blippurchaseapi.exception.BlipPurchaseApiException;
import pt.blip.blippurchaseservice.exception.BlipPurchaseException;
import pt.blip.blippurchaseservice.util.BlipPurchaseProperties;

/**
 * Blip Purchase Configuration class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseConfiguration extends Configuration {
    private final static Log log = LogFactory.getLog(BlipPurchaseConfiguration.class);
    private BlipPurchaseApi blipPurchaseApi;

    public BlipPurchaseConfiguration() throws BlipPurchaseException {
        super();
    }

    public BlipPurchaseApi getInstance() throws BlipPurchaseException {
        this.init();
        return this.blipPurchaseApi;
    }

    private void init() throws BlipPurchaseException {
        // configurations
        String appHost = BlipPurchaseProperties.APP_HOST;
        if (appHost == null || appHost.isEmpty()) {
            throw new BlipPurchaseException("Missing application host property value!");
        }
        String appPort = BlipPurchaseProperties.APP_PORT;
        if (appPort == null || appPort.isEmpty()) {
            throw new BlipPurchaseException("Missing application port property value!");
        }
        String adminHost = BlipPurchaseProperties.ADMIN_HOST;
        if (adminHost == null || adminHost.isEmpty()) {
            throw new BlipPurchaseException("Missing admin host property value!");
        }
        String adminPort = BlipPurchaseProperties.ADMIN_PORT;
        if (adminPort == null || adminPort.isEmpty()) {
            throw new BlipPurchaseException("Missing admin port property value!");
        }
        String dbUri = BlipPurchaseProperties.DB_URI;
        if (dbUri == null || dbUri.isEmpty()) {
            throw new BlipPurchaseException("Missing database URI property value!");
        }
        String dbName = BlipPurchaseProperties.DB_NAME;
        if (dbName == null || dbName.isEmpty()) {
            throw new BlipPurchaseException("Missing database name property value!");
        }
        // String dbUsername = BlipPurchaseProperties.DB_USERNAME;
        // if (dbUsername == null || dbUsername.isEmpty()) {
        // throw new BlipPurchaseException("Missing database username property value!");
        // }
        // String dbPassword = BlipPurchaseProperties.DB_PASSWORD;
        // if (dbPassword == null || dbPassword.isEmpty()) {
        // throw new BlipPurchaseException("Missing database password property value!");
        // }

        // settings for built-in Jetty server
        DefaultServerFactory serverFactory = (DefaultServerFactory) this.getServerFactory();
        HttpConnectorFactory httpConnector = (HttpConnectorFactory) serverFactory.getApplicationConnectors().get(0);
        httpConnector.setBindHost(appHost);
        httpConnector.setPort(Integer.valueOf(appPort));
        HttpConnectorFactory adminConnector = (HttpConnectorFactory) serverFactory.getAdminConnectors().get(0);
        adminConnector.setBindHost(adminHost);
        adminConnector.setPort(Integer.valueOf(adminPort));

        // get Java API instance
        try {
            this.blipPurchaseApi = BlipPurchaseApiFactory.getInstance(dbUri, dbName/* , dbUser, dbPassword */);
        } catch (BlipPurchaseApiException e) {
            throw new BlipPurchaseException("Error creating an instance of BlipPurchaseApi!", e);
        }

        log.debug("Jetty server settings: {appHost: " + appHost + ", appPort: " + appPort + ", adminHost: " + adminHost + ", adminPort: " + adminPort + "}");
    }
}
