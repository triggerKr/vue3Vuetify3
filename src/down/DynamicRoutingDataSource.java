package src.down;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getCurrentDb(); // "db1" 또는 "db2"
    }
}
// 이 클래스는 AbstractRoutingDataSource를 확장하여 현재 사용 중인 데이터베이스를 결정하는 데 사용됩니다.