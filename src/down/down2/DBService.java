package src.down.down2;

@Service
public class DbService {

    @Autowired
    @Qualifier("db1DataSource")
    private DataSource db1;

    @Autowired
    @Qualifier("db2DataSource")
    private DataSource db2;

    public void run() throws SQLException {
        try (Connection conn1 = db1.getConnection()) {
            System.out.println("DB1 접속 성공: " + conn1.getMetaData().getURL());
        }

        try (Connection conn2 = db2.getConnection()) {
            System.out.println("DB2 접속 성공: " + conn2.getMetaData().getURL());
        }
    }
}
