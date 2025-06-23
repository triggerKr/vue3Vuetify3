@Service
public class UserService {

    private final SqlSessionTemplate db1Template;

    public UserService(@Qualifier("db1SqlSessionTemplate") SqlSessionTemplate db1Template) {
        this.db1Template = db1Template;
    }

    public UserVO getUserById(String id) {
        return db1Template.selectOne("UserMapper.selectUserById", id);
    }
}
