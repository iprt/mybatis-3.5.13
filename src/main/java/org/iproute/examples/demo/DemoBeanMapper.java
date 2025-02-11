package org.iproute.examples.demo;

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
}
