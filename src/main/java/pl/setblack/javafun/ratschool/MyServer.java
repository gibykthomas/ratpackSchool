package pl.setblack.javafun.ratschool;

import ratpack.server.RatpackServer;

public class MyServer {
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
                )
        );
    }
}
