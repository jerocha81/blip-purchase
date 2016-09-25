package pt.blip.blippurchaseservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pt.blip.blippurchaseservice.BlipPurchaseConfiguration;
import pt.blip.blippurchaseservice.resources.BlipPurchaseRestResource;

/**
 * Blip Purchase class
 * 
 * @author Jose Rocha
 */
public class BlipPurchase extends Application<BlipPurchaseConfiguration> {
    public static void main(String[] args) throws Exception {
        new BlipPurchase().run(args);
    }

    @Override
    public void initialize(Bootstrap<BlipPurchaseConfiguration> bootstrap) {

    }

    @Override
    public void run(BlipPurchaseConfiguration config, Environment env) throws Exception {
        env.jersey().register(new BlipPurchaseRestResource(config.getInstance()));
    }
}
