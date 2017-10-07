package pl.setblack.javafun.ratschool;

import ratpack.server.RatpackServer;

import java.util.concurrent.atomic.AtomicLong;

public class MyServer {

    private static final AtomicLong cnt = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server ->
                server
                        .serverConfig(cfg->cfg.development(true))
                .handlers(chain -> chain
                        .prefix("add", add ->add
                            .get(":n",
                                ctx -> {
                                        final long n = Long.parseLong(ctx.getPathTokens().get("n"));
                                        ctx.render(String.valueOf(n+1));
                                })
                            )
                        .prefix("counter", counter ->
                                counter.post("inc", ctx ->
                                    ctx.render(String.valueOf(cnt.addAndGet(1)))
                                ))
                )
        );
    }
}
