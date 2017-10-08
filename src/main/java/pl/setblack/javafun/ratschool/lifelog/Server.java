package pl.setblack.javafun.ratschool.lifelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.vavr.jackson.datatype.VavrModule;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfigBuilder;

public class Server {

    private final Persistence persistence = new Persistence();

    public Server() {

    }

    public static void main(String[] args) throws Exception {
        new Server().start();
    }

    RatpackServer start() throws Exception {
        return RatpackServer.start(server ->
                server
                        .serverConfig(
                                this::config)
                        .registryOf(reg -> reg.add(configureMapping()))
                        .handlers(chain -> chain
                                .prefix("life", life ->
                                        life.all(all -> all.byMethod(m ->
                                            m
                                                .post(this::addLog)
                                                .get(this::getLifeLog)
                                        ))
                                )
                        )
        );
    }

    private void addLog(Context context) {
        context.parse(ItemIn.class).then(item -> {
            Blocking.op(() -> persistence.add(item))
                    .then(() -> context.render("ok"));
        });
    }

    private void getLifeLog(Context context) {
        Blocking.get(() -> persistence.getAll())
                .then( list -> context.render(Jackson.json(list)));
    }

    private ServerConfigBuilder config(ServerConfigBuilder cfg) {
        return cfg
                .development(true)
                .port(8080)
                .threads(1);
    }

    private static ObjectMapper configureMapping() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new VavrModule());
    }
}
