//package ua.knd11.util;
//
//import ua.knd11.model.User;
//
//import java.sql.*;
//import java.util.*;
//
//public class UserDB implements List<User> { // TODO: Класс для имплементации листа с внутренней интеграции с дб
//    private static final String DATABASE_URL = Objects.requireNonNull(System.getenv("DATABASE_URL"));
//
//    private static final String QUERY_CREATE_USERS_TABLE = """
//            CREATE TABLE IF NOT EXISTS USERS (
//                id INT AUTO_INCREMENT PRIMARY KEY,
//                name VARCHAR(255),
//                surname VARCHAR(255),
//                email VARCHAR(255) UNIQUE,
//                password VARCHAR(255)
//            );
//            """;
//
//    private static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DATABASE_URL);
//    }
//
//    private User mapRow(ResultSet rs) throws SQLException {
//        User u = new User();
//        // u.setId(rs.getInt("id"));
//        u.setName(rs.getString("name"));
//        u.setSurname(rs.getString("surname"));
//        u.setEmail(rs.getString("email"));
//        u.setPassword(rs.getString("password"));
//        return null;
//    }
//
//    private List<User> fetchAll() {
//        List<User> list = new ArrayList<>();
//        try (Connection conn = getConnection();
//             PreparedStatement ps = conn.prepareStatement(
//                     "SELECT * FROM USERS ORDER BY id")) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) list.add(mapRow(rs));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }
//
//    public UserDB() {
//        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(QUERY_CREATE_USERS_TABLE);
//        } catch (SQLException e) {
//            System.err.println("Database initialization failed: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int size() {
//        final String query_str = "SELECT COUNT(*) FROM USERS";
//        try (Connection con = getConnection(); Statement st = con.createStatement()) {
//            ResultSet rs = st.executeQuery(query_str);
//            return rs.next() ? rs.getInt(1) : 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return size() == 0;
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return indexOf(0) >= 0;
//    }
//
//    @Override
//    public boolean containsAll(Collection<?> c) {
//        for (Object o : c) if (!contains(o)) return false;
//        return true;
//    }
//
//    @Override
//    public int indexOf(Object o) {
//        if (!(o instanceof User target)) return -1;
//        List<User> all = fetchAll();
//        for (int i = 0; i < all.size(); i++) {
//            if (all.get(i).getId() == target.getId()) return i;
//        }
//        return -1;
//    }
//
//    @Override
//    public int lastIndexOf(Object o) {
//        return 0;
//    }
//
//    @Override
//    public Iterator<User> iterator() {
//        return null;
//    }
//
//    @Override
//    public Object[] toArray() {
//        return new Object[0];
//    }
//
//    @Override
//    public <T> T[] toArray(T[] a) {
//        return null;
//    }
//
//    @Override
//    public boolean add(User user) {
//        return false;
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        return false;
//    }
//
//    @Override
//    public boolean addAll(Collection<? extends User> c) {
//        return false;
//    }
//
//    @Override
//    public boolean addAll(int index, Collection<? extends User> c) {
//        return false;
//    }
//
//    @Override
//    public boolean removeAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public User get(int index) {
//        return null;
//    }
//
//    @Override
//    public User set(int index, User element) {
//        return null;
//    }
//
//    @Override
//    public void add(int index, User element) {
//
//    }
//
//    @Override
//    public User remove(int index) {
//        return null;
//    }
//
//    @Override
//    public ListIterator<User> listIterator() {
//        return null;
//    }
//
//    @Override
//    public ListIterator<User> listIterator(int index) {
//        return null;
//    }
//
//    @Override
//    public List<User> subList(int fromIndex, int toIndex) {
//        return List.of();
//    }
//}
