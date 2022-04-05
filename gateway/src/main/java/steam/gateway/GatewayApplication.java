package steam.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> {
                            System.out.println(r);
                            return r.path("/authent/**")
                                    .filters(f -> f.rewritePath("/authent/(?<remains>.*)", "/authent/${remains}")
                                            .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8081/authent");
                        }
                )

                .route( r-> {
                            System.out.println(r);
                            return r.path("/catalogue/**")
                                    .filters(f ->
                                            f.rewritePath("/catalogue/(?<remains>.*)", "/catalogue/${remains}")
                                                    .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8086/catalogue");
                        }
                )
                .route( r-> {
                            System.out.println(r);
                            return r.path("/client/**")
                                    .filters(f ->
                                            f.rewritePath("/client/(?<remains>.*)", "/client/${remains}")
                                                    .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8082/client");
                        }
                )
                .route( r-> {
                            System.out.println(r);
                            return r.path("/fournisseur/**")
                                    .filters(f ->
                                            f.rewritePath("/fournisseur/(?<remains>.*)", "/fournisseur/${remains}")
                                                    .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8083/fournisseur");
                        }
                )
                .route( r-> {
                            System.out.println(r);
                            return r.path("/social/**")
                                    .filters(f ->
                                            f.rewritePath("/social/(?<remains>.*)", "/social/${remains}")
                                                    .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8084/social");
                        }
                )
                .route( r-> {
                            System.out.println(r);
                            return r.path("/vente/**")
                                    .filters(f ->
                                            f.rewritePath("/vente/(?<remains>.*)", "/vente/${remains}")
                                                    .preserveHostHeader()
                                    )
                                    .uri("http://localhost:8085/vente");
                        }
                )
                .build();
    }

}
