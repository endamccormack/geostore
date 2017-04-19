package geostore;

import geostore.health.TemplateHealthCheck;
import geostore.resources.GeostoreResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class GeostoreApplication extends Application<GeostoreConfiguration> {

    public static void main(String[] args) throws Exception {
        new GeostoreApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<GeostoreConfiguration> bootstrap) {
        bootstrap.addBundle(new FlywayBundle<GeostoreConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(GeostoreConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

            @Override
            public FlywayFactory getFlywayFactory(GeostoreConfiguration configuration) {
                return configuration.getFlywayFactory();
            }
        });
    }

    @Override
    public void run(GeostoreConfiguration configuration,
                    Environment environment) {
        DataSourceFactory dsFactory = configuration.getDataSourceFactory();
        DataSource dataSource = dsFactory.build(environment.metrics(), "geostore");

        final FlywayFactory factory = new FlywayFactory();
        final Flyway flyway = factory.build(dataSource);

        flyway.baseline();
        flyway.migrate();

        environment.jersey().register(new GeostoreResource());
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
