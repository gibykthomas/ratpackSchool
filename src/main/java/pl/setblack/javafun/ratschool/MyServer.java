package pl.setblack.javafun.ratschool;

import ratpack.server.RatpackServer;

public class MyServer {
    public static void main(String[] args) throws Exception {
        RatpackServer.start(server ->
                server.handlers(chain -> chain
                        .get(ctx -> ctx.render("Hello " + ctx.get(String.class)))
                        .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                )
        );
    }
}
