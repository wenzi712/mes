package com.qcadoo.mes.model.search;

import java.util.List;
import java.util.Map;

import com.qcadoo.mes.api.Entity;

/**
 * ResultSet contains list of entities, counted aggregations, total number of entities and the criteria used for produce this
 * result set.
 * 
 * @apiviz.owns com.qcadoo.mes.core.data.beans.Entity
 * @apiviz.has com.qcadoo.mes.core.data.search.SearchCriteria
 */
public interface SearchResult {

    List<Entity> getEntities();

    Map<String, Integer> getAggregations();

    SearchCriteria getCriteria();

    int getTotalNumberOfEntities();

}
