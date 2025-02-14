package org.iproute.examples.demo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DemoMapper
 *
 * @author zhuzhenjie
 * @since 3/13/2023
 */
public interface DemoBeanMapper {

    /**
     * Select all map.
     *
     * @param map the map
     * @return the map
     */
    DemoBean selectById(Map<String, Object> map);

    /**
     * Select all list.
     *
     * @return the list
     */
    List<DemoBean> selectAll();


    /**
     * Inserts a new record into the database based on the provided DemoBean object.
     *
     * @param demoBean the DemoBean object containing the data to be inserted
     * @return the number of rows affected by the insert operation
     */
    int insert(DemoBean demoBean);

    /**
     * Updates an existing record in the database based on the provided DemoBean object.
     *
     * @param demoBean the DemoBean object containing the data to be updated
     * @return the number of rows affected by the update operation
     */
    int update(DemoBean demoBean);

    /**
     * Deletes a record from the database based on the provided id.
     *
     * @param id the unique identifier of the record to be deleted
     * @return the number of rows affected by the delete operation
     */
    int delete(@Param("id") Long id);

}
