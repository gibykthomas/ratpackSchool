package pl.setblack.javafun.ratschool;

import org.junit.jupiter.api.Test;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.jupiter.api.Assertions.*;

class MyServerTest {

    @Test
    public void testFibb1() throws Exception {
        EmbeddedApp.fromServer(
            new MyServer().start()
        ).test( testHttpClient -> {
            final String response = testHttpClient.get("/fibb/1").getBody().getText();
            assertEquals(1L, Long.parseLong(response));
        } );
    }

    @Test
    public void testFibb5() throws Exception {
        EmbeddedApp.fromServer(
                new MyServer().start()
        ).test( testHttpClient -> {
            final String response = testHttpClient.get("/fibb/5").getBody().getText();
            assertEquals(8L, Long.parseLong(response));
        } );
    }

}