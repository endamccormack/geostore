package mapper;

import geostore.model.GeostoreItem;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by endamccormack on 21/04/2017.
 */
public class GeostoreMapper  implements ResultSetMapper<GeostoreItem>
{
    public GeostoreItem map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new GeostoreItem(resultSet.getString("local-authority-code"), resultSet.getString("official-name"), resultSet.getString("geojs"));
    }

}
