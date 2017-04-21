package geostore.dao;

import geostore.model.GeostoreItem;
import mapper.GeostoreMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by endamccormack on 21/04/2017.
 */

@RegisterMapper(GeostoreMapper.class)
public interface GeostoreDao {

    @SqlQuery("select \"local-authority-code\", \"official-name\", ST_AsGeoJSON(wkb_geometry) as geojs from uk_local_authorities u\n" +
            "        INNER JOIN ogrgeojson o on o.ctyua14nm LIKE u.\"alt-name-1\"\n" +
            "        where st_contains(wkb_geometry, ST_GeomFromText(:point, 4326)) LIMIT 1")
    GeostoreItem getGeostoreItem(@Bind("point") String point);
}
