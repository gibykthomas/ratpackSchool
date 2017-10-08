package pl.setblack.javafun.ratschool.lifelog;

import io.vavr.collection.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import pl.setblack.ratschool.lifelog.public_.Sequences;
import pl.setblack.ratschool.lifelog.public_.tables.Lifelog;
import pl.setblack.ratschool.lifelog.public_.tables.records.LifelogRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Persistence {

    private final Connection dbConnection;

    public Persistence() {
        String userName = "sa";
        String password = "";
        String url = "jdbc:h2:~/lifeLog";
        try {
            this.dbConnection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    List<Item> getAll() {
        return List.ofAll(DSL.using(dbConnection).select(

        ).from(Lifelog.LIFELOG).fetch().map(
                log -> {
                    LifelogRecord record = (LifelogRecord) log;
                    return new Item(
                            record.getId(),
                            record.getPoints(),
                            record.getInfo(),
                            record.getAtime().toLocalDateTime());
                }
        ));
    }

    void add(ItemIn log) {
        DSLContext create = DSL.using(dbConnection, SQLDialect.H2);
        create.transaction(txConf ->
                DSL.using(txConf).insertInto(Lifelog.LIFELOG)
                        .values(
                                Sequences.LIFELOG_ID.nextval(),
                                log.points,
                                log.info,
                                DSL.currentTimestamp()).execute());

    }

}
