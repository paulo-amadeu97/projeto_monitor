package factory;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringJoiner;

public class EntityManager {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public void save(Object entity) throws IllegalAccessException, SQLException {
        Class<?> entityClass = entity.getClass();
        String tableName = AnnotationMapper.getTableName(entityClass);

        StringJoiner columns = new StringJoiner(",");
        StringJoiner placeholders = new StringJoiner(",");
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                columns.add(field.getName());
                placeholders.add("?");
            }
        }

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    pstmt.setObject(index++, field.get(entity));
                }
            }
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
