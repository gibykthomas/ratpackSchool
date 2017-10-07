package pl.setblack.javafun.ratschool;

import ratpack.handling.Context;
import ratpack.server.RatpackServer;

import java.util.concurrent.atomic.AtomicLong;

public class MyServer {

    private final AtomicLong cnt = new AtomicLong(0);




    public static void main(String[] args) throws Exception {
       new MyServer().start();
    }

    private void start() throws Exception{
        RatpackServer.start(server ->
                server
                        .serverConfig(cfg->cfg.development(true))
                        .handlers(chain -> chain
                                .prefix("add", add ->add
                                        .get(":n", this::add)
                                )
                                .prefix("counter", counter ->
                                        counter
                                                .post("inc", this::inc)
                                                .post("dec", this::dec)
                                )
                        )
        );
    }



    private void add(Context ctx) {
        final long n = Long.parseLong(ctx.getPathTokens().get("n"));
        ctx.render(String.valueOf(n+1));
    }

    private void inc(Context ctx) {
        ctx.render(String.valueOf(cnt.addAndGet(1)));
    }

    private void dec(Context ctx) {
        ctx.render(String.valueOf(cnt.addAndGet(-1)));
    }
}
