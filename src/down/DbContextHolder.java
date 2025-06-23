package src.down;
// 현재 사용할 DB를 
// ThreadLocal에 저장하고, 필요할 때마다 가져올 수 있도록 하는 클래스
// 이 클래스는 데이터베이스 연결을 관리하는 데 사용됩니다.
public class DbContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setCurrentDb(String dbKey) {
        contextHolder.set(dbKey);
    }

    public static String getCurrentDb() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
