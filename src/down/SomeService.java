package src.down;

@Service
public class SomeService {

    @Autowired
    private SomeRepository someRepository;

    public void doSomething(String userId) {
        try {
            if (userId.startsWith("A")) {
                DbContextHolder.setCurrentDb("db1");
            } else {
                DbContextHolder.setCurrentDb("db2");
            }

            // Repository 사용 → 설정된 DB로 자동 연결됨
            someRepository.findById(1L);

        } finally {
            DbContextHolder.clear(); // 꼭 초기화
        }
    }
}

