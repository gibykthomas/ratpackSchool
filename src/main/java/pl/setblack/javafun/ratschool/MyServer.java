package pl.setblack.javafun.ratschool;

import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.http.client.HttpClient;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfigBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class MyServer {

    public static void main(String[] args) throws Exception {
       new MyServer().start();
    }



    private void start() throws Exception{
        RatpackServer.start(server ->
                server
                        .serverConfig(
                                this::config)
                        .handlers(chain -> chain
                                .prefix("fibb", add ->add
                                        .get(":n", this::fibb)
                                )

                        )
        );
    }

    private void fibb(Context context) throws URISyntaxException {
        final long n = Long.parseLong(context.getPathTokens().get("n"));
       // System.out.println("called for:" + n);
        if ( n < 2) {
            context.render("1");
        } else {
            HttpClient httpClient = context.get(HttpClient.class);
            Promise<Long> fibb1 = httpClient.get(new URI("http://localhost:8080/fibb/" + (n - 1)))
                    .map(response -> Long.parseLong(response.getBody().getText())).fork();
            Promise<Long> fibb2 = httpClient.get(new URI("http://localhost:8080/fibb/" + (n - 2)))
                    .map(response -> Long.parseLong(response.getBody().getText())).fork();
            Promise<Long> result = fibb1.flatMap( n1 -> fibb2.map( n2-> n1+n2));

            context.render(result.map(String::valueOf));
        }

    }

    private ServerConfigBuilder config(ServerConfigBuilder cfg) {
        return cfg
                .development(true)
                .port(8080)
                .threads(1);
    }



    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
